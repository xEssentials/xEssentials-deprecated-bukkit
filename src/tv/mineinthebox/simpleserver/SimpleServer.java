package tv.mineinthebox.simpleserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;

public class SimpleServer implements Runnable, Server {

	private final int port;
	private final String servername;
	private volatile ServerSocket server;
	private volatile Thread thread;
	
	private volatile Set<Tag> tags = new HashSet<Tag>();
	
	private volatile SortedList<ServerListener> listeners = new SortedList<ServerListener>(new Comparator<ServerListener>() {

		@Override
		public int compare(ServerListener o1, ServerListener o2) {
			Class<?> a = o1.getClass();
			Class<?> b = o2.getClass();
			int ap = 0;
			int bp = 0;
			for(Method method : a.getMethods()) {
				if(method.isAnnotationPresent(ServerEvent.class)) {
					ap += method.getAnnotation(ServerEvent.class).priority().getPriorityLevel();
				}
			}
			for(Method method : b.getMethods()) {
				if(method.isAnnotationPresent(ServerEvent.class)) {
					bp += method.getAnnotation(ServerEvent.class).priority().getPriorityLevel();
				}
			}
			return Integer.valueOf(ap).compareTo(bp);
		}

	});

	/**
	 * creates a server object
	 * 
	 * @param port the port number
	 * @param servername the servername
	 */
	public SimpleServer(int port, String servername) {
		this.port = port;
		this.servername = servername;
	}

	/**
	 * attempt to start the server under a async operation
	 * 
	 * @throws Exception as the server cannot be started due a port is blocked or when the port is already in use
	 */
	public void startServer() throws Exception {
		if(thread == null) {
			this.server = new ServerSocket(port);
			this.thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * attempt to shutdown the server
	 * 
	 * @throws IOException when the thread was interrupted
	 */
	@SuppressWarnings("deprecation")
	public void stopServer() throws IOException {
		if(thread != null) {
			this.server.close();
			this.server = null;
			this.thread.stop();
			this.thread = null;
			tags.clear();
			listeners.clear();
		}
	}

	@Override
	public boolean isRunning() {
		return (this.server != null && this.thread != null);
	}

	/**
	 * registers a listener to fire the events
	 * 
	 * @param listener - the class implementing the listener
	 */
	public void addListener(ServerListener listener) {
		listeners.add(listener);
	}

	/**
	 * removes a current listener
	 * 
	 * @param listener - the class implementing the listener
	 */
	public void removeListener(ServerListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * adds a template tag to the server instance
	 * 
	 * @param tag the tag representing a dynamic or abstract replacement of a piece of content
	 */
	public void addTemplateTag(Tag tag) {
		tags.add(tag);
	}
	
	/**
	 * removes a template tag from the server instance
	 * 
	 * @param tag the tag representing a dynamic or abstract replacement of a piece of content
	 */
	public void removeTemplateTag(Tag tag) {
		tags.remove(tag);
	}
	
	/**
	 * returns a list of template tags for this instance
	 * 
	 * @return Set<Tag>
	 */
	public Set<Tag> getTemplateTags() {
		return Collections.unmodifiableSet(tags);
	}

	private SimpleServerEvent callEvent(SimpleServerEvent event) {
		for(ServerListener listener : listeners) {
			Class<?> clazz = listener.getClass();
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				if(method.isAnnotationPresent(ServerEvent.class)) {
					try {
						method.invoke(listener, event);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return event;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket socket = server.accept();
				
				DataOutputStream response = new DataOutputStream(socket.getOutputStream());
				BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String line = "";

				String[] headerdata = new String[5];
				
				int postindex = -1;
				
				//read data from the clients header in a limited way to push it later inside HttpClient
				while((line = read.readLine()) != null && (line.length() != 0)) {
					
					if(line.startsWith("GET")) {
						headerdata[0] = new String(line);
					} else if(line.startsWith("POST")) {
						headerdata[0] = new String(line);
					} else if(line.startsWith("Host:")) {
						headerdata[1] = new String(line);
					} else if(line.startsWith("User-Agent:")) {
						headerdata[2] = new String(line);
					} else if(line.startsWith("Accept-Language:")) {
						headerdata[3] = new String(line);
					} else if(line.indexOf("Content-Length:") > -1) {
						postindex = new Integer(line.substring(line.indexOf("Content-Length:") + 16, line.length())).intValue();
					}
				}
				
				if(postindex > 0) {
					char[] array = new char[postindex];
					read.read(array, 0, postindex);
					headerdata[4] = new String(array);
				}
				
				boolean isValidHeader = isValidHeader(headerdata);
				
				if(isValidHeader) {
					
					HttpClient client = new HttpClient(headerdata);

					SimpleServerEvent event = null;

					if(client.getUrl().isEmpty() || client.getUrl().equalsIgnoreCase("/")) {
						event = new SimpleServerEvent(client, this, MimeType.MIME_HTML, socket.getInetAddress());
					} else if(client.getUrl().endsWith(".html") || client.getUrl().endsWith(".htm") || client.getUrl().endsWith(".php")) {
						event = new SimpleServerEvent(client, this, MimeType.MIME_HTML, socket.getInetAddress());
					} else if(client.getUrl().endsWith(".jpg") || client.getUrl().endsWith(".gif") || client.getUrl().endsWith(".png")) {
						event = new SimpleServerEvent(client, this, MimeType.MIME_JPEG, socket.getInetAddress());
					} else if(client.getUrl().endsWith(".json")) {
						event = new SimpleServerEvent(client, this, MimeType.MIME_JSON, socket.getInetAddress());
					} else if(client.getUrl().endsWith(".xml")) {
						event = new SimpleServerEvent(client, this, MimeType.MIME_XML, socket.getInetAddress());
					} else {
						event = new SimpleServerEvent(client, this, MimeType.MIME_PLAIN, socket.getInetAddress());
					}
					
					callEvent(event);
					
					if(event.isCancelled() || !event.hasContents()) {
						response.write("HTTP/1.1 404 Not Found\r\n".getBytes());
						response.flush();
					} else {
						response.write("HTTP/1.1 200 OK\r\n".getBytes());
						response.write(("Content-Type: " + event.getMimeType().getMimeType() + "\r\n").getBytes());
						response.write(("Content-Length: " + event.getContents().length + "\n\r").getBytes());
						response.write("\n".getBytes());	
						response.flush();
						response.write(event.getContents());
						response.flush();
					}
				}
				response.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isValidHeader(String[] data) {
		if(data[0] == null) {
			return false;
		} else if(data[1] == null) {
			return false;
		} else if(data[2] == null) {
			return false;
		} else if(data[3] == null) {
			return false;
		}
		return true;
	}

	/**
	 * returns the name of the server
	 * 
	 * @return String
	 */
	@Override
	public String getName() {
		return this.servername;
	}

	/**
	 * returns the port
	 * 
	 * @return int
	 */
	@Override
	public int getPort() {
		return port;
	}
}

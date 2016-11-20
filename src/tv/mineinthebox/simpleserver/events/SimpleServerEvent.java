package tv.mineinthebox.simpleserver.events;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tv.mineinthebox.simpleserver.Content;
import tv.mineinthebox.simpleserver.HttpClient;
import tv.mineinthebox.simpleserver.MimeType;
import tv.mineinthebox.simpleserver.Server;
import tv.mineinthebox.simpleserver.SimpleServer;
import tv.mineinthebox.simpleserver.Tag;

/**
 * The event which modifies the servers result displayed on the clients side
 */

public class SimpleServerEvent implements SimpleEvent {

	private final Server server;
	private final HttpClient client;

	private boolean cancel = false;
	private MimeType mime = MimeType.MIME_HTML;
	private byte[] contents;
	private final InetAddress ip;
	private final List<MimeType> textmime =  new ArrayList<MimeType>() {
		private static final long serialVersionUID = 1L; {
			add(MimeType.MIME_CSS);
			add(MimeType.MIME_HTML);
			add(MimeType.MIME_JAVASCRIPT);
			add(MimeType.MIME_JSON);
			add(MimeType.MIME_PLAIN);
			add(MimeType.MIME_XML);
		}};

		/**
		 * this is the constructor for the SimpleServerEvent</p>
		 * 
		 * note its not recommend to instance it yourself
		 * @param client the http client
		 * @param server the server where this event belongs to
		 * @param mime   the default mime-type
		 * @param adress the ip adress from the client
		 */
		public SimpleServerEvent(HttpClient client, Server server, MimeType mime, InetAddress address) {
			this.client = client;
			this.server = server;
			this.mime = mime;
			this.ip = address;
		}

		/**
		 * returns true if the event came from a get request otherwise false
		 * 
		 * @return boolean
		 */
		@Override
		public boolean isGetRequest() {
			return client.isGet();
		}

		/**
		 * returns true if the event came from a post request otherwise false
		 * 
		 * @return boolean
		 */
		@Override
		public boolean isPostRequest() {
			return client.isPost();
		}

		/**
		 * returns the Server instance of this event
		 * 
		 * @return SimpleServer
		 */
		@Override
		public Server getServer() {
			return server;
		}

		/**
		 * returns the requested url by the client
		 * 
		 * @return String
		 */
		@Override
		public String getUri() {
			return client.getUrl();
		}

		/**
		 * adds content to the existing content
		 * 
		 * @param bytes the bytes to inject on the already existing content
		 * @deprecated 
		 * <p>
		 * (this method could malfunction due the fact it injects new content to the already existing content, its more encouraged to have for each page one listener which listens to the url rather than injecting contents) 
		 * @see SimpleServerEvent#setContent(byte[])
		 * @see SimpleServerEvent#setContent(Content)
		 */
		@Override
		public void addContent(byte[] bytes) {
			byte[] newbytes = Arrays.copyOf(getContents(), (getContents().length+bytes.length));
			int index = 0;
			for(int i = getContents().length; i < newbytes.length; i++) {
				newbytes[i] = bytes[index++]; 
			}
			this.contents = newbytes;
		}

		/**
		 * adds content to the existing content
		 * 
		 * @param content the content to inject on the already existing content
		 * @deprecated (this method could malfunction due the fact it injects new content to the already existing content, its more encouraged to have for each page one listener which listens to the url rather than injecting contents)
		 * <p>
		 * @see SimpleServerEvent#setContent(byte[])
		 * @see SimpleServerEvent#setContent(Content)
		 */
		@Override
		public void addContent(Content content) {
			byte[] newbytes = Arrays.copyOf(getContents(), (getContents().length+content.getContent().getBytes().length));
			int index = 0;
			for(int i = getContents().length; i < newbytes.length; i++) {
				newbytes[i] = content.getContent().getBytes()[index++]; 
			}
			this.contents = newbytes;
		}

		/**
		 * sent a file back to the client, as long the mime type is set correctly
		 * 
		 * @param f the file being converted as content
		 * @see SimpleServerEvent#setContent(byte[])
		 */
		@Override
		public void setContent(File f) {
			try {
				InputStream is = new FileInputStream(f);
				ByteArrayOutputStream ous = new ByteArrayOutputStream();
				int read = 0;

				while((read = is.read()) != -1) {
					ous.write(read);
					ous.flush();
				}
				is.close();
				this.contents = ous.toByteArray();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * sets the content of the event, meaning that the visitor will see it as result on the website
		 * 
		 * @param bytes the bytes from a object or String being sent to the client
		 */
		@Override
		public void setContent(byte[] bytes) {
			this.contents = bytes;
		}

		/**
		 * sets the content of the event, meaning that the visitor will see it as result on the website
		 * 
		 * @param content the content which is a wrapper to easily add text or html syntax to server users for a web page
		 */
		@Override
		public void setContent(Content content) {
			this.contents = content.getContent().getBytes();
		}

		/**
		 * returns the contents in a byte array
		 * 
		 * @return byte[]
		 */
		@Override
		public byte[] getContents() {
			return contents;
		}

		/**
		 * returns true if any contents are written, otherwise false
		 * 
		 * @return boolean
		 */
		@Override
		public boolean hasContents() {
			return (contents != null);
		}

		/**
		 * returns true if the event is cancelled otherwise false
		 * 
		 * @return boolean
		 */
		@Override
		public boolean isCancelled() {
			return cancel;
		}

		/**
		 * if boolean is cancelled the website will return a 404 header to the client unless a other event modifies it
		 * 
		 * @param bol the boolean given in, true means it will be cancelled false means you uncancel the event
		 */
		@Override
		public void setCancelled(boolean bol) {
			this.cancel = bol;
		}

		/**
		 * returns the Mime-Type of the event
		 * 
		 * @return MimeType
		 */
		@Override
		public MimeType getMimeType() {
			return mime;
		}

		/**
		 * sets the Mime-Type of this event
		 * 
		 * @param mime the Mime-Type where it should be changed to
		 */
		@Override
		public void setMimeType(MimeType mime) {
			this.mime = mime;
		}

		/**
		 * returns a map giving the keys as the form names and values as input of the keys, this works for both post and get requests
		 *
		 * @return Map<String, String>
		 */
		@Override
		public Map<String, String> getFormData() {
			return client.getFormParameters();
		}

		/**
		 * returns true if something has been posted or getted by form activity only, every other get request does not count
		 * 
		 * @return boolean
		 */
		@Override
		public boolean hasFormData() {
			return client.hasFormParameters();
		}

		/**
		 * returns the clients header data wrapped in a understandable wrap
		 * 
		 * @return HttpClient
		 */
		@Override
		public HttpClient getClientHeaderData() {
			return client;
		}
		
		/**
		 * returns true if the content has tags else false
		 * 
		 * @return boolean
		 */
		@Override
		public boolean hasTags() {
			if(getAvailableTags().length > 0) {
				return true;
			}
			return false;
		}
		
		/**
		 * returns a list of available tags once when the content has been set this comes in handy when using {@link #setContent(File)} in order to modify small pieces of content
		 *
		 * @return Tag[]
		 */
		@Override
		public Tag[] getAvailableTags() {
			List<Tag> tags = new ArrayList<Tag>();
			for(Tag tag : ((SimpleServer)server).getTemplateTags()) {
				if(tag.containsTag(getContents())) {
					tags.add(tag);
				}
			}
			return tags.toArray(new Tag[tags.size()]);
		}

		/**
		 * returns true if the mime type is not a image, but readable text such as a html document
		 * 
		 * @return boolean
		 */
		@Override
		public boolean isMimeText() {
			return textmime.contains(getMimeType());
		}
		
		/**
		 * returns the InetAddress of the client triggering this event
		 * 
		 * @return InetAddress
		 */
		public InetAddress getInetAddress() {
			return ip;
		}

}

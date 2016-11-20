package tv.mineinthebox.simpleserver.example.events;

import java.io.File;

import tv.mineinthebox.simpleserver.Content;
import tv.mineinthebox.simpleserver.MimeType;
import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;
import tv.mineinthebox.simpleserver.example.Main;

public class AboutListener implements ServerListener {
	
	@ServerEvent
	public void onRenderPage(SimpleServerEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.isGetRequest()) {
			if(e.getUri().equalsIgnoreCase("/about.html")) {
				e.setContent(new Content() {{
					add("<html>");
					add("<title>about page || Simple-Server</title>");
					add("<body>");
					add("<h1>Simple-Server about me page</h1>");
					add("<hr>");
					add("<p><a href=\"/index.html\"/>index</a> || <a href=\"/about.html\"/>about</a> || <a href=\"/contact.html\"/>contact</a></p>");
					add("<img src=\"/image.jpg\" height=\"48\" width=\"64\"/>");
					add("<p></p>");
					add("<p>Hi there, Im xize the actor of this library this is the about page.</p>");
					add("</body>");
					add("</html>");
				}});
			}
		}
	}
	
	@ServerEvent
	public void onImage(SimpleServerEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.isGetRequest()) {
			if(e.getUri().equalsIgnoreCase("/image.jpg")) {
					e.setMimeType(MimeType.MIME_JPEG);
					File f = new File(Main.getResource("Xeph0re3D.jpg"));
					if(f.exists()) {
						e.setContent(f);
					} else {
						System.out.println("Xeph0re3D.jpg not found!");
					}
			}
		}
	}

}

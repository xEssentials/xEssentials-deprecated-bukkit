package tv.mineinthebox.simpleserver.example.events;

import tv.mineinthebox.simpleserver.Content;
import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;

public class IndexListener implements ServerListener {
	
	@ServerEvent
	public void onRenderPage(SimpleServerEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.isGetRequest()) {
			if(e.getUri().equalsIgnoreCase("/index.html")) {
				e.setContent(new Content() {{
					add("<html>");
					add("<title>index page || Simple-Server</title>");
					add("<body>");
					add("<h1>Simple-Server home page</h1>");
					add("<hr>");
					add("<p><a href=\"/index.html\"/>index</a> || <a href=\"/about.html\"/>about</a> || <a href=\"/contact.html\"/>contact</a></p>");
					add("<p></p>");
					add("<p>this is the default page for Simple-Server a library written by Xeph0re AKA xize <a href=\"http://github.com/xize\"/>http://github.com/xize</a></p>");
					add("</body>");
					add("</html>");
				}});
			}
		}
	}

}

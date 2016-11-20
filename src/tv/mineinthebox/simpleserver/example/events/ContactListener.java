package tv.mineinthebox.simpleserver.example.events;

import java.util.Map;

import tv.mineinthebox.simpleserver.Content;
import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;

public class ContactListener implements ServerListener {
	
	@ServerEvent
	public void onRenderPage(SimpleServerEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.isGetRequest()) {
			if(e.getUri().equalsIgnoreCase("/contact.html")) {
				e.setContent(new Content() {{
					add("<html>");
					add("<title>contact page || Simple-Server</title>");
					add("<body>");
					add("<h1>Simple-Server contact page</h1>");
					add("<hr>");
					add("<p><a href=\"/index.html\"/>index</a> || <a href=\"/about.html\"/>about</a> || <a href=\"/contact.html\"/>contact</a></p>");
					add("<p></p>");
					add("<p>this is a fake form this does not work and does not mail:</p>");
					add("<form name=\"contact\" method=\"post\" action=\"\"><br/>");
					add("<input type=\"text\" name=\"name\" value=\"your name\"/><br/>");
					add("<textarea name=\"mail\"></textarea><br/>");
					add("<input type=\"submit\" value=\"sent mail!\"/>");
					add("</form>");
					add("</body>");
					add("</html>");
				}});
			}
		} else if(e.isPostRequest()) {
			if(e.getUri().startsWith("/contact.html")) {
				
				final Map<String, String> value = e.getFormData();
				
				e.setContent(new Content() {{
					add("<html>");
					add("<title>contact page || Simple-Server</title>");
					add("<body>");
					add("<h1>Simple-Server contact page</h1>");
					add("<hr>");
					add("<p><a href=\"/index.html\"/>index</a> || <a href=\"/about.html\"/>about</a> || <a href=\"/contact.html\"/>contact</a></p>");
					add("<p></p>");
					add("<p>mail retrieved and the text you typed was:</p>");
					add("<p>Text field \"name\": " + value.get("name") + " </p>");
					add("<p>Text area \"mail\": " + value.get("mail") + "</p>");
					add("</body>");
					add("</html>");
				}});
			}
		}
	}
}

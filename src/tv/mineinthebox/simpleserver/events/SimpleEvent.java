package tv.mineinthebox.simpleserver.events;

import java.io.File;
import java.net.InetAddress;
import java.util.Map;

import tv.mineinthebox.simpleserver.Content;
import tv.mineinthebox.simpleserver.HttpClient;
import tv.mineinthebox.simpleserver.MimeType;
import tv.mineinthebox.simpleserver.Server;
import tv.mineinthebox.simpleserver.Tag;

public interface SimpleEvent {
	
	public boolean isGetRequest();
	
	public boolean isPostRequest();
	
	public Server getServer();
	
	public String getUri();

	public void addContent(byte[] bytes);
	
	public void addContent(Content content);
	
	public void setContent(File f);
	
	public void setContent(byte[] bytes);
	
	public void setContent(Content content);
	
	public boolean hasContents();
	
	public byte[] getContents();
	
	public boolean isCancelled();
	
	public void setCancelled(boolean bol);
	
	public MimeType getMimeType();
	
	public void setMimeType(MimeType type);
	
	public boolean hasFormData();
	
	public Map<String, String> getFormData();
	
	public HttpClient getClientHeaderData();
	
	public boolean hasTags();
	
	public Tag[] getAvailableTags();
	
	public boolean isMimeText();
	
	public InetAddress getInetAddress();
	
}

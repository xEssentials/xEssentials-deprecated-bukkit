package tv.mineinthebox.simpleserver;

public enum MimeType {

	MIME_HTML("text/html"),
	MIME_PLAIN("text/plain"),
	MIME_XML("application/xml"),
	MIME_JPEG("image/jpeg"),
	MIME_PNG("image/png"),
	MIME_GIF("image/gif"),
	MIME_JSON("application/json"),
	MIME_CSS("text/css"),
	MIME_JAVASCRIPT("text/javascript");
	
	private final String type;
	
	private MimeType(String type) {
		this.type = type;
	}
	
	public String getMimeType() {
		return type;
	}
	
}

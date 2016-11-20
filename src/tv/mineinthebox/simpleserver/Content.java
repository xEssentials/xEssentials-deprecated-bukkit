package tv.mineinthebox.simpleserver;

/**
 * the Content is a builder pattern class to make writing to html support easier
 */
public class Content {
	
	private String content = "";
	
	/**
	 * writes automatic a html string and ends it with \n\r
	 * 
	 * @param anothercontent the content to be added to existing content
	 */
	public void add(String anothercontent) {
		this.content += anothercontent+"\r\n";
	}
	
	/**
	 * returns a modified content string based on the \n\r scope
	 * 
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	
}

package tv.mineinthebox.essentials.instances;

public class RssFeed {
	
	private String title;
	private String author;

	private String link;
	
	/**
	 * @author xize
	 * @param title
	 * @param author
	 * @param link
	 * @return rssFeed
	 */
	public RssFeed(String title, String author, String link) {
		this.title = title;
		this.author = author;
		this.link = link;
	}
	
	/**
	 * @author xize
	 * @param get the title of this new home page feed!
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @author xize
	 * @param get the authors name of the thread
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * @author xize
	 * @param get the full thread url
	 * @return String
	 */
	public String getLink() {
		return link;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RssFeed other = (RssFeed) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}

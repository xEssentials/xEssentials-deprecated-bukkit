package tv.mineinthebox.simpleserver;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class Tag {

	private final String tag;
	private final String replacement;
	private final boolean isCustom;

	/**
	 * a tag is a piece content ready to be replaced
	 * <p>
	 * example: if a tag is called name in the html/text document it should be display as {name} inside the file, this is a special parameter ready to be replaced.
	 * <p>
	 * if you are known with the php's template syntax smarty this look similar to that
	 * 
	 * @param tag                 the parameter such like {content} which got replaced
	 * @param replacement         the content to replace all the tags on the current content
	 * @param isCustomReplacement if it has to be custom replaced or replaced by default replacement
	 */
	public Tag(String tag, String replacement, boolean isCustomReplacement) {
		if(tag.matches("{.*}")) {
			this.tag = tag;	
		} else {
			this.tag = "{"+tag+"}";
		}
		this.replacement = replacement;
		this.isCustom = isCustomReplacement;
	}

	/**
	 * returns the tag
	 * 
	 * @return String
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * returns the default replacement for the the current tag
	 * 
	 * @return String
	 */
	public String getReplacement() {
		return replacement;
	}

	/**
	 * replace the tag by the replacement tag in a non dynamic way
	 * 
	 * @param bytes    the bytes being the content
	 * @return boolean true when process is finished otherwise false
	 */
	public boolean replace(byte[] bytes) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream output = new ObjectInputStream(in);
			String data = output.readUTF();
			data.replace(getTag(), getReplacement());
			bytes = data.getBytes();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * replace the tag by a custom replacement in a dynamic way
	 * 
	 * @param bytes           the bytes being the content
	 * @param replacementdata the replacement tag 
	 * @return boolean true when process is finished otherwise false
	 */
	public boolean replace(byte[] bytes, String replacementdata) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream output = new ObjectInputStream(in);
			String data = output.readUTF();
			data.replace(getTag(), replacementdata);
			bytes = data.getBytes();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * returns true if the tag exists inside the content, otherwise false
	 * 
	 * @param bytes representing the content
	 * @return boolean
	 */
	public boolean containsTag(byte[] bytes) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream output = new ObjectInputStream(in);
			String data = output.readUTF();
			if(data.contains(tag)) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * returns true if indicated to override the string with {@link #replace(byte[], String)} else with {@link #replace(byte[])}
	 *
	 * @return boolean
	 */
	public boolean isCustomReplacement() {
		return isCustom;
	}

}

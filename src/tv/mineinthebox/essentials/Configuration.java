package tv.mineinthebox.essentials;

import tv.mineinthebox.essentials.enums.ConfigType;

public interface Configuration {

	/**
	 * returns the name of the configuration
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName();
	
	/**
	 * returns the configuration type
	 * 
	 * @author xize
	 * @return ConfigType
	 */
	public ConfigType getType();
	
	/**
	 * returns true if the configuration has been generated and saved to a file, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGenerated();
	
	/**
	 * returns true if the {@link #generateConfig()} should be called once and not more times
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGeneratedOnce();
	
	/**
	 * generates the configuration
	 * 
	 * @author xize
	 */
	public void generateConfig();
	
	/**
	 * reloads the file into the memory
	 * 
	 * @author xize
	 */
	public void reload();
	
}

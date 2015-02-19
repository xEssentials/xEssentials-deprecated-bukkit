package tv.mineinthebox.essentials;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.enums.ConfigType;

public abstract class Configuration {
	
	protected final File f;
	protected final FileConfiguration con;
	protected xEssentials pl;
	
	public Configuration(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	public Configuration(xEssentials pl, File f, FileConfiguration con) {
		this.pl = pl;
		this.f = f;
		this.con = con;
	}

	/**
	 * returns the name of the configuration
	 * 
	 * @author xize
	 * @return String
	 */
	public abstract String getName();
	
	/**
	 * returns the configuration type
	 * 
	 * @author xize
	 * @return ConfigType
	 */
	public abstract ConfigType getType();
	
	/**
	 * returns true if the configuration has been generated and saved to a file, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean isGenerated();
	
	/**
	 * returns true if the {@link #generateConfig()} should be called once and not more times
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean isGeneratedOnce();
	
	/**
	 * generates the configuration
	 * 
	 * @author xize
	 */
	public abstract void generateConfig();
	
	/**
	 * reloads the file into the memory
	 * 
	 * @author xize
	 */
	public abstract void reload();
	
}

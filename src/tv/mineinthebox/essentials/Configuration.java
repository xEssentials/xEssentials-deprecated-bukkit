package tv.mineinthebox.essentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.enums.LogType;

public abstract class Configuration {

	protected final File f;
	protected final FileConfiguration con;
	protected final LinkedHashMap<String, Object> preconfig = new LinkedHashMap<String, Object>();
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
	public boolean isGenerated() {
		return f.exists();
	}

	/**
	 * generates the configuration
	 * 
	 * @author xize
	 */
	public void generateConfig() {
		Iterator<Entry<String, Object>> it = preconfig.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> entry = it.next();
			con.set(entry.getKey(), entry.getValue());
		}
		try {
			con.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * validates the configuration to check if it is up to date
	 * 
	 * @author xize
	 */
	public void validate() {
		if(isGenerated()) {
			Iterator<Entry<String, Object>> it = preconfig.entrySet().iterator();
			boolean needsUpdate = false;
			while(it.hasNext()) {
				Entry<String, Object> entry = it.next();
				
				if(getType() == ConfigType.ENTITY) {
					//xEssentials.log("key is: " + entry.getKey(), LogType.DEBUG);
				}
				
				if(!con.contains(entry.getKey()) || !con.isSet(entry.getKey())) {
					needsUpdate = true;
					xEssentials.log("configuration " + getType().getFileName() + " has been outdated!, adding key: " + entry.getKey(), LogType.SEVERE);
					con.set(entry.getKey(), entry.getValue());
				}
			}
			if(needsUpdate) {
				try {
					con.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					con.load(f);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * reloads the file into the memory
	 * 
	 * @author xize
	 */
	public void reload() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}

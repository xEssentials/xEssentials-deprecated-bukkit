package tv.mineinthebox.essentials.interfaces;

import tv.mineinthebox.essentials.GlobalConfiguration;
import tv.mineinthebox.essentials.managers.Manager;

public interface xEssentialsAPI {
	
	/**
	 * returns the core managers of this plugin
	 * 
	 * @author xize
	 * @return Manager
	 */
	public Manager getManagers();
	
	/**
	 * returns the memory presentation from the configuration of this plugin
	 * 
	 * @author xize
	 * @return Configuration
	 */
	public GlobalConfiguration getConfiguration();
	
	

}

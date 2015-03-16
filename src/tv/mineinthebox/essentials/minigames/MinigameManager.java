package tv.mineinthebox.essentials.minigames;

import java.util.Collections;
import java.util.Set;

import tv.mineinthebox.essentials.xEssentials;

public class MinigameManager extends MinigameLoader {
	
	public MinigameManager(xEssentials pl) {
		super(pl);
	}
	
	/**
	 * returns a unmodifiable Set with all minigame plugins!
	 * 
	 * @author xize
	 * @return Set<MinigamePlugin>
	 */
	public Set<MinigamePlugin> getPlugins() {
		return Collections.unmodifiableSet(super.getPlugins());
	}
	
	/**
	 * returns a minigame by name
	 * 
	 * @author xize
	 * @return MinigamePlugin
	 */
	public MinigamePlugin getPlugin(String name) {
		return super.getPlugin(name);
	}
	
	/**
	 * returns true if the plugin is enabled otherwise false
	 * 
	 * @author xize
	 * @param name - the plugin name
	 * @return boolean
	 */
	public boolean isEnabled(String name) {
		for(MinigamePlugin plugin : getPlugins()) {
			if(plugin.getName().equalsIgnoreCase(name)) {
				return plugin.isEnabled();
			}
		}
		return false;
	}
	
}

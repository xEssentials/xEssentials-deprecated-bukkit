package tv.mineinthebox.essentials.managers;

import java.util.Collections;
import java.util.Set;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameLoader;
import tv.mineinthebox.essentials.minigames.MinigamePlugin;

public class MinigameManager extends MinigameLoader{
	
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
		return Collections.unmodifiableSet(super.plugins);
	}
	
	/**
	 * returns a minigame by name
	 * 
	 * @author xize
	 * @return MinigamePlugin
	 */
	public MinigamePlugin getPlugin(String name) {
		for(MinigamePlugin game : getPlugins()) {
			if(game.getName().equalsIgnoreCase(name)) {
				return game;
			}
		}
		return null;
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
	
	/**
	 * sets the plugin on enabled state or disabled state
	 * 
	 * @author xize
	 * @param plugin - the plugin name
	 * @param bol - the boolean
	 */
	public void setEnabled(String plugin, boolean bol) {
		MinigamePlugin game = getPlugin(plugin);
		if(game != null) {
			if(bol) {
				game.setEnabled(true);
				game.onEnable();
			} else {
				game.setEnabled(false);
				game.onDisable();
			}
		} else {
			throw new IllegalArgumentException("plugin " + plugin + " does not exist");
		}
	}
	
}

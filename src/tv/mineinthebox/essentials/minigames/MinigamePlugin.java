package tv.mineinthebox.essentials.minigames;

import java.io.File;
import java.util.HashMap;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.xEssentialsAPI;

public abstract class MinigamePlugin {
	
	private final HashMap<String, MinigameArena> arenas = new HashMap<String, MinigameArena>();
	
	private xEssentials pl;
	private MinigameHandler handler;
	private String name;
	private String[] authors;
	private double version;
	private String description;
	private File datafolder;
	private ResourcePack resourcepack;
	
	private boolean isEnabled = false;
	
	/**
	 * the onEnable start method
	 * 
	 * @author xize
	 */
	public abstract void onEnable();
	
	/**
	 * the onDisable method
	 * 
	 * @author xize
	 */
	public abstract void onDisable();
	
	/**
	 * returns the name of the minigame
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns the authors of this minigmae
	 * 
	 * @author xize
	 * @return String[]
	 */
	public String[] getAuthors() {
		return authors;
	}
	
	/**
	 * returns the version of the minigame
	 * 
	 * @author xize
	 * @return double
	 */
	public double getVersion() {
		return version;
	}
	
	/**
	 * returns the description of the game
	 * 
	 * @author xize
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * returns true if the plugin is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	
	/**
	 * enables or disables the plugin
	 * 
	 * @author xize
	 * @param bol - true if plugin is set to enabled, otherwise false
	 */
	public void setEnabled(boolean bol) {
		this.isEnabled = bol;
	}
	
	/**
	 * returns the datafolder of where the plugin is made
	 * 
	 * @author xize
	 * @return File
	 */
	public File getDataFolder() {
		return datafolder;
	}
	
	/**
	 * registers the arena
	 * 
	 * @author xize
	 * @param arena the arena
	 */
	public void registerArena(MinigameArena arena) {
		arenas.put(arena.getName().toLowerCase(), arena);
	}
	
	/**
	 * unregisters the arena
	 * 
	 * @author xize
	 * @param arena - the arena
	 */
	public void unregisterArena(MinigameArena arena) {
		arenas.remove(arena.getName().toLowerCase());
	}
	
	/**
	 * tries to get a arena, and returns it if no arena is found null will be returned
	 * 
	 * @author xize
	 * @param name - the arena
	 * @return MinigameArena
	 */
	public MinigameArena getArena(String name) {
		return arenas.get(name.toLowerCase());
	}
	
	/**
	 * returns all the arenas
	 * 
	 * @author xize
	 * @return MinigameArena[]
	 */
	public MinigameArena[] getArenas() {
		MinigameArena[] a = new MinigameArena[arenas.size()];
		int i = 0;
		for(MinigameArena arena : arenas.values()) {
			a[i] = arena;
			i++;	
		}
		return a;
	}
	
	/**
	 * returns the minigame handler
	 * 
	 * @author xize
	 * @return MinigameHandler
	 */
	public MinigameHandler getHandlers() {
		return handler;
	}
	
	/**
	 * returns the xEssentials Api
	 * 
	 * @author xize
	 * @return xEssentialsAPI
	 */
	public xEssentialsAPI getMinigameApi() {
		return (xEssentialsAPI) pl;
	}
	
	/**
	 * sets a default resource pack for every player joining the arena!
	 * 
	 * @author xize
	 * @param f - the file
	 */
	public void setDefaultResourcePack(File f) {
		this.resourcepack = new ResourcePack(f);
	}
	
	/**
	 * returns the ResourcePack
	 * 
	 * @author xize
	 * @return ResourcePack
	 */
	public ResourcePack getResourcePack() {
		return resourcepack;
	}
	
}

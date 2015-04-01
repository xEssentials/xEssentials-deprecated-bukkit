package tv.mineinthebox.essentials.minigames.plugin.session;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;


public abstract class MinigameSession {

	private final Map<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();
	
	/**
	 * creates a session of this Player
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public final void createSession(Player p) {
			data.put(p.getName(), new HashMap<String, Object>());
	}

	/**
	 * stops the session of this Player
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public final void stopSession(Player p) {
		data.remove(p.getName());
	}

	/**
	 * returns true if the Player has a session, otherwise false
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public final boolean hasSession(Player p) {
		return data.containsKey(p.getName());
	}

	/**
	 * returns true if the given data exists otherwise false
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @return boolean
	 */
	public final boolean hasSessionData(Player p, String key) {
		if(hasSession(p)) {
			return data.get(p.getName()).containsKey(key);
		}
		return false;
	}

	/**
	 * tries to return a object
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @return Object
	 */
	public final Object getSessionData(Player p, String key) {
		return data.get(p.getName()).get(key);
	}

	/**
	 * tries to add a object to the session data
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @param obj - the object
	 */
	public final void addSessionData(Player p, String key, Object obj) {
		data.get(p.getName()).put(key, obj);
	}

	/**
	 * returns true if the session matches with the given data otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean isSessionComplete(Player p);

	/**
	 * processes what is needed to create a arena
	 * 
	 * @author xize
	 */
	public abstract void processSession(Player p);
}

package tv.mineinthebox.essentials.minigames.plugin.session;

import org.bukkit.entity.Player;


public interface MinigameSession {

	/**
	 * creates a session of this Player
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public void createSession(Player p);

	/**
	 * stops the session of this Player
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public void stopSession(Player p);

	/**
	 * returns true if the Player has a session, otherwise false
	 * 
	 * @author xize
	 * @param p - the Player
	 */
	public boolean hasSession(Player p);

	/**
	 * returns true if the given data exists otherwise false
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @return boolean
	 */
	public boolean hasSessionData(Player p, String key);

	/**
	 * tries to return a object
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @return Object
	 */
	public Object getSessionData(Player p, String key);

	/**
	 * tries to add a object to the session data
	 * 
	 * @author xize
	 * @param p - the Player
	 * @param key - the key
	 * @param obj - the object
	 */
	public void addSessionData(Player p, String key, Object obj);

	/**
	 * returns true if the session matches with the given data otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSessionComplete(Player p);

	/**
	 * processes what is needed to create a arena
	 * 
	 * @author xize
	 */
	public abstract void processSession(Player p);
}

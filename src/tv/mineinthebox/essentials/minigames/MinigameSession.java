package tv.mineinthebox.essentials.minigames;

import java.util.HashMap;

import tv.mineinthebox.essentials.interfaces.XPlayer;


public abstract class MinigameSession {
	
	protected final HashMap<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();
	
	/**
	 * creates a session of this XPlayer
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 */
	public void createSession(XPlayer p) {
		data.put(p.getName().toLowerCase(), new HashMap<String, Object>());
	}
	
	/**
	 * stops the session of this XPlayer
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 */
	public void stopSession(XPlayer p) {
		data.remove(p.getName().toLowerCase());
	}
	
	/**
	 * returns true if the XPlayer has a session, otherwise false
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 */
	public boolean hasSession(XPlayer p) {
		return data.containsKey(p.getName().toLowerCase());
	}
	
	/**
	 * returns true if the given data exists otherwise false
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 * @param key - the key
	 * @return boolean
	 */
	public boolean hasSessionData(XPlayer p, String key) {
		return data.get(p.getName().toLowerCase()).containsKey(key);
	}
	
	/**
	 * tries to return a object
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 * @param key - the key
	 * @return Object
	 */
	public Object getSessionData(XPlayer p, String key) {
		return data.get(p.getName().toLowerCase()).get(key);
	}
	
	/**
	 * tries to add a object to the session data
	 * 
	 * @author xize
	 * @param p - the XPlayer
	 * @param key - the key
	 * @param obj - the object
	 */
	public void addSessionData(XPlayer p, String key, Object obj) {
		data.get(p).put(key, obj);
	}
	
	/**
	 * returns true if the session matches with the given data otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean isSessionComplete(tv.mineinthebox.essentials.interfaces.XPlayer p);
	
	/**
	 * processes what is needed to create a arena
	 * 
	 * @author xize
	 */
	public abstract void processSession(XPlayer p);
	
}

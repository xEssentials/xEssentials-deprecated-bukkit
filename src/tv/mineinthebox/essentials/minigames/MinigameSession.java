package tv.mineinthebox.essentials.minigames;


public interface MinigameSession {

	public void createSession(String player);
	
	public boolean hasSession(String player);
	
	public void removeSession(String player);
	
	public boolean containsSessionData(String player, String key);
	
	public void addSessionData(String player, String key, Object obj);
	
	public void removeSessionData(String player, String key);
	
	public boolean isSessionComplete(String player);
	
	public boolean saveArena(String player);
	
}

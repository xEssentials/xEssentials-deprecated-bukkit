package tv.mineinthebox.essentials.minigames.parkour;

import java.util.HashMap;

import tv.mineinthebox.essentials.minigames.MinigameSession;

public class ParkourSession implements MinigameSession {

	private final HashMap<String, HashMap<String, Object>> hash = new HashMap<String, HashMap<String, Object>>();
	
	@Override
	public void createSession(String player) {
		hash.put(player, new HashMap<String, Object>());
	}

	@Override
	public boolean hasSession(String player) {
		return hash.containsKey(player);
	}

	@Override
	public void removeSession(String player) {
		hash.remove(player);
	}

	@Override
	public boolean containsSessionData(String player, String key) {
		return hash.get(player).containsKey(key);
	}

	@Override
	public void addSessionData(String player, String key, Object obj) {
		hash.get(player).put(key, obj);
	}

	@Override
	public void removeSessionData(String player, String key) {
		hash.get(player).remove(key);
	}

	@Override
	public boolean isSessionComplete(String player) {
		String[] keys = {"name", "spawnpoints", "finish"};
		for(String key : keys) {
			if(!hash.get(player).containsKey(key)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean saveArena(String player) {
		// TODO Auto-generated method stub
		return false;
	}

}

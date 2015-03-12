package tv.mineinthebox.essentials.managers;

import java.util.HashMap;

import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class ProtectionManager {
	
	private final HashMap<String, Object[]> session = new HashMap<String, Object[]>();
	
	public void addSession(String player, ProtectionType type) {
		Object[] obj = new Object[2];
		obj[0] = type;
		session.put(player.toLowerCase(), obj);
	}
	
	public void addSession(String player, XOfflinePlayer otherplayer, ProtectionType type) {
		Object[] obj = new Object[2];
		obj[0] = type;
		obj[1] = otherplayer;
		session.put(player.toLowerCase(), obj);
	}
	
	public Object[] getSessionData(String player) {
		return session.get(player);
	}
	
	public boolean hasSession(String player) {
		return session.containsKey(player.toLowerCase());
	}
	
	public void removeSession(String player) {
		session.remove(player.toLowerCase());
	}

}

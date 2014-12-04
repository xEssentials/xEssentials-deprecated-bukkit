package tv.mineinthebox.bukkit.essentials.helpers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnlinePlayersHelper {
	
	//credits for this class goes to shadypotato from bukkit: https://forums.bukkit.org/threads/code-snippet-workaround-for-the-new-bukkit-getonlineplayers-method.285072/
	
	@SuppressWarnings("unchecked")
	public static Player[] getOnlinePlayers() {
		try {
			Method check = Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]);
			if(check.getReturnType() == Player[].class) {
				return (Player[])check.invoke(null, new Object[0]);	
			} else if(check.getReturnType() == List.class || check.getReturnType() == Collection.class) {
				Collection<Player> players = (Collection<Player>) check.invoke(null, new Object[0]);
				Player[] ps = new Player[(players.size())];
				int i = 0;
				for(Player p : players) {
					ps[i] = p;
					i++;
				}
				return ps;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new NullPointerException("a fatal error has been occuried, please restart your server.");
	}

}

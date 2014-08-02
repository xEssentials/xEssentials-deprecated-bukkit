package tv.mineinthebox.essentials.events.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.enums.MojangStatusResponse;
import tv.mineinthebox.essentials.events.customEvents.MojangStatusEvent;

public class PublishMojangStatus implements Listener {
	
	@EventHandler
	public void OnCheck(MojangStatusEvent e) {
		if(e.getChangedStatus() == MojangStatusResponse.LOGIN_SERVER_ACTIVE) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Login Server] " + ChatColor.GRAY + "seems now to be active again!, its now safe to reconnect to the server again.");
		} else if(e.getChangedStatus() == MojangStatusResponse.LOGIN_SERVER_DOWN) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Login Server] " + ChatColor.GRAY + "seems to be down, we recommend to stay online as you may not be able to login back on the server again.");
		} else if(e.getChangedStatus() == MojangStatusResponse.SESSION_SERVER_ACTIVE) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Session Server] " + ChatColor.GRAY + "seems now to be active again!, its now safe to reconnect to the server again.");
		} else if(e.getChangedStatus() == MojangStatusResponse.SESSION_SERVER_DOWN) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Session Server] " + ChatColor.GRAY + "seems to be down, we recommend to stay online as you may not be able to login back on the server again.");
		} else if(e.getChangedStatus() == MojangStatusResponse.SKIN_SERVER_UP) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Skin Server] " + ChatColor.GRAY + "has been up again!");
		} else if(e.getChangedStatus() == MojangStatusResponse.SKIN_SERVER_DOWN) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Mojang Skin Server] " + ChatColor.GRAY + "seems to be down!");
		}
	}

}

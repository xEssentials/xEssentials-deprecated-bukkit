package tv.mineinthebox.essentials.events.ban;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class ShowAlternateAccountsEvent implements Listener {

	@EventHandler
	public void showAltsOnJoin(PlayerJoinEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(!xp.isStaff()) {
				if(xp.hasAlternateAccounts()) {
					for(XPlayer player : xEssentials.getManagers().getPlayerManager().getPlayers()) {
						if(player.isStaff()) {
							player.getPlayer().sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
							player.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Alternate Accounts for player " + xp.getUser()+"]___Oo.");
							if(Configuration.getBanConfig().isFishbansEnabled()) {
								if(xp.getAlternateAccounts().isListedOnService()) {
									try {
										player.getPlayer().sendMessage(xp.getAlternateAccounts().getServiceLookupResultMessage());
									} catch (Exception e1) {
										xEssentials.getPlugin().log("could not lookup ban status of player " + xp.getUser() + " on api.fishbans.com", LogType.SEVERE);
									}
								} else {
									player.getPlayer().sendMessage("player is not banned on any service.");
								}
							}
							player.getPlayer().sendMessage(ChatColor.GREEN + xp.getUser() + ChatColor.GRAY + " has may alternate accounts!");
							player.getPlayer().sendMessage(xp.getAlternateAccounts().getAltsDetailed());
							player.getPlayer().sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
						}
					}
				}
			}
		}
	}

}

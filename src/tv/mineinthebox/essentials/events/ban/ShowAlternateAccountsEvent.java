package tv.mineinthebox.essentials.events.ban;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class ShowAlternateAccountsEvent implements Listener {
	
	private final xEssentials pl;
	
	public ShowAlternateAccountsEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void showAltsOnJoin(PlayerJoinEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(!xp.isStaff()) {
				if(xp.hasAlternateAccounts()) {
					for(XPlayer player : pl.getManagers().getPlayerManager().getPlayers()) {
						if(player.isStaff()) {
							player.getBukkitPlayer().sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
							player.getBukkitPlayer().sendMessage(ChatColor.GOLD + ".oO___[Alternate Accounts for player " + xp.getName()+"]___Oo.");
							if(pl.getConfiguration().getBanConfig().isFishbansEnabled()) {
								if(xp.getAlternateAccounts().isListedOnService()) {
									try {
										player.getBukkitPlayer().sendMessage(xp.getAlternateAccounts().getServiceLookupResultMessage());
									} catch (Exception e1) {
										xEssentials.log("could not lookup ban status of player " + xp.getName() + " on api.fishbans.com", LogType.SEVERE);
									}
								} else {
									player.getBukkitPlayer().sendMessage("player is not banned on any service.");
								}
							}
							player.getBukkitPlayer().sendMessage(ChatColor.GREEN + xp.getName() + ChatColor.GRAY + " has may alternate accounts!");
							player.getBukkitPlayer().sendMessage(xp.getAlternateAccounts().getAltsDetailed());
							player.getBukkitPlayer().sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
						}
					}
				}
			}
		}
	}

}

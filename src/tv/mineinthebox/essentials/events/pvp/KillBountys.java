package tv.mineinthebox.essentials.events.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;

public class KillBountys implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if(Hooks.isVaultEnabled()) {
				xEssentials.getManagers().getVaultManager().desposit(p, Configuration.getPvpConfig().getKillBountyPrice());
				p.sendMessage(ChatColor.GOLD + "you earned " + Configuration.getPvpConfig().getKillBountyPrice() + "$! by killing " + ChatColor.GREEN + e.getEntity().getType().name().toLowerCase().replace("_", " "));
			}
		}
	}

}

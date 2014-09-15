package tv.mineinthebox.bukkit.essentials.events.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;

public class KillBountys implements Listener {
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.SPAWNER) {
			e.getEntity().setMetadata("spawner", new FixedMetadataValue(xEssentials.getPlugin(), "cant spawn"));
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if(Hooks.isVaultEnabled()) {
				if(e.getEntity().hasMetadata("spawner")) {
					return;
				}
				xEssentials.getManagers().getVaultManager().desposit(p, Configuration.getPvpConfig().getKillBountyPrice());
				p.sendMessage(ChatColor.GOLD + "you earned " + Configuration.getPvpConfig().getKillBountyPrice() + "$! by killing " + ChatColor.GREEN + e.getEntity().getType().name().toLowerCase().replace("_", " "));
			}
		}
	}

}

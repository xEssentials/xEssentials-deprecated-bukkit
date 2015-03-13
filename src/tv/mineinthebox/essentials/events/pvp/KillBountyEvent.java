package tv.mineinthebox.essentials.events.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class KillBountyEvent extends EventTemplate implements Listener {
	
	public KillBountyEvent(xEssentials pl) {
		super(pl, "KillBounty");
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.SPAWNER) {
			e.getEntity().setMetadata("spawner", new FixedMetadataValue(pl, "cant spawn"));
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if(Hooks.isVaultEcoEnabled()) {
				if(e.getEntity().hasMetadata("spawner")) {
					return;
				}
				pl.getManagers().getVaultManager().desposit(p, pl.getConfiguration().getPvpConfig().getKillBountyPrice());
				sendMessage(p, ChatColor.GOLD + "you earned " + pl.getConfiguration().getPvpConfig().getKillBountyPrice() + "$! by killing " + ChatColor.GREEN + e.getEntity().getType().name().toLowerCase().replace("_", " "));
			}
		}
	}

}

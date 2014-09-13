package tv.mineinthebox.essentials.events.chairs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.ChairExitEvent;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PlayerSitOnChair implements Listener, Runnable {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.WOOD_STAIRS || e.getClickedBlock().getType() == Material.COBBLESTONE_STAIRS || e.getClickedBlock().getType() == Material.SMOOTH_STAIRS || e.getClickedBlock().getType() == Material.BRICK_STAIRS) {
				if(isChair(e.getClickedBlock())) {
					Chicken chicken = (Chicken)e.getClickedBlock().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.5, 0, 0.5), EntityType.CHICKEN);
					chicken.setMetadata("chair", new FixedMetadataValue(xEssentials.getPlugin(), e.getPlayer().getName()));
					chicken.setPassenger(e.getPlayer());
					chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10));
					chicken.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100));
					e.getPlayer().sendMessage(ChatColor.GREEN + "you are now sitting on a chair.");
					xEssentials.getManagers().getChairManager().addChicken(e.getPlayer(), chicken);
					Bukkit.getScheduler().runTaskTimer(xEssentials.getPlugin(), this, 0L, 1L);
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
					xp.setInChair(true);
				}
			}
		}
	}

	@EventHandler
	public void onEject(ChairExitEvent e) {
		xEssentials.getManagers().getChairManager().removeChicken(e.getPlayer().getName());
		e.getChairPiece().remove();
		e.getPlayer().sendMessage(ChatColor.GREEN + "you no longer sit on a chair.");
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		xp.setInChair(false);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getChairManager().containsPlayer(e.getPlayer().getName())) {
			xEssentials.getManagers().getChairManager().removeChicken(e.getPlayer().getName());
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.setInChair(false);
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(xEssentials.getManagers().getChairManager().containsPlayer(e.getPlayer().getName())) {
			xEssentials.getManagers().getChairManager().removeChicken(e.getPlayer().getName());
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.setInChair(false);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Chicken) {
			Chicken chicken = (Chicken) e.getEntity();
			if(chicken.hasMetadata("chair")) {
				e.setCancelled(true);
			}
		}
	}

	private boolean isChair(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

		List<Block> blocks = new ArrayList<Block>();

		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.WALL_SIGN) {
				blocks.add(block.getRelative(face));
			}
		}

		return (blocks.size() > 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Iterator<Entry<String, Entity>> it = xEssentials.getManagers().getChairManager().getIterator();
		while(it.hasNext()) {
			Entry<String, Entity> entry = it.next();
			if(entry.getValue().getPassenger() == null) {
				Bukkit.getPluginManager().callEvent(new ChairExitEvent(Bukkit.getPlayer(entry.getKey()), (Chicken)entry.getValue()));	
			}
		}
	}

}

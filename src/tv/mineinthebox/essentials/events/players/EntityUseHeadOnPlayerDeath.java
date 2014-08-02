package tv.mineinthebox.essentials.events.players;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class EntityUseHeadOnPlayerDeath implements Listener {
	
	private HashMap<String, UUID> entitys = new HashMap<String, UUID>();
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(e.getDamager() instanceof Zombie) {
				entitys.put(p.getName(), e.getDamager().getUniqueId());
			} else if(e.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Skeleton) {
					Skeleton skel = (Skeleton) arrow.getShooter();
					entitys.put(p.getName(), skel.getUniqueId());
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(entitys.containsKey(e.getEntity().getName())) {
			try {
				Entity entity = getEntity(entitys.get(e.getEntity().getName()), e.getEntity().getWorld());
				if(entity instanceof Zombie) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM);
					item.setDurability((short) 3);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setOwner(e.getEntity().getName());
					item.setItemMeta(meta);
					Zombie zombie = (Zombie) entity;
					zombie.getEquipment().setHelmet(item);
					zombie.getEquipment().setHelmetDropChance(100);
					entitys.remove(e.getEntity().getName());
				} else if(entity instanceof Skeleton) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM);
					item.setDurability((short) 3);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setOwner(e.getEntity().getName());
					item.setItemMeta(meta);
					Skeleton skeleton = (Skeleton) entity;
					skeleton.getEquipment().setHelmet(item);
					skeleton.getEquipment().setHelmetDropChance(100);
					entitys.remove(e.getEntity().getName());
				}
			} catch(NullPointerException r) {
				entitys.remove(e.getEntity().getName());
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(entitys.containsKey(e.getPlayer().getName())) {
			entitys.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(entitys.containsKey(e.getPlayer().getName())) {
			entitys.remove(e.getPlayer().getName());
		}
	}
	
	private Entity getEntity(UUID uid, World w) {
		for(Entity entity : w.getEntities()) {
			if(entity.getUniqueId().equals(uid)) {
				return entity;
			}
		}
		throw new NullPointerException();
	}

}

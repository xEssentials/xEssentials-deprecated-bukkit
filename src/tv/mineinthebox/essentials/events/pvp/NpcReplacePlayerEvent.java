package tv.mineinthebox.essentials.events.pvp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class NpcReplacePlayerEvent implements Listener {
	
	private final HashMap<String, String> essentialsPlayers = new HashMap<String, String>();
	private final HashMap<UUID, ItemStack[]> npcs = new HashMap<UUID, ItemStack[]>();
	private final xEssentials pl;
	
	public NpcReplacePlayerEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Player) {
				Player p = (Player) e.getEntity();
				Player damage = (Player) e.getDamager();
				essentialsPlayers.put(damage.getName(), p.getName());
			}
		}
	}
	
	@EventHandler
	public void onNameTag(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof Skeleton) {
			Skeleton skel = (Skeleton) e.getRightClicked();
			if(skel.isCustomNameVisible()) {
				if(npcs.containsKey(skel.getUniqueId())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(essentialsPlayers.containsKey(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				essentialsPlayers.remove(e.getPlayer().getName());
				return;
			}
			ItemStack[] items = e.getPlayer().getInventory().getContents();
			Skeleton skel = (Skeleton) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.SKELETON);
			//lets pimp the skeleton a bit!
			turnSkelToNpc(skel, e.getPlayer().getName());
			npcs.put(skel.getUniqueId(), items);
			essentialsPlayers.remove(e.getPlayer().getName());
			NpcDespawn(skel);
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(essentialsPlayers.containsKey(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				return;
			}
			ItemStack[] items = e.getPlayer().getInventory().getContents();
			Skeleton skel = (Skeleton) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.SKELETON);
			//lets pimp the skeleton a bit!
			turnSkelToNpc(skel, e.getPlayer().getName());
			npcs.put(skel.getUniqueId(), items);
			essentialsPlayers.remove(e.getPlayer().getName());
			NpcDespawn(skel);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(essentialsPlayers.containsKey(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				essentialsPlayers.remove(e.getPlayer().getName());
				return;
			}
			ItemStack[] items = e.getPlayer().getInventory().getContents();
			Skeleton skel = (Skeleton) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.SKELETON);
			//lets pimp the skeleton a bit!
			turnSkelToNpc(skel, e.getPlayer().getName());
			npcs.put(skel.getUniqueId(), items);
			essentialsPlayers.remove(e.getPlayer().getName());
			NpcDespawn(skel);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onNpcDeath(EntityDeathEvent e) {
		if(npcs.containsKey(e.getEntity().getUniqueId())) {
			e.getDrops().clear();
			for(ItemStack stack : npcs.get(e.getEntity().getUniqueId())) {
				e.getDrops().add(stack);
			}
			String PlayerName = e.getEntity().getCustomName();
			Player p = Bukkit.getPlayer(PlayerName);
			if(p instanceof Player) {
				p.getInventory().clear();
			} else {
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(PlayerName);
				off.ClearInventoryOnRelog();	
			}
		}
	}
	
	private void NpcDespawn(final Entity entity) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(npcs.containsKey(entity.getUniqueId())) {
					npcs.remove(entity.getUniqueId());
					entity.remove();
				}
			}
		}, 1000);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoinNpc(PlayerJoinEvent e) {
		for(Entity entity : e.getPlayer().getNearbyEntities(16, 16, 16)) {
			if(entity instanceof Skeleton) {
				Skeleton skel = (Skeleton) entity;
				if(skel.isCustomNameVisible()) {
					if(skel.getCustomName().equalsIgnoreCase(e.getPlayer().getName())) {
						e.getPlayer().kickPlayer(ChatColor.GREEN + "[Combat log]" + ChatColor.GRAY + "\nyou are not allowed to join until the npc is dead.");
						e.setJoinMessage("");
						return;
					}
				}
			}
		}
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isInventoryClearanceOnRelog()) {
			xp.ClearInventoryOnRelog();	
		}
	}
	
	private void turnSkelToNpc(Skeleton skel, String player) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM);
		head.setDurability((short)3);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwner(player);
		head.setItemMeta(meta);
		head.setAmount(1);
		skel.getEquipment().setHelmet(head);
		skel.getEquipment().setHelmetDropChance(0);
		ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
		chestplate.setAmount(1);
		skel.getEquipment().setChestplate(chestplate);
		skel.getEquipment().setChestplateDropChance(0);
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
		leggings.setAmount(1);
		skel.getEquipment().setLeggings(leggings);
		skel.getEquipment().setLeggingsDropChance(0);
		ItemStack shoes = new ItemStack(Material.IRON_BOOTS);
		skel.getEquipment().setBoots(shoes);
		skel.getEquipment().setBootsDropChance(0);
		skel.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 30));
		skel.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 30));
		skel.setCustomName(player);
		skel.setCustomNameVisible(true);
	}

}

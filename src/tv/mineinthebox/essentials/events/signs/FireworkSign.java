package tv.mineinthebox.essentials.events.signs;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import tv.mineinthebox.essentials.enums.PermissionKey;

public class FireworkSign implements Listener {
	
	public HashMap<String, String> colours = new HashMap<String, String>();

	@EventHandler
	public void fireworksign(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[firework]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_FIREWORK.getPermission())) {
				if(colours.isEmpty()) {
					for(DyeColor color : DyeColor.values()) {
						colours.put(color.name(), color.name());
					}	
				}
				if(colours.containsKey(e.getLine(1).toString().toUpperCase().replace(" ", "_")) || e.getLine(1).contains("random")) {
					e.setLine(0, ChatColor.DARK_BLUE + "[Firework]");
					e.setLine(1, e.getLine(1).toLowerCase());
					e.getPlayer().sendMessage(ChatColor.GREEN + "you successfully placed a firework sign!");
					e.getBlock().getState().update();
					colours.clear();
				} else {
					e.getBlock().breakNaturally();
					e.getPlayer().sendMessage(ChatColor.RED + "a colour is needed to create this sign");
					colours.clear();
				}
			} else {
				if(e.getLine(0).equalsIgnoreCase("[firework]")) {
					e.getBlock().breakNaturally();
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place these signs\n" + ChatColor.WHITE + "permission: xEssentials.signs.firework");
				}
			}
		}

	}

	@EventHandler
	public void redstone(BlockRedstoneEvent e) {
		if(e.getBlock().getType() == Material.SIGN_POST) {
			if(e.getNewCurrent() > 0) {
				Sign sign = (Sign) e.getBlock().getState();
				if(sign.getLine(0).contains("[Firework]") || sign.getLine(0).contains("[firework]")) {
					for(DyeColor colour : DyeColor.values()) {
						colours.put(colour.name(), colour.name());
					}
					if(colours.containsKey(sign.getLine(1).toString().toUpperCase().replace(" ", "_"))) {
						Location loc = e.getBlock().getLocation();
						Firework fw = (Firework) e.getBlock().getLocation().getWorld().spawnEntity(loc, EntityType.FIREWORK);
						FireworkMeta fwm = fw.getFireworkMeta();
						Random r = new Random();
						Type type = Type.BALL;
						int rt = r.nextInt(4) + 1;
						if(rt == 1) type = Type.BALL;
						if(rt == 2) type = Type.BALL_LARGE;
						if(rt == 3) type = Type.BURST;
						if(rt == 4) type = Type.CREEPER;
						if(rt == 5) type = Type.STAR;
						DyeColor dyecolor = (DyeColor) DyeColor.valueOf(sign.getLine(1).toUpperCase().replace(" ", "_"));
						Color color = dyecolor.getColor();
						FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(color).with(type).trail(r.nextBoolean()).build();
						fwm.addEffect(effect);
						int rp = r.nextInt(2) + 1;
						fwm.setPower(rp);
						fw.setFireworkMeta(fwm);
					} else {
						if(sign.getLine(1).contains("random")) {
							for(DyeColor colour : DyeColor.values()) {
								colours.put(colour.name(), colour.name());
							}
							Random rand = new Random();
							Object[] entries = colours.values().toArray();
							Object randomValue = entries[rand.nextInt(entries.length)];
							DyeColor randomColor = DyeColor.valueOf((String) randomValue);
							Color random = randomColor.getColor();

							Location loc = e.getBlock().getLocation();
							Firework fw = (Firework) e.getBlock().getLocation().getWorld().spawnEntity(loc, EntityType.FIREWORK);
							FireworkMeta fwm = fw.getFireworkMeta();
							Random r = new Random();
							Type type = Type.BALL;
							int rt = r.nextInt(4) + 1;
							if(rt == 1) type = Type.BALL;
							if(rt == 2) type = Type.BALL_LARGE;
							if(rt == 3) type = Type.BURST;
							if(rt == 4) type = Type.CREEPER;
							if(rt == 5) type = Type.STAR;
							FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(random).with(type).trail(r.nextBoolean()).build();
							fwm.addEffect(effect);
							int rp = r.nextInt(2) + 1;
							fwm.setPower(rp);
							fw.setFireworkMeta(fwm);
						} else {
							//illegal colour.
						}
						//illegal colour.
					}
				}
			}
		}
	}

}

package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdPotato extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdPotato(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("potato")) {
			if(sender.hasPermission(PermissionKey.CMD_POTATO.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.vanish();
						Item item = xp.getPlayer().getWorld().dropItem(xp.getPlayer().getLocation(), new ItemStack(Material.POTATO_ITEM));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 100));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 100));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
						xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
						sendMessage(ChatColor.GREEN + "you are turned into a potato!");
						xp.setPotato(item);
						onPotatoSchedule(xp.getUser());
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						xp.vanish();
						Item item = xp.getPlayer().getWorld().dropItem(xp.getPlayer().getLocation(), new ItemStack(Material.POTATO_ITEM));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 100));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 100));
						xp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
						xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
						sendMessageTo(xp.getPlayer(), ChatColor.GREEN + "you are turned into a potato!");
						xp.setPotato(item);
						onPotatoSchedule(xp.getUser());
						sendMessage(ChatColor.GREEN + "you successfully changed " + xp.getPlayer().getName() + " into a potato!");
					} else {
						sender.sendMessage(ChatColor.RED + "the player is not online!");
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

	private void onPotatoSchedule(final String player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

			@Override
			public void run() {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(player);
				if(xp instanceof XPlayer) {
					if(xp.isPotato()) {
						xp.unvanish(true);
						sendMessageTo(xp.getPlayer(), ChatColor.GREEN + "you are not longer cursed as potato!");
						xp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
						xp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
						xp.getPlayer().removePotionEffect(PotionEffectType.SPEED);
						xp.removePotato();
					}
				}
			}
		}, 1500);
	}

}

package tv.mineinthebox.essentials.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Kit;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdKit extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdKit(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kit")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_KIT.getPermission())) {
					List<Kit> kits = new ArrayList<Kit>(pl.getConfiguration().getKitConfig().getConfigKits().values());
					List<String> names = new ArrayList<String>();
					for(Kit kit : kits) {
						if(kit.getKitName().toUpperCase().startsWith(args[0].toUpperCase())) {
							names.add(kit.getKitName());
						}
					}
					return names;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kit")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_KIT.getPermission())) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						HashMap<String, Kit> kits = new HashMap<String, Kit>(pl.getConfiguration().getKitConfig().getConfigKits());
						if(kits.containsKey(args[0])) {
							if(sender.hasPermission(PermissionKey.CMD_KIT.getPermission()+"."+args[0])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								if(pl.getConfiguration().getKitConfig().isCooldownEnabled()) {
									if(xp.hasKitCooldown()) {
										long cooldown = xp.getKitCooldown() / 1000L + (long)pl.getConfiguration().getKitConfig().getCoolDown() - System.currentTimeMillis() / 1000L;
										if(cooldown > 0L) {
											DecimalFormat df = new DecimalFormat("#.##");
											sendMessage("you cannot use kits at this time please wait " + df.format((double)cooldown/60.0D) + " seconds!");
											return false;
										} else {
											xp.removeKitCoolDown();
											Kit kit = kits.get(args[0]);
											xp.getBukkitPlayer().getInventory().addItem(kit.getKitItems());
											sendMessage("enjoy your " + kit.getKitName() + "!");
											xp.setKitCooldown(System.currentTimeMillis());
										}
									} else {
										Kit kit = kits.get(args[0]);
										xp.getBukkitPlayer().getInventory().addItem(kit.getKitItems());
										sendMessage("enjoy your " + kit.getKitName() + "!");
										xp.setKitCooldown(System.currentTimeMillis());
									}
								} else {
									Kit kit = kits.get(args[0]);
									xp.getBukkitPlayer().getInventory().addItem(kit.getKitItems());
									sendMessage("enjoy your " + kit.getKitName() + "!");
								}
							} else {
								getWarning(WarningType.NO_PERMISSION);
							}
						} else {
							sendMessage("this kit does not exist!");
						}
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else {
				getWarning(WarningType.PLAYER_ONLY);
			}
		}
		return false;
	}

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[kit help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/kit <kit name> " + ChatColor.WHITE + ": get the kit by name in your inventory!");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/kits" + ChatColor.WHITE + ": shows you all the kits!");
	}

}

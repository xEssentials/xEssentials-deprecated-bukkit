package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.helpers.EffectType;
import tv.mineinthebox.essentials.helpers.EnumHelper;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdEffect extends CommandTemplate {

	private final List<String> enumdata = new ArrayList<String>();
	
	public CmdEffect(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		enumdata.add("help");
		enumdata.add("list");
		enumdata.add("stop");
		for(EffectType type : EffectType.values()) {
			enumdata.add(type.name());
		}
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("effect")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_EFFECT.getPermission())) {
					
					List<String> newdata = new ArrayList<String>();
					
					for(String data : enumdata) {
						if(data.startsWith(args[0].toUpperCase())) {
							newdata.add(data);
						}
					}
					
					return newdata;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("effect")) {
			if(sender.hasPermission(PermissionKey.CMD_EFFECT.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else if(args[0].equalsIgnoreCase("list")) {
							String list = "";
							int i = 1;
							for(EffectType type : EffectType.values()) {
								if(i == EffectType.values().length) {
									list += type.name();
								} else {
									list += type.name() + ", ";
								}
								i++;
							}
							sendMessage(list);
						} else if(args[0].equalsIgnoreCase("stop")) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.hasEffects()) {
								xp.setEffect(null);
								sendMessage("effects disabled!");
							} else {
								sendMessage("you dont wear any effects!");
							}
						} else {
							if(EnumHelper.isDefined(EffectType.class, args[0].toUpperCase())) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								EffectType type = EnumHelper.getEnum(EffectType.class, args[0].toUpperCase());
								xp.setEffect(type);
								sendMessage("effect has been set!");
							} else {
								sendMessage("effect does not exist!");
							}
						}
					}
				} else {
					getWarning(WarningType.PLAYER_ONLY);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[effect help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/effect help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/effect list " + ChatColor.WHITE + ": shows the list of effects");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/effect <name> " + ChatColor.WHITE + ": uses an effect on the player");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/effect stop " + ChatColor.WHITE + ": removes the effect");
	}

}

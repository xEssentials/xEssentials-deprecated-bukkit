package tv.mineinthebox.essentials.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Portal;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdPortals extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdPortals(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	private List<String> getPortals(String p) {
		List<String> list = new ArrayList<String>();
		for(Portal portal : pl.getConfiguration().getPortalConfig().getPortals().values()) {
			if(portal.getPortalName().startsWith(p)) {
				list.add(portal.getPortalName());
			}
		}
		return list;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("portals")) {
			if(sender.hasPermission(PermissionKey.CMD_PORTALS.getPermission())) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("delete")) {
						return getPortals(args[1]);
					} else if(args[0].equalsIgnoreCase("link")) {
						return getPortals(args[1]);
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("link")) {
						return getPortals(args[2]);
					}
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("portals")) {
			if(sender.hasPermission(PermissionKey.CMD_PORTALS.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else if(args[0].equalsIgnoreCase("list")) {
						File dir = new File(pl.getDataFolder() + File.separator + "portals");
						if(dir.isDirectory()) {
							StringBuilder build = new StringBuilder();
							HashMap<String, Portal> portals = new HashMap<String, Portal>(pl.getConfiguration().getPortalConfig().getPortals());
							for(int i = 0; i < portals.values().size(); i++) {
								Portal portal = portals.values().toArray(new Portal[portals.size()])[i];
								if(i == portals.values().size()) {
									build.append(ChatColor.GREEN + "(" + ChatColor.GRAY + portal.getPortalName() + ", isLinked="+ (portal.isLinked() ? ChatColor.GREEN + "true(" + ChatColor.GRAY + portal.getLinkedPortal().getPortalName() + ChatColor.GREEN + ")" : ChatColor.RED + "false") + ChatColor.GREEN + ")");
								} else {
									build.append(ChatColor.GREEN + "(" + ChatColor.GRAY + portal.getPortalName() + ", isLinked="+ (portal.isLinked() ? ChatColor.GREEN + "true(" + ChatColor.GRAY + portal.getLinkedPortal().getPortalName() + ChatColor.GREEN + ")" : ChatColor.RED + "false") + ChatColor.GREEN + ")" + ChatColor.GRAY + ", ");
								}
							}
							sender.sendMessage(ChatColor.GOLD + ".oO___[portal list]____Oo.");
							sender.sendMessage(build.toString());
						} else {
							sendMessage("no portals where found!");
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("create")) {
						File f = new File(pl.getDataFolder() + File.separator + "portals" + File.separator + args[1] + ".yml");
						if(!f.exists()) {
							Player p = (Player) sender;
							p.setMetadata("portal", new FixedMetadataValue(pl, args[1]));
							sendMessage("now right click a block to set your first pos!");
						} else {
							sendMessage("this portal name does already exist!");
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						try {
							Portal portal = pl.getConfiguration().getPortalConfig().getPortal(args[1]);
							portal.remove();
							sendMessage("you have successfully removed portal " + args[1]);
						} catch(Exception e) {
							sendMessage("this portal does not exist!");
						}
					} else if(args[0].equalsIgnoreCase("teleport")) {
						World w = Bukkit.getWorld(args[1]);
						if(w instanceof World) {
							if(sender instanceof Player) {
								Player p = (Player) sender;
								p.teleport(w.getSpawnLocation());
								sendMessage("teleported to spawn location of the world " + w.getName());
							} else {
								getWarning(WarningType.PLAYER_ONLY);
							}
						} else {
							sendMessage("world is not loaded.");
						}
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("link")) {
						try {
							Portal portal1 = pl.getConfiguration().getPortalConfig().getPortal(args[1]);
							Portal portal2 = pl.getConfiguration().getPortalConfig().getPortal(args[2]);
							portal1.linkPortal(portal2.getPortalName(), true);
							sendMessage("you have successfully linked portal " + portal1.getPortalName() + " to " + portal2.getPortalName());
						} catch(Exception e) {
							sendMessage("one of the portals has a invalid name!");
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[portals help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals list " + ChatColor.WHITE + ": shows a list of all portal names");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals teleport <world> " + ChatColor.WHITE + ": teleports to the spawn point of a other world.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals create <portal-name> " + ChatColor.WHITE + ": creates a selection and adress a name/id to it");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals delete <portal-name> " + ChatColor.WHITE + ": delete a portal");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/portals link <portal1> <portal2> " + ChatColor.WHITE + ": links portals to each other.");
	}

}

package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.ModreqDoneEvent;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdDone extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdDone(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	private List<String> getIdsFromModreq(XOfflinePlayer off, String input) {
		List<String> list = new ArrayList<String>();
		for(Modreq mod : off.getModreqs()) {
			String id = ""+mod.getId();
			if(id.startsWith(input)) {
				list.add(""+mod.getId());
			}
		}
		return list;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("done")) {
			if(sender.hasPermission(PermissionKey.CMD_DONE.getPermission())) {
				if(args.length == 1) {
					return getPlayerByName(args[0]);
				}else if(args.length == 2) {
					XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
					return getIdsFromModreq(off, args[1]);
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("done")) {
			if(args.length == 0) {
				if(sender.hasPermission(PermissionKey.CMD_DONE.getPermission())) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[done modreq]____Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/done player <id> <message>");
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_DONE.getPermission())) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[done modreq]____Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/done player <id> <message>");
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else if(args.length == 2) {
				if(sender.hasPermission(PermissionKey.CMD_DONE.getPermission())) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						if(xp.hasModreqsOpen()) {
							try {
								int id = Integer.parseInt(args[1]);
								if(xp.isValidModreqId(id)) {
									Modreq mod = xp.getModreq(id);
									int idNumber = mod.getId();
									String title;
									if(mod.getMessage().length() > 15) {
										title = mod.getMessage().substring(0, (mod.getMessage().length()/2))+"...";	
									} else {
										title = mod.getMessage();
									}
									String author = mod.getPlayersName();
									Date date = mod.getDate();
									if(sender instanceof Player) {
										Player p = (Player) sender;
										Bukkit.getPluginManager().callEvent(new ModreqDoneEvent(xp.getPlayer(), title, "comment not defined", author, id, date, p));
									}
									xp.removeModreq(id);
									sendMessageTo(xp.getPlayer(), "staff member " + sender.getName() + " has closed your modreq.");
									sendMessageTo(xp.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
									sendMessageTo(xp.getPlayer(), "message: " + ChatColor.GREEN + title);
									sendMessageTo(xp.getPlayer(), "comment: " + ChatColor.GREEN + "comment not defined");
									for(Player a : Bukkit.getOnlinePlayers()) {
										if(a.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
											sendMessageTo(xp.getPlayer(), "staff member " + sender.getName() + " has closed " + xp.getUser() + " his modreq.");
											sendMessageTo(xp.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
											sendMessageTo(xp.getPlayer(), "message: " + ChatColor.GREEN + title);
											sendMessageTo(xp.getPlayer(), "comment: " + ChatColor.GREEN + "comment not defined");
										}
									}
								} else {
									sendMessage(ChatColor.RED + "invalid modreq id!");
								}
							} catch(NumberFormatException e) {
								sendMessage(ChatColor.RED + "second argument needs to be a number!");
							}
						} else {
							sendMessage(ChatColor.RED + "this player has no modreqs open!");
						}
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							if(off.hasModreqsOpen()) {
								try {
									int id = Integer.parseInt(args[1]);
									if(off.isValidModreqId(id)) {
										Modreq mod = off.getModreq(id);
										int idNumber = mod.getId();
										String title;
										if(mod.getMessage().length() > 15) {
											title = mod.getMessage().substring(0, (mod.getMessage().length()/2))+"...";
										} else {
											title = mod.getMessage();
										}
										off.removeModreq(id);
										String s = (ChatColor.GREEN + "staff member " + sender.getName() + " has closed your modreq.,"+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "id: " + ChatColor.GREEN + idNumber + ","+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "message: " + ChatColor.GREEN + title+","+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "comment: " + ChatColor.GREEN + "comment not defined").replace('\u00A7', '&');
										off.setModreqDoneMessage(s);
										for(Player a : Bukkit.getOnlinePlayers()) {
											if(a.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
												sendMessageTo(a.getPlayer(), "staff member " + sender.getName() + " has closed offline player " + off.getUser() + " his modreq.");
												sendMessageTo(a.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
												sendMessageTo(a.getPlayer(), "message: " + ChatColor.GREEN + title);
												sendMessageTo(a.getPlayer(), "comment: " + ChatColor.GREEN + "comment not defined");
											}
										}
									} else {
										sendMessage(ChatColor.RED + "invalid modreq id!");
									}
								} catch(NumberFormatException e) {
									sendMessage(ChatColor.RED + "second argument needs to be a number!");
								}
							} else {
								sendMessage(ChatColor.RED + "this player has no modreqs open!");
							}
						} catch(NullPointerException e) {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
						}
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else {
				if(sender.hasPermission(PermissionKey.CMD_DONE.getPermission())) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						try {
							int id = Integer.parseInt(args[1]);
							if(xp.isValidModreqId(id)) {
								Modreq mod = xp.getModreq(id);
								int idNumber = mod.getId();
								String title;
								String comment = Arrays.toString(args).replace("[", "").replace(args[0]+",", "").replace(args[1]+",", "").replace(",", "").replace("]", "").substring(2);
								if(mod.getMessage().length() > 15) {
									title = mod.getMessage().substring(0, (mod.getMessage().length()/2))+"...";
								} else {
									title = mod.getMessage();
								}
								String author = mod.getPlayersName();
								Date date = mod.getDate();
								if(sender instanceof Player) {
									Player p = (Player) sender;
									Bukkit.getPluginManager().callEvent(new ModreqDoneEvent(xp.getPlayer(), title, comment, author, id, date, p));
								}
								xp.removeModreq(id);
								sendMessageTo(xp.getPlayer(), "staff member " + sender.getName() + " has closed your modreq.");
								sendMessageTo(xp.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
								sendMessageTo(xp.getPlayer(), "message: " + ChatColor.GREEN + title);
								sendMessageTo(xp.getPlayer(), "comment: " + ChatColor.GREEN + comment);
								for(Player a : Bukkit.getOnlinePlayers()) {
									if(a.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										sendMessageTo(a.getPlayer(), "staff member " + sender.getName() + " has closed " + xp.getUser() + " his modreq.");
										sendMessageTo(a.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
										sendMessageTo(a.getPlayer(), "message: " + ChatColor.GREEN + title);
										sendMessageTo(a.getPlayer(), "comment: " + ChatColor.GREEN + comment);
									}
								}
							} else {
								sendMessage(ChatColor.RED + "invalid modreq id!");
							}
						} catch(NumberFormatException e) {
							sendMessage(ChatColor.RED + "second argument needs to be a number!");
						}
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							try {
								int id = Integer.parseInt(args[1]);
								if(off.isValidModreqId(id)) {
									Modreq mod = off.getModreq(id);
									int idNumber = mod.getId();
									String comment = Arrays.toString(args).replace("[", "").replace(args[0]+",", "").replace(args[1]+",", "").replace(",", "").replace("]", "").substring(2);
									String title;
									if(mod.getMessage().length() > 15) {
										title = mod.getMessage().substring(0, (mod.getMessage().length()/2))+"...";
									} else {
										title = mod.getMessage();
									}
									off.removeModreq(idNumber);
									String s = ("staff member " + sender.getName() + " has closed your modreq.,"+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "id: " + ChatColor.GREEN + idNumber + ","+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "message: " + ChatColor.GREEN + title+","+ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "comment: " + comment).replace('\u00A7', '&');
									off.setModreqDoneMessage(s);
									for(Player a : Bukkit.getOnlinePlayers()) {
										if(a.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
											sendMessageTo(a.getPlayer(), "staff member " + sender.getName() + " has closed offline player " + off.getUser() + " his modreq.");
											sendMessageTo(a.getPlayer(), "id: " + ChatColor.GREEN + idNumber);
											sendMessageTo(a.getPlayer(), "message: " + ChatColor.GREEN + title);
											sendMessageTo(a.getPlayer(), "comment: " + ChatColor.GREEN + comment);
										}
									}
								} else {
									sendMessage(ChatColor.RED + "invalid modreq id!");
								}
							} catch(NumberFormatException e) {
								sendMessage(ChatColor.RED + "second argument needs to be a number!");
							}
						} catch(NullPointerException e) {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
						}
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			}
		}
		return false;
	}

}

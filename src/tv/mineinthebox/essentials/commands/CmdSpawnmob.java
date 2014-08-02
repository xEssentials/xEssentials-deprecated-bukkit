package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSpawnmob {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			if(sender.hasPermission(PermissionKey.CMD_SPAWN_MOB.getPermission())) {
				if(args.length == 0) {
					sendHelp(sender);
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sendHelp(sender);
					} else if(args[0].equalsIgnoreCase("wither")) {
						sender.sendMessage(ChatColor.RED + "you are not allowed to spawn withers");
					} else if(args[0].equalsIgnoreCase("player")) {
						sender.sendMessage(ChatColor.RED + "you are not allowed to spawn null players");
					} else {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							try {
								EntityType entity = EntityType.valueOf(args[0].toUpperCase());
								if(entity.isAlive()) {
									if(entity == EntityType.SKELETON) {
										Skeleton e = (Skeleton) p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										e.getEquipment().setItemInHand(new ItemStack(Material.BOW));
									} else {
										p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);	
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this is not a mob, but a other entity!");
								}
							} catch(IllegalArgumentException e) {
								sender.sendMessage(ChatColor.RED + "invalid mob");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				} else if(args.length == 2) {
					if(args[1].equalsIgnoreCase("type")) {
						try {
							EntityType entity = EntityType.valueOf(args[0].toUpperCase());
							if(entity == EntityType.PLAYER) {
								sender.sendMessage(ChatColor.RED + "players are not supported");
							} else if(entity == EntityType.WITHER) {
								sender.sendMessage(ChatColor.RED + "withers are not supported");
							} else if(entity == EntityType.HORSE) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type horses]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, donkey, zombie, skeleton, baby");
							} else if(entity == EntityType.CHICKEN) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type chickens]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.COW) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type cows]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.CREEPER) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type creepers]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, charged");
							} else if(entity == EntityType.MUSHROOM_COW) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type mushroom cows]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.OCELOT) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type ocelots]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.PIG) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type pigs]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.PIG_ZOMBIE) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type zombie pig mans]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.SHEEP) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type sheeps]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.SKELETON) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type skeleton]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, wither");
							} else if(entity == EntityType.VILLAGER) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type villagers]___Oo.");
								StringBuilder build = new StringBuilder();
								for(Profession pro : Profession.values()) {
									build.append(pro.name() + ", ").toString();
								}
								sender.sendMessage(ChatColor.GRAY + "normal, baby, " + build.toString().toLowerCase());
							} else if(entity == EntityType.WOLF) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type wolfs]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby");
							} else if(entity == EntityType.ZOMBIE) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[type zombies]___Oo.");
								sender.sendMessage(ChatColor.GRAY + "normal, baby, zombie_villager");
							} else {
								sender.sendMessage(ChatColor.RED + "this mob does not support any types");
							}
						} catch(IllegalArgumentException e) {
							sender.sendMessage(ChatColor.RED + "invalid mob");
						}
					} else if(args[1].equalsIgnoreCase("color")) {
						try {
							EntityType entity = EntityType.valueOf(args[0].toUpperCase());
							if(entity == EntityType.PLAYER) {
								sender.sendMessage(ChatColor.RED + "players are not supported");
							} else if(entity == EntityType.WITHER) {
								sender.sendMessage(ChatColor.RED + "withers are not supported");
							} else if(entity == EntityType.HORSE) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[color horses for the type normal]___Oo.");
								StringBuilder build = new StringBuilder();
								for(Color col : Horse.Color.values()) {
									build.append(col.name() + ", ").toString();
								}
								sender.sendMessage(ChatColor.GRAY + build.toString().toLowerCase());
							} else if(entity == EntityType.SHEEP) {
								StringBuilder build = new StringBuilder();
								for(DyeColor col : DyeColor.values()) {
									build.append(col.name() + ", ").toString();
								}
								sender.sendMessage(ChatColor.GRAY + build.toString());
								sender.sendMessage(ChatColor.GOLD + ".oO___[color sheeps for the type normal]___Oo.");
								sender.sendMessage(ChatColor.GRAY + build.toString().toLowerCase());
							} else {
								sender.sendMessage(ChatColor.RED + "this mob does not support any colors!");
							}
						} catch(IllegalArgumentException e) {
							sender.sendMessage(ChatColor.RED + "invalid mob");
						}
					} else {
						try {
							EntityType entity = EntityType.valueOf(args[0].toUpperCase());
							if(entity == EntityType.PLAYER) {
								sender.sendMessage(ChatColor.RED + "you are not allowed to spawn null players");
							} else if(entity == EntityType.WITHER) {
								sender.sendMessage(ChatColor.RED + "you are not allowed to spawn withers");
							} else {
								Player victem = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getPlayer();
								if(victem instanceof Player) {
									if(entity.isAlive()) {
										victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);	
									} else {
										sender.sendMessage(ChatColor.RED + "this is not a mob, but a other entity!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you cannot spawn mobs to a players offline location!");
								}
							}
						} catch(IllegalArgumentException e) {
							sender.sendMessage("invalid mob");
						}
					}
				} else if(args.length == 3) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(args[1].equalsIgnoreCase("type")) {
							try {
								EntityType entity = EntityType.valueOf(args[0].toUpperCase());
								if(entity == EntityType.PLAYER) {
									sender.sendMessage(ChatColor.RED + "you are not allowed to spawn null players");
								} else if(entity == EntityType.WITHER) {
									sender.sendMessage(ChatColor.RED + "you are not allowed to spawn withers");
								} else if(entity == EntityType.HORSE) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal horse near you!");
									} else if(args[2].equalsIgnoreCase("donkey")) {
										Entity spawned_entity = p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										Horse horse = (Horse) spawned_entity;
										horse.setVariant(Variant.DONKEY);
										sender.sendMessage(ChatColor.GREEN + "spawned a donkey horse near you!");
									} else if(args[2].equalsIgnoreCase("zombie")) {
										Entity spawned_entity = p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										Horse horse = (Horse) spawned_entity;
										horse.setVariant(Variant.UNDEAD_HORSE);
										sender.sendMessage(ChatColor.GREEN + "spawned a zombie horse near you!");
									} else if(args[2].equalsIgnoreCase("skeleton")) {
										Entity spawned_entity = p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										Horse horse = (Horse) spawned_entity;
										horse.setVariant(Variant.SKELETON_HORSE);
										sender.sendMessage(ChatColor.GREEN + "spawned a skeleton horse near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity spawned_entity = p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										Horse horse = (Horse) spawned_entity;
										horse.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby horse near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.CHICKEN) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal chicken near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity baby = p.getLocation().getWorld().spawnEntity(p.getLocation(), entity);
										Chicken chick = (Chicken) baby;
										chick.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby chicken near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.COW) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getPlayer().getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal cow near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity cowE = p.getPlayer().getWorld().spawnEntity(p.getLocation(), entity);
										Cow cow = (Cow) cowE;
										cow.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby cow near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.CREEPER) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a creeper near you!");
									} else if(args[2].equalsIgnoreCase("charged")) {
										Entity cEntity = p.getWorld().spawnEntity(p.getLocation(), entity);
										Creeper creeper = (Creeper) cEntity;
										creeper.setPowered(true);
										sender.sendMessage(ChatColor.GREEN + "spawned a charged creeper near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.MUSHROOM_COW) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal mushroom cow near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity bEntity = p.getWorld().spawnEntity(p.getLocation(), entity);
										MushroomCow cow = (MushroomCow) bEntity;
										cow.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby mushroom cow near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.OCELOT) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal ocelot near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Ocelot ocelot = (Ocelot) e;
										ocelot.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby ocelot near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.PIG) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal pig near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Pig pig = (Pig) e;
										pig.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby pig near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.PIG_ZOMBIE) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal pig zombie near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										PigZombie pig = (PigZombie) e;
										pig.setBaby(true);
										sender.sendMessage(ChatColor.GREEN + "spawned a baby pig zombie near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.SHEEP) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a normal sheep near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Sheep sheep = (Sheep) e;
										sheep.setBaby();
										sender.sendMessage(ChatColor.GREEN + "spawned a baby sheep near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.SKELETON) {
									if(args[2].equalsIgnoreCase("normal")) {
										Skeleton e = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), entity);
										e.getEquipment().setItemInHand(new ItemStack(Material.BOW));
										sender.sendMessage(ChatColor.GREEN + "spawned a normal skeleton near you!");
									} else if(args[2].equalsIgnoreCase("wither")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Skeleton skeleton = (Skeleton) e;
										skeleton.setSkeletonType(SkeletonType.WITHER);
										skeleton.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
										sender.sendMessage(ChatColor.GREEN + "spawned a wither skeleton near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.VILLAGER) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										sender.sendMessage(ChatColor.GREEN + "spawned a villager near you!");
									} else if(args[2].equalsIgnoreCase("blacksmith")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Villager villager = (Villager) e;
										villager.setProfession(Profession.BLACKSMITH);
										sender.sendMessage(ChatColor.GREEN + "spawned a blacksmith villager near you!");
									} else if(args[2].equalsIgnoreCase("butcher")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Villager villager = (Villager) e;
										villager.setProfession(Profession.BUTCHER);
										sender.sendMessage(ChatColor.GREEN + "spawned a butcher villager near you!");
									} else if(args[2].equalsIgnoreCase("farmer")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Villager villager = (Villager) e;
										villager.setProfession(Profession.FARMER);
										sender.sendMessage(ChatColor.GREEN + "spawned a farmer villager near you!");
									} else if(args[2].equalsIgnoreCase("librarian")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Villager villager = (Villager) e;
										villager.setProfession(Profession.LIBRARIAN);
										sender.sendMessage(ChatColor.GREEN + "spawned a librarian villager near you!");
									} else if(args[2].equalsIgnoreCase("priest")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Villager villager = (Villager) e;
										villager.setProfession(Profession.PRIEST);
										sender.sendMessage(ChatColor.GREEN + "spawned a priest villager near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.WOLF) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										p.sendMessage(ChatColor.GREEN + "spawned a normal wolf near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Wolf wolf = (Wolf) e;
										wolf.setBaby();
										p.sendMessage(ChatColor.GREEN + "spawned a baby wolf near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else if(entity == EntityType.ZOMBIE) {
									if(args[2].equalsIgnoreCase("normal")) {
										p.getWorld().spawnEntity(p.getLocation(), entity);
										p.sendMessage(ChatColor.GREEN + "spawned a normal zombie near you!");
									} else if(args[2].equalsIgnoreCase("baby")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Zombie zombie = (Zombie) e;
										zombie.setBaby(true);
										p.sendMessage(ChatColor.GREEN + "spawned a baby zombie near you!");
									} else if(args[2].equalsIgnoreCase("zombie_villager")) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Zombie zombie = (Zombie) e;
										zombie.setVillager(true);
										p.sendMessage(ChatColor.GREEN + "spawned a villager zombie near you!");
									} else {
										sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this mob does not have any types use /spawnmob <mob> type instead!");
								}
							} catch(IllegalArgumentException e) {
								sender.sendMessage(ChatColor.RED + "invalid mob");
							}
						} else if(args[1].equalsIgnoreCase("color")) {
							try {
								EntityType entity = EntityType.valueOf(args[0].toUpperCase());
								try {
									if(entity == EntityType.HORSE) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Horse horse = (Horse) e;
										horse.setColor(Horse.Color.valueOf(args[2].toUpperCase()));
									} else if(entity == EntityType.SHEEP) {
										Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
										Sheep sheep = (Sheep) e;
										sheep.setColor(DyeColor.valueOf(args[2].toUpperCase()));
									}	
								} catch(IllegalArgumentException e) {
									sender.sendMessage(ChatColor.RED + "invalid type color");
								}
							} catch(IllegalArgumentException e) {
								sender.sendMessage(ChatColor.RED + "invalid mob");
							}
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 4) {
					Player p = (Player) sender;
					if(p instanceof Player) {
						Player victem = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[3]).getPlayer();
						if(victem instanceof Player) {
							if(args[1].equalsIgnoreCase("type")) {
								try {
									EntityType entity = EntityType.valueOf(args[0].toUpperCase());
									if(entity == EntityType.PLAYER) {
										sender.sendMessage(ChatColor.RED + "you are not allowed to spawn null players");
									} else if(entity == EntityType.WITHER) {
										sender.sendMessage(ChatColor.RED + "you are not allowed to spawn withers");
									} else if(entity == EntityType.HORSE) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal horse near you!");
										} else if(args[2].equalsIgnoreCase("donkey")) {
											Entity spawned_entity = victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											Horse horse = (Horse) spawned_entity;
											horse.setVariant(Variant.DONKEY);
											sender.sendMessage(ChatColor.GREEN + "teleported a donkey horse near you!");
										} else if(args[2].equalsIgnoreCase("zombie")) {
											Entity spawned_entity = victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											Horse horse = (Horse) spawned_entity;
											horse.setVariant(Variant.UNDEAD_HORSE);
											sender.sendMessage(ChatColor.GREEN + "teleported a zombie horse near you!");
										} else if(args[2].equalsIgnoreCase("skeleton")) {
											Entity spawned_entity = victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											Horse horse = (Horse) spawned_entity;
											horse.setVariant(Variant.SKELETON_HORSE);
											sender.sendMessage(ChatColor.GREEN + "teleported a skeleton horse near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity spawned_entity = victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											Horse horse = (Horse) spawned_entity;
											horse.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby horse near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.CHICKEN) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal chicken near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity baby = victem.getLocation().getWorld().spawnEntity(victem.getLocation(), entity);
											Chicken chick = (Chicken) baby;
											chick.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby chicken near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.COW) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getPlayer().getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal cow near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity cowE = victem.getPlayer().getWorld().spawnEntity(victem.getLocation(), entity);
											Cow cow = (Cow) cowE;
											cow.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby cow near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.CREEPER) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a creeper near you!");
										} else if(args[2].equalsIgnoreCase("charged")) {
											Entity cEntity = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Creeper creeper = (Creeper) cEntity;
											creeper.setPowered(true);
											sender.sendMessage(ChatColor.GREEN + "teleported a charged creeper near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.MUSHROOM_COW) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal mushroom cow near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity bEntity = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											MushroomCow cow = (MushroomCow) bEntity;
											cow.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby mushroom cow near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.OCELOT) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal ocelot near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Ocelot ocelot = (Ocelot) e;
											ocelot.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby ocelot near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.PIG) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal pig near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Pig pig = (Pig) e;
											pig.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby pig near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.PIG_ZOMBIE) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal pig zombie near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											PigZombie pig = (PigZombie) e;
											pig.setBaby(true);
											sender.sendMessage(ChatColor.GREEN + "teleported a baby pig zombie near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.SHEEP) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a normal sheep near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Sheep sheep = (Sheep) e;
											sheep.setBaby();
											sender.sendMessage(ChatColor.GREEN + "teleported a baby sheep near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.SKELETON) {
										if(args[2].equalsIgnoreCase("normal")) {
											Skeleton e = (Skeleton) victem.getWorld().spawnEntity(victem.getLocation(), entity);
											e.getEquipment().setItemInHand(new ItemStack(Material.BOW));
											sender.sendMessage(ChatColor.GREEN + "teleported a normal skeleton near you!");
										} else if(args[2].equalsIgnoreCase("wither")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Skeleton skeleton = (Skeleton) e;
											skeleton.setSkeletonType(SkeletonType.WITHER);
											skeleton.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
											sender.sendMessage(ChatColor.GREEN + "teleported a wither skeleton near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.VILLAGER) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											sender.sendMessage(ChatColor.GREEN + "teleported a villager near you!");
										} else if(args[2].equalsIgnoreCase("blacksmith")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Villager villager = (Villager) e;
											villager.setProfession(Profession.BLACKSMITH);
											sender.sendMessage(ChatColor.GREEN + "teleported a blacksmith villager near you!");
										} else if(args[2].equalsIgnoreCase("butcher")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Villager villager = (Villager) e;
											villager.setProfession(Profession.BUTCHER);
											sender.sendMessage(ChatColor.GREEN + "teleported a butcher villager near you!");
										} else if(args[2].equalsIgnoreCase("farmer")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Villager villager = (Villager) e;
											villager.setProfession(Profession.FARMER);
											sender.sendMessage(ChatColor.GREEN + "teleported a farmer villager near you!");
										} else if(args[2].equalsIgnoreCase("librarian")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Villager villager = (Villager) e;
											villager.setProfession(Profession.LIBRARIAN);
											sender.sendMessage(ChatColor.GREEN + "teleported a librarian villager near you!");
										} else if(args[2].equalsIgnoreCase("priest")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Villager villager = (Villager) e;
											villager.setProfession(Profession.PRIEST);
											sender.sendMessage(ChatColor.GREEN + "teleported a priest villager near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.WOLF) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											victem.sendMessage(ChatColor.GREEN + "teleported a normal wolf near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Wolf wolf = (Wolf) e;
											wolf.setBaby();
											victem.sendMessage(ChatColor.GREEN + "teleported a baby wolf near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else if(entity == EntityType.ZOMBIE) {
										if(args[2].equalsIgnoreCase("normal")) {
											victem.getWorld().spawnEntity(victem.getLocation(), entity);
											victem.sendMessage(ChatColor.GREEN + "teleported a normal zombie near you!");
										} else if(args[2].equalsIgnoreCase("baby")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Zombie zombie = (Zombie) e;
											zombie.setBaby(true);
											victem.sendMessage(ChatColor.GREEN + "teleported a baby zombie near you!");
										} else if(args[2].equalsIgnoreCase("zombie_villager")) {
											Entity e = victem.getWorld().spawnEntity(victem.getLocation(), entity);
											Zombie zombie = (Zombie) e;
											zombie.setVillager(true);
											victem.sendMessage(ChatColor.GREEN + "teleported a villager zombie near you!");
										} else {
											sender.sendMessage(ChatColor.RED + "this type does not exist! " + args[2]);
										}
									} else {
										sender.sendMessage(ChatColor.RED + "this mob does not have any types use /spawnmob <mob> type instead!");
									}
								} catch(IllegalArgumentException e) {
									sender.sendMessage(ChatColor.RED + "invalid mob");
								}
							} else if(args[1].equalsIgnoreCase("color")) {
								try {
									EntityType entity = EntityType.valueOf(args[0].toUpperCase());
									try {
										if(entity == EntityType.HORSE) {
											Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
											Horse horse = (Horse) e;
											horse.setColor(Horse.Color.valueOf(args[2].toUpperCase()));
										} else if(entity == EntityType.SHEEP) {
											Entity e = p.getWorld().spawnEntity(p.getLocation(), entity);
											Sheep sheep = (Sheep) e;
											sheep.setColor(DyeColor.valueOf(args[2].toUpperCase()));
										}	
									} catch(IllegalArgumentException e) {
										sender.sendMessage(ChatColor.RED + "invalid type color");
									}
								} catch(IllegalArgumentException e) {
									sender.sendMessage(ChatColor.RED + "invalid mob");
								}
							}
						} else {
							sender.sendMessage(ChatColor.RED + args[3] + "is not online");
						}
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			}
		} else {
			Warnings.getWarnings(sender).noPermission();
		}
		return false;
	}

	private void sendHelp(CommandSender sender) {
		StringBuilder build = new StringBuilder();
		for(EntityType entity : EntityType.values()) {
			if(entity.isAlive()) {
				if(entity == EntityType.PLAYER) {

				} else if(entity == EntityType.WITHER) {

				} else {
					build.append(entity.name() + ", ").toString();
				}
			}
		}
		sender.sendMessage(ChatColor.GOLD + ".oO___[spawnmob]___Oo.");
		sender.sendMessage(ChatColor.GRAY + build.toString().toLowerCase());
		sender.sendMessage(ChatColor.GOLD + ".oO___[spawnmob types/colors]___Oo.");
		sender.sendMessage(ChatColor.GRAY + "/spawnmob <mobname> - spawns a mob");
		sender.sendMessage(ChatColor.GRAY + "/spawnmob <mobname> color <red> - spawns a mob with colour");
		sender.sendMessage(ChatColor.GRAY + "/spawnmob <mobname> type <zombie> - spawns a type mob");
	}

}

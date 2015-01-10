package tv.mineinthebox.essentials.minigames.football;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.Teamable;
import tv.mineinthebox.essentials.minigames.football.events.FootballPassEvent;
import tv.mineinthebox.essentials.minigames.football.gear.BlueTeamGear;
import tv.mineinthebox.essentials.minigames.football.gear.RedTeamGear;

public class FootballArena extends Teamable implements MinigameArena {

	private final File f;
	private final FileConfiguration con;

	private final Set<XPlayer> blueteam = new HashSet<XPlayer>();
	private final Set<XPlayer> redteam = new HashSet<XPlayer>();
	private final RedTeamGear redgear = new RedTeamGear();
	private final BlueTeamGear bluegear = new BlueTeamGear();

	private int bluescore = 0;
	private int redscore = 0;

	private boolean isStarted = false;
	private Slime slime;

	private BukkitTask listener_task;

	public FootballArena(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}

	public int getMaxPlayersAllowed() {
		return con.getInt("game.maxplayers-allowed");
	}

	public Set<XPlayer> getBlueTeam() {
		return blueteam;
	}

	public Set<XPlayer> getRedTeam() {
		return redteam;
	}

	public Set<XPlayer> getPlayers() {
		Set<XPlayer> players = new HashSet<XPlayer>();
		players.addAll(getBlueTeam());
		players.addAll(getRedTeam());
		return players;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public boolean isFull() {
		return ((blueteam.size() + redteam.size()) == getMaxPlayersAllowed() ? true : false);
	}

	public void addPlayer(XPlayer xp) {
		if(!blueteam.contains(xp) && !redteam.contains(xp) && !isFull()) {
			if(redteam.size() < blueteam.size()) {
				addMeta(xp);
				redteam.add(xp);
				xp.saveInventory();
				xp.getPlayer().teleport(getRedTeamSpawn());
				xp.getPlayer().getInventory().setArmorContents(null);
				xp.getPlayer().getInventory().clear();
				xp.getPlayer().getInventory().setHelmet(redgear.getHelmet());
				xp.getPlayer().getInventory().setChestplate(redgear.getChestPlate());
				xp.getPlayer().getInventory().setLeggings(redgear.getLeggings());
				xp.getPlayer().getInventory().setBoots(redgear.getBoots());
				Bukkit.broadcastMessage(ChatColor.GRAY + xp.getUser() + " has joined the football arena " + getName() + " in team red (" + (redteam.size()+blueteam.size()) + "/" + this.getMaxPlayersAllowed()+")");
			} else if(blueteam.size() < redteam.size()) {
				addMeta(xp);
				blueteam.add(xp);
				xp.saveInventory();
				xp.getPlayer().teleport(getBlueTeamSpawn());
				xp.getPlayer().getInventory().setArmorContents(null);
				xp.getPlayer().getInventory().clear();
				xp.getPlayer().getInventory().setHelmet(bluegear.getHelmet());
				xp.getPlayer().getInventory().setChestplate(bluegear.getChestPlate());
				xp.getPlayer().getInventory().setLeggings(bluegear.getLeggings());
				xp.getPlayer().getInventory().setBoots(bluegear.getBoots());
				Bukkit.broadcastMessage(ChatColor.GRAY + xp.getUser() + " has joined the football arena " + getName() + " in team blue (" + (redteam.size()+blueteam.size()) + "/" + this.getMaxPlayersAllowed()+")");
			} else if(redteam.size() == blueteam.size()) {
				addMeta(xp);
				redteam.add(xp);
				xp.saveInventory();
				xp.getPlayer().teleport(getRedTeamSpawn());
				xp.getPlayer().getInventory().setArmorContents(null);
				xp.getPlayer().getInventory().clear();
				xp.getPlayer().getInventory().setHelmet(redgear.getHelmet());
				xp.getPlayer().getInventory().setChestplate(redgear.getChestPlate());
				xp.getPlayer().getInventory().setLeggings(redgear.getLeggings());
				xp.getPlayer().getInventory().setBoots(redgear.getBoots());
				Bukkit.broadcastMessage(ChatColor.GRAY + xp.getUser() + " has joined the football arena " + getName() + " in team red (" + (redteam.size()+blueteam.size()) + "/" + this.getMaxPlayersAllowed()+")");
			}
			startSchedule();
		}
	}

		private void startSchedule() {
			if(isFull()) {
				new BukkitRunnable() {

					private int i = 10;

					@Override
					public void run() {
						if(i == 0) {
							for(XPlayer xp : getPlayers()) {
								xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
								xp.getPlayer().sendMessage(ChatColor.GRAY + "game has been started!");
							}
							isStarted = true;
							slime = (Slime)getDefaultBallLocation().getWorld().spawnEntity(getDefaultBallLocation(), EntityType.SLIME);
							slime.setCustomName(ChatColor.GOLD + "football");
							slime.setCustomNameVisible(true);
							slime.setSize(1);
							initalizeSlimeMove();
							cancel();
						} else {
							for(XPlayer xp : getPlayers()) {
								xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.NOTE_PLING, 1F, 1F);
								xp.getPlayer().sendMessage(ChatColor.GRAY + "countdown: " + i);
							}
						}
						i--;
					}

				}.runTaskTimer(xEssentials.getPlugin(), 5L, 10L);
			}
		}

	public Location getDefaultBallLocation() {
		World w = Bukkit.getWorld(con.getString("game.ball.world"));
		int x = con.getInt("game.ball.x");
		int y = con.getInt("game.ball.y");
		int z = con.getInt("game.ball.z");
		return new Location(w, x, y, z);
	}

	public Location getRedTeamSpawn() {
		World w = Bukkit.getWorld(con.getString("game.red.spawn.world"));
		double x = con.getDouble("game.red.spawn.x");
		double y = con.getDouble("game.red.spawn.y");
		double z = con.getDouble("game.red.spawn.z");
		int yaw = con.getInt("game.red.spawn.yaw");
		int pitch = con.getInt("game.red.spawn.pitch");
		return new Location(w, x, y, z, yaw, pitch);
	}

	public Location getBlueTeamSpawn() {
		World w = Bukkit.getWorld(con.getString("game.blue.spawn.world"));
		double x = con.getDouble("game.blue.spawn.x");
		double y = con.getDouble("game.blue.spawn.y");
		double z = con.getDouble("game.blue.spawn.z");
		int yaw = con.getInt("game.blue.spawn.yaw");
		int pitch = con.getInt("game.blue.spawn.pitch");
		return new Location(w, x, y, z, yaw, pitch);
	}

	private List<Location> getBlueGoalLocations() {
		List<Location> locs = new ArrayList<Location>();
		for(String serialized : con.getStringList("game.blue.locations")) {
			String[] split = serialized.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			locs.add(new Location(w, x, y, z));
		}
		return locs;
	}

	private List<Location> getRedGoalLocations() {
		List<Location> locs = new ArrayList<Location>();
		for(String serialized : con.getStringList("game.red.locations")) {
			String[] split = serialized.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			locs.add(new Location(w, x, y, z));
		}
		return locs;
	}

	private void initalizeSlimeMove() {
		this.listener_task = new BukkitRunnable() {

			@Override
			public void run() {

				XPlayer p = null;

				if(slime != null && !slime.isDead()) {
					List<Entity> entities = slime.getNearbyEntities(2, 3, 2);
					if(entities.size() > 0) {
						Entity entity = entities.get(0);
						if(entity instanceof Player) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(((Player)entity).getName());
							if(getPlayers().contains(xp)) {
								p = xp;
								slime.setVelocity(xp.getPlayer().getLocation().getDirection().multiply(10).normalize());	
							}
						}
					}
					if(p != null) {
						if(hasScored(p, slime.getLocation())) {
							FootballPassEvent event = new FootballPassEvent(p, getArena(), true);
							Bukkit.getPluginManager().callEvent(event);
							if(!event.hasWon()) {
								if(slime != null) {
									slime.remove();
									slime = null;
									slime = (Slime)getDefaultBallLocation().getWorld().spawnEntity(getDefaultBallLocation(), EntityType.SLIME);
									slime.setCustomName(ChatColor.GOLD + "football");
									slime.setCustomNameVisible(true);
									slime.setSize(1);
								} else {
									cancel();
								}
							}
						} else {
							Bukkit.getPluginManager().callEvent(new FootballPassEvent(p, getArena(), false));
						}	
					}
				} else {
					cancel();
				}
			}

		}.runTaskTimer(xEssentials.getPlugin(), 0L, 1L);
	}

	private FootballArena getArena() {
		return this;
	}

	private boolean hasScored(XPlayer xp, Location location) {
		Location loc = location.getBlock().getLocation().add(0, -1, 0);
		Location loc2 = location.getBlock().getLocation().add(0, -2, 0);
		if(getRedTeam().contains(xp)) {
			return (getBlueGoalLocations().contains(loc) || getBlueGoalLocations().contains(loc2) ? true : false);
		} else if(getBlueTeam().contains(xp))  {
			return (getRedGoalLocations().contains(loc) || getRedGoalLocations().contains(loc2) ? true : false);
		}
		return false;
	}

	public int getMaxScore() {
		return con.getInt("score");
	}

	public int getRedScore() {
		return redscore;
	}

	public int getBlueScore() {
		return bluescore;
	}

	public void setBlueScore(int score) {
		bluescore += score;
	}

	public void setRedScore(int score) {
		redscore += score;
	}

	@Override
	public MinigameType getType() {
		return MinigameType.FOOT_BALL;
	}

	@Override
	public String getName() {
		return f.getName().replace(".yml", "");
	}

	@Override
	public void remove() {
		xEssentials.getManagers().getMinigameManager().removeMinigame(getType(), this);
		f.delete();
	}

	@Override
	public void removePlayer(XPlayer xp) {
		if(blueteam.contains(xp)) {
			removeMeta(xp);
			xp.loadInventory();
			blueteam.remove(xp);
			xp.getPlayer().chat("/spawn");
		} else if(redteam.contains(xp)) {
			removeMeta(xp);
			xp.getPlayer().removeMetadata("gameType", xEssentials.getPlugin());
			xp.getPlayer().removeMetadata("game", xEssentials.getPlugin());
			xp.loadInventory();
			redteam.remove(xp);
			xp.getPlayer().chat("/spawn");
		}
	}

	@Override
	public void reset() {
		if(!this.isStarted) {
			return;
		}
		this.isStarted = false;
		if(this.listener_task != null) {
			this.listener_task.cancel();
			this.listener_task = null;
		}
		this.slime.remove();
		this.slime = null;
		this.bluescore = 0;
		this.redscore = 0;
		Iterator<XPlayer> it = getPlayers().iterator();
		while(it.hasNext()) {
			removePlayer(it.next());
		}
	}

	@Override
	public void sentReward(XPlayer xp) {
		if(Hooks.isVaultEcoEnabled()) {
			xEssentials.getManagers().getVaultManager().desposit(xp.getPlayer(), con.getDouble("reward"));
		}
	}

	@Override
	public double getReward() {
		return con.getDouble("reward");
	}

	@Override
	public void broadcastMessage(String message) {
		for(XPlayer xp : getPlayers()) {
			xp.getPlayer().sendMessage(message);
		}
	}

	@Override
	public void removeMeta(XPlayer xp) {
		if(xp.getPlayer().hasMetadata("gameType") && xp.getPlayer().hasMetadata("game")) {
			xp.getPlayer().removeMetadata("gameType", xEssentials.getPlugin());
			xp.getPlayer().removeMetadata("game", xEssentials.getPlugin());
		}
	}

	@Override
	public void addMeta(XPlayer xp) {
		xp.getPlayer().setMetadata("gameType", new FixedMetadataValue(xEssentials.getPlugin(), getType()));
		xp.getPlayer().setMetadata("game", new FixedMetadataValue(xEssentials.getPlugin(), getName()));
	}
	
}

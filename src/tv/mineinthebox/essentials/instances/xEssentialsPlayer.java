package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.events.players.FakeNukeEvent;
import tv.mineinthebox.essentials.helpers.EffectType;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.plugin.arena.MinigameArena;
import tv.mineinthebox.essentials.minigames.plugin.session.MinigameSession;

@SuppressWarnings("deprecation")
public class xEssentialsPlayer implements XPlayer {

	private final xEssentials pl;
	private final Player player;
	private final File f;
	private final FileConfiguration con;
	private Item Potato;
	private AlternateAccount accounts;
	private BukkitTask spectate;
	private BukkitTask pwnagetask;
	private MinigameArena arena;
	private MinigameSession session;
	private EffectType effect;
	private BukkitTask effect_task;

	public xEssentialsPlayer(Player player, String UUID, xEssentials pl) {
		this.pl = pl;
		if(pl.getConfiguration().getDebugConfig().isEnabled()) {
			xEssentials.log("setting profile for player " + player.getName(), LogType.DEBUG);
		}
		this.player = player;
		if(Bukkit.getOnlineMode()) {
			this.f = new File(pl.getDataFolder() + File.separator + "players"+File.separator+UUID+".yml");	
		} else {
			this.f = new File(pl.getDataFolder() + File.separator + "players"+File.separator+player.getName().toLowerCase()+".yml");
		}
		if(this.f.exists()){
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("profile found, checking for a possible name change!", LogType.DEBUG);
			}
			this.con = YamlConfiguration.loadConfiguration(this.f);
			if(!this.con.getString("user").equalsIgnoreCase(player.getName())) {
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("the players name is changed from " + this.con.getString("user") + " to " + player.getName(), LogType.DEBUG);
				}
				String oldName = this.con.getString("user");
				this.con.set("user", player.getName());
				this.con.set("ip", player.getAddress().getAddress().getHostAddress());
				try {
					this.con.save(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//call the custom event whenever we noticed the name has been changed!
				//Bukkit.getPluginManager().callEvent(new PlayerNameChangeEvent(oldName, player.getName(), player, this));
				setNameHistory(oldName);
			} else {
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("players name is still intact and matches with: " + UUID, LogType.DEBUG);
				}
				this.con.set("ip", player.getAddress().getAddress().getHostAddress());
				try {
					this.con.save(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			//check if the player file is just a normal name if it is we rename the file to the UUID spec, then cancelling futher code execution.
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("profile not found, checking for non converted names in order to convert...", LogType.DEBUG);
			}
			File possiblename = new File(pl.getDataFolder() + File.separator + "players" + File.separator + player.getName().toLowerCase() + ".yml");
			if(possiblename.exists()) {
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("profile of "+player.getName()+" has been successfull found and renamed to the uuid spec", LogType.DEBUG);
				}
				possiblename.renameTo(this.f);
				this.con = YamlConfiguration.loadConfiguration(this.f);
				return;
			}
			this.con = YamlConfiguration.loadConfiguration(this.f);
			this.con.set("isDefault", true);
			this.con.set("ip", player.getAddress().getAddress().getHostAddress());
			this.con.set("user", player.getName());
			this.con.set("fly", false);
			this.con.set("torch", false);
			this.con.set("firefly", false);
			this.con.set("uuid", player.getUniqueId().toString());
			if(pl.getConfiguration().getEconomyConfig().isEconomyEnabled()){
				this.con.set("money", pl.getConfiguration().getEconomyConfig().getStartersMoney());
			}
			try {
				this.con.save(this.f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setLastLoginTime(System.currentTimeMillis());
		save();
	}
	
	@Override
	public String getName() {
		if(!player.getName().equalsIgnoreCase(con.getString("user"))) {
			con.set("user", player.getName());
		}
		return con.getString("user");
	}
	
	@Override
	public boolean hasSession() {
		return session != null;
	}

	@Override
	public MinigameSession getSession() {
		return session;
	}

	@Override
	public void setSession(MinigameSession sess) {
		this.session = sess;
	}
	
	@Override
	public boolean isInArena() {
		return (arena != null);
	}

	@Override
	public MinigameArena getArena() {
		return arena;
	}

	@Override
	public void setArena(MinigameArena arena) {
		this.arena = arena;
	}

	@Override
	public boolean isOnline() {
		return true;
	}
	
	@Override
	public boolean isGreyListed() {
		if(con.contains("isDefault")) {
			if(!con.getBoolean("isDefault")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void setGreyListed(boolean bol) {
		con.set("isDefault", !bol);
	}

	@Override
	public void setPowerTool(ItemStack item, String command) {
		con.set("powertool."+item.getType().name()+".command", command);
	}

	@Override
	public boolean hasPowerTool() {
		if(con.contains("powertool."+player.getItemInHand().getType().name()+".command")) {
			return con.contains("powertool."+player.getItemInHand().getType().name());
		}
		return false;
	}

	@Override
	public String getPowerTool() {
		return con.getString("powertool."+player.getItemInHand().getType().name()+".command");
	}

	@Override
	public void removePowerTool() {
		con.set("powertool."+player.getItemInHand().getType().name()+".command", null);
	}

	@Override
	public boolean hasLoginTask() {
		if(con.contains("task.command")) {
			return true;
		}
		return false;
	}

	@Override
	public void nuke() {
		for(int x = -16; x <= 16; x +=8) {
			for(int z = -16; z<=16; z +=8) {
				player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getBlockX()+x,100,player.getLocation().getBlockZ()+z), EntityType.PRIMED_TNT);		
			}
		}
	}

	@Override
	public void saveLastLocation() {
		con.set("lastLocation.x", player.getLocation().getBlockX());
		con.set("lastLocation.y", player.getLocation().getBlockY());
		con.set("lastLocation.z", player.getLocation().getBlockZ());
		con.set("lastLocation.yaw", player.getLocation().getYaw());
		con.set("lastLocation.pitch", player.getLocation().getPitch());
		con.set("lastLocation.world", player.getWorld().getName());
	}

	@Override
	public void fakenuke() {
		for(int x = -16; x <= 16; x +=8) {
			for(int z = -16; z<=16; z +=8) {
				Location loc = new Location(player.getWorld(), player.getLocation().getBlockX()+x, 100, player.getLocation().getBlockZ()+z);
				if(!isFallingBreakAble(loc.getBlock())) {
					FallingBlock fall = player.getWorld().spawnFallingBlock(loc, Material.TNT, (byte)0);
					FakeNukeEvent.blocks.add(fall);
				}
			}
		}
	}

	private boolean isFallingBreakAble(Block block) {
		Block highest = block.getLocation().getWorld().getHighestBlockAt(block.getLocation());
		if(highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(126) || highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(44) || highest.getType() == Material.RAILS || highest.getType() == Material.POWERED_RAIL || highest.getType() == Material.CARPET || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.WOOD_PLATE || highest.getType() == Material.WHEAT || highest.getType() == Material.MELON_STEM || highest.getType() == Material.PUMPKIN_STEM || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.TRIPWIRE || highest.getType() == Material.NETHER_FENCE || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.CROPS || highest.getType() == Material.SIGN_POST || highest.getType() == Material.SUGAR_CANE_BLOCK || highest.getType() == Material.STONE_PLATE) {
			return true;
		}
		return false;
	}

	@Override
	public void performLoginTask() {
		PlayerTaskEnum task = PlayerTaskEnum.valueOf(con.getString("task.type"));
		if(task == PlayerTaskEnum.PLAYER) {
			Bukkit.dispatchCommand(player, con.getString("task.command"));	
		} else if(task == PlayerTaskEnum.CONSOLE) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), con.getString("task.command"));
		}
		con.set("task", null);
	}

	@Override
	public boolean isSpeedEnabled() {
		if(con.contains("isSpeed")) {
			return con.getBoolean("isSpeed");
		}
		return false;
	}

	@Override
	public void setSpeed(int i) {
		con.set("isSpeed", true);
		getBukkitPlayer().setWalkSpeed(i);
		getBukkitPlayer().setFlySpeed(i);
	}

	@Override
	public void removeSpeed() {
		con.set("isSpeed", false);
		getBukkitPlayer().setWalkSpeed(0.2f);
		getBukkitPlayer().setFlySpeed(0.1f);
	}

	@Override
	public boolean isStaff() {
		if(player.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isBoom() {
		if(con.contains("isBoom")) {
			return con.getBoolean("isBoom");
		}
		return false;
	}

	@Override
	public void setBoom() {
		con.set("isBoom", true);
	}

	@Override
	public void removeBoom() {
		con.set("isBoom", null);
	}

	@Override
	public boolean isPotato() {
		if(con.contains("isPotato")) {
			return con.getBoolean("isPotato");
		}
		return false;
	}

	@Override
	public void removePotato() {
		con.set("isPotato", false);
		this.Potato.remove();
		this.Potato = null;
	}

	@Override
	public void setPotato(Item potato) {
		this.Potato = potato;
		con.set("isPotato", true);
	}

	@Override
	public Item getPotato() {
		if(Potato instanceof Item) {
			return Potato;
		} else {
			throw new NullPointerException("no potato has been instanced!");
		}
	}

	@Override
	public String getIp() {
		return player.getAddress().getAddress().getHostAddress();
	}

	@Override
	public boolean setIp() {
		con.set("ip", player.getAddress().getAddress().getHostAddress());
		return true;
	}

	@Override
	public boolean isFishing() {
		if(player.getItemInHand().getType() == Material.FISHING_ROD) {
			for(Entity entity : player.getNearbyEntities(16, 16, 16)) {
				if(entity instanceof Fish) {
					Fish fish = (Fish) entity;
					if(fish.getShooter() instanceof Player) {
						Player p = (Player) fish.getShooter();
						if(p.equals(player)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isMuted() {
		if(con.contains("muted.isMuted")) {
			return con.getBoolean("muted.isMuted");
		} else {
			return false;
		}
	}

	@Override
	public boolean isAfk() {
		if(con.contains("afk.isAfk")) {
			return con.getBoolean("afk.isAfk");
		}
		return false;
	}

	@Override
	public String getAfkReason() {
		if(con.contains("afk.reason")) {
			return con.getString("afk.reason");
		}
		return null;
	}

	@Override
	public void removeAfk() {
		if(con.contains("afk.isAfk")) {
			con.set("afk", null);
		}
	}

	@Override
	public void setAfk(String message) {
		con.set("afk.isAfk", true);
		con.set("afk.reason", message);
	}

	@Override
	public void unmute() {
		con.set("muted", null);
	}

	@Override
	public void mute(Long time) {
		con.set("muted.isMuted", true);
		con.set("muted.mutedTime", time);
	}

	@Override
	public Long getMutedTime() {
		return con.getLong("muted.mutedTime");
	}

	@Override
	public boolean isPermBanned() {
		return Bukkit.getServer().getBanList(Type.NAME).isBanned(player.getName());
	}

	@Override
	public void setPermBanned(String reason, String who) {
		Bukkit.getServer().getBanList(Type.NAME).addBan(player.getName(), reason, null, who).save();
		con.set("banned.isBanned", true);
	}

	@Override
	public String getBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getReason();
	}

	@Override
	public boolean isTempBanned() {
		if(Bukkit.getBanList(Type.NAME).isBanned(player.getName())) {
			Date date = Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getExpiration();
			if(date != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void setTempbanned(Long time, String reason, String who) {
		Date date = new Date(time);
		Bukkit.getServer().getBanList(Type.NAME).addBan(player.getName(), reason, date, who).save();
		con.set("tempbanned.isBanned", true);
	}

	@Override
	public String getTempBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getReason();
	}

	@Override
	public void unban() {
		Bukkit.getServer().getBanList(Type.NAME).pardon(player.getName());
	}

	@Override
	public boolean isBannedBefore() {
		if(con.contains("banned.isBanned")) {
			if(!con.getBoolean("banned.isBanned")) {
				return true;
			}
		} else if(con.contains("tempbanned.isBanned")) {
			if(!con.getBoolean("tempbanned.isBanned")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Long getTempbanRemaining() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getExpiration().getTime();
	}

	@Override
	public UUID getUniqueId() {
		return player.getUniqueId();
	}

	@Override
	public boolean isFlying() {
		return con.getBoolean("fly");
	}

	@Override
	public void setFlying(boolean bol) {
		if(bol) {
			player.setAllowFlight(true);
			player.setFlying(true);
		} else {
			player.setAllowFlight(false);
			player.setFlying(false);
		}
		con.set("fly", bol);
	}

	@Override
	public boolean isTorch() {
		return con.getBoolean("torch");
	}

	@Override
	public void setTorch(boolean bol) {
		con.set("torch", bol);
	}

	@Override
	public boolean isFirefly() {
		return con.getBoolean("firefly");
	}

	@Override
	public void setFirefly(boolean bol) {
		con.set("firefly", bol);
	}

	@Override
	public boolean hasHome() {
		if(con.contains("homes.default")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidHome(String home) {
		if(con.contains("homes."+home)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Home> getAllHomes() {
		List<Home> homes = new ArrayList<Home>();
		if(hasHome()) {
			for(String home : con.getConfigurationSection("homes").getKeys(false)) {
				Home playerhome = new Home(con, home);
				homes.add(playerhome);
			}	
		}
		return homes;
	}

	@Override
	public Home getHome(String homeName) {
		Home home = new Home(con, homeName);
		return home;
	}

	@Override
	public int getAmountOfHomes() {
		//returns a fixed version for permissions;)
		return (this.getAllHomes().size());
	}

	@Override
	public void setHome(String home) {
		con.set("homes."+home.toLowerCase()+".x", player.getLocation().getX());
		con.set("homes."+home.toLowerCase()+".y", player.getLocation().getY());
		con.set("homes."+home.toLowerCase()+".z", player.getLocation().getZ());
		con.set("homes."+home.toLowerCase()+".yaw", player.getLocation().getYaw());
		con.set("homes."+home.toLowerCase()+".pitch", player.getLocation().getPitch());
		con.set("homes."+home.toLowerCase()+".world", player.getWorld().getName());
	}

	@Override
	public void removeHome(String home) {
		con.set("homes."+home.toLowerCase(), null);
	}

	/**
	 * @author xize
	 * @param is true when vanished otherwise false
	 * @return boolean
	 */
	@Override
	public boolean isVanished() {
		if(con.contains("isVanished")) {
			return con.getBoolean("isVanished");
		}
		return false;
	}

	@Override
	public void vanish() {
		for(Player p : pl.getOnlinePlayers()) {
			if(!player.equals(p)) {
				p.hidePlayer(player);
			}
		}
		con.set("isVanished", true);
		con.set("noPickUp", true);
	}

	@Override
	public void unvanish() {
		for(Player p : pl.getOnlinePlayers()) {
			if(!player.equals(p)) {
				p.showPlayer(player);
			}
		}
		if(hasVanishEffects()) {
			final LinkedList<Block> list = new LinkedList<Block>();
			for(double i = 0.0; i < 360.0; i += 20.0) {
				LinkedList<Block> pilars = new LinkedList<Block>();
				for(int y = 0; y < 4; y++) {
					double angle = (i*Math.PI / 180);
					int x = (int) (6*Math.cos(angle));
					int z = (int) (6*Math.sin(angle));
					Location loc = player.getLocation().add(x, y, z);
					pilars.add(loc.getBlock());
					loc.getWorld().strikeLightningEffect(loc.clone().add(0,-50,0));
					loc.getWorld().spawnEntity(loc.clone().add(0, 1, 0), EntityType.BAT);
					for(Entity entity : player.getNearbyEntities(10, 10, 10)) {
						if(entity instanceof Player) {
							Player p = (Player) entity;
							p.sendBlockChange(loc, Material.LAVA, (byte)0);
							list.add(loc.getBlock());
						}
					}
				}
				LavaPilar pilar = new LavaPilar(pilars, pl);
				pilar.startTask();
			}
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_IDLE, 1F, 1F);
		}
		con.set("isVanished", false);
		con.set("noPickUp", false);
	}

	@Override
	public void unvanish(boolean sillenced) {
		for(Player p : pl.getOnlinePlayers()) {
			if(!player.equals(p)) {
				p.showPlayer(player);
			}
		}
		if(hasVanishEffects() && !sillenced) {
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_IDLE, 1F, 1F);
			player.getWorld().strikeLightning(player.getLocation().add(0, -120, 0));
		}
		con.set("isVanished", false);
		con.set("noPickUp", false);
	}

	@Override
	public boolean hasVanishEffects() {
		if(con.getBoolean("vanishEffects")) {
			return con.getBoolean("vanishEffects");
		}
		return false;
	}

	@Override
	public void setVanishEffects(boolean bol) {
		con.set("vanishEffects", bol);
	}

	@Override
	public boolean isNoPickUpEnabled() {
		if(con.contains("noPickUp")) {
			return con.getBoolean("noPickUp");
		}
		return false;
	}

	@Override
	public void setNoPickUp(boolean bol) {
		con.set("noPickUp", bol);
	}

	@Override
	public void saveCreativeInventory() {
		if(con.contains("inventory.creative")) {
			con.set("inventory.creative.inv", null);
			con.set("inventory.creative.armor", null);
			con.set("inventory.creative.inv", player.getInventory().getContents());
			con.set("inventory.creative.armor", player.getInventory().getArmorContents());
		} else {
			con.set("inventory.creative.inv", player.getInventory().getContents());
			con.set("inventory.creative.armor", player.getInventory().getArmorContents());
		}
	}

	@Override
	public void saveSurvivalInventory() {
		if(con.contains("inventory.survival")) {
			con.set("inventory.survival.inv", null);
			con.set("inventory.survival.armor", null);
			con.set("inventory.survival.inv", player.getInventory().getContents());
			con.set("inventory.survival.armor", player.getInventory().getArmorContents());
		} else {
			con.set("inventory.survival.inv", player.getInventory().getContents());
			con.set("inventory.survival.armor", player.getInventory().getArmorContents());
		}
	}

	@Override
	public boolean hasSurvivalInventory() {
		if(con.contains("inventory.survival")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasCreativeInventory() {
		if(con.contains("inventory.creative")) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack[] getSurvivalInventory() {
		Object obj = con.get("inventory.survival.inv");
		ItemStack[] items = null;
		if(obj instanceof List) {
			items = ((List<ItemStack>)obj).toArray(new ItemStack[0]);
		} else {
			items = (ItemStack[])obj;
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack[] getSurvivalArmorInventory() {
		Object obj = con.get("inventory.survival.armor");
		ItemStack[] items = null;
		if(obj instanceof List) {
			items = ((List<ItemStack>)obj).toArray(new ItemStack[0]);
		} else {
			items = (ItemStack[])obj;
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack[] getCreativeInventory() {
		Object obj = con.get("inventory.creative.inv");
		ItemStack[] items = null;
		if(obj instanceof List) {
			items = ((List<ItemStack>)obj).toArray(new ItemStack[0]);
		} else {
			items = (ItemStack[])obj;
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack[] getCreativeArmorInventory() {
		Object obj = con.get("inventory.creative.armor");
		ItemStack[] items = null;
		if(obj instanceof List) {
			items = ((List<ItemStack>)obj).toArray(new ItemStack[0]);
		} else {
			items = (ItemStack[])obj;
		}
		return items;
	}

	@Override
	public boolean hasOfflineInventory() {
		if(con.contains("offlineInventory.contents")) {
			return true;
		}
		return false;
	}

	@Override
	public void saveOfflineInventory() {
		if(hasOfflineInventory()) {
			con.set("offlineInventory.contents", null);
		}
		con.set("offlineInventory.contents", player.getInventory().getContents());
	}

	@Override
	public boolean hasModreqDoneMessage() {
		if(con.contains("modreq.done.message")) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setModreqDoneMessage(String message) {
		con.set("modreq.done.message", message);
		try {
			con.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		save();
	}

	@Override
	public String getModreqDoneMessage() {
		return con.getString("modreq.done.message");
	}

	@Override
	public void removeGetModregDoneMessage() {
		con.set("modreq", null);
	}

	@Override
	public boolean hasModreqsOpen() {
		if(con.contains("modreqs")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidModreqId(int id) {
		if(con.contains("modreqs."+"modreq"+id)) {
			return true;
		}
		return false;
	}

	@Override
	public Modreq getModreq(int id) {
		Modreq mod = new Modreq(con, id);
		return mod;
	}

	@Override
	public void removeModreq(int id) {
		if(isValidModreqId(id)) {
			con.set("modreqs."+"modreq"+id, null);
			if(con.getConfigurationSection("modreqs").getKeys(true).isEmpty()) {
				con.set("modreqs", null);
			}
		} else {
			throw new NullPointerException("you cannot remove a configuration node for a modreq wich doesn't exists!");
		}
	}

	@Override
	public Modreq[] getModreqs() {
		List<Modreq> items = new ArrayList<Modreq>();
		for(int i = 0; con.contains("modreqs."+"modreq"+i); i++) {
			if(isValidModreqId(i)) {
				Modreq mod = new Modreq(con, i);
				items.add(mod);	
			} else {
				break;
			}
		}
		Modreq[] modreqs = items.toArray(new Modreq[items.size()]);
		return modreqs;
	}

	@Override
	public void createModreq(String message) {
		int id = getModreqs().length;
		con.set("modreqs."+"modreq"+id+".message", message);
		con.set("modreqs."+"modreq"+id+".getDate", System.currentTimeMillis());
		con.set("modreqs."+"modreq"+id+".location.x", player.getLocation().getX());
		con.set("modreqs."+"modreq"+id+".location.y", player.getLocation().getY());
		con.set("modreqs."+"modreq"+id+".location.z", player.getLocation().getZ());
		con.set("modreqs."+"modreq"+id+".location.yaw", player.getLocation().getY());
		con.set("modreqs."+"modreq"+id+".location.pitch", player.getLocation().getPitch());
		con.set("modreqs."+"modreq"+id+".location.world", player.getWorld().getName());
	}

	@Override
	public Player getBukkitPlayer() {
		return player;
	}

	@Override
	public boolean isInventoryClearanceOnRelog() {
		if(con.contains("ClearInventory")) {
			return con.getBoolean("ClearInventory");
		}
		return false;
	}

	@Override
	public void ClearInventoryOnRelog() {
		if(isInventoryClearanceOnRelog()) {
			player.getInventory().clear();
			con.set("ClearInventory", null);
		}
	}

	@Override
	public AlternateAccount getAlternateAccounts() {
		if(accounts instanceof AlternateAccount) {
			return accounts;
		} else {
			this.accounts = new AlternateAccount(this, pl);
			return accounts;
		}
	}

	@Override
	public boolean hasAlternateAccounts() {
		AlternateAccount alts = getAlternateAccounts();
		if(alts instanceof AlternateAccount) {
			if(alts.getAltNames().length > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasCompass() {
		if(con.contains("isCompass.enabled")) {
			return con.getBoolean("isCompass.enabled");
		}
		return false;
	}
	
	@Override
	public XOfflinePlayer getCompass() {
		return pl.getManagers().getPlayerManager().getOfflinePlayer(con.getString("isCompass.follow"));
	}

	@Override
	public void setCompass(Player p) {
		con.set("isCompass.enabled", true);
		con.set("isCompass.follow", p.getName());
	}

	@Override
	public void setCompass(String p) {
		con.set("isCompass.enabled", true);
		con.set("isCompass.follow", p);
	}

	@Override
	public void removeCompass() {
		con.set("isCompass", null);
	}

	@Override
	public void setSilenced(boolean bol) {
		con.set("silenced", bol);
	}

	@Override
	public boolean isSilenced() {
		if(con.contains("silenced")) {
			return con.getBoolean("silenced");
		}
		return false;
	}

	@Override
	public void addIgnoredPlayer(String s) {
		if(hasIgnoredPlayers()) {
			List<String> list = new ArrayList<String>(getIgnoredPlayers());
			list.add(s);
			con.set("IgnoredPlayers", list.toArray());
		} else {
			String[] a = {s};
			con.set("IgnoredPlayers", Arrays.asList(a).toArray());
		}
		save();
	}

	@Override
	public List<String> getIgnoredPlayers() {
		return con.getStringList("IgnoredPlayers");
	}

	@Override
	public boolean hasIgnoredPlayers() {
		if(con.contains("IgnoredPlayers")) {
			return true;
		}
		return false;
	}

	@Override
	public void removeIgnoredPlayer(String s) {
		List<String> list = new ArrayList<String>(getIgnoredPlayers());
		if(list.size() == 1) {
			con.set("IgnoredPlayers", null);
		} else {
			list.remove(s);
			con.set("IgnoredPlayers", list.toArray());
		}
	}

	@Override
	public boolean isFreezed() {
		if(con.contains("isFreezed")) {
			return con.getBoolean("isFreezed");
		}
		return false;
	}

	@Override
	public void setFreezed(boolean bol) {
		con.set("isFreezed", bol);
	}

	@Override
	public boolean isTrollMode() {
		if(con.contains("isTrollmode")) {
			return con.getBoolean("isTrollmode");
		}
		return false;
	}

	@Override
	public void setTrollMode(boolean bol) {
		con.set("isTrollmode", bol);
	}

	@Override
	public boolean hasCommandRestrictions() {
		if(con.contains("command-restrictions")) {
			if(!con.getString("command-restrictions").isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void setCommandRestriction(String command, String reason, String taskCommand) {
		if(taskCommand == null) {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason);
			con.set("command-restrictions", list);
		} else {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason+","+taskCommand);
			con.set("command-restrictions", list);
		}
	}

	@Override
	public List<RestrictedCommand> getCommandRestrictions() {
		List<String> commands = new ArrayList<String>(con.getStringList("command-restrictions"));
		List<RestrictedCommand> restricts = new ArrayList<RestrictedCommand>();
		for(String args : commands) {
			RestrictedCommand cmd = new RestrictedCommand(args);
			restricts.add(cmd);
		}
		return restricts;
	}

	@Override
	public boolean hasContainedRestriction(String command) {
		for(RestrictedCommand restriction : getCommandRestrictions()) {
			if(restriction.getCommand().equalsIgnoreCase(command)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeCommandRestriction(RestrictedCommand cmd) {
		List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
		list.remove(cmd.getSerializedKey());
		con.set("command-restrictions", list);
	}

	@Override
	public void setKitCooldown(Long cooldown) {
		con.set("kitCooldown", cooldown);
	}

	@Override
	public void removeKitCoolDown() {
		con.set("kitCooldown", null);
	}

	@Override
	public Long getKitCooldown() {
		return con.getLong("kitCooldown");
	}

	@Override
	public boolean hasKitCooldown() {
		if(con.contains("kitCooldown")) {
			return true;
		}
		return false;
	}

	@Override
	public double getMoney() {
		return con.getDouble("money");
	}

	@Override
	public boolean hasMoney() {
		if(con.contains("money")) {
			if(con.getDouble("money") >= 0.0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean payMoney(double price, XOfflinePlayer toPayTo) {
		Double money = (getMoney()-price);
		if(money >= 0.0) {
			con.set("money", money);
			toPayTo.depositMoney(price);
			return true;
		}
		return false;
	}

	@Override
	public boolean payMoney(double price, XPlayer toPayTo) {
		Double money = (getMoney()-price);
		if(money >= 0.0) {
			con.set("money", money);
			toPayTo.depositMoney(price);
			return true;
		}
		return false;
	}

	@Override
	public boolean withdrawMoney(double price) {
		if((getMoney()-price) >= 0.0) {
			con.set("money", (getMoney()-price));
			return true;
		}
		return false;
	}

	@Override
	public boolean payMoney(double money) {
		Double moneya = (getMoney()-money);
		if(moneya >= 0.0) {
			con.set("money", moneya);
			return true;
		}
		return false;
	}

	@Override
	public void depositMoney(double price) {
		con.set("money", getMoney()+price);
	}

	@Override
	public boolean hasEnoughMoney(double price) {
		if((getMoney()-price) >= 0.0) {
			return true;
		}
		return false;
	}

	@Override
	public void clearMoney() {
		con.set("money", null);
	}

	@Override
	public void setNameHistory(String oldName) {
		List<String> list = con.getStringList("name-history");
		list.add(oldName);
		if(list.size() > 8) {
			list.remove(8);
		}
		con.set("name-history", list);
	}

	@Override
	public List<String> getNameHistory() {
		return con.getStringList("name-history");
	}

	@Override
	public boolean hasNameHistory() {
		return !con.getStringList("name-history").isEmpty();
	}

	@Override
	public boolean hasSavedInventory() {
		if(con.contains("orginalinv")) {
			return true;
		}
		return false;
	}

	@Override
	public void saveInventory() {
		con.set("orginalinv.items", player.getInventory().getContents());
		con.set("orginalinv.armor", player.getInventory().getArmorContents());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadInventory() {
		Object c = con.get("orginalinv.items");
		ItemStack[] contents = null;
		if(c instanceof List) {
			contents = ((List<ItemStack>)c).toArray(new ItemStack[0]);
		} else {
			contents = (ItemStack[])c;
		}
		Object a = con.get("orginalinv.armor");
		ItemStack[] armor = null;
		if(a instanceof List) {
			armor = ((List<ItemStack>)a).toArray(new ItemStack[0]);
		} else {
			armor = (ItemStack[])a;
		}
		player.getInventory().setContents(contents);
		player.getInventory().setArmorContents(armor);
		con.set("orginalinv", null);
	}

	@Override
	public void setProc(boolean bol) {
		con.set("proc.enable", bol);
	}

	@Override
	public void setCustomName(String name) {
		player.setDisplayName(name);
		player.setPlayerListName(name);
		con.set("customname", name);
	}

	@Override
	public boolean hasCustomName() {
		return (player.getDisplayName().equals(getName()) ? false : true);
	}

	@Override
	public String getCustomName() {
		return (con.getString("customname") == null ? player.getName() : con.getString("customname"));
	}

	@Override
	public boolean hasProc() {
		if(con.contains("proc.enable")) {
			return con.getBoolean("proc.enable");
		}
		return false;
	}

	@Override
	public void setKnock(boolean bol) {
		con.set("knock", bol);
	}

	@Override
	public boolean isKnock() {
		if(con.contains("knock")) {
			return con.getBoolean("knock");
		}
		return false;
	}

	@Override
	public void setInChair(boolean bol) {
		con.set("chair", bol);
	}

	@Override
	public boolean isInChair() {
		if(con.contains("chair")) {
			return con.getBoolean("chair");
		}
		return false;
	}

	@Override
	public void setDoubleJump(boolean bol) {
		con.set("doublejump", bol);
	}

	@Override
	public boolean hasDoubleJump() {
		if(con.contains("doublejump")) {
			return con.getBoolean("doublejump");
		}
		return false;
	}

	@Override
	public boolean isSpectate() {
		if(spectate instanceof BukkitTask) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void stopSpectate() {
		if(isSpectate()) {
			if(this.spectate != null) {
				spectate.cancel();
				spectate = null;
			}
			player.chat("/spawn");

			ItemStack[] contents = null;

			Object c = con.get("spectate-inventory");

			if(c instanceof List) {
				contents = ((List<ItemStack>)c).toArray(new ItemStack[0]);
			} else {
				contents = (ItemStack[])c;
			}
			player.getInventory().setContents(contents);
			con.set("spectate-inventory", null);
		}
	}

	@Override
	public void spectate(final Player pa) {
		if(pa.equals(player)) {
			return;
		}

		con.set("spectate-inventory", player.getInventory().getContents());
		this.spectate = new BukkitRunnable() {

			@Override
			public void run() {
				if(pa.isOnline()) {
					player.hidePlayer(pa);
					pa.hidePlayer(player);
					player.teleport(pa);	
					player.getInventory().setContents(pa.getInventory().getContents());
					player.getInventory().setHeldItemSlot(pa.getInventory().getHeldItemSlot());
				} else {
					player.sendMessage(ChatColor.GREEN + "player is not longer online, cancelling spectate");
					stopSpectate();
				}
			}
		}.runTaskTimer(pl, 0L, 1L);
	}

	@Override
	public boolean isEditSignEnabled() {
		if(con.contains("signedit")) {
			return con.getBoolean("signedit");
		}
		return false;
	}

	@Override
	public void setEditSign(boolean bol) {
		con.set("signedit", bol);
	}

	@Override
	public boolean isDrunk() {
		if(con.contains("drunk")) {
			return con.getBoolean("drunk");
		}
		return false;
	}

	@Override
	public void setDrunk(boolean bol) {
		con.set("drunk", bol);
	}

	@Override
	public String getDrunkMessageFrom(String message, boolean bol) {
		boolean posneg = bol;
		boolean isSpace = false;
		String newmsg = "";
		for(char chr : message.toCharArray()) {
			if(isLitteral(chr)) {
				if(posneg) {
					//don't add chr, only make sure the next char will be accepted.
					posneg = false;
				} else {
					newmsg += chr;
					posneg = true;
				}
			} else {
				if(isSpace) {
					if(!isSpace(chr)) {
						isSpace = false;
						newmsg += chr;
					}
				} else {
					if(isSpace(chr)) {
						isSpace = true;
						newmsg += chr;
					} else {
						newmsg += chr;
						isSpace = false;
					}
				}
			}
		}
		return newmsg;
	}

	private boolean isSpace(char chr) {
		switch(chr) {
		case ' ' : return true;
		default : return false;
		}
	}

	private boolean isLitteral(char chr) {
		switch(chr) {
		case 'a': return true; 
		case 'e': return true;
		case 'i': return true;
		case 'o': return true;
		case 'u': return true;
		case 'A': return true;
		case 'E': return true;
		case 'I': return true;
		case 'O': return true;
		case 'U': return true;
		default : return false; 
		}
	}

	@Override
	public boolean isFloorMode() {
		if(con.contains("floormode.enable")) {
			return con.getBoolean("floormode.enable");
		}
		return false;
	}

	@Override
	public void setFloorMode(boolean bol, int range) {
		con.set("floormode.enable", bol);
		con.set("floormode.range", range);
	}

	@Override
	public int getFloorModeRange() {
		return con.getInt("floormode.range");
	}

	@Override
	public boolean isWallMode() {
		if(con.contains("wallmode.enable")) {
			return con.getBoolean("wallmode.enable");
		}
		return false;
	}

	@Override
	public void setWallMode(boolean bol, int range) {
		con.set("wallmode.enable", bol);
		con.set("wallmode.range", range);
	}


	@Override
	public int getWallModeRange() {
		return con.getInt("wallmode.range");
	}

	@Override
	public void setShop(Location loc, Chest chest) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		con.set("shops."+id.toString()+".location", loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ());
		con.set("shops."+id.toString()+".chestloc", chest.getWorld().getName()+":"+chest.getX()+":"+chest.getY()+":"+chest.getZ());
	}

	@Override
	public boolean isShop(Location loc) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		if(con.contains("shops."+id.toString())) {
			return true;
		}
		return false;
	}

	@Override
	public Shop getShop(Location loc) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		if(isShop(loc)) {
			String[] args = con.getString("shops."+id.toString()+".chestloc").split(":");
			Sign sign = (Sign)loc.getBlock().getState();
			World w = Bukkit.getWorld(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			Block block = new Location(w, x, y, z).getBlock();
			if(block.getType() != Material.CHEST) {
				block.setType(Material.CHEST);
			}
			Chest chest = (Chest)block.getState();
			return new Shop(chest, sign, pl.getManagers().getPlayerManager().getOfflinePlayer(getName()));
		}
		return null;
	}

	@Override
	public void removeShop(Location loc) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		con.set("shops."+id.toString(), null);
	}

	@Override
	public void PrepareLoginTask(String command, PlayerTaskEnum task) {
		con.set("task.command", command);
		con.set("task.type", task.name());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		if(hasOfflineInventory()) {
			Object obj = con.get("offlineInventory.contents");
			ItemStack[] items = null;
			if(obj instanceof List) {
				items = ((List<ItemStack>)obj).toArray(new ItemStack[0]);
			} else {
				items = (ItemStack[]) obj;
			}	
			inv.setContents(items);
		}
		return inv;
	}

	@Override
	public XPlayer getEssentialsPlayer() {
		return pl.getManagers().getPlayerManager().getPlayer(getName());
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public void setPwnageLevel(int level) {
		con.set("pwnage-level", level);
	}

	@Override
	public boolean hasPwnageLevel() {
		return (con.contains("pwnage-level") || getPwnageLevel() > 0 ? true : false);
	}

	@Override
	public int getPwnageLevel() {
		return con.getInt("pwnage-level");
	}

	@Override
	public long getLastLoginTime() {
		return con.getLong("last-login-time");
	}

	@Override
	public void setLastLoginTime(long time) {
		con.set("last-login-time", time);
	}

	@Override
	public boolean hasLastLoginTime() {
		return con.contains("last-login-time");
	}

	@Override
	public void setPwnageScheduler(BukkitTask task) {
		if(pwnagetask != null) {
			pwnagetask.cancel();
			pwnagetask = null;
		}
		this.pwnagetask = task;
	}
	
	@Override
	public void sendMessage(String prefix, String message) {
		player.sendMessage(prefix + message);
	}
	
	@Override
	public boolean hasMessages() {
		return con.contains("messagepool");
	}

	@Override
	public List<String> getMessages() {
		List<String> messages = new ArrayList<String>();
		messages.addAll(con.getStringList("messagepool"));
		con.set("messagepool", null);
		return messages;
	}
	
	@Override
	public boolean hasEffects() {
		return (this.effect != null && effect_task instanceof BukkitTask);
	}

	@Override
	public void setEffect(EffectType type) {
		if(type == null) {
			if(effect_task instanceof BukkitTask) {
				effect_task.cancel();
				this.effect_task = null;
				this.effect = null;
			}
		} else {
			if(effect_task instanceof BukkitTask) {
				this.effect = type;
			} else {
				this.effect = type;
				this.effect_task = new BukkitRunnable() {

					@Override
					public void run() {
						if(effect != null && player.isOnline() && !isVanished()) {
							EffectType.playEffect(player.getWorld(), effect, player.getLocation().add(0, 2, 0), 0.25, 0, 0.25, 0, 10);
						} else {
							cancel();
						}
					}
					
				}.runTaskTimer(pl, 0L, 5L);
			}
		}
	}

	@Override
	public void save() {
		try {
			if(f.canWrite()) {
				con.save(f);
				con.load(f);	
			} else {
				f.setReadable(true, false);
				f.setWritable(true, false);
				con.save(f);
				con.load(f);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			if(obj instanceof xEssentialsPlayer) {
				xEssentialsPlayer xp = (xEssentialsPlayer) obj;
				return xp.getName().equalsIgnoreCase(this.getName()) && xp.getUniqueId().equals(this.getUniqueId());
			}
		}
		return false;
	}
	
}

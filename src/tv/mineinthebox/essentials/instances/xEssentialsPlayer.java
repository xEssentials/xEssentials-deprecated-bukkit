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

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.events.players.FakeNukeEvent;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class xEssentialsPlayer implements XPlayer {
	
	private Player player;
	private File f;
	private FileConfiguration con;
	private Item Potato;
	private AlternateAccount accounts;
	private BukkitTask spectate;
	private BukkitTask pwnagetask;

	/**
	 * 
	 * @author xize
	 * @param a constructor which pass our interface, if there is no user file this constructor will create a new one this constructor only allows type Player.
	 * @return xEssentialsPlayer
	 * 
	 */
	public xEssentialsPlayer(Player player, String UUID) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("setting profile for player " + player.getName(), LogType.DEBUG);
		}
		this.player = player;
		if(Bukkit.getOnlineMode()) {
			this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players"+File.separator+UUID+".yml");	
		} else {
			this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players"+File.separator+player.getName().toLowerCase()+".yml");
		}
		if(this.f.exists()){
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("profile found, checking for a possible name change!", LogType.DEBUG);
			}
			this.con = YamlConfiguration.loadConfiguration(this.f);
			if(!this.con.getString("user").equalsIgnoreCase(player.getName())) {
				if(Configuration.getDebugConfig().isEnabled()) {
					xEssentials.getPlugin().log("the players name is changed from " + this.con.getString("user") + " to " + player.getName(), LogType.DEBUG);
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
				if(Configuration.getProtectionConfig().isProtectionEnabled()) {
					xEssentials.getManagers().getProtectionDBManager().updatePlayer(oldName, player.getName());
				}
			} else {
				if(Configuration.getDebugConfig().isEnabled()) {
					xEssentials.getPlugin().log("players name is still intact and matches with: " + UUID, LogType.DEBUG);
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
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("profile not found, checking for non converted names in order to convert...", LogType.DEBUG);
			}
			File possiblename = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players" + File.separator + player.getName().toLowerCase() + ".yml");
			if(possiblename.exists()) {
				if(Configuration.getDebugConfig().isEnabled()) {
					xEssentials.getPlugin().log("profile of "+player.getName()+" has been successfull found and renamed to the uuid spec", LogType.DEBUG);
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
			if(Configuration.getEconomyConfig().isEconomyEnabled()){
				this.con.set("money", Configuration.getEconomyConfig().getStartersMoney());
			}
			try {
				this.con.save(this.f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setLastLoginTime(System.currentTimeMillis());
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is greylisted
	 * @return boolean
	 */
	@Override
	public boolean isGreyListed() {
		update();
		if(con.contains("isDefault")) {
			if(!con.getBoolean("isDefault")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @author xize
	 * @param set the player as greylisted
	 * @return void
	 */
	@Override
	public void setGreyListed(Boolean bol) {
		con.set("isDefault", !bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param set the powertool of a player
	 * @return void
	 */
	@Override
	public void setPowerTool(ItemStack item, String command) {
		con.set("powertool."+item.getType().name()+".command", command);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param when true the player has a powertool otherwise false
	 * @return Boolean
	 */
	@Override
	public boolean hasPowerTool() {
		update();
		if(con.contains("powertool."+player.getItemInHand().getType().name()+".command")) {
			return con.contains("powertool."+player.getItemInHand().getType().name());
		}
		return false;
	}

	/**
	 * @author xize
	 * @param get the command from the power tool
	 * @return String
	 */
	@Override
	public String getPowerTool() {
		update();
		return con.getString("powertool."+player.getItemInHand().getType().name()+".command");
	}

	/**
	 * @author xize
	 * @param removes the powertool
	 * @return void
	 */
	@Override
	public void removePowerTool() {
		con.set("powertool."+player.getItemInHand().getType().name()+".command", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true if a player has a login task
	 * @return boolean
	 */
	@Override
	public boolean hasLoginTask() {
		update();
		if(con.contains("task.command")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param nukes the player with a massive explosion rain
	 * @return void
	 */
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
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param nukes the player with a massive explosion rain but as fake
	 * @return void
	 */
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

	@Override
	public boolean isFallingBreakAble(Block block) {
		Block highest = block.getLocation().getWorld().getHighestBlockAt(block.getLocation());
		if(highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(126) || highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(44) || highest.getType() == Material.RAILS || highest.getType() == Material.POWERED_RAIL || highest.getType() == Material.CARPET || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.WOOD_PLATE || highest.getType() == Material.WHEAT || highest.getType() == Material.MELON_STEM || highest.getType() == Material.PUMPKIN_STEM || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.TRIPWIRE || highest.getType() == Material.NETHER_FENCE || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.CROPS || highest.getType() == Material.SIGN_POST || highest.getType() == Material.SUGAR_CANE_BLOCK || highest.getType() == Material.STONE_PLATE) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param peform a login task
	 * @return void
	 */
	@Override
	public void performLoginTask() {
		PlayerTaskEnum task = PlayerTaskEnum.valueOf(con.getString("task.type"));
		if(task == PlayerTaskEnum.PLAYER) {
			Bukkit.dispatchCommand(player, con.getString("task.command"));	
		} else if(task == PlayerTaskEnum.CONSOLE) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), con.getString("task.command"));
		}
		con.set("task", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever the speed is enabled
	 * @return boolean
	 */
	@Override
	public boolean isSpeedEnabled() {
		update();
		if(con.contains("isSpeed")) {
			return con.getBoolean("isSpeed");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param sets the speed of a player
	 * @return void
	 */
	@Override
	public void setSpeed(int i) {
		con.set("isSpeed", true);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPlayer().setWalkSpeed(i);
		getPlayer().setFlySpeed(i);
		update();
	}

	/**
	 * @author xize
	 * @param removes the walk speed
	 * @return void
	 */
	@Override
	public void removeSpeed() {
		con.set("isSpeed", false);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
		getPlayer().setWalkSpeed(0.2f);
		getPlayer().setFlySpeed(0.1f);
	}

	/**
	 * @author xize
	 * @param returns whenever the player is staff and has permission: xEssentials.isStaff
	 * @return boolean
	 */
	@Override
	public boolean isStaff() {
		if(player.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param checks whenever the player is boomed
	 * @return Boolean
	 */
	@Override
	public boolean isBoom() {
		update();
		if(con.contains("isBoom")) {
			return con.getBoolean("isBoom");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param set the boom status of this player
	 * @return void
	 */
	@Override
	public void setBoom() {
		con.set("isBoom", true);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param remove the boom status of this player
	 * @return void
	 */
	@Override
	public void removeBoom() {
		con.set("isBoom", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is cursed to be a potato!
	 * @return boolean
	 */
	@Override
	public boolean isPotato() {
		update();
		if(con.contains("isPotato")) {
			return con.getBoolean("isPotato");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param remove potato curse of this player!
	 * @return void
	 */
	@Override
	public void removePotato() {
		con.set("isPotato", false);
		this.Potato.remove();
		this.Potato = null;
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param set a potato curse of this player!
	 * @return void
	 */
	@Override
	public void setPotato(Item potato) {
		this.Potato = potato;
		con.set("isPotato", true);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param gets the Potato Item
	 * @return Item
	 * @throws NullPointerException
	 */
	@Override
	public Item getPotato() {
		if(Potato instanceof Item) {
			return Potato;
		} else {
			throw new NullPointerException("no potato has been instanced!");
		}
	}

	/**
	 * 
	 * @author xize
	 * @param gets the ip adress of this player
	 * @return String
	 * 
	 */
	@Override
	public String getIp() {
		return player.getAddress().getAddress().getHostAddress();
	}

	/**
	 * @author xize
	 * @param refresh the player on teleport
	 * @deprecated is deprecated because this is a tempory fix for invisibillity delay while teleporting.
	 * @return void
	 */
	@Override
	public void refreshPlayer() {
		for(Player p : xEssentials.getOnlinePlayers()) {
			if(!player.equals(p)) {
				p.hidePlayer(player);
			}
		}
		for(Player p : xEssentials.getOnlinePlayers()) {
			if(!player.equals(p)) {
				p.showPlayer(player);
			}
		}
	}

	/**
	 * 
	 * @author xize
	 * @param set the ip of this player
	 * @return boolean
	 * 
	 */
	@Override
	public boolean setIp() {
		con.set("ip", player.getAddress().getAddress().getHostAddress());
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 
	 * @author xize
	 * @param gets the username from the configuration of this player, this will also get updated whenever the name does not match with the uniqueID
	 * @return String 
	 *
	 */
	@Override
	public String getUser() {
		if(!player.getName().equalsIgnoreCase(con.getString("user"))) {
			con.set("user", player.getName());
			try {
				con.save(f);
				update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con.getString("user");
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is fishing
	 * @return boolean
	 */
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

	/**
	 * @author xize
	 * @param returns true when this player is muted
	 * @return boolean
	 */
	@Override
	public boolean isMuted() {
		update();
		if(con.contains("muted.isMuted")) {
			return con.getBoolean("muted.isMuted");
		} else {
			return false;
		}
	}

	/**
	 * @author xize
	 * @param
	 * @return boolean
	 */
	@Override
	public boolean isAfk() {
		update();
		if(con.contains("afk.isAfk")) {
			return con.getBoolean("afk.isAfk");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the afk reason
	 * @return String
	 */
	@Override
	public String getAfkReason() {
		update();
		if(con.contains("afk.reason")) {
			return con.getString("afk.reason");
		}
		return null;
	}

	/**
	 * @author xize
	 * @param remove the player from afk
	 * @return void
	 */
	@Override
	public void removeAfk() {
		if(con.contains("afk.isAfk")) {
			con.set("afk", null);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}

	/**
	 * @author xize
	 * @param set the player to afk
	 * @return void
	 */
	@Override
	public void setAfk(String message) {
		con.set("afk.isAfk", true);
		con.set("afk.reason", message);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param removes the mute
	 * @return void
	 */
	@Override
	public void unmute() {
		con.set("muted", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param mutes the player for chatting
	 * @param time - where the milliseconds are the modified date.
	 * @return void
	 */
	@Override
	public void mute(Long time) {
		con.set("muted.isMuted", true);
		con.set("muted.mutedTime", time);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param get the modified time in milliseconds
	 * @return Long
	 */
	@Override
	public Long getMutedTime() {
		return con.getLong("muted.mutedTime");
	}

	/**
	 * @author xize
	 * @param check whenever this player is perm banned
	 * @return boolean
	 */
	@Override
	public boolean isPermBanned() {
		return Bukkit.getServer().getBanList(Type.NAME).isBanned(player.getName());
	}

	/**
	 * @author xize
	 * @param sets the player permbanned
	 * @return void
	 */
	@Override
	public void setPermBanned(String reason, String who) {
		Bukkit.getServer().getBanList(Type.NAME).addBan(player.getName(), reason, null, who).save();
		con.set("banned.isBanned", true);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param gets the ban message of this player
	 * @return String
	 */
	@Override
	public String getBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getReason();
	}

	/**
	 * 
	 * @author xize
	 * @param returns true if the player is temp banned
	 * @return boolean
	 * 
	 */
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

	/**
	 * @author xize
	 * @param bans a player tempory (Long time, String reason, String who)
	 * @return boolean
	 */
	@Override
	public void setTempbanned(Long time, String reason, String who) {
		Date date = new Date(time);
		Bukkit.getServer().getBanList(Type.NAME).addBan(player.getName(), reason, date, who).save();
		con.set("tempbanned.isBanned", true);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param gets the tempory ban message
	 * @return String
	 */
	@Override
	public String getTempBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getReason();
	}

	/**
	 * @author xize
	 * @param unbans the player for both Tempbanned or PermBanned
	 * @return void
	 */
	@Override
	public void unban() {
		Bukkit.getServer().getBanList(Type.NAME).pardon(player.getName());
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is banned before
	 * @return boolean
	 */
	@Override
	public boolean isBannedBefore() {
		update();
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

	/**
	 * @author xize
	 * @param gets the time remaining of the ban
	 * @return Long
	 */
	@Override
	public Long getTempbanRemaining() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getExpiration().getTime();
	}

	/**
	 * 
	 * @author xize
	 * @param get the Unique ID of this player
	 * @return String
	 */
	@Override
	public String getUniqueId() {
		return player.getUniqueId().toString();
	}

	/**
	 * 
	 * @author xize
	 * @param checks whenever fly is enabled for this player
	 * @return boolean
	 * 
	 */
	@Override
	public boolean isFlying() {
		update();
		return con.getBoolean("fly");
	}

	/**
	 * @author xize
	 * @param set the fly mode of the player
	 * @return void
	 */
	@Override
	public void setFlying(Boolean bol) {
		con.set("fly", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * 
	 * @author xize
	 * @param checks whenever torch is enabled for this player
	 * @return boolean
	 * 
	 */
	@Override
	public boolean isTorch() {
		update();
		return con.getBoolean("torch");
	}

	/**
	 * @author xize
	 * @param set the torch mode to false or on
	 * @return void
	 */
	@Override
	public void setTorch(Boolean bol) {
		con.set("torch", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever firefly is enabled otherwise false
	 * @return boolean
	 */
	@Override
	public boolean isFirefly() {
		update();
		return con.getBoolean("firefly");
	}

	/**
	 * @author xize
	 * @param set firefly for this player
	 * @return void
	 */
	@Override
	public void setFirefly(Boolean bol) {
		con.set("firefly", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * 
	 * @author xize
	 * @param checks whenever the player has set his home
	 * @return boolean
	 * 
	 */
	@Override
	public boolean hasHome() {
		update();
		if(con.contains("homes.default")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns true if the home name is valid within the String
	 * @return boolean
	 */
	@Override
	public boolean isValidHome(String home) {
		update();
		if(con.contains("homes."+home)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author xize
	 * @param gets all homes of this player!
	 * @return List<Home>
	 * 
	 */
	@Override
	public List<Home> getAllHomes() {
		update();
		List<Home> homes = new ArrayList<Home>();
		if(hasHome()) {
			for(String home : con.getConfigurationSection("homes").getKeys(false)) {
				Home playerhome = new Home(con, home);
				homes.add(playerhome);
			}	
		}
		return homes;
	}

	/**
	 * @author xize
	 * @param homeName
	 * @return Home
	 * @throws NullPointerException
	 * 
	 */
	@Override
	public Home getHome(String homeName) {
		update();
		Home home = new Home(con, homeName);
		return home;
	}

	/**
	 * @author xize
	 * @param gets the fixed size of all the homes from this player
	 * @return int
	 */
	@Override
	public int getAmountOfHomes() {
		//returns a fixed version for permissions;)
		return (this.getAllHomes().size());
	}

	/**
	 * @author xize
	 * @param allows the player to set his home!, the key world default is the default home!
	 * @return void
	 */
	@Override
	public void setHome(String home) {
		update();
		con.set("homes."+home.toLowerCase()+".x", player.getLocation().getX());
		con.set("homes."+home.toLowerCase()+".y", player.getLocation().getY());
		con.set("homes."+home.toLowerCase()+".z", player.getLocation().getZ());
		con.set("homes."+home.toLowerCase()+".yaw", player.getLocation().getYaw());
		con.set("homes."+home.toLowerCase()+".pitch", player.getLocation().getPitch());
		con.set("homes."+home.toLowerCase()+".world", player.getWorld().getName());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param remove the home
	 * @param home
	 * @return void
	 */
	@Override
	public void removeHome(String home) {
		update();
		con.set("homes."+home.toLowerCase(), null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param is true when vanished otherwise false
	 * @return boolean
	 */
	@Override
	public boolean isVanished() {
		update();
		if(con.contains("isVanished")) {
			return con.getBoolean("isVanished");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param vanish the current player for all others
	 * @return void
	 */
	@Override
	public void vanish() {
		for(Player p : xEssentials.getOnlinePlayers()) {
			if(!player.equals(p)) {
					p.hidePlayer(player);
			}
		}
		con.set("isVanished", true);
		con.set("noPickUp", true);
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param unvanish the player for all others
	 * @return void
	 */
	@Override
	public void unvanish() {
		update();
		for(Player p : xEssentials.getOnlinePlayers()) {
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
				LavaPilar pilar = new LavaPilar(pilars);
				pilar.startTask();
			}
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
			player.getWorld().playSound(player.getLocation(), Sound.WITHER_IDLE, 1F, 1F);
		}
		con.set("isVanished", false);
		con.set("noPickUp", false);
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param unvanish the player for all others
	 * @return void
	 */
	@Override
	public void unvanish(boolean sillenced) {
		update();
		for(Player p : xEssentials.getOnlinePlayers()) {
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
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has vanish effects
	 * @return Boolean
	 */
	@Override
	public boolean hasVanishEffects() {
		update();
		if(con.getBoolean("vanishEffects")) {
			return con.getBoolean("vanishEffects");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param sets the vanish effects of this player
	 * @param bol - the boolean
	 */
	@Override
	public void setVanishEffects(Boolean bol) {
		con.set("vanishEffects", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param checks whenever the player has no pick up enabled this is for item drops and interaction for vanish
	 * @return boolean
	 */
	@Override
	public boolean isNoPickUpEnabled() {
		update();
		if(con.contains("noPickUp")) {
			return con.getBoolean("noPickUp");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param sets the pickup state of this player only affects while vanished
	 * @return void
	 */
	@Override
	public void setNoPickUp(Boolean bol) {
		con.set("noPickUp", bol);
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param unsets the pickup state of this player only affects while vanished
	 * @return void
	 */
	@Override
	public void unsetNoPickUp() {
		con.set("noPickUp", false);
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param set the creative inventory in the config
	 * @return void
	 */
	@Override
	public void saveCreativeInventory() {
		update();
		try {
			if(con.contains("inventory.creative")) {
				con.set("inventory.creative.inv", null);
				con.set("inventory.creative.armor", null);
				con.save(f);
				update();
				con.set("inventory.creative.inv", player.getInventory().getContents());
				con.set("inventory.creative.armor", player.getInventory().getArmorContents());
				con.save(f);
				update();
			} else {
				con.set("inventory.creative.inv", player.getInventory().getContents());
				con.set("inventory.creative.armor", player.getInventory().getArmorContents());
				con.save(f);
				update();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param set the survival inventory in the config
	 * @return void
	 */
	@Override
	public void saveSurvivalInventory() {
		update();
		try {
			if(con.contains("inventory.survival")) {
				con.set("inventory.survival.inv", null);
				con.set("inventory.survival.armor", null);
				con.save(f);
				update();
				con.set("inventory.survival.inv", player.getInventory().getContents());
				con.set("inventory.survival.armor", player.getInventory().getArmorContents());
				con.save(f);
				update();
			} else {
				con.set("inventory.survival.inv", player.getInventory().getContents());
				con.set("inventory.survival.armor", player.getInventory().getArmorContents());
				con.save(f);
				update();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param checks whenever the player has a saved instance of the survival inventory
	 * @return boolean
	 */
	@Override
	public boolean hasSurvivalInventory() {
		update();
		if(con.contains("inventory.survival")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param checks whenever the player has a saved instance of the creative inventory
	 * @return boolean
	 */
	@Override
	public boolean hasCreativeInventory() {
		update();
		if(con.contains("inventory.creative")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the survival inventory
	 * @return ItemStack[]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ItemStack[] getSurvivalInventory() {
		update();
		ItemStack[] items = ((List<ItemStack>)con.get("inventory.survival.inv")).toArray(new ItemStack[0]);
		return items;
	}

	/**
	 * @author xize
	 * @param returns the survival armor
	 * @return ItemStack[]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ItemStack[] getSurvivalArmorInventory() {
		update();
		ItemStack[] items = ((List<ItemStack>)con.get("inventory.survival.armor")).toArray(new ItemStack[0]);
		return items;
	}

	/**
	 * @author xize
	 * @param returns the creative inventory
	 * @return ItemStack[]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ItemStack[] getCreativeInventory() {
		update();
		ItemStack[] items = ((List<ItemStack>)con.get("inventory.creative.inv")).toArray(new ItemStack[0]);
		return items;
	}

	/**
	 * @author xize
	 * @param returns the creative armor
	 * @return ItemStack[]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ItemStack[] getCreativeArmorInventory() {
		update();
		ItemStack[] items = ((List<ItemStack>)con.get("inventory.creative.armor")).toArray(new ItemStack[0]);
		return items;
	}

	/**
	 * @author xize
	 * @param checks whenever the player has a offline inventory
	 * @return boolean
	 */
	@Override
	public boolean hasOfflineInventory() {
		update();
		if(con.contains("offlineInventory.contents")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param save the inventory for offline view
	 * @return void
	 */
	@Override
	public void saveOfflineInventory() {
		if(hasOfflineInventory()) {
			con.set("offlineInventory.contents", null);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
		con.set("offlineInventory.contents", player.getInventory().getContents());
		try {
			con.save(f);
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param returns the online inventory
	 * @return Inventory
	 */
	@Override
	public Inventory getOnlineInventory() {
		Inventory inv = player.getInventory();
		return inv;
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has a modreq done message
	 * @return boolean
	 */
	@Override
	public boolean hasModreqDoneMessage() {
		update();
		if(con.contains("modreq.done.message")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the modreq done message
	 * @return String
	 */
	@Override
	public String getModreqDoneMessage() {
		return con.getString("modreq.done.message");
	}

	/**
	 * @author xize
	 * @param remove the modreq done message
	 * @return void
	 */
	@Override
	public void removeGetModregDoneMessage() {
		con.set("modreq", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param checks whenever this player has open modreqs
	 * @return boolean
	 */
	@Override
	public boolean hasModreqsOpen() {
		update();
		if(con.contains("modreqs")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param checks whenever a id is a valid ID
	 * @return boolean
	 */
	@Override
	public boolean isValidModreqId(int id) {
		update();
		if(con.contains("modreqs."+"modreq"+id)) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param gets the Modreq object containing all data
	 * @return Modreq
	 */
	@Override
	public Modreq getModreq(int id) {
		update();
		Modreq mod = new Modreq(con, id);
		return mod;
	}

	/**
	 * @author xize
	 * @param removes a modreq
	 * @return void
	 * @throws NullPointerException when the node doesn't exist
	 */
	@Override
	public void removeModreq(int id) {
		update();
		if(isValidModreqId(id)) {
			con.set("modreqs."+"modreq"+id, null);
			if(con.getConfigurationSection("modreqs").getKeys(true).isEmpty()) {
				con.set("modreqs", null);
			}
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		} else {
			throw new NullPointerException("you cannot remove a configuration node for a modreq wich doesn't exists!");
		}
	}

	/**
	 * @author xize
	 * @param returns a array of modreqs for this player
	 * @return Modreq[]
	 */
	@Override
	public Modreq[] getModreqs() {
		update();
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

	/**
	 * @author xize
	 * @param create a new modreq with a new ID
	 * @return void
	 */
	@Override
	public void createModreq(String message) {
		update();
		int id = getModreqs().length;
		con.set("modreqs."+"modreq"+id+".message", message);
		con.set("modreqs."+"modreq"+id+".getDate", System.currentTimeMillis());
		con.set("modreqs."+"modreq"+id+".location.x", player.getLocation().getX());
		con.set("modreqs."+"modreq"+id+".location.y", player.getLocation().getY());
		con.set("modreqs."+"modreq"+id+".location.z", player.getLocation().getZ());
		con.set("modreqs."+"modreq"+id+".location.yaw", player.getLocation().getY());
		con.set("modreqs."+"modreq"+id+".location.pitch", player.getLocation().getPitch());
		con.set("modreqs."+"modreq"+id+".location.world", player.getWorld().getName());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns the player
	 * @return Player
	 */
	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * @author xize
	 * @param returns the boolean whenever the inventory needs to be cleared on relog
	 * @return boolean
	 */
	@Override
	public boolean isInventoryClearanceOnRelog() {
		update();
		if(con.contains("ClearInventory")) {
			return con.getBoolean("ClearInventory");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param clears the inventory on relog
	 * @return void
	 */
	@Override
	public void ClearInventoryOnRelog() {
		update();
		if(isInventoryClearanceOnRelog()) {
			player.getInventory().clear();
			con.set("ClearInventory", null);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}

	/**
	 * @author xize
	 * @param get the alternate accounts of this player!
	 * @return AlternateAccount
	 */
	@Override
	public AlternateAccount getAlternateAccounts() {
		if(accounts instanceof AlternateAccount) {
			return accounts;
		} else {
			//this.accounts = new AlternateAccount(this);
			return accounts;
		}
	}

	/**
	 * @author xize
	 * @param check whenever the player has alternate accounts!
	 * @return boolean
	 */
	@Override
	public boolean hasAlternateAccounts() {
		AlternateAccount alts = getAlternateAccounts();
		if(alts.getAltNames().length > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasCompass() {
		update();
		if(con.contains("isCompass.enabled")) {
			return con.getBoolean("isCompass.enabled");
		}
		return false;
	}

	@Override
	public void setCompass(Player p) {
		con.set("isCompass.enabled", true);
		con.set("isCompass.follow", p.getName());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@Override
	public void setCompass(String p) {
		con.set("isCompass.enabled", true);
		con.set("isCompass.follow", p);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@Override
	public void removeCompass() {
		con.set("isCompass", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@Override
	public XOfflinePlayer getCompass() {
		update();
		return xEssentials.getManagers().getPlayerManager().getOfflinePlayer(con.getString("isCompass.follow"));
	}

	/**
	 * @author xize
	 * @param set the silence state of the player
	 * @param Boolean
	 * @return void
	 */
	@Override
	public void setSilenced(Boolean bol) {
		con.set("silenced", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has silenced the chat
	 * @return Boolean
	 */
	@Override
	public boolean isSilenced() {
		update();
		if(con.contains("silenced")) {
			return con.getBoolean("silenced");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param add a player to this players ignore list, this means that this player ignores the chat by the called name.
	 * @return void
	 */
	@Override
	public void addIgnoredPlayer(String s) {
		if(hasIgnoredPlayers()) {
			List<String> list = new ArrayList<String>(getIgnoredPlayers());
			list.add(s);
			con.set("IgnoredPlayers", list.toArray());
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		} else {
			String[] a = {s};
			con.set("IgnoredPlayers", Arrays.asList(a).toArray());
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();	
		}
	}

	/**
	 * @author xize
	 * @param get a list of all player names which get ignored by this player
	 * @return List<String>()
	 */
	@Override
	public List<String> getIgnoredPlayers() {
		return con.getStringList("IgnoredPlayers");
	}

	/**
	 * @author xize
	 * @param returns true whenever the player ignores a player
	 * @return Boolean
	 */
	@Override
	public boolean hasIgnoredPlayers() {
		update();
		if(con.contains("IgnoredPlayers")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param remove a player from the ignore list
	 * @return void
	 */
	@Override
	public void removeIgnoredPlayer(String s) {
		List<String> list = new ArrayList<String>(getIgnoredPlayers());
		if(list.size() == 1) {
			con.set("IgnoredPlayers", null);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		} else {
			list.remove(s);
			con.set("IgnoredPlayers", list.toArray());
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is freezed
	 * @return Boolean
	 */
	@Override
	public boolean isFreezed() {
		if(con.contains("isFreezed")) {
			return con.getBoolean("isFreezed");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param freezes the player or unfreeze the player
	 * @param void
	 */
	@Override
	public void setFreezed(Boolean bol) {
		con.set("isFreezed", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever a player has trollmode enabled!
	 * @return Boolean
	 */
	@Override
	public boolean isTrollMode() {
		if(con.contains("isTrollmode")) {
			return con.getBoolean("isTrollmode");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param enabled trollmode or disable trollmode
	 * @return void
	 */
	@Override
	public void setTrollMode(Boolean bol) {
		con.set("isTrollmode", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns true if the player has command restrictions
	 * @return Boolean
	 */
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

	/**
	 * @author xize
	 * @param command - the command+args which needs to be disabled
	 * @param reason - the reason why the command is blocked for the player
	 * @param taskCommand - an aditional task: command+args, if null we ignore this.
	 * @return void
	 */
	@Override
	public void setCommandRestriction(String command, String reason, String taskCommand) {
		if(taskCommand == null) {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason);
			con.set("command-restrictions", list);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();	
		} else {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason+","+taskCommand);
			con.set("command-restrictions", list);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}

	/**
	 * @author xize
	 * @param returns a list of all command restrictions for this player
	 * @return List<CommandRestriction>();
	 */
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

	/**
	 * @author xize
	 * @param check if the player has a restriction inside the list, make sure to use hasCommandRestrictions() first.
	 * @return Boolean
	 */
	@Override
	public boolean hasContainedRestriction(String command) {
		for(RestrictedCommand restriction : getCommandRestrictions()) {
			if(restriction.getCommand().equalsIgnoreCase(command)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author xize
	 * @param remove the command restriction
	 * @return void
	 */
	@Override
	public void removeCommandRestriction(RestrictedCommand cmd) {
		List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
		list.remove(cmd.getSerializedKey());
		con.set("command-restrictions", list);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param set the kit cooldown
	 * @return void
	 */
	@Override
	public void setKitCooldown(Long cooldown) {
		con.set("kitCooldown", cooldown);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param remove the kit cooldown
	 * @return void
	 */
	@Override
	public void removeKitCoolDown() {
		con.set("kitCooldown", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param return the saved cooldown of this player
	 * @return Long
	 */
	@Override
	public Long getKitCooldown() {
		return con.getLong("kitCooldown");
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has the cooldown else false
	 * @return Boolean
	 */
	@Override
	public Boolean hasKitCooldown() {
		if(con.contains("kitCooldown")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the total amount of Essentials money of this player
	 * @return Double
	 */
	@Override
	public Double getTotalEssentialsMoney() {
		update();   
		return con.getDouble("money");
	}

	/**
	 * @author xize
	 * @param returns true if the player has money if its 0.0 or the config entry doesn't exist it is false
	 * @return Boolean
	 */
	@Override
	public Boolean hasEssentialsMoney() {
		update();
		if(con.contains("money")) {
			if(con.getDouble("money") > 0.0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author xize
	 * @param price - the price the player is gonna be to pay
	 * @param toPayTo - the retriever
	 * @return Boolean - if the player has no money it will be false.
	 */
	@Override
	public Boolean payEssentialsMoney(Double price, XOfflinePlayer toPayTo) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money >= 0.0) {
			con.set("money", money);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
			toPayTo.addEssentialsMoney(price);
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param price - the price the player is gonna be to pay
	 * @param toPayTo - the retriever
	 * @return Boolean - if the player has no money it will be false.
	 */
	@Override
	public Boolean payEssentialsMoney(Double price, XPlayer toPayTo) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money >= 0.0) {
			con.set("money", money);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
			toPayTo.addEssentialsMoney(price);
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param this will add money to the players bank
	 */
	@Override
	public void addEssentialsMoney(Double price) {
		update();
		con.set("money", getTotalEssentialsMoney()+price);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param price - the price
	 * @return Boolean
	 */
	@Override
	public boolean hasPlayerEnoughMoneyFromPrice(Double price) {
		update();
		if(getTotalEssentialsMoney() >= price) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param clear the money of the player
	 * @return void
	 */
	@Override
	public void clearMoney() {
		update();
		con.set("money", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param set the old name in the history
	 * @param oldName - old name
	 */
	@Override
	public void setNameHistory(String oldName) {
		List<String> list = con.getStringList("name-history");
		list.add(oldName);
		if(list.size() > 8) {
			list.remove(8);
		}
		con.set("name-history", list);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns atleast 8 results of this players name history
	 * @return List<String>()
	 */
	@Override
	public List<String> getNameHistory() {
		return con.getStringList("name-history");
	}

	/**
	 * @author xize
	 * @param returns true if the player has name history
	 * @return Boolean
	 */
	@Override
	public boolean hasNameHistory() {
		return !con.getStringList("name-history").isEmpty();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has a saved inventory
	 * @return Boolean
	 */
	@Override
	public boolean hasSavedInventory() {
		if(con.contains("orginalinv")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param saves the inventory of the player, unlike the saveSurvivalInventory, this will be used for minigames.
	 */
	@Override
	public void saveInventory() {
		con.set("orginalinv.items", player.getInventory().getContents());
		con.set("orginalinv.armor", player.getInventory().getArmorContents());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param loads the inventory of the player, unlike the saveSurvivalInventory, this will be used for minigames.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void loadInventory() {
		ItemStack[] contents = ((List<ItemStack>)con.get("orginalinv.items")).toArray(new ItemStack[0]);
		ItemStack[] armor = ((List<ItemStack>)con.get("orginalinv.armor")).toArray(new ItemStack[0]);
		player.getInventory().setContents(contents);
		player.getInventory().setArmorContents(armor);
		con.set("orginalinv", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param bol - when the boolean is true enable procs, else disable procs
	 */
	@Override
	public void setProc(boolean bol) {
		con.set("proc.enable", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	@Override
	public void setCustomName(String name) {
		player.setDisplayName(name);
		player.setPlayerListName(name);
		try {
			con.set("customname", name);
			con.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		update();
	}
	
	@Override
	public boolean hasCustomName() {
		return (player.getDisplayName().equals(getUser()) ? false : true);
	}
	
	@Override
	public String getCustomName() {
		return (con.getString("customname") == null ? player.getName() : con.getString("customname"));
	}

	/**
	 * @author xize
	 * @param return true when the player has procs
	 * @return Boolean
	 */
	@Override
	public boolean hasProc() {
		if(con.contains("proc.enable")) {
			return con.getBoolean("proc.enable");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param bol - sets the anti knockback of this player.
	 */
	@Override
	public void setKnock(boolean bol) {
		con.set("knock", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns the state of the anti knockback.
	 * @return Boolean
	 */
	@Override
	public boolean isKnock() {
		if(con.contains("knock")) {
			return con.getBoolean("knock");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param sets the player in a chair.
	 */
	@Override
	public void setInChair(Boolean bol) {
		con.set("chair", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns true if the player sits in a chair
	 * @return Boolean
	 */
	@Override
	public boolean isInChair() {
		update();
		if(con.contains("chair")) {
			return con.getBoolean("chair");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param toggles double jump
	 * @param bol - true when enabled, false when disabled.
	 */
	@Override
	public void setDoubleJump(Boolean bol) {
		try {
			con.set("doublejump", bol);
			con.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns true if the player has double jump else false
	 * @return Boolean
	 */
	@Override
	public boolean hasDoubleJump() {
		if(con.contains("doublejump")) {
			return con.getBoolean("doublejump");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param returns true if the player has spectate on
	 * @return Boolean
	 */
	@Override
	public boolean isSpectate() {
		if(spectate instanceof BukkitTask) {
			return true;
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param stops the spectate
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void stopSpectate() {
		if(isSpectate()) {
			spectate.cancel();
			spectate = null;
			player.performCommand("spawn");
			ItemStack[] contents = ((List<ItemStack>)con.get("spectate-inventory")).toArray(new ItemStack[0]);
			player.getInventory().setContents(contents);
			con.set("spectate-inventory", null);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		}
	}
	
	/**
	 * @author xize
	 * @param spectates a player.
	 * @param p
	 */
	@Override
	public void spectate(final Player pa) {
		con.set("spectate-inventory", player.getInventory().getContents());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
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
		}.runTaskTimer(xEssentials.getPlugin(), 0L, 1L);
	}
	
	/**
	 * @author xize
	 * @param returns true when edit mode is enabled
	 * @return Boolean
	 */
	@Override
	public boolean isEditSignEnabled() {
		if(con.contains("signedit")) {
			return con.getBoolean("signedit");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param bol - sets the mode whenever a player edits a sign
	 */
	@Override
	public void setEditSign(Boolean bol) {
		con.set("signedit", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns true when player is in drunk mode
	 * @return Boolean
	 */
	@Override
	public boolean isDrunk() {
		if(con.contains("drunk")) {
			return con.getBoolean("drunk");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param bol - sets the drunk state
	 */
	@Override
	public void setDrunk(Boolean bol) {
		con.set("drunk", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param message - converts a normal message in a derpish message :)
	 * @return String
	 */
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

	@Override
	public boolean isSpace(char chr) {
		switch(chr) {
		case ' ' : return true;
		default : return false;
		}
	}
	
	@Override
	public boolean isLitteral(char chr) {
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
	
	/**
	 * @author xize
	 * @param returns true if floor mode is true
	 * @return boolean
	 */
	@Override
	public boolean isFloorMode() {
		if(con.contains("floormode.enable")) {
			return con.getBoolean("floormode.enable");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param bol - when true build mode is activated, else its not.
	 * @param range - the range of the floor mode
	 */
	@Override
	public void setFloorMode(boolean bol, int range) {
		con.set("floormode.enable", bol);
		con.set("floormode.range", range);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns the range of the floor
	 * @return int
	 */
	@Override
	public int getFloorModeRange() {
		return con.getInt("floormode.range");
	}
	
	/**
	 * @author xize
	 * @param returns true if the wall mode is activated
	 * @return
	 */
	@Override
	public boolean isWallMode() {
		if(con.contains("wallmode.enable")) {
			return con.getBoolean("wallmode.enable");
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param bol - activates the wall mode
	 * @param range - the range how heigh the wall should be.
	 */
	@Override
	public void setWallMode(boolean bol, int range) {
		con.set("wallmode.enable", bol);
		con.set("wallmode.range", range);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns the wall size
	 * @return int
	 */
	@Override
	public int getWallModeRange() {
		return con.getInt("wallmode.range");
	}
	
	@Override
	public void setShop(Location loc, Chest chest) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		con.set("shops."+id.toString()+".location", loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ());
		con.set("shops."+id.toString()+".chestloc", chest.getWorld().getName()+":"+chest.getX()+":"+chest.getY()+":"+chest.getZ());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	@Override
	public boolean isShop(Location loc) {
		update();
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		if(con.contains("shops."+id.toString())) {
			return true;
		}
		return false;
	}
	
	@Override
	public Shop getShop(Location loc) {
		update();
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
			return new Shop(chest, sign, xEssentials.getManagers().getPlayerManager().getOfflinePlayer(getUser()));
		}
		return null;
	}
	
	@Override
	public void removeShop(Location loc) {
		update();
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		con.set("shops."+id.toString(), null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@Override
	public void PrepareLoginTask(String command, PlayerTaskEnum task) {
		con.set("task.command", command);
		con.set("task.type", task.name());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Inventory getOfflineInventory(Player viewer) {
		update();
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		if(hasOfflineInventory()) {
			ItemStack[] items = ((List<ItemStack>)con.get("offlineInventory.contents")).toArray(new ItemStack[0]);	
			inv.setContents(items);
		}
		return inv;
	}

	@Override
	public void setModreqDoneMessage(String message) {
		con.set("modreq.done.message", message);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	@Override
	public XPlayer getEssentialsPlayer() {
		return xEssentials.getManagers().getPlayerManager().getPlayer(getUser());
	}

	@Override
	public Location getLastLocation() {
		update();
		if(con.contains("lastLocation")) {
			return new Location(Bukkit.getWorld(con.getString("lastLocation.world")), con.getDouble("lastLocation.x"), con.getDouble("lastLocation.y"), con.getDouble("lastLocation.z"), con.getInt("lastLocation.yaw"), con.getInt("lastLocation.pitch"));
		}
		return null;
	}

	@Override
	public boolean payEssentialsMoney(double money) {
		update();
		Double moneya = (getTotalEssentialsMoney()-money);
		if(moneya >= 0.0) {
			con.set("money", moneya);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
			return true;
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param level - the level of the relogs
	 */
	@Override
	public void setPwnageLevel(int level) {
		con.set("pwnage-level", level);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	@Override
	public boolean hasPwnageLevel() {
		update();
		return (con.contains("pwnage-level") || getPwnageLevel() > 0 ? true : false);
	}
	
	/**
	 * @author xize
	 * @param returns the current level.
	 * @return int
	 */
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
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	@Override
	public boolean hasLastLoginTime() {
		update();
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

	/**
	 * @author xize
	 * @param gets updated at every call so we know that the configuration stored in the memory is still recent with the flat saved file!
	 * @return void
	 */
	@Override
	public void update() {
		try {
			con.save(f);
			con.load(f);
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
		result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
		result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			if(obj instanceof xEssentialsPlayer) {
				xEssentialsPlayer xp = (xEssentialsPlayer) obj;
				//make sure name and uuid matches, now these objects will be detected in HashMaps even though a field may differentates see older commit.
				return xp.getUser().equalsIgnoreCase(this.getUser()) && xp.getUniqueId().equals(this.getUniqueId());
			}
		}
		return false;
	}

}
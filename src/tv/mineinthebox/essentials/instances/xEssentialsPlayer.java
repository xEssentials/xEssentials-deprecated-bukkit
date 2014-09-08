package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.events.customEvents.PlayerNameChangeEvent;
import tv.mineinthebox.essentials.events.players.FakeNukeEvent;

public class xEssentialsPlayer {

	private Player player;
	private File f;
	private FileConfiguration con;
	private Item Potato;
	private AlternateAccount accounts;
	private BukkitTask spectate;

	/**
	 * 
	 * @author xize
	 * @param a constructor which pass our interface, if there is no user file this constructor will create a new one this constructor only allows type Player.
	 * @return xEssentialsPlayer
	 * 
	 */
	public xEssentialsPlayer(Player player, String UUID) {
		this.player = player;
		if(Bukkit.getOnlineMode()) {
			this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players"+File.separator+UUID+".yml");	
		} else {
			this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players"+File.separator+player.getName().toLowerCase()+".yml");
		}
		if(this.f.exists()){
			this.con = YamlConfiguration.loadConfiguration(this.f);
			if(!this.con.getString("user").equalsIgnoreCase(player.getName())) {
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
				Bukkit.getPluginManager().callEvent(new PlayerNameChangeEvent(oldName, player.getName(), player, this));
				setNameHistory(oldName);
				if(Configuration.getProtectionConfig().isProtectionEnabled()) {
					xEssentials.getManagers().getProtectionDBManager().updatePlayer(oldName, player.getName());
				}
			} else {
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
			File possiblename = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players" + File.separator + player.getName().toLowerCase() + ".yml");
			if(possiblename.exists()) {
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
		update();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is greylisted
	 * @return boolean
	 */
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
	public String getPowerTool() {
		update();
		return con.getString("powertool."+player.getItemInHand().getType().name()+".command");
	}

	/**
	 * @author xize
	 * @param removes the powertool
	 * @return void
	 */
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
	public void nuke() {
		for(int x = -16; x <= 16; x +=8) {
			for(int z = -16; z<=16; z +=8) {
				player.getWorld().spawnEntity(new Location(player.getWorld(), player.getLocation().getBlockX()+x,100,player.getLocation().getBlockZ()+z), EntityType.PRIMED_TNT);		
			}
		}
	}

	/**
	 * @author xize
	 * @param nukes the player with a massive explosion rain but as fake
	 * @return void
	 */
	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	private boolean isFallingBreakAble(Block block) {
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
	public String getIp() {
		return player.getAddress().getAddress().getHostAddress();
	}

	/**
	 * @author xize
	 * @param refresh the player on teleport
	 * @deprecated is deprecated because this is a tempory fix for invisibillity delay while teleporting.
	 * @return void
	 */
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
	public Long getMutedTime() {
		return con.getLong("muted.mutedTime");
	}

	/**
	 * @author xize
	 * @param check whenever this player is perm banned
	 * @return boolean
	 */
	public boolean isPermBanned() {
		return Bukkit.getServer().getBanList(Type.NAME).isBanned(player.getName());
	}

	/**
	 * @author xize
	 * @param sets the player permbanned
	 * @return void
	 */
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
	public String getTempBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getReason();
	}

	/**
	 * @author xize
	 * @param unbans the player for both Tempbanned or PermBanned
	 * @return void
	 */
	public void unban() {
		Bukkit.getServer().getBanList(Type.NAME).pardon(player.getName());
	}

	/**
	 * @author xize
	 * @param returns true whenever the player is banned before
	 * @return boolean
	 */
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
	public Long getTempbanRemaining() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player.getName()).getExpiration().getTime();
	}

	/**
	 * 
	 * @author xize
	 * @param get the Unique ID of this player
	 * @return String
	 */
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
	public boolean isFlying() {
		update();
		return con.getBoolean("fly");
	}

	/**
	 * @author xize
	 * @param set the fly mode of the player
	 * @return void
	 */
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
	public boolean isTorch() {
		update();
		return con.getBoolean("torch");
	}

	/**
	 * @author xize
	 * @param set the torch mode to false or on
	 * @return void
	 */
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
	public boolean isFirefly() {
		update();
		return con.getBoolean("firefly");
	}

	/**
	 * @author xize
	 * @param set firefly for this player
	 * @return void
	 */
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
	public int getAmountOfHomes() {
		//returns a fixed version for permissions;)
		return (this.getAllHomes().size());
	}

	/**
	 * @author xize
	 * @param allows the player to set his home!, the key world default is the default home!
	 * @return void
	 */
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
	public void vanish() {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Team vanishTeam = board.registerNewTeam("vanish");
		vanishTeam.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+"[vn]" + ChatColor.RESET);
		vanishTeam.setCanSeeFriendlyInvisibles(true);
		vanishTeam.addPlayer(player);
		player.setScoreboard(board);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 100));
		for(Player p : xEssentials.getOnlinePlayers()) {
			if(!player.equals(p)) {
				if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					vanishTeam.addPlayer(p);
					p.setScoreboard(board);
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 100));
				} else {
					p.hidePlayer(player);
				}
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
	@SuppressWarnings("deprecation")
	public void unvanish() {
		update();
		player.getScoreboard().getTeam("vanish").removePlayer(player);
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
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
	public void unvanish(boolean sillenced) {
		update();
		player.getScoreboard().getTeam("vanish").removePlayer(player);
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
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
	@SuppressWarnings("unchecked")
	public ItemStack[] getCreativeArmorInventory() {
		update();
		ItemStack[] items = ((List<ItemStack>)con.get("inventory.creative.armor")).toArray(new ItemStack[0]);
		return items;
	}

	/**
	 * @author xize
	 * @param save the last location of the player
	 * @return void
	 */
	public void saveLocation() {
		con.set("lastLocation.x", player.getLocation().getX());
		con.set("lastLocation.y", player.getLocation().getY());
		con.set("lastLocation.z", player.getLocation().getZ());
		con.set("lastLocation.yaw", player.getLocation().getYaw());
		con.set("lastLocation.pitch", player.getLocation().getPitch());
		con.set("lastLocation.world", player.getWorld().getName());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param checks whenever the player has a offline inventory
	 * @return boolean
	 */
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
	 * @param returns the offline inventory
	 * @return Inventory
	 */
	@SuppressWarnings("unchecked")
	public Inventory getOfflineInventory() {
		update();
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		if(hasOfflineInventory()) {
			ItemStack[] items = ((List<ItemStack>)con.get("offlineInventory.contents")).toArray(new ItemStack[0]);	
			inv.setContents(items);
		}
		return inv;
	}

	/**
	 * @author xize
	 * @param returns the online inventory
	 * @return Inventory
	 */
	public Inventory getOnlineInventory() {
		Inventory inv = player.getInventory();
		return inv;
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has a modreq done message
	 * @return boolean
	 */
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
	public String getModreqDoneMessage() {
		return con.getString("modreq.done.message");
	}

	/**
	 * @author xize
	 * @param remove the modreq done message
	 * @return void
	 */
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
	public Player getPlayer() {
		return player;
	}

	/**
	 * @author xize
	 * @param returns the boolean whenever the inventory needs to be cleared on relog
	 * @return boolean
	 */
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
	public AlternateAccount getAlternateAccounts() {
		if(accounts instanceof AlternateAccount) {
			return accounts;
		} else {
			this.accounts = new AlternateAccount(this);
			return accounts;
		}
	}

	/**
	 * @author xize
	 * @param check whenever the player has alternate accounts!
	 * @return boolean
	 */
	public boolean hasAlternateAccounts() {
		AlternateAccount alts = getAlternateAccounts();
		if(alts.getAltNames().length > 0) {
			return true;
		}
		return false;
	}

	public boolean hasCompass() {
		update();
		if(con.contains("isCompass.enabled")) {
			return con.getBoolean("isCompass.enabled");
		}
		return false;
	}

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

	public xEssentialsOfflinePlayer getCompass() {
		update();
		return xEssentials.getManagers().getPlayerManager().getOfflinePlayer(con.getString("isCompass.follow"));
	}

	/**
	 * @author xize
	 * @param set the silence state of the player
	 * @param Boolean
	 * @return void
	 */
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
	public List<String> getIgnoredPlayers() {
		return con.getStringList("IgnoredPlayers");
	}

	/**
	 * @author xize
	 * @param returns true whenever the player ignores a player
	 * @return Boolean
	 */
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
	public Long getKitCooldown() {
		return con.getLong("kitCooldown");
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has the cooldown else false
	 * @return Boolean
	 */
	public Boolean hasKitCooldown() {
		if(con.contains("kitCooldown")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns true if the player owns warps
	 * @return Boolean
	 */
	public boolean hasOwnedWarps() {
		update();
		if(con.contains("warp")) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns all the warps of this player!
	 * @return Warp[]
	 */
	public Warp[] getWarps() {
		update();
		List<Warp> warps = new ArrayList<Warp>();
		for(String key : con.getConfigurationSection("warp").getKeys(false)) {
			Warp warp = new Warp(con, getPlayer().getName(), key);
			warps.add(warp);
		}
		return warps.toArray(new Warp[warps.size()]);
	}

	/**
	 * @author xize
	 * @param warpname - the warpname
	 * @param loc - the Location of the player
	 * @param sets the warp.
	 */
	public void setWarp(String warpname, Location loc) {
		con.set("warp."+warpname.toLowerCase()+".x", loc.getX());
		con.set("warp."+warpname.toLowerCase()+".y", loc.getY());
		con.set("warp."+warpname.toLowerCase()+".z", loc.getZ());
		con.set("warp."+warpname.toLowerCase()+".world", loc.getWorld().getName());
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
	 * @param warpname - the disired warp name to be removed
	 */
	public void removeWarp(String warpname) {
		con.set("warp."+warpname.toLowerCase(), null);
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
	 * @param key - possible warp name
	 * @param returns true whenever the possible name of the warp exist
	 * @return Boolean
	 */
	public Boolean isValidWarp(String key) {
		update();
		if(con.contains("warp."+key.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param get a warp through a key
	 * @return Warp
	 * @throws NullPointerException - when there is no warp and you didn't used isValidWarp()
	 */
	public Warp getWarp(String key) {
		if(isValidWarp(key)) {
			return new Warp(con, getPlayer().getName(), key);
		}
		throw new NullPointerException("this player has no warp called " + key);
	}

	/**
	 * @author xize
	 * @param returns the total amount of Essentials money of this player
	 * @return Double
	 */
	public Double getTotalEssentialsMoney() {
		update();   
		return con.getDouble("money");
	}

	/**
	 * @author xize
	 * @param returns true if the player has money if its 0.0 or the config entry doesn't exist it is false
	 * @return Boolean
	 */
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
	 * @param this withdraws the players money
	 * @return Boolean
	 */
	public Boolean payEssentialsMoney(Double price) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money > 0.0 || money == 0.0) {
			con.set("money", money);
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
	 * @param price - the price the player is gonna be to pay
	 * @param toPayTo - the retriever
	 * @return Boolean - if the player has no money it will be false.
	 */
	public Boolean payEssentialsMoney(Double price, xEssentialsOfflinePlayer toPayTo) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money > 0.0 || money == 0) {
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
	public Boolean payEssentialsMoney(Double price, xEssentialsPlayer toPayTo) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money > 0.0 || money == 0) {
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
	public boolean hasPlayerEnoughMoneyFromPrice(Double price) {
		update();
		if(getTotalEssentialsMoney() > price || getTotalEssentialsMoney() == price) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param clear the money of the player
	 * @return void
	 */
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
	 * @param get all the shop signs from the player
	 * @return List<String>()
	 */
	public List<String> getSignShops() {
		return con.getStringList("signshops");
	}

	/**
	 * @author xize
	 * @param adds a shop sign to this player
	 * @param loc - the Location
	 */
	public void addShopSign(Location loc) {
		List<String> signs = new ArrayList<String>(getSignShops());
		String serialize = loc.getWorld().getName()+":"+loc.getX()+":"+loc.getY()+":"+loc.getZ();
		signs.add(serialize);
		con.set("signshops", null);
		con.set("signshops", signs.toArray());
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
	 * @param loc - the Location
	 * @param checks if the sign is a contained sign in the configuration of this player
	 * @return Boolean
	 */
	public boolean containsShopSign(Location loc) {
		update();
		String serialize = loc.getWorld().getName()+":"+loc.getX()+":"+loc.getY()+":"+loc.getZ();
		List<String> list = new ArrayList<String>(getSignShops());
		return list.contains(serialize);
	}

	/**
	 * @author xize
	 * @param removes the sign
	 * @param loc - the Location
	 */
	public void removeShopSign(Location loc) {
		update();
		String serialize = loc.getWorld().getName()+":"+loc.getX()+":"+loc.getY()+":"+loc.getZ();
		List<String> locations = new ArrayList<String>(getSignShops());
		if(locations.contains(serialize)) {
			locations.remove(serialize);
			con.set("signshops", null);
			con.set("signshops", locations.toArray());
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update(); 
		} else {
			throw new NullPointerException("sign does not exist on this player!");
		}
	}

	/**
	 * @author xize
	 * @param set the old name in the history
	 * @param oldName - old name
	 */
	private void setNameHistory(String oldName) {
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
	public List<String> getNameHistory() {
		return con.getStringList("name-history");
	}

	/**
	 * @author xize
	 * @param returns true if the player has name history
	 * @return Boolean
	 */
	public boolean hasNameHistory() {
		return !con.getStringList("name-history").isEmpty();
	}

	/**
	 * @author xize
	 * @param returns true whenever the player has a saved inventory
	 * @return Boolean
	 */
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
	
	public boolean hasCustomName() {
		return (player.getDisplayName().equals(getUser()) ? false : true);
	}
	
	public String getCustomName() {
		return (con.getString("customname") == null ? player.getName() : con.getString("customname"));
	}

	/**
	 * @author xize
	 * @param return true when the player has procs
	 * @return Boolean
	 */
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
			unvanish(true);
		}
	}
	
	/**
	 * @author xize
	 * @param spectates a player.
	 * @param p
	 */
	public void spectate(final Player pa) {
		vanish();
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
	 * @param gets updated at every call so we know that the configuration stored in the memory is still recent with the flat saved file!
	 * @return void
	 */
	private void update() {
		try {
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
		result = prime * result + ((Potato == null) ? 0 : Potato.hashCode());
		result = prime * result + ((con == null) ? 0 : con.hashCode());
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		xEssentialsPlayer other = (xEssentialsPlayer) obj;
		if (Potato == null) {
			if (other.Potato != null)
				return false;
		} else if (!Potato.equals(other.Potato))
			return false;
		if (con == null) {
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

}

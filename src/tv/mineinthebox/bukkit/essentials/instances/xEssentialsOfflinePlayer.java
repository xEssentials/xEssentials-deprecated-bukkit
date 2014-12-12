package tv.mineinthebox.bukkit.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PlayerTaskEnum;

public class xEssentialsOfflinePlayer {

	private final File f;
	private final FileConfiguration con;
	private String player;
	private AlternateAccount accounts;

	/**
	 * @author xize
	 * @param a constructor which pass our interface, if there is no user file this constructor will create a new one this constructor only allows type Player.
	 * @return xEssentialsOfflinePlayer
	 * @throws NullPointerException when there is no configuration file of this player
	 */
	public xEssentialsOfflinePlayer(String player) {
		this.f = xEssentials.getManagers().getPlayerManager().getOfflinePlayerFile(player);
		this.player = player;
		if(!this.f.getName().equals(null)) {
			if(this.f.exists()){
				this.con = YamlConfiguration.loadConfiguration(this.f);	
			} else {
				throw new NullPointerException();
			}
		} else {
			throw new NullPointerException();
		}
	}
	
	public xEssentialsOfflinePlayer(OfflinePlayer offliner) {
		this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players" + File.separator + offliner.getName() + ".yml");
		if(!this.f.exists()) {
			this.con = YamlConfiguration.loadConfiguration(this.f);
			this.con.set("isDefault", true);
			this.con.set("ip", "fake");
			this.con.set("user", offliner.getName());
			this.con.set("fly", false);
			this.con.set("torch", false);
			this.con.set("firefly", false);
			this.con.set("uuid", offliner.getUniqueId().toString());
			if(Configuration.getEconomyConfig().isEconomyEnabled()){
				this.con.set("money", Configuration.getEconomyConfig().getStartersMoney());
			}
			try {
				this.con.save(this.f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update();
		} else {
			this.con = YamlConfiguration.loadConfiguration(this.f);
		}
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
	 * @param prepares a command task on players next join
	 * @return void
	 */
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
		getPlayer().setFlySpeed(0.2f);
	}

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
	public void setPotato() {
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
	 * @param gets the player if online
	 * @return
	 */
	public Player getPlayer() {
		Player p = Bukkit.getPlayer(UUID.fromString(getUniqueId()));
		if(p instanceof Player) {
			return p;
		}
		return null;
	}

	/**
	 * 
	 * @author xize
	 * @param gets the ip adress of this player
	 * @return String
	 * 
	 */
	public String getIp() {
		update();
		return con.getString("ip");
	}

	/**
	 * @author xize
	 * @param allows to unvanish the player when offline
	 * @return void
	 */
	public void unvanish() {
		update();
		con.set("isVanished", false);
		con.set("noPickUp", false);
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
	 * 
	 * @author xize
	 * @param set the ip of this player
	 * @return boolean
	 * 
	 */
	public boolean setIp(String ip) {
		con.set("ip", ip);
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
		update();
		return con.getString("user");
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
		return Bukkit.getServer().getBanList(Type.NAME).isBanned(player);
	}

	/**
	 * @author xize
	 * @param sets the player permbanned
	 * @return void
	 */
	public void setPermBanned(String reason, String who) {
		Bukkit.getServer().getBanList(Type.NAME).addBan(player, reason, null, who).save();
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
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getReason();
	}

	/**
	 * 
	 * @author xize
	 * @param returns true if the player is temp banned
	 * @return boolean
	 * 
	 */
	public boolean isTempBanned() {
		if(Bukkit.getBanList(Type.NAME).isBanned(player)) {
			Date date = Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getExpiration();
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
		Bukkit.getServer().getBanList(Type.NAME).addBan(player, reason, date, who).save();
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
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getReason();
	}

	/**
	 * @author xize
	 * @param unbans the player for both Tempbanned or PermBanned
	 * @return void
	 */
	public void unban() {
		Bukkit.getServer().getBanList(Type.NAME).pardon(player);
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
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getExpiration().getTime();
	}

	/**
	 * @author xize
	 * @param get the Unique ID of this player
	 * @return Long
	 */
	public String getUniqueId() {
		update();
		if(con.contains("uuid")) {
			return con.getString("uuid");
		}  else {
			String uid = f.getName().replace(".yml", "");
			return (uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20) + "-" +uid.substring(20, 32));
		}
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
	 * 
	 * @author xize
	 * @param checks whenever the player has set his home
	 * @return boolean
	 * 
	 */
	public boolean hasHome() {
		update();
		if(con.contains("homes")) {
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
	public Home getHome(String homeName) throws NullPointerException {
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
	 * @param gets the last location of this player
	 * @return Location
	 */
	public Location getLocation() {
		update();
		if(con.contains("lastLocation")) {
			return new Location(Bukkit.getWorld(con.getString("lastLocation.world")), con.getDouble("lastLocation.x"), con.getDouble("lastLocation.y"), con.getDouble("lastLocation.z"), con.getInt("lastLocation.yaw"), con.getInt("lastLocation.pitch"));
		}
		return null;
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
	 * @param returns the offline inventory
	 * @return Inventory
	 */
	@SuppressWarnings("unchecked")
	public Inventory getOfflineInventory(Player viewer) {
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
	 * @param set the last modreq done message for this player when used /done
	 * @return void
	 */
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

	/**
	 * @author xize
	 * @param clear the inventory on players relogs!
	 * @return void
	 */
	public void ClearInventoryOnRelog() {
		update();
		con.set("ClearInventory", true);
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

	public boolean isTrollMode() {
		if(con.contains("isTrollmode")) {
			return con.getBoolean("isTrollmode");
		}
		return false;
	}

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
			if(!con.getStringList("command-restrictions").isEmpty()) {
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
	 * @return CommandRestriction[]
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
	 * @param price - the price the player is gonna be to pay
	 * @param toPayTo - the retriever
	 * @return Boolean - if the player has no money it will be false.
	 */
	public Boolean payEssentialsMoney(Double price, xEssentialsPlayer toPayTo) {
		update();
		Double money = (getTotalEssentialsMoney()-price);
		if(money > 0.0) {
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
	 * @param price - the price
	 * @return Boolean
	 */
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
		con.set("knock", true);
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
	 * @param returns the essentials player
	 * @return xEssentialsPlayer
	 */
	public xEssentialsPlayer getEssentialsPlayer() {
		return xEssentials.getManagers().getPlayerManager().getPlayer(getUser());
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
	 * @param returns true when edit mode is enabled
	 * @return Boolean
	 */
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
	 * @param returns true if floor mode is true
	 * @return boolean
	 */
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
	public int getFloorModeRange() {
		return con.getInt("floormode.range");
	}
	
	/**
	 * @author xize
	 * @param returns true if the wall mode is activated
	 * @return
	 */
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
	
	public boolean isShop(Location loc) {
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		if(con.contains("shops."+id.toString())) {
			return true;
		}
		return false;
	}
	
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
			return new Shop(chest, sign, this);
		}
		return null;
	}
	
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
	
	/**
	 * @author xize
	 * @param returns the wall size
	 * @return int
	 */
	public int getWallModeRange() {
		return con.getInt("wallmode.range");
	}
	
	/**
	 * @author xize
	 * @param gets updated at every call so we know that the configuration stored in the memory is still recent with the flat saved file!
	 * @return void
	 */
	public void update() {
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
		result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
		result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			if(obj instanceof xEssentialsOfflinePlayer) {
				xEssentialsPlayer xp = (xEssentialsPlayer) obj;
				return xp.getUser().equalsIgnoreCase(this.getUser()) && xp.getUniqueId().equals(this.getUniqueId());
			}
		}
		return false;
	}

}


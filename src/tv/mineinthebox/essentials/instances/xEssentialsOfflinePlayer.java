package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.plugin.MinigameType;
import tv.mineinthebox.essentials.minigames.plugin.arena.GameStatus;

public class xEssentialsOfflinePlayer implements XOfflinePlayer {

	private final File f;
	private final FileConfiguration con;
	private final xEssentials pl;
	
	private String player;
	private AlternateAccount accounts;
	private GameStatus status;
	
	public xEssentialsOfflinePlayer(String player, xEssentials pl) {
		this.pl = pl;	
		this.f = pl.getManagers().getPlayerManager().getOfflinePlayerFile(player);
		this.player = player;
		if(!this.f.getName().equals(null)) {
			if(this.f.exists()){
				this.con = YamlConfiguration.loadConfiguration(f);
			} else {
				throw new NullPointerException();
			}
		} else {
			throw new NullPointerException();
		}
	}

	//this is ment for a dummy profile.
	public xEssentialsOfflinePlayer(OfflinePlayer offliner, xEssentials pl) {
		this.pl = pl;
		this.f = new File(pl.getDataFolder() + File.separator + "players" + File.separator + "town-"+offliner.getName() + ".yml");
		if(!this.f.exists()) {
			this.con = YamlConfiguration.loadConfiguration(f);
			this.con.set("isDefault", true);
			this.con.set("ip", "fake");
			this.con.set("user", "town-"+offliner.getName());
			this.con.set("fly", false);
			this.con.set("torch", false);
			this.con.set("firefly", false);
			this.con.set("uuid", offliner.getUniqueId().toString());
			if(pl.getConfiguration().getEconomyConfig().isEconomyEnabled()){
				this.con.set("money", pl.getConfiguration().getEconomyConfig().getStartersMoney());
			}
			save();
		} else {
			this.con = YamlConfiguration.loadConfiguration(f);
		}
	}

	@Override
	public boolean isOnline() {
		return false;
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
		save();
	}

	@Override
	public Player getBukkitPlayer() {
		Player p = Bukkit.getPlayer(getUniqueId());
		if(p instanceof Player) {
			return p;
		}
		return null;
	}

	@Override
	public String getIp() {
		save();
		return con.getString("ip");
	}

	@Override
	public String getName() {
		save();
		return con.getString("user");
	}

	@Override
	public boolean isMuted() {
		save();
		if(con.contains("muted.isMuted")) {
			return con.getBoolean("muted.isMuted");
		} else {
			return false;
		}
	}

	@Override
	public void unmute() {
		con.set("muted", null);
		save();
	}

	@Override
	public void mute(Long time) {
		con.set("muted.isMuted", true);
		con.set("muted.mutedTime", time);
		save();
	}

	@Override
	public Long getMutedTime() {
		return con.getLong("muted.mutedTime");
	}

	@Override
	public boolean isPermBanned() {
		return Bukkit.getServer().getBanList(Type.NAME).isBanned(player);
	}

	@Override
	public void setPermBanned(String reason, String who) {
		Bukkit.getServer().getBanList(Type.NAME).addBan(player, reason, null, who).save();
		con.set("banned.isBanned", true);
		save();
	}

	@Override
	public String getBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getReason();
	}

	@Override
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

	@Override
	public void setTempbanned(Long time, String reason, String who) {
		Date date = new Date(time);
		Bukkit.getServer().getBanList(Type.NAME).addBan(player, reason, date, who).save();
		con.set("tempbanned.isBanned", true);
		save();
	}

	@Override
	public String getTempBanMessage() {
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getReason();
	}

	@Override
	public void unban() {
		Bukkit.getServer().getBanList(Type.NAME).pardon(player);
	}

	@Override
	public boolean isBannedBefore() {
		save();
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
		return Bukkit.getServer().getBanList(Type.NAME).getBanEntry(player).getExpiration().getTime();
	}

	@Override
	public UUID getUniqueId() {
		save();
		if(con.contains("uuid")) {
			String uid = f.getName().replace(".yml", "");
			return UUID.fromString((uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20) + "-" +uid.substring(20, 32)));
		}
		return null;
	}

	@Override
	public boolean hasHome() {
		save();
		if(con.contains("homes")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidHome(String home) {
		save();
		if(con.contains("homes."+home)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Home> getAllHomes() {
		save();
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
	public Home getHome(String homeName) throws NullPointerException {
		save();
		Home home = new Home(con, homeName);
		return home;
	}

	@Override
	public int getAmountOfHomes() {
		//returns a fixed version for permissions;)
		return (this.getAllHomes().size());
	}

	@Override
	public void removeHome(String home) {
		save();
		con.set("homes."+home.toLowerCase(), null);
		save();
	}

	@Override
	public Location getLocation() {
		save();
		if(con.contains("lastLocation")) {
			return new Location(Bukkit.getWorld(con.getString("lastLocation.world")), con.getDouble("lastLocation.x"), con.getDouble("lastLocation.y"), con.getDouble("lastLocation.z"), con.getInt("lastLocation.yaw"), con.getInt("lastLocation.pitch"));
		}
		return null;
	}

	@Override
	public boolean hasOfflineInventory() {
		save();
		if(con.contains("offlineInventory.contents")) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Inventory getInventory() {
		save();
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		if(hasOfflineInventory()) {
			ItemStack[] items = ((List<ItemStack>)con.get("offlineInventory.contents")).toArray(new ItemStack[0]);	
			inv.setContents(items);
		}
		return inv;
	}

	@Override
	public boolean hasModreqsOpen() {
		save();
		if(con.contains("modreqs")) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setModreqDoneMessage(String message) {
		con.set("modreq.done.message", message);
		save();
	}

	@Override
	public boolean isValidModreqId(int id) {
		save();
		if(con.contains("modreqs."+"modreq"+id)) {
			return true;
		}
		return false;
	}

	@Override
	public Modreq getModreq(int id) {
		save();
		Modreq mod = new Modreq(con, id);
		return mod;
	}

	@Override
	public void removeModreq(int id) {
		save();
		if(isValidModreqId(id)) {
			con.set("modreqs."+"modreq"+id, null);
			if(con.getConfigurationSection("modreqs").getKeys(true).isEmpty()) {
				con.set("modreqs", null);
			}
			save();
		} else {
			throw new NullPointerException("you cannot remove a configuration node for a modreq wich doesn't exists!");
		}
	}

	@Override
	public Modreq[] getModreqs() {
		save();
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
	public void ClearInventoryOnRelog() {
		save();
		con.set("ClearInventory", true);
		save();
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
		if(alts.getAltNames().length > 0) {
			return true;
		}
		return false;
	}

	@Override
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

	@Override
	public void setCommandRestriction(String command, String reason, String taskCommand) {
		if(taskCommand == null) {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason);
			con.set("command-restrictions", list);
			save();	
		} else {
			List<String> list = new ArrayList<String>(con.getStringList("command-restrictions"));
			list.add(command+","+reason+","+taskCommand);
			con.set("command-restrictions", list);
			save();
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
		save();
	}

	@Override
	public double getMoney() {
		save();   
		return con.getDouble("money");
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
	public boolean withdrawMoney(double price) {
		if((getMoney()-price) >= 0.0) {
			con.set("money", (getMoney()-price));
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
	public boolean payMoney(double money) {
		Double moneya = (getMoney()-money);
		if(moneya >= 0.0) {
			con.set("money", moneya);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasMoney() {
		save();
		if(con.contains("money")) {
			if(con.getDouble("money") >= 0.0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void depositMoney(double price) {
		save();
		con.set("money", getMoney()+price);
		save();
	}

	@Override
	public boolean hasEnoughMoney(double price) {
		save();
		if((getMoney()-price) >= 0.0) {
			return true;
		}
		return false;
	}

	@Override
	public void clearMoney() {
		save();
		con.set("money", null);
		save();
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
	public XPlayer getEssentialsPlayer() {
		return pl.getManagers().getPlayerManager().getPlayer(getName());
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
		save();
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

	@Override
	public void removeShop(Location loc) {
		save();
		UUID id = UUID.nameUUIDFromBytes(((loc.getWorld().getName())+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()).getBytes());
		con.set("shops."+id.toString(), null);
		save();
	}

	@Override
	public void PrepareLoginTask(String command, PlayerTaskEnum task) {
		con.set("task.command", command);
		con.set("task.type", task.name());
		save();
	}
	
	@Override
	public GameStatus getGameStatus(MinigameType type) {
		if(status != null && status.getType().equals(type)) {
			return status;
		} else {
			this.status = new GameStatus(type, f, con);
			return status;
		} 
	}

	@Override
	public boolean hasGameStatus(MinigameType type) {
		if(status != null && status.getType().equals(type)) {
			return (status.matchesPlayed() > 0 ? true : false);
		} else {
			this.status = new GameStatus(type, f, con);
			return (status.matchesPlayed() > 0 ? true : false);	
		} 
	}

	@Override
	public void save() {
		if(f.canWrite()) {
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				con.load(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}	
		} else {
			f.setReadable(true, false);
			f.setWritable(true, false);
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				con.load(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
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
			if(obj instanceof xEssentialsOfflinePlayer) {
				xEssentialsPlayer xp = (xEssentialsPlayer) obj;
				return xp.getName().equalsIgnoreCase(this.getName()) && xp.getUniqueId().equals(this.getUniqueId());
			}
		}
		return false;
	}

	@Override
	public void sendMessage(String name, String message) {
		List<String> a = con.getStringList("messagepool");
		a.add(message);
		con.set("messagepool", a.toArray());
		save();
	}
}

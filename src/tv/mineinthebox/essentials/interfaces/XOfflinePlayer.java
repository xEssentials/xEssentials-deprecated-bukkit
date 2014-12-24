package tv.mineinthebox.essentials.interfaces;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.instances.AlternateAccount;
import tv.mineinthebox.essentials.instances.Home;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.instances.RestrictedCommand;
import tv.mineinthebox.essentials.instances.Shop;

public interface XOfflinePlayer {

	public boolean isGreyListed();

	public void setGreyListed(Boolean bol);

	/**
	 * @author xize
	 * @param gets the player if online
	 * @return
	 */
	public Player getPlayer();

	/**
	 * 
	 * @author xize
	 * @param gets the ip adress of this player
	 * @return String
	 * 
	 */
	public String getIp();
	
	public void PrepareLoginTask(String command, PlayerTaskEnum task);

	/**
	 * 
	 * @author xize
	 * @param gets the username from the configuration of this player, this will also get updated whenever the name does not match with the uniqueID
	 * @return String 
	 *
	 */
	public String getUser();

	/**
	 * @author xize
	 * @param returns true when this player is muted
	 * @return boolean
	 */
	public boolean isMuted();

	/**
	 * @author xize
	 * @param removes the mute
	 * @return void
	 */
	public void unmute();

	/**
	 * @author xize
	 * @param mutes the player for chatting
	 * @param time - where the milliseconds are the modified date.
	 * @return void
	 */
	public void mute(Long time);

	/**
	 * @author xize
	 * @param get the modified time in milliseconds
	 * @return Long
	 */
	public Long getMutedTime();

	/**
	 * @author xize
	 * @param check whenever this player is perm banned
	 * @return boolean
	 */
	public boolean isPermBanned();

	/**
	 * @author xize
	 * @param sets the player permbanned
	 * @return void
	 */
	public void setPermBanned(String reason, String who);

	/**
	 * @author xize
	 * @param gets the ban message of this player
	 * @return String
	 */
	public String getBanMessage();

	/**
	 * 
	 * @author xize
	 * @param returns true if the player is temp banned
	 * @return boolean
	 * 
	 */
	public boolean isTempBanned();

	/**
	 * @author xize
	 * @param bans a player tempory (Long time, String reason, String who)
	 * @return boolean
	 */
	public void setTempbanned(Long time, String reason, String who);

	/**
	 * @author xize
	 * @param gets the tempory ban message
	 * @return String
	 */
	public String getTempBanMessage();

	/**
	 * @author xize
	 * @param unbans the player for both Tempbanned or PermBanned
	 * @return void
	 */
	public void unban();

	/**
	 * @author xize
	 * @param returns true whenever the player is banned before
	 * @return boolean
	 */
	public boolean isBannedBefore();

	/**
	 * @author xize
	 * @param gets the time remaining of the ban
	 * @return Long
	 */
	public Long getTempbanRemaining();

	/**
	 * @author xize
	 * @param get the Unique ID of this player
	 * @return Long
	 */
	public String getUniqueId();

	/**
	 * 
	 * @author xize
	 * @param checks whenever the player has set his home
	 * @return boolean
	 * 
	 */
	public boolean hasHome();

	/**
	 * @author xize
	 * @param returns true if the home name is valid within the String
	 * @return boolean
	 */
	public boolean isValidHome(String home);

	/**
	 * 
	 * @author xize
	 * @param gets all homes of this player!
	 * @return List<Home>
	 * 
	 */
	public List<Home> getAllHomes();

	/**
	 * @author xize
	 * @param homeName
	 * @return Home
	 * @throws NullPointerException
	 * 
	 */
	public Home getHome(String homeName);

	/**
	 * @author xize
	 * @param gets the fixed size of all the homes from this player
	 * @return int
	 */
	public int getAmountOfHomes();

	/**
	 * @author xize
	 * @param remove the home
	 * @param home
	 * @return void
	 */
	public void removeHome(String home);


	/**
	 * @author xize
	 * @param gets the last location of this player
	 * @return Location
	 */
	public Location getLastLocation();

	/**
	 * @author xize
	 * @param checks whenever the player has a offline inventory
	 * @return boolean
	 */
	public boolean hasOfflineInventory();
	
	public boolean payEssentialsMoney(double money);

	/**
	 * @author xize
	 * @param returns the offline inventory
	 * @return Inventory
	 */
	public Inventory getOfflineInventory(Player viewer);

	/**
	 * @author xize
	 * @param checks whenever this player has open modreqs
	 * @return boolean
	 */
	public boolean hasModreqsOpen();

	/**
	 * @author xize
	 * @param checks whenever a id is a valid ID
	 * @return boolean
	 */
	public boolean isValidModreqId(int id);

	/**
	 * @author xize
	 * @param gets the Modreq object containing all data
	 * @return Modreq
	 */
	public Modreq getModreq(int id);

	/**
	 * @author xize
	 * @param removes a modreq
	 * @return void
	 * @throws NullPointerException when the node doesn't exist
	 */
	public void removeModreq(int id);

	/**
	 * @author xize
	 * @param returns a array of modreqs for this player
	 * @return Modreq[]
	 */
	public Modreq[] getModreqs();


	/**
	 * @author xize
	 * @param set the last modreq done message for this player when used /done
	 * @return void
	 */
	public void setModreqDoneMessage(String message);

	/**
	 * @author xize
	 * @param clear the inventory on players relogs!
	 * @return void
	 */
	public void ClearInventoryOnRelog();

	/**
	 * @author xize
	 * @param get the alternate accounts of this player!
	 * @return AlternateAccount
	 */
	public AlternateAccount getAlternateAccounts();

	/**
	 * @author xize
	 * @param check whenever the player has alternate accounts!
	 * @return boolean
	 */
	public boolean hasAlternateAccounts();

	/**
	 * @author xize
	 * @param returns true if the player has command restrictions
	 * @return Boolean
	 */
	public boolean hasCommandRestrictions();

	/**
	 * @author xize
	 * @param command - the command+args which needs to be disabled
	 * @param reason - the reason why the command is blocked for the player
	 * @param taskCommand - an aditional task: command+args, if null we ignore this.
	 * @return void
	 */
	public void setCommandRestriction(String command, String reason, String taskCommand);

	/**
	 * @author xize
	 * @param returns a list of all command restrictions for this player
	 * @return CommandRestriction[]
	 */
	public List<RestrictedCommand> getCommandRestrictions();

	/**
	 * @author xize
	 * @param check if the player has a restriction inside the list, make sure to use hasCommandRestrictions() first.
	 * @return Boolean
	 */
	public boolean hasContainedRestriction(String command);

	/**
	 * @author xize
	 * @param remove the command restriction
	 * @return void
	 */
	public void removeCommandRestriction(RestrictedCommand cmd);

	/**
	 * @author xize
	 * @param returns the total amount of Essentials money of this player
	 * @return Double
	 */
	public Double getTotalEssentialsMoney();

	/**
	 * @author xize
	 * @param returns true if the player has money if its 0.0 or the config entry doesn't exist it is false
	 * @return Boolean
	 */
	public Boolean hasEssentialsMoney();

	/**
	 * @author xize
	 * @param this will add money to the players bank
	 */
	public void addEssentialsMoney(Double price);
	
	/**
	 * @author xize
	 * @param price - the price
	 * @return Boolean
	 */
	public boolean hasPlayerEnoughMoneyFromPrice(Double price);

	/**
	 * @author xize
	 * @param clear the money of the player
	 * @return void
	 */
	public void clearMoney();
	
	/**
	 * @author xize
	 * @param returns atleast 8 results of this players name history
	 * @return List<String>()
	 */
	public List<String> getNameHistory();
	
	/**
	 * @author xize
	 * @param returns true if the player has name history
	 * @return Boolean
	 */
	public boolean hasNameHistory();
	
	/**
	 * @author xize
	 * @param returns true whenever the player has a saved inventory
	 * @return Boolean
	 */
	public boolean hasSavedInventory();
	
	
	/**
	 * @author xize
	 * @param returns the essentials player
	 * @return xEssentialsPlayer
	 */
	public XPlayer getEssentialsPlayer();
	
	public boolean isShop(Location loc);
	
	public Shop getShop(Location loc);
	
	public void removeShop(Location loc);
	
	/**
	 * @author xize
	 * @param gets updated at every call so we know that the configuration stored in the memory is still recent with the flat saved file!
	 * @return void
	 */
	public void update();

}

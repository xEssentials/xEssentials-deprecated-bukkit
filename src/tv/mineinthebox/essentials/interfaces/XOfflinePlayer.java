package tv.mineinthebox.essentials.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tv.mineinthebox.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.essentials.instances.AlternateAccount;
import tv.mineinthebox.essentials.instances.Home;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.instances.RestrictedCommand;
import tv.mineinthebox.essentials.instances.Shop;
import tv.mineinthebox.essentials.minigames.plugin.arena.MinigameOfflinePlayer;

public interface XOfflinePlayer extends MinigameOfflinePlayer {

	/**
	 * returns true if the player has been greylisted otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGreyListed();

	/**
	 * sets the player greylisted when true, otherwise false
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setGreyListed(boolean bol);

	/**
	 * returns the Bukkit player instance
	 * 
	 * @author xize
	 * @return Player
	 */
	public Player getBukkitPlayer();

	/**
	 * returns the ip address of the player
	 * 
	 * @author xize
	 * @return String
	 */
	public String getIp();
	
	/**
	 * saves an task for next time this player logs on
	 * 
	 * @author xize
	 * @param command - the command
	 * @param task - the type task
	 */
	public void PrepareLoginTask(String command, PlayerTaskEnum task);
	
	/**
	 * returns true if the player is online, otherwise flase
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isOnline();

	/**
	 * returns the name of this player
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName();

	/**
	 * returns true if the player is muted, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isMuted();
	
	/**
	 * unmutes the player
	 * 
	 * @author xize
	 */
	public void unmute();

	/**
	 * mutes an player based on the time given in
	 * 
	 * @author xize
	 * @param time - the time how long the mute should be
	 */
	public void mute(Long time);

	/**
	 * returns the muted time (if applicable)
	 * 
	 * @author xize
	 * @return Long
	 */
	public Long getMutedTime();

	/**
	 * returns true if the player is perm banned, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPermBanned();

	/**
	 * permanently bans the player 
	 * 
	 * @author xize
	 * @param reason - the reason
	 * @param who - the player who issued the ban
	 */
	public void setPermBanned(String reason, String who);

	/**
	 * returns the ban reason
	 * 
	 * @author xize
	 * @return String
	 */
	public String getBanMessage();

	/**
	 * returns true if the player has been tempory banned
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isTempBanned();

	/**
	 * tempory bans an player based on given time, reason and the issuer
	 * 
	 * @author xize
	 * @param time - the time when it expires
	 * @param reason - the reason why the person is banned
	 * @param who - the issuer
	 */
	public void setTempbanned(Long time, String reason, String who);

	/**
	 * returns the tempory ban reason
	 * 
	 * @author xize
	 * @return String
	 */
	public String getTempBanMessage();

	/**
	 * unbans the player
	 * 
	 * @author xize
	 */
	public void unban();

	/**
	 * returns true if the player was banned before
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBannedBefore();

	/**
	 * returns the remaining tempory ban time of this player
	 * 
	 * @author xize
	 * @return Long
	 */
	public Long getTempbanRemaining();

	/**
	 * returns the unique id associated with this player in a friendly non server freezing manner
	 * 
	 * @author xize
	 * @return UUID
	 */
	public UUID getUniqueId();

	/**
	 * returns true if the player has an home, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasHome();

	/**
	 * returns true if the home is valid, otherwise false
	 * 
	 * @author xize
	 * @param home - the home name
	 * @return boolean
	 */
	public boolean isValidHome(String home);

	/**
	 * returns an List with homes, if the player has no homes set we return an empty List
	 * 
	 * @author xize
	 * @return List<Home>
	 */
	public List<Home> getAllHomes();

	/**
	 * returns the home based by name, null if the home is invalid
	 * 
	 * @author xize
	 * @param homeName - the home name
	 * @return Home
	 */
	public Home getHome(String homeName);
	
	/**
	 * returns the amount of homes this player has
	 * 
	 * @author xize
	 * @return int
	 */
	public int getAmountOfHomes();

	/**
	 * attempts to remove the home by name
	 * 
	 * @author xize
	 * @param home - the home name
	 */
	public void removeHome(String home);

	/**
	 * returns the last location seen of this player
	 * 
	 * @author xize
	 * @return Location
	 */
	public Location getLocation();
	
	/**
	 * returns true if the player has an saved inventory
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasOfflineInventory();

	/**
	 * attempts to open the inventory of the offline player
	 * 
	 * @author xize
	 * @return Inventory
	 */
	public Inventory getInventory();

	/**
	 * returns true if the player has any modreqs open
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasModreqsOpen();

	/**
	 * sets the modreq done message for next login
	 * 
	 * @author xize
	 * @param message - the message
	 */
	public void setModreqDoneMessage(String message);
	
	/**
	 * returns true if the modreq ticket id is valid, otherwise false
	 * 
	 * @author xize
	 * @param id - the id
	 * @return boolean
	 */
	public boolean isValidModreqId(int id);

	/**
	 * returns the Modreq ticket if any found
	 * 
	 * @author xize
	 * @param id - the ticket id
	 * @return Modreq
	 */
	public Modreq getModreq(int id);

	/**
	 * attempts to remove the modreq
	 * 
	 * @author xize
	 * @param id - the ticket id
	 */
	public void removeModreq(int id);

	/**
	 * returns all the modreqs of this player
	 * 
	 * @author xize
	 * @return Modreq[]
	 */
	public Modreq[] getModreqs();
	/**
	 * clears the inventory on reload
	 * 
	 * @author xize
	 */
	public void ClearInventoryOnRelog();

	/**
	 * attempts to return the players alternate accounts
	 * 
	 * @author xize
	 * @return AlternateAccount
	 */
	public AlternateAccount getAlternateAccounts();
	
	/**
	 * returns true if the player has alternate accounts, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasAlternateAccounts();

	/**
	 * returns true if the offline player has command restrictions, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasCommandRestrictions();

	/**
	 * sets a task command ready which gets fired on next login!
	 * 
	 * @author xize
	 * @param command - the command
	 * @param reason - the reason
	 * @param taskCommand - the task command
	 */
	public void setCommandRestriction(String command, String reason, String taskCommand);

	/**
	 * returns an List with restricted commands
	 * 
	 * @author xize
	 * @return List<RestrictedCommand>
	 */
	public List<RestrictedCommand> getCommandRestrictions();

	/**
	 * returns true if the restriction exists on the command, otherwise false
	 * 
	 * @author xize
	 * @param command - the possible command where the restriction is set on
	 * @return boolean
	 */
	public boolean hasContainedRestriction(String command);

	/**
	 * removes the restriction from the command
	 * 
	 * @author xize
	 * @param cmd - the command where the restriction should be removed from
	 */
	public void removeCommandRestriction(RestrictedCommand cmd);

	/**
	 * returns the balance of the player
	 * 
	 * @author xize
	 * @return double
	 */
	public double getMoney();
	
	/**
	 * returns true if the player has money, otherwise false
	 * 
	 * @author xize
	 * @return double
	 */
	public boolean hasMoney();
	
	/**
	 * pays a offline player, true if the transaction succeeded, otherwise false
	 * 
	 * @author xize
	 * @param price - the price
	 * @param toPayTo - to who
	 * @return boolean
	 */
	public boolean payMoney(double price, XOfflinePlayer toPayTo);
	
	/**
	 * pays a online player, true if the transaction succeeded, otherwise false
	 * 
	 * @author xize
	 * @param price - the price
	 * @param toPayTo - the player to pay to
	 * @return boolean
	 */
	public boolean payMoney(double price, XPlayer toPayTo);

	/**
	 * @author xize
	 * @param price - removes money (same as withdrawing money)
	 * @return boolean
	 */
	public boolean payMoney(double price);
	
	/**
	 * adds money to the player
	 * 
	 * @author xize
	 * @param price - the amount which gets added to the balance
	 */
	public void depositMoney(double price);

	/**
	 * removes money from the player
	 * 
	 * @author xize
	 * @param price - the price
	 * @return boolean
	 */
	public boolean withdrawMoney(double price);
	
	/**
	 * returns true if the player has enough money, otherwise false
	 * 
	 * @author xize
	 * @param price - the amount of money
	 * @return boolean
	 */
	public boolean hasEnoughMoney(double price);

	/**
	 * clears the players balance
	 * 
	 * @author xize
	 */
	public void clearMoney();
	
	/**
	 * returns the players name history
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getNameHistory();
	
	/**
	 * returns true if this player has an name history, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasNameHistory();
	
	/**
	 * returns true if the player has an saved inventory
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasSavedInventory();
	
	/**
	 * returns the online instance of this player
	 * 
	 * @author xize
	 * @return XPlayer
	 */
	public XPlayer getEssentialsPlayer();
	
	/**
	 * returns true if the location is an shop, otherwise false
	 * 
	 * @author xize
	 * @param loc - the location
	 * @return boolean
	 */
	public boolean isShop(Location loc);
	
	/**
	 * returns the shop by location
	 * 
	 * @author xize
	 * @param loc - the location
	 * @return Shop
	 */
	public Shop getShop(Location loc);
	
	/**
	 * removes the shop
	 * 
	 * @author xize
	 * @param loc
	 */
	public void removeShop(Location loc);
	
	/**
	 * saves an themed message to the message pool
	 * 
	 * @author xize
	 * @param prefix - the name
	 * @param message - the message
	 */
	public void sendMessage(String name, String message);
	
	/**
	 * saves the playerdata
	 * 
	 * @author xize
	 */
	public void save();

}

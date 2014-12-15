package tv.mineinthebox.bukkit.essentials.interfaces;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tv.mineinthebox.bukkit.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.bukkit.essentials.instances.AlternateAccount;
import tv.mineinthebox.bukkit.essentials.instances.Home;
import tv.mineinthebox.bukkit.essentials.instances.Modreq;
import tv.mineinthebox.bukkit.essentials.instances.RestrictedCommand;
import tv.mineinthebox.bukkit.essentials.instances.Shop;

public interface XOfflinePlayer {
	
	/**
	 * @author xize
	 * @param returns true whenever the speed is enabled
	 * @return boolean
	 */
	public boolean isSpeedEnabled();

	/**
	 * @author xize
	 * @param sets the speed of a player
	 * @return void
	 */
	public void setSpeed(int i);

	/**
	 * @author xize
	 * @param prepares a command task on players next join
	 * @return void
	 */
	public void PrepareLoginTask(String command, PlayerTaskEnum task);

	/**
	 * @author xize
	 * @param removes the walk speed
	 * @return void
	 */
	public void removeSpeed();

	public boolean isGreyListed();

	public void setGreyListed(Boolean bol);


	/**
	 * @author xize
	 * @param checks whenever the player is boomed
	 * @return Boolean
	 */
	public boolean isBoom();

	/**
	 * @author xize
	 * @param set the boom status of this player
	 * @return void
	 */
	public void setBoom();

	/**
	 * @author xize
	 * @param remove the boom status of this player
	 * @return void
	 */
	public void removeBoom();

	/**
	 * @author xize
	 * @param returns true whenever the player is cursed to be a potato!
	 * @return boolean
	 */
	public boolean isPotato();

	/**
	 * @author xize
	 * @param remove potato curse of this player!
	 * @return void
	 */
	public void removePotato();

	/**
	 * @author xize
	 * @param set a potato curse of this player!
	 * @return void
	 */
	public void setPotato();

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

	/**
	 * @author xize
	 * @param allows to unvanish the player when offline
	 * @return void
	 */
	public void unvanish();
	
	/**
	 * @author xize
	 * @param returns true whenever the player has vanish effects
	 * @return Boolean
	 */
	public boolean hasVanishEffects();
	
	/**
	 * @author xize
	 * @param sets the vanish effects of this player
	 * @param bol - the boolean
	 */
	public void setVanishEffects(Boolean bol);

	/**
	 * 
	 * @author xize
	 * @param set the ip of this player
	 * @return boolean
	 * 
	 */
	public boolean setIp(String ip);

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
	 * @param checks whenever fly is enabled for this player
	 * @return boolean
	 * 
	 */
	public boolean isFlying();
	/**
	 * 
	 * @author xize
	 * @param checks whenever torch is enabled for this player
	 * @return boolean
	 * 
	 */
	public boolean isTorch();

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
	 * @param is true when vanished otherwise false
	 * @return boolean
	 */
	public boolean isVanished();

	/**
	 * @author xize
	 * @param gets the last location of this player
	 * @return Location
	 */
	public Location getLocation();

	/**
	 * @author xize
	 * @param checks whenever the player has a offline inventory
	 * @return boolean
	 */
	public boolean hasOfflineInventory();

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
	 * @param returns true whenever the player has a modreq done message
	 * @return boolean
	 */
	public boolean hasModreqDoneMessage();

	/**
	 * @author xize
	 * @param returns the modreq done message
	 * @return String
	 */
	public String getModreqDoneMessage();

	/**
	 * @author xize
	 * @param remove the modreq done message
	 * @return void
	 */
	public void removeGetModregDoneMessage();
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
	 * @param set the silence state of the player
	 * @param Boolean
	 * @return void
	 */
	public void setSilenced(Boolean bol);

	/**
	 * @author xize
	 * @param returns true whenever the player has silenced the chat
	 * @return Boolean
	 */
	public boolean isSilenced();

	/**
	 * @author xize
	 * @param add a player to this players ignore list, this means that this player ignores the chat by the called name.
	 * @return void
	 */
	public void addIgnoredPlayer(String s);

	/**
	 * @author xize
	 * @param get a list of all player names which get ignored by this player
	 * @return List<String>()
	 */
	public List<String> getIgnoredPlayers();

	/**
	 * @author xize
	 * @param returns true whenever the player ignores a player
	 * @return Boolean
	 */
	public boolean hasIgnoredPlayers();

	/**
	 * @author xize
	 * @param remove a player from the ignore list
	 * @return void
	 */
	public void removeIgnoredPlayer(String s);

	/**
	 * @author xize
	 * @param returns true whenever the player is freezed
	 * @return Boolean
	 */
	public boolean isFreezed();

	/**
	 * @author xize
	 * @param freezes the player or unfreeze the player
	 * @param void
	 */
	public void setFreezed(Boolean bol);

	public boolean isTrollMode();

	public void setTrollMode(Boolean bol);

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
	 * @param set the kit cooldown
	 * @return void
	 */
	public void setKitCooldown(Long cooldown);

	/**
	 * @author xize
	 * @param remove the kit cooldown
	 * @return void
	 */
	public void removeKitCoolDown();

	/**
	 * @author xize
	 * @param return the saved cooldown of this player
	 * @return Long
	 */
	public Long getKitCooldown();

	/**
	 * @author xize
	 * @param returns true whenever the player has the cooldown else false
	 * @return Boolean
	 */
	public Boolean hasKitCooldown();

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
	 * @param this withdraws the players money
	 * @return Boolean
	 */
	public Boolean payEssentialsMoney(Double price);

	/**
	 * @author xize
	 * @param price - the amount specified
	 * @param toPayTo - the retriever of the money
	 * @return Boolean
	 */
	public Boolean payEssentialsMoney(Double price, XPlayer toPayTo);
	
	/**
	 * @author xize
	 * @param price - the price the player is gonna be to pay
	 * @param toPayTo - the retriever
	 * @return Boolean - if the player has no money it will be false.
	 */
	public Boolean payEssentialsMoney(Double price, XOfflinePlayer toPayTo);

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
	 * @param bol - when the boolean is true enable procs, else disable procs
	 */
	public void setProc(boolean bol);
	
	/**
	 * @author xize
	 * @param return true when the player has procs
	 * @return Boolean
	 */
	public boolean hasProc();
	
	/**
	 * @author xize
	 * @param bol - sets the anti knockback of this player.
	 */
	public void setKnock(boolean bol);
	
	/**
	 * @author xize
	 * @param returns the state of the anti knockback.
	 * @return Boolean
	 */
	public boolean isKnock();
	
	/**
	 * @author xize
	 * @param sets the player in a chair.
	 */
	public void setInChair(Boolean bol);
	
	/**
	 * @author xize
	 * @param returns true if the player sits in a chair
	 * @return Boolean
	 */
	public boolean isInChair();
	
	/**
	 * @author xize
	 * @param returns the essentials player
	 * @return xEssentialsPlayer
	 */
	public XPlayer getEssentialsPlayer();
	
	/**
	 * @author xize
	 * @param toggles double jump
	 * @param bol - true when enabled, false when disabled.
	 */
	public void setDoubleJump(Boolean bol);
	
	/**
	 * @author xize
	 * @param returns true if the player has double jump else false
	 * @return Boolean
	 */
	public boolean hasDoubleJump();
	
	/**
	 * @author xize
	 * @param returns true when edit mode is enabled
	 * @return Boolean
	 */
	public boolean isEditSignEnabled();
	
	/**
	 * @author xize
	 * @param bol - sets the mode whenever a player edits a sign
	 */
	public void setEditSign(Boolean bol);
	
	/**
	 * @author xize
	 * @param returns true when player is in drunk mode
	 * @return Boolean
	 */
	public boolean isDrunk();
	
	/**
	 * @author xize
	 * @param bol - sets the drunk state
	 */
	public void setDrunk(Boolean bol);
	
	/**
	 * @author xize
	 * @param returns true if floor mode is true
	 * @return boolean
	 */
	public boolean isFloorMode();
	
	/**
	 * @author xize
	 * @param bol - when true build mode is activated, else its not.
	 * @param range - the range of the floor mode
	 */
	public void setFloorMode(boolean bol, int range);
	
	/**
	 * @author xize
	 * @param returns the range of the floor
	 * @return int
	 */
	public int getFloorModeRange();
	
	/**
	 * @author xize
	 * @param returns true if the wall mode is activated
	 * @return
	 */
	public boolean isWallMode();
	
	/**
	 * @author xize
	 * @param bol - activates the wall mode
	 * @param range - the range how heigh the wall should be.
	 */
	public void setWallMode(boolean bol, int range);
	
	public void setShop(Location loc, Chest chest);
	
	public boolean isShop(Location loc);
	
	public Shop getShop(Location loc);
	
	public void removeShop(Location loc);
	
	/**
	 * @author xize
	 * @param returns the wall size
	 * @return int
	 */
	public int getWallModeRange();
	
	/**
	 * @author xize
	 * @param gets updated at every call so we know that the configuration stored in the memory is still recent with the flat saved file!
	 * @return void
	 */
	public void update();

}

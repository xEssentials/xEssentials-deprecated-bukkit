package tv.mineinthebox.essentials.interfaces;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.instances.AlternateAccount;
import tv.mineinthebox.essentials.instances.Home;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.instances.RestrictedCommand;
import tv.mineinthebox.essentials.instances.Shop;

public interface XPlayer extends XOfflinePlayer {
	/**
	 * @author xize
	 * @param returns true whenever the player is greylisted
	 * @return boolean
	 */
	public boolean isGreyListed();

	/**
	 * @author xize
	 * @param set the player as greylisted
	 * @return void
	 */
	public void setGreyListed(boolean bol);

	/**
	 * @author xize
	 * @param set the powertool of a player
	 * @return void
	 */
	public void setPowerTool(ItemStack item, String command);

	/**
	 * @author xize
	 * @param when true the player has a powertool otherwise false
	 * @return boolean
	 */
	public boolean hasPowerTool();

	/**
	 * @author xize
	 * @param get the command from the power tool
	 * @return String
	 */
	public String getPowerTool();

	/**
	 * @author xize
	 * @param removes the powertool
	 * @return void
	 */
	public void removePowerTool();

	/**
	 * @author xize
	 * @param returns true if a player has a login task
	 * @return boolean
	 */
	public boolean hasLoginTask();

	/**
	 * @author xize
	 * @param nukes the player with a massive explosion rain
	 * @return void
	 */
	public void nuke();

	/**
	 * @author xize
	 * @param nukes the player with a massive explosion rain but as fake
	 * @return void
	 */
	public void fakenuke();

	abstract boolean isFallingBreakAble(Block block);

	/**
	 * @author xize
	 * @param peform a login task
	 * @return void
	 */
	public void performLoginTask();

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
	 * @param removes the walk speed
	 * @return void
	 */
	public void removeSpeed();

	/**
	 * @author xize
	 * @param returns whenever the player is staff and has permission: xEssentials.isStaff
	 * @return boolean
	 */
	public boolean isStaff();

	/**
	 * @author xize
	 * @param checks whenever the player is boomed
	 * @return boolean
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
	public void setPotato(Item potato);

	/**
	 * @author xize
	 * @param gets the Potato Item
	 * @return Item
	 * @throws NullPointerException
	 */
	public Item getPotato();

	/**
	 * 
	 * @author xize
	 * @param gets the ip adress of this player
	 * @return String
	 * 
	 */
	public String getIp();

	/**
	 * 
	 * @author xize
	 * @param set the ip of this player
	 * @return boolean
	 * 
	 */
	
	public boolean setIp();

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
	 * @param returns true whenever the player is fishing
	 * @return boolean
	 */
	public boolean isFishing();

	/**
	 * @author xize
	 * @param returns true when this player is muted
	 * @return boolean
	 */
	public boolean isMuted();

	/**
	 * @author xize
	 * @param
	 * @return boolean
	 */
	public boolean isAfk();
	/**
	 * @author xize
	 * @param returns the afk reason
	 * @return String
	 */
	public String getAfkReason();

	/**
	 * @author xize
	 * @param remove the player from afk
	 * @return void
	 */
	public void removeAfk();

	/**
	 * @author xize
	 * @param set the player to afk
	 * @return void
	 */
	public void setAfk(String message);

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
	 * 
	 * @author xize
	 * @param get the Unique ID of this player
	 * @return String
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
	 * @author xize
	 * @param set the fly mode of the player
	 * @return void
	 */
	public void setFlying(boolean bol);

	/**
	 * 
	 * @author xize
	 * @param checks whenever torch is enabled for this player
	 * @return boolean
	 * 
	 */
	public boolean isTorch();

	/**
	 * @author xize
	 * @param set the torch mode to false or on
	 * @return void
	 */
	public void setTorch(boolean bol);

	/**
	 * @author xize
	 * @param returns true whenever firefly is enabled otherwise false
	 * @return boolean
	 */
	public boolean isFirefly();

	/**
	 * @author xize
	 * @param set firefly for this player
	 * @return void
	 */
	public void setFirefly(boolean bol);

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
	 * @param allows the player to set his home!, the key world default is the default home!
	 * @return void
	 */
	public void setHome(String home);

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
	 * @param vanish the current player for all others
	 * @return void
	 */
	public void vanish();

	/**
	 * @author xize
	 * @param unvanish the player for all others
	 * @return void
	 */
	public void unvanish();

	/**
	 * @author xize
	 * @param unvanish the player for all others
	 * @return void
	 */
	public void unvanish(boolean sillenced);

	/**
	 * @author xize
	 * @param returns true whenever the player has vanish effects
	 * @return boolean
	 */
	public boolean hasVanishEffects();

	/**
	 * @author xize
	 * @param sets the vanish effects of this player
	 * @param bol - the boolean
	 */
	public void setVanishEffects(boolean bol);

	/**
	 * @author xize
	 * @param checks whenever the player has no pick up enabled this is for item drops and interaction for vanish
	 * @return boolean
	 */
	public boolean isNoPickUpEnabled();

	/**
	 * @author xize
	 * @param sets the pickup state of this player only affects while vanished
	 * @return void
	 */
	public void setNoPickUp(boolean bol);

	/**
	 * @author xize
	 * @param unsets the pickup state of this player only affects while vanished
	 * @return void
	 */
	public void unsetNoPickUp();

	/**
	 * @author xize
	 * @param set the creative inventory in the config
	 * @return void
	 */
	public void saveCreativeInventory();

	/**
	 * @author xize
	 * @param set the survival inventory in the config
	 * @return void
	 */
	public void saveSurvivalInventory();

	/**
	 * @author xize
	 * @param checks whenever the player has a saved instance of the survival inventory
	 * @return boolean
	 */
	public boolean hasSurvivalInventory();

	/**
	 * @author xize
	 * @param checks whenever the player has a saved instance of the creative inventory
	 * @return boolean
	 */
	public boolean hasCreativeInventory();

	/**
	 * @author xize
	 * @param returns the survival inventory
	 * @return ItemStack[]
	 */
	public ItemStack[] getSurvivalInventory();

	/**
	 * @author xize
	 * @param returns the survival armor
	 * @return ItemStack[]
	 */
	public ItemStack[] getSurvivalArmorInventory();

	/**
	 * @author xize
	 * @param returns the creative inventory
	 * @return ItemStack[]
	 */
	public ItemStack[] getCreativeInventory();

	/**
	 * @author xize
	 * @param returns the creative armor
	 * @return ItemStack[]
	 */
	public ItemStack[] getCreativeArmorInventory();

	/**
	 * @author xize
	 * @param save the inventory for offline view
	 * @return void
	 */
	public void saveOfflineInventory();

	/**
	 * @author xize
	 * @param returns the online inventory
	 * @return Inventory
	 */
	public Inventory getOnlineInventory();

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
	 * @param create a new modreq with a new ID
	 * @return void
	 */
	public void createModreq(String message);

	/**
	 * @author xize
	 * @param returns the player
	 * @return Player
	 */
	public Player getPlayer();

	/**
	 * @author xize
	 * @param returns the boolean whenever the inventory needs to be cleared on relog
	 * @return boolean
	 */
	public boolean isInventoryClearanceOnRelog();
	
	/**
	 * @author xize
	 * @param clears the inventory on relog
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

	public boolean hasCompass();

	public void setCompass(Player p);

	public void setCompass(String p);

	public void removeCompass();

	public XOfflinePlayer getCompass();

	/**
	 * @author xize
	 * @param set the silence state of the player
	 * @param boolean
	 * @return void
	 */
	public void setSilenced(boolean bol);

	/**
	 * @author xize
	 * @param returns true whenever the player has silenced the chat
	 * @return boolean
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
	 * @return boolean
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
	 * @return boolean
	 */
	public boolean isFreezed();

	/**
	 * @author xize
	 * @param freezes the player or unfreeze the player
	 * @param void
	 */
	public void setFreezed(boolean bol);

	/**
	 * @author xize
	 * @param returns true whenever a player has trollmode enabled!
	 * @return boolean
	 */
	public boolean isTrollMode();

	/**
	 * @author xize
	 * @param enabled trollmode or disable trollmode
	 * @return void
	 */
	public void setTrollMode(boolean bol);

	/**
	 * @author xize
	 * @param returns true if the player has command restrictions
	 * @return boolean
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
	 * @return List<CommandRestriction>();
	 */
	public List<RestrictedCommand> getCommandRestrictions();

	/**
	 * @author xize
	 * @param check if the player has a restriction inside the list, make sure to use hasCommandRestrictions() first.
	 * @return boolean
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
	 * @return boolean
	 */
	public boolean hasKitCooldown();

	/**
	 * @author xize
	 * @param set the old name in the history
	 * @param oldName - old name
	 */
	public void setNameHistory(String oldName);

	/**
	 * @author xize
	 * @param returns atleast 8 results of this players name history
	 * @return List<String>()
	 */
	public List<String> getNameHistory();

	/**
	 * @author xize
	 * @param returns true if the player has name history
	 * @return boolean
	 */
	public boolean hasNameHistory();

	/**
	 * @author xize
	 * @param returns true whenever the player has a saved inventory
	 * @return boolean
	 */
	public boolean hasSavedInventory();

	/**
	 * @author xize
	 * @param saves the inventory of the player, unlike the saveSurvivalInventory, this will be used for minigames.
	 */
	public void saveInventory();

	/**
	 * @author xize
	 * @param loads the inventory of the player, unlike the saveSurvivalInventory, this will be used for minigames.
	 */
	public void loadInventory();

	/**
	 * @author xize
	 * @param bol - when the boolean is true enable procs, else disable procs
	 */
	public void setProc(boolean bol);
	
	public void setCustomName(String name);
	
	public boolean hasCustomName();
	
	public String getCustomName();
	
	public void saveLastLocation();

	/**
	 * @author xize
	 * @param return true when the player has procs
	 * @return boolean
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
	 * @return boolean
	 */
	public boolean isKnock();
	
	/**
	 * @author xize
	 * @param sets the player in a chair.
	 */
	public void setInChair(boolean bol);
	
	/**
	 * @author xize
	 * @param returns true if the player sits in a chair
	 * @return boolean
	 */
	public boolean isInChair();
	
	/**
	 * @author xize
	 * @param toggles double jump
	 * @param bol - true when enabled, false when disabled.
	 */
	public void setDoubleJump(boolean bol);
	
	/**
	 * @author xize
	 * @param returns true if the player has double jump else false
	 * @return boolean
	 */
	public boolean hasDoubleJump();
	
	/**
	 * @author xize
	 * @param returns true if the player has spectate on
	 * @return boolean
	 */
	public boolean isSpectate();
	
	/**
	 * @author xize
	 * @param stops the spectate
	 */
	public void stopSpectate();
	
	/**
	 * @author xize
	 * @param spectates a player.
	 * @param p
	 */
	public void spectate(final Player pa);
	
	/**
	 * @author xize
	 * @param returns true when edit mode is enabled
	 * @return boolean
	 */
	public boolean isEditSignEnabled();
	
	/**
	 * @author xize
	 * @param bol - sets the mode whenever a player edits a sign
	 */
	public void setEditSign(boolean bol);
	
	/**
	 * @author xize
	 * @param returns true when player is in drunk mode
	 * @return boolean
	 */
	public boolean isDrunk();
	
	/**
	 * @author xize
	 * <p>sets the chat mode of the player</p>
	 * @param bol - if true the player has changed into drunk chat mode, otherwise false
	 */
	public void setDrunk(boolean bol);
	
	/**
	 * @author xize
	 * <p>returns a converted message which looks drunky</p>
	 * @see XPlayer#isDrunk()
	 * @param message - the message to be converted to
	 * @return String
	 */
	public String getDrunkMessageFrom(String message, boolean bol);

	/**
	 * @author xize
	 * <p>returns true if floor mode is activated otherwise false</p>
	 * @return boolean
	 */
	public boolean isFloorMode();
	
	/**
	 * @author xize
	 * <p>enables or disables floor mode, this allows a player to build floors</p>
	 * @param bol - when true build mode is activated, otherwise deactivated
	 * @param range - the range of the floor mode
	 */
	public void setFloorMode(boolean bol, int range);
	
	/**
	 * @author xize
	 * <p>returns the size of the floor</p>
	 * @return int
	 */
	public int getFloorModeRange();
	
	/**
	 * @author xize
	 * <p>returns true if wallmode is activated otherwise false</p>
	 * @return boolean
	 */
	public boolean isWallMode();
	
	/**
	 * @author xize
	 * <p>enables or disables wall mode</p>
	 * @param bol - if true it enables wallmode, otherwise false
	 * @param range - the range how heigh the wall should be.
	 */
	public void setWallMode(boolean bol, int range);
	
	/**
	 * @author xize
	 * <p>sets the range of wall mode</p>
	 * @return int
	 */
	public int getWallModeRange();
	
	/**
	 * @author xize
	 * <p>sets a shop on the current location with the chest, note the chest has to be somehow attached to the chest (north, east, south, west)</p>
	 * @param loc - the location
	 * @param chest - the chest
	 */
	public void setShop(Location loc, Chest chest);
	
	/**
	 * @author xize
	 * <p>returns true if the location is a shop, otherwise false</p>
	 * @param loc - the location
	 */
	public boolean isShop(Location loc);
	
	/**
	 * @author xize
	 * <p>gets the shop by an location</p>
	 * @see XPlayer#isShop(Location)
	 * @param loc - the location
	 */
	public Shop getShop(Location loc);
	
	/**
	 * @author xize
	 * <p>removes the shop from a location</p>
	 * @see XPlayer#isShop(Location)
	 * @param loc - the location
	 */
	public void removeShop(Location loc);
	
	/**
	 * @author xize
	 * <p>sets the warning level of the anti-pwnage system</p>
	 * @param level - the level of the relogs
	 */
	public void setPwnageLevel(int level);
	
	/**
	 * @author xize
	 * <p>returns true if the player has a anti-pwnage level otherwise false<p>
	 * @return boolean
	 */
	public boolean hasPwnageLevel();
	
	/**
	 * @author xize
	 * <p>returns the current anti-pwnage warning level</p>
	 * @see XPlayer#hasPwnageLevel()
	 * @return int
	 */
	public int getPwnageLevel();
	
	/**
	 * @author xize
	 * <p>returns the time when the player was last logged in</p>
	 * @see XPlayer#hasLastLoginTime()
	 * @return long
	 */
	public long getLastLoginTime();
	
	/**
	 * @author xize
	 * <p>sets the last login time</p>
	 * @param time - login time
	 */
	public void setLastLoginTime(long time);
	
	/**
	 * @author xize
	 * <p>returns true if the player has a last login time if not false</p>
	 * @return boolean
	 */
	public boolean hasLastLoginTime();
	
	/**
	 * @author xize
	 * <p>sets the anti-pwnage task and checks if the player logs in in a certain time</p>
	 * @param task - the anti-pwnage task
	 */
	public void setPwnageScheduler(BukkitTask task);
}

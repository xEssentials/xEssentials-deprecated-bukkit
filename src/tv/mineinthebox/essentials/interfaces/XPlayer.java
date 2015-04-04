package tv.mineinthebox.essentials.interfaces;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public interface XPlayer extends MinigamePlayer, XOfflinePlayer {
	
	/**
	 * binds an command to an ItemStack
	 * 
	 * @author xize
	 * @param item - the item in hand
	 * @param command - the command
	 */
	public void setPowerTool(ItemStack item, String command);

	/**
	 * returns true if the player has set the powertool on the current item in hand
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasPowerTool();

	/**
	 * returns the command which is binded on the item in hand
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPowerTool();

	/**
	 * removes the powertool currently attached on hand
	 * 
	 * @author xize
	 */
	public void removePowerTool();

	/**
	 * launches a nuke cloud of tnt on the player
	 * 
	 * @author xize
	 */
	public void nuke();

	/**
	 * launches a fake nuke cloud of tnt on the player
	 * 
	 * @author xize
	 */
	public void fakenuke();

	/**
	 * returns true if the player has an login task, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasLoginTask();
	
	/**
	 * performs the login task
	 * 
	 * @author xize
	 */
	public void performLoginTask();

	/**
	 * returns true if custom speed has been enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSpeedEnabled();

	/**
	 * sets the walk and fly speed
	 * 
	 * @author xize
	 * @param i - the speed
	 */
	public void setSpeed(int i);

	/**
	 * removes the speed
	 * 
	 * @author xize
	 */
	public void removeSpeed();

	/**
	 * returns true if the player is an staff member
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isStaff();

	/**
	 * returns true if the player is inside an boom event
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBoom();

	/**
	 * launchers the player in the air without giving the player damage!
	 * 
	 * @author xize
	 */
	public void setBoom();

	/**
	 * removes the boom ability from the player so the player can get damage again
	 * 
	 * @author xize
	 */
	public void removeBoom();

	/**
	 * returns true if the player is potato cursed, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPotato();

	/**
	 * removes the potato curse
	 * 
	 * @author xize
	 */
	public void removePotato();

	/**
	 * potato curse the player
	 * 
	 * @author xize
	 * @param potato - the potato
	 */
	public void setPotato(Item potato);

	/**
	 * returns the potato item
	 * 
	 * @author xize
	 * @return Item
	 */
	public Item getPotato();
	
	/**
	 * sets the ip of the player
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean setIp();

	/**
	 * returns true if the player is fishing, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFishing();

	/**
	 * returns true if the player afk, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAfk();

	/**
	 * returns the afk reason
	 * 
	 * @author xize
	 * @return String
	 */
	public String getAfkReason();

	/**
	 * removes afk status from the player
	 * 
	 * @author xize
	 */
	public void removeAfk();

	/**
	 * sets the player to afk
	 * 
	 * @author xize
	 * @param message - the message
	 */
	public void setAfk(String message);

	/**
	 * returns true if the player is in fly mode
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFlying();

	/**
	 * enables or disables fly mode
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setFlying(boolean bol);

	/**
	 * returns true if the player is in torch mode
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isTorch();

	/**
	 * sets the torch mode of this player
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setTorch(boolean bol);

	/**
	 * returns true if the player is in firefly mode, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFirefly();

	/**
	 * sets the firefly mode of this player
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setFirefly(boolean bol);

	/**
	 * sets the home of the player
	 * 
	 * @author xize
	 * @param home - the home name
	 */
	public void setHome(String home);

	/**
	 * returns true if the player is vanished
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isVanished();

	/**
	 * vanishes the player
	 * 
	 * @author xize
	 */
	public void vanish();

	/**
	 * unvanishes the player
	 * 
	 * @author xize
	 */
	public void unvanish();

	/**
	 * unvanishes the player either silenced or unsilenced
	 * 
	 * @author xize
	 * @param silenced - whenever the unvanish state should be silenced or un-silenced
	 */
	public void unvanish(boolean silenced);

	/**
	 * returns true the player has vanish effects on, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasVanishEffects();

	/**
	 * sets the effect of vanish
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setVanishEffects(boolean bol);

	/**
	 * returns true if no-pickup for vanished is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isNoPickUpEnabled();

	/**
	 * enables or disabled no-pickup for vanish
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setNoPickUp(boolean bol);
	
	/**
	 * saves the surival inventory
	 * 
	 * @author xize
	 */
	public void saveSurvivalInventory();
	
	/**
	 * saves the creative inventory
	 * 
	 * @author xize
	 */
	public void saveCreativeInventory();

	/**
	 * returns true if the player has an saved survival inventory
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasSurvivalInventory();

	/**
	 * returns true if the player has an saved creative inventory
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasCreativeInventory();

	/**
	 * returns the survival inventory
	 * 
	 * @author xize
	 * @return ItemStack[]
	 */
	public ItemStack[] getSurvivalInventory();

	/**
	 * returns the survival inventory's armor contents
	 * 
	 * @author xize
	 * @return ItemStack[]
	 */
	public ItemStack[] getSurvivalArmorInventory();

	/**
	 * returns the creative inventory
	 * 
	 * @author xize
	 * @return ItemStack[]
	 */
	public ItemStack[] getCreativeInventory();

	/**
	 * returns the creative's armor contents
	 * 
	 * @author xize
	 * @return ItemStack[]
	 */
	public ItemStack[] getCreativeArmorInventory();

	/**
	 * saves the current inventory for offline usage
	 * 
	 * @author xize
	 */
	public void saveOfflineInventory();

	/**
	 * returns the online Inventory of this player
	 * 
	 * @author xize
	 * @return Inventory
	 */
	@Override
	public Inventory getInventory();

	/**
	 * returns true if the player has a modreq done message, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasModreqDoneMessage();

	/**
	 * returns the modreq done message
	 * 
	 * @author xize
	 * @return String
	 */
	public String getModreqDoneMessage();

	/**
	 * removes the modreq done message
	 * 
	 * @author xize
	 */
	public void removeGetModregDoneMessage();

	/**
	 * create a modreq on this player
	 * 
	 * @author xize
	 * @param message - the modreq message
	 */
	public void createModreq(String message);

	/**
	 * returns true if the inventory should be cleared on relog
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isInventoryClearanceOnRelog();

	/**
	 * returns true if the player has an compass target set
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasCompass();
	
	/**
	 * returns the player of this compass
	 * 
	 * @author xize
	 * @return XOfflinePlayer
	 */
	public XOfflinePlayer getCompass();

	/**
	 * sets the compass to an player
	 * 
	 * @author xize
	 * @param p - the player
	 */
	public void setCompass(Player p);

	/**
	 * sets the compass to an player
	 * 
	 * @author xize
	 * @param p - the players name
	 */
	public void setCompass(String p);

	/**
	 * removes the compass
	 * 
	 * @author xize
	 */
	public void removeCompass();

	/**
	 * true to silence the players chat, false to enable chat
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setSilenced(boolean bol);

	/**
	 * returns true if the player has silenced his chat, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSilenced();

	/**
	 * adds the player to the ignore list
	 * 
	 * @author xize
	 * @param s - the player
	 */
	public void addIgnoredPlayer(String s);

	/**
	 * returns a list of ignored players by this player
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getIgnoredPlayers();

	/**
	 * returns true if this player has ignored players
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasIgnoredPlayers();

	/**
	 * removes a ignored player
	 * 
	 * @author xize
	 * @param s - the player
	 */
	public void removeIgnoredPlayer(String s);

	/**
	 * returns true if the players movements are frozen, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFreezed();

	/**
	 * sets the freeze mode of this player
	 * 
	 * @author xize
	 * @param bol - true to freeze, false to unfreeze
	 */
	public void setFreezed(boolean bol);

	/**
	 * returns true when this player has trollmode activated
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isTrollMode();

	/**
	 * sets the trollmode on this player
	 * 
	 * @author xize
	 * @param bol - true to enable trollmode, false to disable
	 */
	public void setTrollMode(boolean bol);

	/**
	 * sets the kit's cooldown time
	 * 
	 * @author xize
	 * @param cooldown - the cooldown time
	 */
	public void setKitCooldown(Long cooldown);

	/**
	 * removes the kit cooldown on the player
	 * 
	 * @author xize
	 */
	public void removeKitCoolDown();

	/**
	 * returns the kit cooldown
	 * 
	 * @author xize
	 * @return Long
	 */
	public Long getKitCooldown();

	/**
	 * returns true if the player has an kit cooldown
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasKitCooldown();

	/**
	 * sets the oldname inside the history
	 * 
	 * @author xize
	 * @param oldName - the oldname
	 */
	public void setNameHistory(String oldName);

	/**
	 * sets the proc mode of this player
	 * 
	 * @author xize
	 * @param bol - true to enable proc, false to disable proc
	 */
	public void setProc(boolean bol);
	
	/**
	 * returns true if the player has proc set
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasProc();

	/**
	 * sets the nickname of this player
	 * 
	 * @author xize
	 * @param name - the new custom name
	 */
	public void setCustomName(String name);
	
	/**
	 * returns true if the player has an custom nickname
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasCustomName();
	
	/**
	 * returns the nickname/custom name of this player
	 * 
	 * @author xize
	 * @return String
	 */
	public String getCustomName();
	
	/**
	 * saves the last location of the player
	 * 
	 * @author xize
	 */
	public void saveLastLocation();
	
	/**
	 * returns the location of online player
	 * 
	 * @author xize
	 */
	public Location getLocation();
	
	/**
	 * returns true if the player has no knock back enabled
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isKnock();
	
	/**
	 * sets no knock back mode to this player
	 * 
	 * @author xize
	 * @param bol - true to enable knock, false to disable
	 */
	public void setKnock(boolean bol);

	/**
	 * allows a player to sit on a chair
	 * 
	 * @author xize
	 * @param bol - true when a player sits, false to not let him sit
	 */
	public void setInChair(boolean bol);
	
	/**
	 * returns true if the player sit inside a chair, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isInChair();
	
	/**
	 * sets the state of doublejump
	 * 
	 * @author xize
	 * @param bol - true to enable double jump false to disable
	 */
	public void setDoubleJump(boolean bol);
	
	/**
	 * returns true if the player has double jump enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasDoubleJump();
	
	/**
	 * returns true if spectate is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSpectate();
	
	/**
	 * stops spectator mode of this player
	 * 
	 * @author xize
	 */
	public void stopSpectate();
	
	/**
	 * enabled spectate mode on this player
	 * 
	 * @author xize
	 * @param pa - the player who gets spected by this player
	 */
	public void spectate(final Player pa);
	
	/**
	 * returns true if sign edit is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEditSignEnabled();
	
	/**
	 * sets the signedit state
	 * 
	 * @author xize
	 * @param bol - true to enabled sign edit mode, false to disable
	 */
	public void setEditSign(boolean bol);
	
	/**
	 * returns true if the player has drunk chat enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isDrunk();
	
	/**
	 * sets the drunk chat
	 * 
	 * @author xize
	 * @param bol - true to enable drunk chat, false to disable
	 */
	public void setDrunk(boolean bol);
	
	/**
	 * returns a converted drunk message in return
	 * 
	 * @author xize
	 * @param message - the message
	 * @param bol - toggle key space
	 * @return String
	 */
	public String getDrunkMessageFrom(String message, boolean bol);

	/**
	 * returns true if floor mode is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFloorMode();
	
	/**
	 * enables or disables floor mode on this player
	 * 
	 * @author xize
	 * @param bol - true to enable floormode, false to disable
	 * @param range - the range
	 */
	public void setFloorMode(boolean bol, int range);
	
	/**
	 * returns the floor mode range
	 * 
	 * @author xize
	 * @return int
	 */
	public int getFloorModeRange();
	
	/**
	 * returns true if player is in wallmode, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWallMode();
	
	/**
	 * enables or disables the wall mode
	 * 
	 * @author xize
	 * @param bol - true to enable wall mode, false to disable wallmode
	 * @param range - the range
	 */
	public void setWallMode(boolean bol, int range);

	/**
	 * returns the wall mode range
	 * 
	 * @author xize
	 * @return int
	 */
	public int getWallModeRange();
	
	/**
	 * sets the shop based on location
	 * 
	 * @author xize
	 * @param loc - the location
	 * @param chest - the chest
	 */
	public void setShop(Location loc, Chest chest);
	
	/**
	 * sets the warning level of pwnage spam
	 * 
	 * @author xize
	 * @param level - the warning level
	 */
	public void setPwnageLevel(int level);

	/**
	 * returns true if this player has pwnage warnings
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasPwnageLevel();
	
	/**
	 * returns anti pwnage warning level
	 * 
	 * @author xize
	 * @return int
	 */
	public int getPwnageLevel();
	
	/**
	 * sets the anti-pwnage scheduler
	 * 
	 * @author xize
	 * @param task - the task
	 */
	public void setPwnageScheduler(BukkitTask task);
	
	/**
	 * returns last login time of this player
	 * 
	 * @author xize
	 * @return Long
	 */
	public long getLastLoginTime();
	
	/**
	 * sets the last login time
	 * 
	 * @author xize
	 * @param time - the time
	 */
	public void setLastLoginTime(long time);

	/**
	 * returns true if the player has last login data, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasLastLoginTime();
	
	/**
	 * sends an themed message to the player
	 * 
	 * @author xize
	 * @param prefix - the prefix
	 * @param message - the message
	 */
	@Override
	public void sendMessage(String prefix, String message);
	
}

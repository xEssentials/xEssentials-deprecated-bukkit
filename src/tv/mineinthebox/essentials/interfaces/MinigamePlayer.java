package tv.mineinthebox.essentials.interfaces;

import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.minigames.plugin.arena.MinigameArena;
import tv.mineinthebox.essentials.minigames.plugin.session.MinigameSession;

public interface MinigamePlayer {
	
	/**
	 * returns true if the player has an open game arena session, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasSession();
	
	/**
	 * returns the minigame session
	 * 
	 * @author xize
	 * @return MinigameSession
	 */
	public MinigameSession getSession();
	
	/**
	 * sets the session
	 * 
	 * @author xize
	 * @param sess - the session
	 */
	public void setSession(MinigameSession sess);
	
	/**
	 * returns true if the player is inside a minigame arena, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isInArena();
	
	/**
	 * returns the minigame arena
	 * 
	 * @author xize
	 * @return MinigameArena
	 */
	public MinigameArena getArena();
	
	/**
	 * sets the player inside a arena
	 * 
	 * @author xize
	 * @param arena - the arena
	 */
	public void setArena(MinigameArena arena);
	
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
	 * returns the player instance
	 * 
	 * @author xize
	 * @return Player
	 */
	public Player getBukkitPlayer();
	
}

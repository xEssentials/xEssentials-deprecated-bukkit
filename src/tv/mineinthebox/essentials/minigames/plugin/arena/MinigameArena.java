package tv.mineinthebox.essentials.minigames.plugin.arena;

import java.util.Set;

import org.bukkit.Sound;

import tv.mineinthebox.essentials.interfaces.XPlayer;

public interface MinigameArena {

	/**
	 * returns the name
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName();
	
	/**
	 * returns the type of minigame
	 * 
	 * @author xize
	 * @return MinigameType
	 */
	public MinigameType getType();
	
	/**
	 * returns true if the arena has max players, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasMaxPlayers();
	
	/**
	 * returns the max amount of players allowed!
	 * 
	 * @author xize
	 * @return int
	 */
	public int getMaxPlayers();
	
	/**
	 * returns a status String for e.g buttons in inventory menus
	 * 
	 * @author xize
	 * @return String
	 */
	public String getArenaStatus();
	
	/**
	 * returns true if the game is started otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isStarted();
	
	/**
	 * sets the game to started or stopped
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setStarted(boolean bol);
	
	/**
	 * returns true if the arena some sort of score system, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasScore();
	
	/**
	 * returns the max score!
	 * 
	 * @author xize
	 * @return int
	 */
	public int getMaxScore();

	/**
	 * lets a player join the arena
	 * 
	 * @author xize
	 * @param xp - the player
	 */
	public void joinArena(XPlayer xp);

	/**
	 * lets a player leave the arena
	 * 
	 * @author xize
	 * @param xp - the player
	 */
	public void leaveArena(XPlayer xp);

	/**
	 * returns true if the player exists inside the arena, otherwise false
	 * 
	 * @author xize
	 * @param xp - the player
	 * @return boolean
	 */
	public boolean isInArena(XPlayer xp);

	/**
	 * returns true if the arena is full
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFull();

	/**
	 * returns true if the arena has teams
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasTeams();

	/**
	 * returns the players
	 * 
	 * @author xize
	 * @return Set<String>
	 */
	public Set<XPlayer> getPlayers();
	
	/**
	 * a simple countdown method
	 * 
	 * @author xize
	 * @param seconds - the seconds
	 * @param clocktick - the clock tick sound
	 * @param gamestart - the starters sound
	 */
	public void docCountdown(final int seconds, final Sound clocktick, final Sound gamestart);
	
	/**
	 * resets the arena to default
	 * 
	 * @author xize
	 */
	public void reset();

	/**
	 * removes the arena, from whenever the storage is
	 * 
	 * @author xize
	 */
	public void remove();

}

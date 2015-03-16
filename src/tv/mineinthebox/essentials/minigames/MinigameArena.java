package tv.mineinthebox.essentials.minigames;

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
	 * @return MinigmaeType
	 */
	public MinigameType getType();
	
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

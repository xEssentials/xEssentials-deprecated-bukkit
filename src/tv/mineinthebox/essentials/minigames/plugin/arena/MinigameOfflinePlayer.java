package tv.mineinthebox.essentials.minigames.plugin.arena;

import tv.mineinthebox.essentials.minigames.plugin.MinigameType;

public interface MinigameOfflinePlayer {
	
	/**
	 * returns the game status of the player
	 * 
	 * @author xize
	 * @param type - the game type
	 * @return GameStatus
	 */
	public GameStatus getGameStatus(MinigameType type);
	
	/**
	 * returns true if the player has game status
	 * 
	 * @author xize
	 * @param type - the type
	 * @return boolean
	 */
	public boolean hasGameStatus(MinigameType type);

}

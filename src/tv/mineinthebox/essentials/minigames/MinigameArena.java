package tv.mineinthebox.essentials.minigames;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.interfaces.XPlayer;

public abstract class MinigameArena {
	
	protected File f;
	protected FileConfiguration con;
	
	public MinigameArena(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	/**
	 * returns the name
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName() {
		return con.getString("arena.name");
	}
	
	/**
	 * returns the type of minigame
	 * 
	 * @author xize
	 * @return MinigmaeType
	 */
	public abstract MinigameType getType();
	
	/**
	 * lets a player join the arena
	 * 
	 * @author xize
	 * @param xp - the player
	 */
	public abstract void joinArena(XPlayer xp);
	
	/**
	 * lets a player leave the arena
	 * 
	 * @author xize
	 * @param xp - the player
	 */
	public abstract void leaveArena(XPlayer xp);
	
	/**
	 * returns true if the arena is full
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean isFull();
	
	/**
	 * returns true if the arena has teams
	 * 
	 * @author xize
	 * @return boolean
	 */
	public abstract boolean hasTeams();
	
	/**
	 * resets the arena to default
	 * 
	 * @author xize
	 */
	public abstract void reset();
	
	/**
	 * removes the arena, from whenever the storage is
	 * 
	 * @author xize
	 */
	public abstract void remove();
	
}

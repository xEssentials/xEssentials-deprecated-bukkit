package tv.mineinthebox.essentials.minigames.plugin.arena;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.plugin.MinigamePlugin;

public abstract class MinigameArena {

	protected MinigamePlugin pl;
	protected File f;
	protected FileConfiguration con;
	protected final Set<XPlayer> players = new HashSet<XPlayer>();
	protected boolean isStarted = false;

	public MinigameArena(MinigamePlugin pl, File f, FileConfiguration con) {
		this.pl = pl;
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
		return f.getName().replace(".yml", "");
	}
	
	/**
	 * returns the max amount of players allowed!
	 * 
	 * @author xize
	 * @return int
	 */
	public int getMaxPlayers() {
		return con.getInt("arena.maxplayers");
	}
	
	/**
	 * returns true if the arena has max players, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasMaxPlayers() {
		return con.contains("arena.maxplayers");
	}
	
	/**
	 * returns a list of players
	 * 
	 * @author xize
	 * @return Set<XPlayer>
	 */
	public Set<XPlayer> getPlayers() {
		return players;
	}
	
	/**
	 * returns a status String for e.g buttons in inventory menus
	 * 
	 * @author xize
	 * @return String
	 */
	public String getArenaStatus() {
		if(isFull()) {
			return ChatColor.RED + "FULL!";
		} else if(isStarted() && !isFull()) {
			return ChatColor.GREEN + "GAME STARTED! but players can still join";
		} else {
			return ChatColor.GREEN + "OPEN!";
		}
	}
	
	/**
	 * returns true if the game is started otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isStarted() {
		return isStarted;
	}
	
	/**
	 * sets the game to started or stopped
	 * 
	 * @author xize
	 * @param bol - the boolean
	 */
	public void setStarted(boolean bol) {
		this.isStarted = bol;
	}

	/**
	 * does a countdown based on the parameters given in
	 * 
	 * @author xize
	 * @param seconds - the seconds
	 * @param sound - the sound being player
	 */
	public void doCountdown(final int seconds, final Sound clocktick, final Sound gamestart) {
		new BukkitRunnable() {

			int i = seconds;

			@Override
			public void run() {
				if(i > 0) {
					for(XPlayer xp : players) {
						xp.getPlayer().sendMessage(ChatColor.GREEN + "["+getType().getName()+"]" + ChatColor.GRAY + "counting down: " + i + "s");
						xp.getPlayer().playSound(xp.getPlayer().getLocation(), clocktick, 1F, 1F);
					}
				} else {
					for(XPlayer xp : players) {
						xp.getPlayer().sendMessage(ChatColor.GREEN + "["+getType().getName()+"]" + ChatColor.GRAY + "game started!");
						xp.getPlayer().playSound(xp.getPlayer().getLocation(), gamestart, 1F, 1F);
					}
					setStarted(true);
					cancel();
				}
				i--;
			}
		}.runTaskTimer((xEssentials)pl.getMinigameApi(), 10L, 20L);
	}
	
	/**
	 * returns true if the arena some sort of score system, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasScore() {
		return con.contains("arena.score");
	}
	
	/**
	 * returns the max score!
	 * 
	 * @author xize
	 * @return int
	 */
	public int getMaxScore() {
		return (hasScore() ? con.getInt("arena.score") : 0);
	}

	/**
	 * returns the type of minigame
	 * 
	 * @author xize
	 * @return MinigameType
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
	 * returns true if the player exists inside the arena, otherwise false
	 * 
	 * @author xize
	 * @param xp - the player
	 * @return boolean
	 */
	public abstract boolean isInArena(XPlayer xp);

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

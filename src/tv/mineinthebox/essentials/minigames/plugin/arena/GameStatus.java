package tv.mineinthebox.essentials.minigames.plugin.arena;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.minigames.plugin.MinigameType;

public class GameStatus {
	
	private final MinigameType type;
	private final String index;
	private final File f;
	private final FileConfiguration con;
	
	public GameStatus(MinigameType type, File f, FileConfiguration con) {
		this.type = type;
		this.index = "minigame-status."+type.name();
		this.f = f;
		this.con = con;
	}
	
	/**
	 * returns the amount of times the player died in this game
	 * 
	 * @author xize
	 * @return int
	 */
	public int getDeaths() {
		return con.getInt(index+".deaths");
	}
	
	/**
	 * adds 1 death to the counter
	 * 
	 * @author xize
	 */
	public void addDeath() {
		con.set(index+".deaths", getDeaths()+1);
		reload();
	}
	
	/**
	 * returns how many kills the player has in this game
	 * 
	 * @author xize
	 * @return int
	 */
	public int getKills() {
		return con.getInt(index+".kills");
	}
	
	/**
	 * adds a kill to the player
	 * 
	 * @author xize
	 */
	public void addKill() {
		con.set(index+".kills", getKills()+1);
		reload();
	}
	
	/**
	 * returns how many times the player played this game
	 * 
	 * @author xize
	 * @return int
	 */
	public int matchesPlayed() {
		return con.getInt(index+".rounds-played");
	}
	
	/**
	 * adds a match to the counter
	 * 
	 * @author xize
	 */
	public void addMatchPlayed() {
		con.set(index+".rounds-played", matchesPlayed()+1);
		reload();
	}
	
	/**
	 * returns true if the player has scored, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasScore() {
		if(con.contains(index+".score")) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns the score for ranking
	 * 
	 * @author xize
	 * @return int
	 */
	public int getScore() {
		return con.getInt(index+".score");
	}
	
	public void addScore() {
		con.set(index+".score", getScore()+1);
		reload();
	}
	
	public String getLastSeen() {
		Date date = new Date(con.getLong(index+".last-seen"));
		return DateFormat.getInstance().format(date);
	}
	
	public void setLastSeen() {
		con.set(index+".last-seen", System.currentTimeMillis());
		reload();
	}
	
	/**
	 * returns the longest time the player survived in-game
	 * 
	 * @author xize
	 * @return int
	 */
	public int getLongestSurvived() {
		return con.getInt(index+".last-survived");
	}
	
	/**
	 * if the survival time is longer than the older, the param will be used as new longest survival time in this game
	 * 
	 * @author xize
	 * @param survivaltime - the time the player survived the game
	 */
	public void addLongestSurvived(int survivaltime) {
		if(survivaltime > getLongestSurvived()) {
			con.set(index+".last-survived", getLongestSurvived()+1);
			reload();	
		}
	}
	
	/**
	 * returns the type where this game status is from
	 * 
	 * @author xize
	 * @return MinigameType
	 */
	public MinigameType getType() {
		return type;
	}
	
	public void reload() {
		try {
			con.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}

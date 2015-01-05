package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSessionManager;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.football.FootballArena;
import tv.mineinthebox.essentials.minigames.tennis.TennisArena;

public class MinigameManager {

	private final EnumMap<MinigameType, List<MinigameArena>> minigames = new EnumMap<MinigameType, List<MinigameArena>>(MinigameType.class);
	private MinigameSessionManager minigamesessions;
	
	public void onEnable() {
		loadMinigames();
		this.minigamesessions = new MinigameSessionManager();
		xEssentials.getPlugin().log("minigames for xEssentials has been enabled!", LogType.INFO);
	}

	public void onDisable() {
		cleanup();
		minigames.clear();
		xEssentials.getPlugin().log("minigames for xEssentials has been disabled!", LogType.INFO);
	}

	private void loadMinigames() {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "minigames");
		if(dir.isDirectory()) {
			File[] games = dir.listFiles();
			for(File f : games) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.contains("type")) {
					if(con.getString("type").equalsIgnoreCase(MinigameType.TENNIS.name())) {
						if(minigames.containsKey(MinigameType.TENNIS)) {
							MinigameArena game = new TennisArena(f, con);
							minigames.get(MinigameType.TENNIS).add(game);
						} else {
							List<MinigameArena> list = new ArrayList<MinigameArena>();
							MinigameArena game = new TennisArena(f, con);
							list.add(game);
							minigames.put(MinigameType.TENNIS, list);	
						}
					} else if(con.getString("type").equalsIgnoreCase(MinigameType.FOOT_BALL.name())) {
						if(minigames.containsKey(MinigameType.FOOT_BALL)) {
							MinigameArena game = new FootballArena(f, con);
							minigames.get(MinigameType.FOOT_BALL).add(game);
						} else {
							List<MinigameArena> list = new ArrayList<MinigameArena>();
							MinigameArena game = new FootballArena(f, con);
							list.add(game);
							minigames.put(MinigameType.FOOT_BALL, list);	
						}
					}
				}
			}
		}
	}
	
	private void cleanup() {
		for(MinigameType type : MinigameType.values()) {
			if(minigames.containsKey(type)) {
				for(MinigameArena arena : minigames.get(type)) {
					arena.reset();
				}
			}
		}
	}
	
	public EnumMap<MinigameType, List<MinigameArena>> getMinigames() {
		return minigames;
	}
	
	public MinigameArena getArenaByName(MinigameType type, String name) {
		if(getMinigames().containsKey(type)) {
			for(MinigameArena arena : getMinigames().get(type)) {
				if(arena.getName().equalsIgnoreCase(name)) {
					return arena;
				}
			}
		}
		return null;
	}
	
	public void removeMinigame(MinigameType type, MinigameArena game) {
		minigames.get(type).remove(game);
	}
	
	public boolean containsMinigame(String name) {
		for(MinigameType type : MinigameType.values()) {
			if(minigames.containsKey(type)) {
				for(MinigameArena arena : minigames.get(type)) {
					if(arena.getName().equalsIgnoreCase(name)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public MinigameSessionManager getMinigameSessions() {
		return minigamesessions;
	}

}

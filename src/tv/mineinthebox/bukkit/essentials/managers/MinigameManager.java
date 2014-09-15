package tv.mineinthebox.bukkit.essentials.managers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.arenas.Minigame;
import tv.mineinthebox.bukkit.essentials.enums.MinigameType;
import tv.mineinthebox.bukkit.essentials.instances.SpleefArena;

public class MinigameManager {
	
	/**
	 * @author xize
	 * @param adds a minigame into our global system.
	 * @param type - the MinigameType enum
	 * @param a - the arena.
	 * @throws NullPointerException when the object is something else rather than the Arena Object
	 */
	public boolean addGame(Minigame a, MinigameType typ) throws Exception {
		EnumMap<MinigameType, HashMap<String, Minigame>> hash = getMinigameMap();
		for(MinigameType type : MinigameType.values()) {
			if(a.getType() == type) {
				HashMap<String, Minigame> arena = new HashMap<String, Minigame>();
				hash.put(type, arena);

				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param removes a minigame!
	 * @param type - the MinigameType enum
	 * @param arena - the arena name
	 * @throws NullPointerException - when the arena does not exist!
	 */
	public void removeGame(String arena) throws Exception {
		if(isArena(arena)) {
			Minigame game = getArena(arena);
			game.remove();
		} else {
			throw new NullPointerException("cannot remove a game which doesn't exist!");
		}
	}
	
	/**
	 * @author xize
	 * @param gets the Arena specified by the player
	 * @param p - the player
	 * @return Minigame - this could be HungerGamesArena or SpleefArena
	 */
	public Minigame getArenaFromPlayer(Player p) {
		EnumMap<MinigameType, HashMap<String, Minigame>> hash = getMinigameMap();
		for(MinigameType type : MinigameType.values()) {
			if(hash.containsKey(type)) {
				for(Minigame game : hash.get(type).values()) {
					if(game.isPlayerJoined(p.getName())) {
						return game;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param returns true if the player does exist in one of the arenas!
	 * @param p - the Player
	 * @return Boolean
	 */
	public boolean isPlayerInArea(Player p) {
		return (getArenaFromPlayer(p) instanceof Minigame ? true : false);
	}
	
	/**
	 * @author xize
	 * @param s - the possible arena name
	 * @return Minigame - the super class of arena types.
	 * @throws NullPointerException
	 */
	public Minigame getArena(String s) {
		EnumMap<MinigameType, HashMap<String, Minigame>> hash = getMinigameMap();
		for(MinigameType type : MinigameType.values()) {
			if(hash.containsKey(type)) {
				if(hash.get(type).containsKey(s.toLowerCase())) {
					return (Minigame) hash.get(type).get(s.toLowerCase());
				}
			}
		}
		throw new NullPointerException("arena name does not exist!, please use isArena() first!");
	}
	
	/**
	 * @author xize
	 * @param s - the arena name
	 * @param type - the minigame type
	 * @return Boolean
	 */
	public boolean isArena(String s) {
		EnumMap<MinigameType, HashMap<String, Minigame>> hash = getMinigameMap();
		for(MinigameType type : MinigameType.values()) {
			if(hash.containsKey(type)) {
				if(hash.get(type).containsKey(s.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param gets the Enum map holding all loaded minigames, in a abstract way.
	 * @return EnumMap<MinigameType, HashMap<String, Object>>()
	 */
	private EnumMap<MinigameType, HashMap<String, Minigame>> getMinigameMap() {
		return Configuration.getMinigameMap();
	}
	
	/**
	 * @author xize
	 * @param returns all the spleef arenas
	 * @return SpleefArena[]
	 */
	public SpleefArena[] getAllSpleefArenas() {
		EnumMap<MinigameType, HashMap<String, Minigame>> hash = getMinigameMap();
		List<SpleefArena> arenas = new ArrayList<SpleefArena>();
		if(hash.containsKey(MinigameType.SPLEEF)) {
			Iterator<Entry<String, Minigame>> it = hash.get(MinigameType.SPLEEF).entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Minigame> entry = it.next();
				if(entry.getValue() instanceof SpleefArena) {
					arenas.add((SpleefArena)entry.getValue());
				}
			}
		}
		return arenas.toArray(new SpleefArena[arenas.size()]);
	}

}

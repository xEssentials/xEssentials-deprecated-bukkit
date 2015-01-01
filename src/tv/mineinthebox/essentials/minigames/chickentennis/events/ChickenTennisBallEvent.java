package tv.mineinthebox.essentials.minigames.chickentennis.events;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.chickentennis.ChickenTennisArena;

public class ChickenTennisBallEvent extends Event {

	private final Location chickenloc;
	private final ChickenTennisArena arena;
	private static final HandlerList handlers = new HandlerList();

	public ChickenTennisBallEvent(Location chickenloc, ChickenTennisArena arena) {
		this.chickenloc = chickenloc;
		this.arena = arena;
	}

	public ChickenTennisArena getArena() {
		return arena;
	}

	public Location getLocation() {
		return chickenloc;
	}
	
	public XPlayer getLoser() {
		return (arena.getPlayers()[0].equals(getWhoScore()) ? arena.getPlayers()[1] : arena.getPlayers()[0]);
	}

	public XPlayer getWhoScore() {

		//use Math.abs to ignore the negative numbers, and instead we scale down till we got the final bounds which we caculate /2.
		
		int max = Math.max(Math.abs(arena.getBoundsX()), Math.abs(arena.getBoundsZ()));
		int min = Math.min(Math.abs(arena.getBoundsX()), Math.abs(arena.getBoundsZ()));
		int size = (max-min);
		
		if(max == Math.abs(arena.getBoundsX())) {
			//x is the coordinate we check on.
			int bounds = size/2;

			int chickenx = (max-Math.abs(chickenloc.getBlockX()));
			
			if(chickenx < bounds) {
				//first player, so the second player scores
				return arena.getPlayers()[1];
			} else if(chickenx > bounds) {
				//second player, so the first player scores
				return arena.getPlayers()[0];
			}
		} else if(max == Math.abs(arena.getBoundsZ())) {
			//z is the coordinate we check on
			int bounds = size/2;

			int chickenz = (max-Math.abs(chickenloc.getBlockZ()));
			
			if(chickenz < bounds) {
				//first player, second player scores
				return arena.getPlayers()[1];
			} else if(chickenz > bounds) {
				//second player, first player scores
				return arena.getPlayers()[0];
			}
		}
		return null;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

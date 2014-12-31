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

		int max = Math.max(arena.getBoundsX(), arena.getBoundsZ());
		int min = Math.min(arena.getBoundsX(), arena.getBoundsZ());
		int size = (max-min);
		
		if(max == arena.getBoundsX()) {
			//x is the coordinate we check on.
			int bounds = size/2;

			int chickenx = (max-chickenloc.getBlockX());
			
			if(chickenx < bounds) {
				//first player, so the second player scores
				return arena.getPlayers()[1];
			} else if(chickenx > bounds) {
				//second player, so the first player scores
				return arena.getPlayers()[0];
			}
		} else if(max == arena.getBoundsZ()) {
			//z is the coordinate we check on
			int bounds = size/2;

			int chickenz = (max-chickenloc.getBlockZ());
			
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

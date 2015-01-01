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
		XPlayer p1 = arena.getPlayers()[0];
		XPlayer p2 = arena.getPlayers()[1];
		
		double p1_dist = chickenloc.distanceSquared(p1.getPlayer().getLocation());
		double p2_dist = chickenloc.distanceSquared(p2.getPlayer().getLocation()); 
		
		if(p1_dist > p2_dist) {
			//p1 scores
			return p1;
		} else if(p2_dist > p1_dist) {
			//p2 scores
			return p2;
			
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

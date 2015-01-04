package tv.mineinthebox.essentials.minigames.football.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.football.FootballArena;
import tv.mineinthebox.essentials.minigames.football.TeamType;

public class FootballPassEvent extends Event {
	
	private final XPlayer xp;
	private final FootballArena arena;
	private final boolean hasScored;
	private static final HandlerList handlers = new HandlerList();
	
	public FootballPassEvent(XPlayer xp, FootballArena arena, boolean hasScored) {
		this.xp = xp;
		this.arena = arena;
		this.hasScored = hasScored;
	}
	
	public boolean hasScored() {
		return hasScored;
	}
	
	public TeamType getTeam() {
		if(arena.getRedTeam().contains(xp)) {
			return TeamType.RED_TEAM;
		} else if(arena.getBlueTeam().contains(xp)) {
			return TeamType.BLUE_TEAM;
		}
		return null;
	}
	
	public TeamType getLosingTeam() {
		return (getTeam() == TeamType.RED_TEAM ? TeamType.BLUE_TEAM : TeamType.RED_TEAM);
	}
	
	public XPlayer getPlayer() {
		return xp;
	}
	
	public FootballArena getArena() {
		return arena;
	}
	
	public boolean hasWon() {
		if(arena.getRedTeam().contains(xp)) {
			return (arena.getRedScore() == arena.getMaxScore() ? true : false);
		} else if(arena.getBlueTeam().contains(xp)) {
			return (arena.getBlueScore() == arena.getMaxScore() ? true : false);
		}
		return false;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

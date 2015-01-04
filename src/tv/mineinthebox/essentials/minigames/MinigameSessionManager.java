package tv.mineinthebox.essentials.minigames;

import tv.mineinthebox.essentials.minigames.football.FootballSession;
import tv.mineinthebox.essentials.minigames.tennis.TennisSession;

public class MinigameSessionManager {
	
	private MinigameSession chickentennis;
	private MinigameSession football;
	
	public MinigameSession getTennisSessions() {
		if(chickentennis == null) {
			this.chickentennis = new TennisSession();
		}
		return chickentennis;
	}
	
	public MinigameSession getFootballSessions() {
		if(football == null) {
			this.football = new FootballSession();
		}
		return football;
	}

}

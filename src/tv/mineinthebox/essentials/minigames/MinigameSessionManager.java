package tv.mineinthebox.essentials.minigames;

import tv.mineinthebox.essentials.minigames.tennis.TennisSessions;

public class MinigameSessionManager {
	
	private final TennisSessions chickentennis = new TennisSessions();
	
	public MinigameSession getTennisSessions() {
		return chickentennis;
	}

}

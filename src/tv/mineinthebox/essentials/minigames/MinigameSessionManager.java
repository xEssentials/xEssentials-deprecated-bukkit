package tv.mineinthebox.essentials.minigames;

import tv.mineinthebox.essentials.minigames.chickentennis.ChickenTennisSessions;

public class MinigameSessionManager {
	
	private final ChickenTennisSessions chickentennis = new ChickenTennisSessions();
	
	public MinigameSession getChickenTennisSessions() {
		return chickentennis;
	}

}

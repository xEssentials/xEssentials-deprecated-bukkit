package tv.mineinthebox.essentials.minigames.plugin.arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.plugin.MinigameType;

public class LeaderBoard {

	private final xEssentials pl;

	public LeaderBoard(xEssentials pl) {
		this.pl = pl;
	}

	public List<MinigameOfflinePlayer> getScoreList(final MinigameType type) {
		MinigameOfflinePlayer[] off_args = pl.getManagers().getPlayerManager().getOfflinePlayers();

		List<MinigameOfflinePlayer> applicable_players = new ArrayList<MinigameOfflinePlayer>();

		for(MinigameOfflinePlayer off : off_args) {
			if(off.hasGameStatus(type)) {
				applicable_players.add(off);
			}
		}

		Collections.sort(applicable_players, new Comparator<MinigameOfflinePlayer>() {

			@Override
			public int compare(MinigameOfflinePlayer o1, MinigameOfflinePlayer o2) {
				return ((Integer)o1.getGameStatus(type).getScore()).compareTo(o2.getGameStatus(type).getScore());
			}

		});
		return applicable_players;

	}
}

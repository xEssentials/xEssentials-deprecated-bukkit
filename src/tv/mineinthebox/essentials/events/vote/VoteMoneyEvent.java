package tv.mineinthebox.essentials.events.vote;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteMoneyEvent extends EventTemplate implements Listener {
	
	public VoteMoneyEvent(xEssentials pl) {
		super(pl, "VoteMoney");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVote(VotifierEvent e) {
		Vote vote = e.getVote();
		if(pl.getManagers().getPlayerManager().isEssentialsPlayer(vote.getUsername())) {
			Player p = Bukkit.getPlayer(vote.getUsername());
				pl.getManagers().getVaultManager().desposit(vote.getUsername(), pl.getConfiguration().getVoteConfig().getMoneyReward());
				if(p.isOnline()) {
					sendMessage(p, pl.getConfiguration().getVoteConfig().getVotePersonalMessage().replaceAll("%player%", p.getName()).replaceAll("%reward%", pl.getConfiguration().getVoteConfig().getMoneyReward()+"$"));
				}
				broadcast(pl.getConfiguration().getVoteConfig().getVoteBroadcastMessage().replaceAll("%player%", vote.getUsername()).replaceAll("%reward%", pl.getConfiguration().getVoteConfig().getMoneyReward()+"$"));
		}
	}

}

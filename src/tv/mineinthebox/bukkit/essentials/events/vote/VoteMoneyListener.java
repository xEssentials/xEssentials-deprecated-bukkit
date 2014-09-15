package tv.mineinthebox.bukkit.essentials.events.vote;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteMoneyListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVote(VotifierEvent e) {
		Vote vote = e.getVote();
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(vote.getUsername())) {
			Player p = Bukkit.getPlayer(vote.getUsername());
				xEssentials.getManagers().getVaultManager().desposit(vote.getUsername(), Configuration.getVoteConfig().getMoneyReward());
				if(p.isOnline()) {
					p.sendMessage(Configuration.getVoteConfig().getVotePersonalMessage().replaceAll("%player%", p.getName()).replaceAll("%reward%", Configuration.getVoteConfig().getMoneyReward()+"$"));
				}
				Bukkit.broadcastMessage(Configuration.getVoteConfig().getVoteBroadcastMessage().replaceAll("%player%", vote.getUsername()).replaceAll("%reward%", Configuration.getVoteConfig().getMoneyReward()+"$"));
		}
	}

}

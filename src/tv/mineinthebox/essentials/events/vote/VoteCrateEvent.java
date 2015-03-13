package tv.mineinthebox.essentials.events.vote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.manco.enums.CrateType;
import tv.mineinthebox.manco.interfaces.Crate;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteCrateEvent extends EventTemplate implements Listener {
	
	public VoteCrateEvent(xEssentials pl) {
		super(pl, "VoteCrate");
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVote(VotifierEvent e) {
		Vote vote = e.getVote();
		if(pl.getManagers().getPlayerManager().isEssentialsPlayer(vote.getUsername())) {
			Player p = Bukkit.getPlayer(vote.getUsername());
				Crate crate = pl.getManagers().getManCoManager().spawnRandomCrate(vote.getUsername());
				if(p.isOnline()) {
					sendMessage(p, pl.getConfiguration().getVoteConfig().getVotePersonalMessage().replaceAll("%player%", p.getName()).replaceAll("%reward%", (crate.getType() == CrateType.RARE ? ChatColor.DARK_PURPLE + crate.getCrateName() : crate.getCrateName())));
					broadcast(pl.getConfiguration().getVoteConfig().getVoteBroadcastMessage().replaceAll("%player%", vote.getUsername()).replaceAll("%reward%", (crate.getType() == CrateType.RARE ? ChatColor.DARK_PURPLE + crate.getCrateName() : crate.getCrateName())));
				}
		}
	}
	
}

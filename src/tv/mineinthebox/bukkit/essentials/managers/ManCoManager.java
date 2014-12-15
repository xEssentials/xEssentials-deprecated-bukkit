package tv.mineinthebox.bukkit.essentials.managers;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;
import tv.mineinthebox.manco.ManCo;
import tv.mineinthebox.manco.instances.CratePlayer;
import tv.mineinthebox.manco.instances.NormalCrate;

public class ManCoManager {
	
	/**
	 * @author xize
	 * @param returns the crate of this player
	 * @return Location
	 */
	public Location getCrateLocation(Player p) {
		CratePlayer crate = ManCo.getApi().getCratePlayer(p.getName());
		return crate.getCrateChest().getLocation();
	}
	
	/**
	 * @author xize
	 * @param returns the boolean if the player has a owned crate or not
	 * @return boolean
	 */
	public boolean hasCrate(Player p) {
		CratePlayer crate = ManCo.getApi().getCratePlayer(p.getName());
		return crate.hasCrate();
	}
	
	/**
	 * @author xize
	 * @param returns the random crate
	 * @return NormalCrate
	 */
	public NormalCrate spawnRandomCrate(String player) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(player)) {
			Random rand = new Random();
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
			CratePlayer cratePlayer = ManCo.getApi().getCratePlayer(player);
			NormalCrate crate = ManCo.getApi().getCrates()[rand.nextInt(ManCo.getApi().getCrates().length)];
			return ManCo.getApi().spawnCrate(cratePlayer, crate, xp.getPlayer().getLocation());
		}
		return null;
	}

}

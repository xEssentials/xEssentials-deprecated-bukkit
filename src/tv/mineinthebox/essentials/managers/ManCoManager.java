package tv.mineinthebox.essentials.managers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.manco.instances.CratePlayer;
import tv.mineinthebox.manco.interfaces.Crate;
import tv.mineinthebox.manco.interfaces.ManCoApi;

public class ManCoManager {
	
	private final xEssentials pl;
	private final ManCoApi api;
	
	public ManCoManager(xEssentials pl) {
		this.pl = pl;
		this.api = (ManCoApi) Bukkit.getPluginManager().getPlugin("ManCo");
	}
	
	/**
	 * @author xize
	 * @param returns the crate of this player
	 * @return Location
	 */
	public Location getCrateLocation(Player p) {
		CratePlayer crate = api.getCratePlayer(p.getName());
		return crate.getCrateChest().getLocation();
	}
	
	/**
	 * @author xize
	 * @param returns the boolean if the player has a owned crate or not
	 * @return boolean
	 */
	public boolean hasCrate(Player p) {
		CratePlayer crate = api.getCratePlayer(p.getName());
		return crate.hasCrate();
	}
	
	/**
	 * @author xize
	 * @param returns the random crate
	 * @return NormalCrate
	 */
	public Crate spawnRandomCrate(String player) {
		if(pl.getManagers().getPlayerManager().isOnline(player)) {
			Random rand = new Random();
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(player);
			CratePlayer cratePlayer = api.getCratePlayer(player);
			Crate crate = api.getCrates()[rand.nextInt(api.getCrates().length)];
			return api.spawnCrate(cratePlayer, crate, xp.getPlayer().getLocation());
		}
		return null;
	}

}

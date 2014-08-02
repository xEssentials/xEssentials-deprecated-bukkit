package tv.mineinthebox.essentials.hook;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import tv.mineinthebox.manco.ManCo;
import tv.mineinthebox.manco.instances.CratePlayer;


public class ManCoHook {
	
	/**
	 * @author xize
	 * @param returns the crate of this player
	 * @return Location
	 */
	public static Location getCrateLocation(Player p) {
		CratePlayer crate = ManCo.getApi().getCratePlayer(p.getName());
		return crate.getCrateChest().getLocation();
	}
	
	/**
	 * @author xize
	 * @param returns the boolean if the player has a owned crate or not
	 * @return boolean
	 */
	public static boolean hasCrate(Player p) {
		CratePlayer crate = ManCo.getApi().getCratePlayer(p.getName());
		return crate.hasCrate();
	}

}

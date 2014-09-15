package tv.mineinthebox.bukkit.essentials.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.Warp;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;

public class WarpManager {
	
	/**
	 * @author xize
	 * @param get all the warps
	 * @return Warp[]
	 */
	public Warp[] getWarps() {
		List<Warp> warps = new ArrayList<Warp>();
		for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.hasOwnedWarps()) {
				warps.addAll(Arrays.asList(off.getWarps()));
			}
		}
		return warps.toArray(new Warp[warps.size()]);
	}
	
	/**
	 * @author xize
	 * @param check if the warp exist
	 * @return Boolean
	 */
	public boolean doesWarpExist(String key) {
		for(Warp warp : getWarps()) {
			if(warp.getWarpName().equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param get the warp by using the name
	 * @return Warp
	 * @throws NullPointerException - when the warp does not exist!
	 */
	public Warp getWarpByName(String name) {
		if(doesWarpExist(name)) {
			for(Warp warp : getWarps()) {
				if(warp.getWarpName().equalsIgnoreCase(name)) {
					return warp;
				}
			}
		}
		throw new NullPointerException("warp does not exist");
	}

}

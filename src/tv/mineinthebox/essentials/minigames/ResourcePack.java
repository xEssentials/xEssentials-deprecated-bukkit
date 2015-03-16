package tv.mineinthebox.essentials.minigames;

import java.io.File;

import org.bukkit.entity.Player;

public class ResourcePack {
	
	private final File f;
	
	public ResourcePack(File f) {
		this.f = f;
	}
	
	/**
	 * applies the resource pack to the player
	 * 
	 * @author xize
	 * @param p - the player
	 */
	public void apply(Player p) {
		p.setResourcePack(f.toURI().toString());
	}
	
	/**
	 * removes the resource pack of the player
	 * 
	 * @author xize
	 * @param p - the player
	 */
	public void remove(Player p) {
		p.setResourcePack(null);
	}

}

package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.PlayerChunkMoveEvent;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class PlayerBorderEvent extends EventTemplate implements Listener {
	
	private final int xmax;
	private final int xmin;
	private final int zmax;
	private final int zmin;
	
	public PlayerBorderEvent(xEssentials pl) {
		super(pl, "Border");
		this.xmax = (pl.getConfiguration().getPlayerConfig().getSpawnLocation().getBlockX() + (pl.getConfiguration().getPlayerConfig().getWorldBorderSize()/2));
		this.xmin = (pl.getConfiguration().getPlayerConfig().getSpawnLocation().getBlockX() - (pl.getConfiguration().getPlayerConfig().getWorldBorderSize()/2));
		this.zmax = (pl.getConfiguration().getPlayerConfig().getSpawnLocation().getBlockZ() + (pl.getConfiguration().getPlayerConfig().getWorldBorderSize()/2));
		this.zmin = (pl.getConfiguration().getPlayerConfig().getSpawnLocation().getBlockZ() - (pl.getConfiguration().getPlayerConfig().getWorldBorderSize()/2));
	}
	
	@EventHandler
	public void onBorder(PlayerChunkMoveEvent e) {
		//TODO: handle spawn
		int x = e.getPlayer().getLocation().getBlockX();
		int z = e.getPlayer().getLocation().getBlockZ();
		if(!(x <= xmax && x >= xmin && z <= zmax && z >= zmin)) {
			
			Location loc = e.getFrom();
			loc.add(0, 1, 0);
			loc.setPitch(e.getPlayer().getLocation().getPitch());
			loc.setYaw(e.getPlayer().getLocation().getYaw());
			
			e.getPlayer().teleport(loc);
			sendMessage(e.getPlayer(), ChatColor.RED + "you are at the worldborder!, please return.");
		}
	}

}

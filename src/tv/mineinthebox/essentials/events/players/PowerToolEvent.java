package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PowerToolEvent implements Listener {

	private final xEssentials pl;

	public PowerToolEvent(xEssentials pl) {
		this.pl = pl;	
	}

	@EventHandler
	public void onPowerTool(PlayerInteractEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(xp.hasPowerTool() && xp.getPlayer().hasPermission(PermissionKey.CMD_POWERTOOL.getPermission())) {
				xp.getPlayer().performCommand(xp.getPowerTool());
				e.setCancelled(true);
			}
		}
	}
}

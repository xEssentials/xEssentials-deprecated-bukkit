package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;

public class SignEditEvent implements Listener {

	@EventHandler
	public void onEditSign(SignChangeEvent e) {
		org.bukkit.material.Sign sign = (org.bukkit.material.Sign) e.getBlock().getState().getData();

		Block other = e.getBlock().getRelative(sign.getFacing().getOppositeFace());
		if(other.getType() == Material.WALL_SIGN || other.getType() == Material.SIGN_POST) {
			//check if sign is protected by a plugin or ours.
			if(!e.getPlayer().isSneaking()) {
				if(Configuration.getProtectionConfig().isSignProtectionEnabled()) {
					if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), other)) {
						Sign signn = (Sign)other.getState();
						signn.setLine(0, e.getLine(0));
						signn.setLine(1, e.getLine(1));
						signn.setLine(2, e.getLine(2));
						signn.setLine(3, e.getLine(3));
						signn.update(true);
						e.getPlayer().sendMessage(ChatColor.GREEN + "successfully edited sign.");
						e.getBlock().setType(Material.AIR);
					} else {
						e.getBlock().setType(Material.AIR);
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.RED + "cannot modify sign, this sign does not belongs to you");
					}
				} else {

				}
			}
		}
	}

}

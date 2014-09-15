package tv.mineinthebox.bukkit.essentials.events.signs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class SignBoom implements Listener {

	@EventHandler
	public void m(PlayerMoveEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isBoom()) {
			if(e.getPlayer().getPlayer().getLocation().getBlock().getRelative(BlockFace.SELF).getType() == Material.WEB) {
				xp.removeBoom();
			} else {
				e.getPlayer().getPlayer().getWorld().createExplosion(e.getPlayer().getPlayer().getLocation(), 0.0F, false);
			}
		}
	}
	@EventHandler
	public void damage(EntityDamageEvent p) {
		if(p.getEntity() instanceof Player && p.getCause() == DamageCause.FALL) {
			Player ap = (Player) p.getEntity();
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(ap.getName());
			if(xp.isBoom()) {
				xp.removeBoom();
				p.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void SignBoomEvent(SignChangeEvent s) {
		if(s.getLine(0).equalsIgnoreCase("[boom]") && s.getPlayer().hasPermission(PermissionKey.SIGN_BOOM.getPermission())) {
			s.setLine(0, ChatColor.GOLD + "[Boom]");
			s.getBlock().getState().update(true);
			s.getPlayer().sendMessage(ChatColor.GOLD + "[Boom] " + ChatColor.GREEN + "You successfully placed a boom sign!");
		} else {
			if(s.getLine(0).equalsIgnoreCase("[boom]") && !s.getPlayer().hasPermission(PermissionKey.SIGN_BOOM.getPermission())) {
				s.getBlock().breakNaturally();
				s.getPlayer().sendMessage("You are not allowed to place such signs!");
				s.setCancelled(true);		
			}
		}
	}

	@EventHandler
	public void signRightClickBoom(PlayerInteractEvent s) {
		if(s.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(s.getClickedBlock().getState().getType() == Material.SIGN || s.getClickedBlock().getState().getType() == Material.SIGN_POST || s.getClickedBlock().getState().getType() == Material.WALL_SIGN) {
				//log.info("This is a sign...");
				Sign sign = (Sign) s.getClickedBlock().getState();
				if(sign.getLine(0).contains("[Boom]")) {
					//log.info("Launching dispatch command....");
					if(s.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
						s.getPlayer().sendMessage(ChatColor.GOLD + "[Boom] " + ChatColor.RED + "You need to be survival to do this!");
						s.setCancelled(true);
					} else {
						//log.info("This player has interacted with this sign");
						s.getPlayer().getServer().dispatchCommand(Bukkit.getConsoleSender(), "boom " + s.getPlayer().getName());
						s.getPlayer().sendMessage(ChatColor.GOLD + "[Boom] " + ChatColor.GREEN + "boooooom!");
					}
				}
			}
		}
	}

	@EventHandler
	public void newplayer(PlayerKickEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isBoom()) {
			xp.removeBoom();
		}
	}

	@EventHandler
	public void pquit(PlayerKickEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isBoom()) {
			xp.removeBoom();
		}
	}

}

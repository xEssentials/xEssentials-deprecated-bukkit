package tv.mineinthebox.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class SignBoomEvent implements Listener {
	
	private final xEssentials pl;
	
	public SignBoomEvent(xEssentials pl) {
		this.pl = pl;
	}
	

	@EventHandler
	public void m(PlayerMoveEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
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
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(ap.getName());
			if(xp.isBoom()) {
				xp.removeBoom();
				p.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onCreate(SignChangeEvent s) {
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
				if(s.isCancelled()) {
					return;
				}
				//log.info("This is a sign...");
				Sign sign = (Sign) s.getClickedBlock().getState();
				if(sign.getLine(0).contains("[Boom]")) {
					//log.info("Launching dispatch command....");
					if(s.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
						s.getPlayer().sendMessage(ChatColor.GOLD + "[Boom] " + ChatColor.RED + "You need to be survival to do this!");
						s.setCancelled(true);
					} else {
						//log.info("This player has interacted with this sign");
						//s.getPlayer().getServer().dispatchCommand(Bukkit.getConsoleSender(), "boom " + s.getPlayer().getName());
						s.getPlayer().sendMessage(ChatColor.GOLD + "[Boom] " + ChatColor.GREEN + "boooooom!");
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(s.getPlayer().getName());
						xp.setBoom();
						Location loc = s.getPlayer().getLocation();
						loc.setY(loc.getY() + 100);
						int speed = 10;
						Vector vector = loc.toVector().subtract(s.getPlayer().getLocation().toVector()).normalize();
						s.getPlayer().setVelocity(vector.multiply(speed));
						s.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void newplayer(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isBoom()) {
				xp.removeBoom();
			}
		}
	}

	@EventHandler
	public void pquit(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isBoom()) {
				xp.removeBoom();
			}
		}
	}

}

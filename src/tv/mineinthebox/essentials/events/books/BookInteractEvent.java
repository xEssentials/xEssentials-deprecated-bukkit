package tv.mineinthebox.essentials.events.books;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;

public class BookInteractEvent implements Listener {
	
	private final xEssentials pl;
	
	public BookInteractEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.BOOKSHELF) {
				Random rand = new Random();
				int index = rand.nextInt(pl.getConfiguration().getMiscConfig().getBookMessages().length);
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfiguration().getMiscConfig().getBookMessages()[index]));
			}
		}
	}

}

package tv.mineinthebox.essentials.events.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.PlayerOpenBookEvent;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class AntiAddvertiseEvent extends EventTemplate implements Listener {
	
	public AntiAddvertiseEvent(xEssentials pl) {
		super(pl, "Anti Addvertise");
	}

	@EventHandler
	public void onAntiAddvertise(PlayerChatEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		e.setMessage(ipcheck(e.getMessage(), xp));
	}

	@EventHandler
	public void onAntiAddvertiseOnBook(PlayerOpenBookEvent e) {
		if(e.hasBookContents()) {
			if(e.getBookItem().getType() == Material.WRITTEN_BOOK) {
				if(e.getBookContents().hasAuthor()) {
					if(pl.getManagers().getPlayerManager().isEssentialsPlayer(e.getBookContents().getAuthor())) {
						XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(e.getBookContents().getAuthor());
						for(String bookPage : e.getBookContents().getPages()) {
							if(ipcheck(bookPage, off)) {
								sendMessage(e.getPlayer(), ChatColor.RED + "you cannot open this book, this book contains addvertises from " + off.getUser());
								e.getPlayer().setItemInHand(null);
								e.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void createBook(PlayerEditBookEvent e) {
		if(!e.isSigning()) {
			if(!e.getPreviousBookMeta().hasAuthor()) {
				BookMeta meta = e.getNewBookMeta();
				meta.setAuthor(e.getPlayer().getName());
				e.setNewBookMeta(meta);
				e.setSigning(true);
				sendMessage(e.getPlayer(), ChatColor.GREEN + "book automatic signed, by anti addvertise system!");
			}
		}
	}

	@EventHandler
	public void onPreprocess(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().startsWith("/")) {
			e.setMessage(ipcheck(e.getMessage(), pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName())));
		}
	}

	public String ipcheck(String a, XPlayer xp) {
		String pattern = "\\d{1,3}(?:\\.\\d{1,3}){3}(?::\\d{1,5})?";

		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(a);
		while (matcher.find()) {
			xp.setPermBanned("You are banned for addvertising.", "CONSOLE");
			xp.getPlayer().kickPlayer("You are banned for addvertising.");
			return a.toString().replace(matcher.group(), "***.***.***:****");
		}
		if(a.toLowerCase().contains("no-ip.org")) {
			xp.setPermBanned("You are banned for addvertising.", "CONSOLE");
			xp.getPlayer().kickPlayer("You are banned for addvertising.");
			return a.toString().toLowerCase().replace("no-ip.org", "*****.***");
		} else if(a.toLowerCase().contains("kicks-ass.net")) {
			xp.setPermBanned("You are banned for addvertising.", "CONSOLE");
			xp.getPlayer().kickPlayer("You are banned for addvertising.");
			return a.toString().toLowerCase().replace("kicks-ass.net", "*********.***");
		}
		String url = "mc\\.([a-z0-9])+\\.(.*?)";
		Pattern compile = Pattern.compile(url.toLowerCase());
		Matcher match = compile.matcher(a.toLowerCase());
		while(match.find()) {
			//log.info(match.group());
			xp.setPermBanned("You are banned for addvertising.", "CONSOLE");
			xp.getPlayer().kickPlayer("You are banned for addvertising.");
			return a.toString().toLowerCase().replace(match.group().toString(), "mc.*****.***");
		}
		String nfoServers = "(.*?)\\.nfoservers\\.com";
		Pattern compileNfo = Pattern.compile(nfoServers.toLowerCase());
		Matcher matchNfo = compileNfo.matcher(a.toLowerCase());
		while(matchNfo.find()) {
			//log.info(match.group());
			xp.setPermBanned("You are banned for addvertising.", "CONSOLE");
			xp.getPlayer().kickPlayer("You are banned for addvertising.");
			return a.toString().toLowerCase().replace(matchNfo.group().toString(), "***.*****.***");
		}
		return a;
	}

	public boolean ipcheck(String a, XOfflinePlayer off) {
		String pattern = "\\d{1,3}(?:\\.\\d{1,3}){3}(?::\\d{1,5})?";

		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(a);
		while (matcher.find()) {
			off.setPermBanned("You are banned for addvertising.", "CONSOLE");
			if(off.getPlayer() instanceof Player) {
				off.getPlayer().kickPlayer("You are banned for addvertising.");	
			}
			return true;
		}
		if(a.toLowerCase().contains("no-ip.org")) {
			off.setPermBanned("You are banned for addvertising.", "CONSOLE");
			if(off.getPlayer() instanceof Player) {
				off.getPlayer().kickPlayer("You are banned for addvertising.");	
			}
			return true;
		} else if(a.toLowerCase().contains("kicks-ass.net")) {
			off.setPermBanned("You are banned for addvertising.", "CONSOLE");
			if(off.getPlayer() instanceof Player) {
				off.getPlayer().kickPlayer("You are banned for addvertising.");	
			}
			return true;
		}
		String url = "mc\\.([a-z0-9])+\\.(.*?)";
		Pattern compile = Pattern.compile(url.toLowerCase());
		Matcher match = compile.matcher(a.toLowerCase());
		while(match.find()) {
			//log.info(match.group());
			off.setPermBanned("You are banned for addvertising.", "CONSOLE");
			if(off.getPlayer() instanceof Player) {
				off.getPlayer().kickPlayer("You are banned for addvertising.");	
			}
			return true;
		}
		String nfoServers = "(.*?)\\.nfoservers\\.com";
		Pattern compileNfo = Pattern.compile(nfoServers.toLowerCase());
		Matcher matchNfo = compileNfo.matcher(a.toLowerCase());
		while(matchNfo.find()) {
			//log.info(match.group());
			off.setPermBanned("You are banned for addvertising.", "CONSOLE");
			if(off.getPlayer() instanceof Player) {
				off.getPlayer().kickPlayer("You are banned for addvertising.");	
			}
			return true;
		}
		return false;
	}

}

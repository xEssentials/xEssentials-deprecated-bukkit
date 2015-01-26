package tv.mineinthebox.essentials.events.chat;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

@SuppressWarnings("deprecation")
public class AntiSwearEvent implements Listener {

	private final HashMap<String, Integer> warnings = new HashMap<String, Integer>(); 
	
	private final xEssentials pl;
	
	public AntiSwearEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}

		boolean warning = false;

		Pattern pat = Pattern.compile("[A-Za-z]");
		Pattern p = Pattern.compile(pl.getConfiguration().getChatConfig().getSwearWords(), Pattern.CASE_INSENSITIVE);

		StringBuilder build = new StringBuilder(e.getMessage());

		Matcher match = p.matcher(e.getMessage());

		while(match.find()) {
			if(pl.getConfiguration().getChatConfig().isSwearWarningEnabled()) {
				if(match.group().length() > 0) {
					if(!warning) { //if true it means we ignore it because a chat could have more swear words than just one we count 1 warning per message.
						warning = true;
						if(warnings.containsKey(e.getPlayer().getName())) {
							int level = warnings.get(e.getPlayer().getName());
							if((level+1) > pl.getConfiguration().getChatConfig().getMaxWarningLevel()) {
								warnings.remove(e.getPlayer().getName());
								if(pl.getConfiguration().getDebugConfig().isEnabled()) {
									xEssentials.log("the console should now sent this command: " + pl.getConfiguration().getChatConfig().getWarningCommand().replaceAll("%p", e.getPlayer().getName()), LogType.DEBUG);
								}
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), pl.getConfiguration().getChatConfig().getWarningCommand().replaceAll("%p", e.getPlayer().getName()));
								e.setCancelled(true);
								return; //no need to go futher within the message.
							} else {
								warnings.put(e.getPlayer().getName(), (level+1));
								e.getPlayer().sendMessage(pl.getConfiguration().getChatConfig().getWarningMessage().replaceAll("%w", (level+1)+"/"+pl.getConfiguration().getChatConfig().getMaxWarningLevel()));
							}
						} else {
							warnings.put(e.getPlayer().getName(), 1);
							e.getPlayer().sendMessage(pl.getConfiguration().getChatConfig().getWarningMessage().replaceAll("%w", "1" + "/"+pl.getConfiguration().getChatConfig().getMaxWarningLevel()));
						}
					}
				}
			}

			build.replace(match.start(), match.end(), pat.matcher(match.group()).replaceAll("*"));
		}
		e.setMessage(build.toString());		
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(warnings.containsKey(e.getPlayer().getName())) {
			warnings.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(warnings.containsKey(e.getPlayer().getName())) {
			warnings.remove(e.getPlayer().getName());
		}
	}

}

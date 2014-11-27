package tv.mineinthebox.bukkit.essentials.events.chat;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;

@SuppressWarnings("deprecation")
public class AntiSwearEvent implements Listener {

	private final HashMap<String, Integer> warnings = new HashMap<String, Integer>(); 

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}

		boolean warning = false;

		Pattern pat = Pattern.compile("[A-Za-z]");
		Pattern p = Pattern.compile(Configuration.getChatConfig().getSwearWords(), Pattern.CASE_INSENSITIVE);

		StringBuilder build = new StringBuilder(e.getMessage());

		Matcher match = p.matcher(e.getMessage());

		while(match.find()) {
			if(Configuration.getChatConfig().isSwearWarningEnabled()) {
				if(match.group().length() > 0) {
					if(!warning) { //if true it means we ignore it because a chat could have more swear words than just one we count 1 warning per message.
						warning = true;
						if(warnings.containsKey(e.getPlayer().getName())) {
							int level = warnings.get(e.getPlayer().getName());
							if((level+1) > Configuration.getChatConfig().getMaxWarningLevel()) {
								warnings.remove(e.getPlayer().getName());
								if(Configuration.getDebugConfig().isEnabled()) {
									xEssentials.getPlugin().log("the console should now sent this command: " + Configuration.getChatConfig().getWarningCommand().replaceAll("%p", e.getPlayer().getName()), LogType.DEBUG);
								}
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Configuration.getChatConfig().getWarningCommand().replaceAll("%p", e.getPlayer().getName()));
								e.setCancelled(true);
								return; //no need to go futher within the message.
							} else {
								warnings.put(e.getPlayer().getName(), (level+1));
								e.getPlayer().sendMessage(Configuration.getChatConfig().getWarningMessage().replaceAll("%w", (level+1)+"/"+Configuration.getChatConfig().getMaxWarningLevel()));
							}
						} else {
							warnings.put(e.getPlayer().getName(), 1);
							e.getPlayer().sendMessage(Configuration.getChatConfig().getWarningMessage().replaceAll("%w", "1" + "/"+Configuration.getChatConfig().getMaxWarningLevel()));
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

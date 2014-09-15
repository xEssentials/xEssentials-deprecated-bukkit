package tv.mineinthebox.bukkit.essentials.events.elevators;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ElevatorCreateEvent implements Listener {

	@EventHandler
	public void elevatorCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[elevator]")) {
			if(e.getLine(1).equalsIgnoreCase("up") || e.getLine(2).equalsIgnoreCase("down")) {
				e.setLine(0, ChatColor.DARK_PURPLE + "[Elevator]");
				e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully created a elevator sign");
			}
		}
	}

}

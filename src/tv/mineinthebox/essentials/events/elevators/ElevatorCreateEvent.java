package tv.mineinthebox.essentials.events.elevators;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class ElevatorCreateEvent extends EventTemplate implements Listener {

	public ElevatorCreateEvent(xEssentials pl) {
		super(pl, "Elevator");
	}

	@EventHandler
	public void elevatorCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[elevator]")) {
			if(e.getLine(1).equalsIgnoreCase("up") || e.getLine(2).equalsIgnoreCase("down")) {
				e.setLine(0, ChatColor.DARK_PURPLE + "[Elevator]");
				sendMessage(e.getPlayer(), ChatColor.GREEN + "you have successfully created a elevator sign");
			}
		}
	}

}

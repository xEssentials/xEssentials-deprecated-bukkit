package tv.mineinthebox.essentials.minigames;

import org.bukkit.command.CommandSender;

public interface MinigameCommandExecutor {
	
	public boolean execute(CommandSender sender, String cmd, String[] args);
	
}

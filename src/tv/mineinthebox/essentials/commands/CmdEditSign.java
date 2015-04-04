package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdEditSign extends CommandTemplate {

	private final xEssentials pl;

	public CmdEditSign(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("editsign")) {
			if(sender instanceof Player) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
				if(xp.isEditSignEnabled()) {
					xp.setEditSign(false);
					sendMessage("you have disabled editsign modus");
				} else {
					xp.setEditSign(true);
					sendMessage("you have enabled editsign modus");
				}
			} else {
				getWarning(WarningType.PLAYER_ONLY);
			}
		}
		return false;
	}

}

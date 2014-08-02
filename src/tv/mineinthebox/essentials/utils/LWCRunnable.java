package tv.mineinthebox.essentials.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public class LWCRunnable extends Thread implements Runnable {
	
	private long timestart;
	private long timeend;
	private CommandSender sender;
	
	public LWCRunnable(CommandSender sender) {
		this.sender = sender;
		Thread thread = new Thread(this);
		thread.start();
	}
		
	@Override
	public void run() {
		try {
			timestart = System.currentTimeMillis();
			Connection con = DriverManager.getConnection("jdbc:sqlite:plugins/LWC/lwc.db");
			PreparedStatement state = con.prepareStatement("SELECT * FROM lwc_protections");
			ResultSet set = state.executeQuery();
			while(set.next()) {
				String owner = set.getString("owner");
				int x = set.getInt("x");
				int y = set.getInt("y");
				int z = set.getInt("z");
				World world = Bukkit.getWorld(set.getString("world"));
				if(world instanceof World) {
					Block block = world.getBlockAt(x, y, z);
					xEssentials.getManagers().getProtectionDBManager().register(owner, block);
				}
			}
			state.close();
			set.close();
			con.close();
			timeend = System.currentTimeMillis();
			if(sender instanceof CommandSender) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "lwc database has successfully been intergrated, into our xEssentials system."));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "thread took currently " + (timeend-timestart) + "ms."));
			}
			xEssentials.getPlugin().log("lwc database has successfully been intergrated, into our xEssentials system.", LogType.INFO);
			xEssentials.getPlugin().log("thread took currently " + (timeend-timestart) + "ms.", LogType.INFO);
			try {
				//graceful shutdown of the thread.
				Thread.sleep(100000);
			} catch (InterruptedException e) {}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

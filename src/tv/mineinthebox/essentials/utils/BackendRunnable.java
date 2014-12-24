package tv.mineinthebox.essentials.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public class BackendRunnable implements Runnable {
	
	private CommandSender sender;
	private long start;
	private long end;
	
	public BackendRunnable(CommandSender sender) {
		this.sender = sender;
	}
	/**
	 * @author xize
	 * @param returns fresh sqlite Connection 
	 * @return Connection
	 */
	private Connection getConnection() {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "databases");
		if(!dir.isDirectory()) {
			dir.mkdir();
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:plugins/xEssentials/databases/players.db");
			return con;
		} catch (Exception e) {
			xEssentials.getPlugin().log("couldn't find sqlite in craftbukkit, this is probably because you are running a outdated build!", LogType.SEVERE);
		}
		return null;
	}

	@Override
	public void run() {
		if(isNewDatabase()) {
			createTables();
		}
		start = System.currentTimeMillis();
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			for(File f : files) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				Iterator<String> it = con.getKeys(true).iterator();
				HashMap<String, Object> config = new HashMap<String, Object>();
				while(it.hasNext()) {
					String key = it.next();
					Object value = con.get(key);
					if(!(value instanceof MemorySection || value instanceof FileConfiguration)) {
						config.put(key, value);	
					}
				}
				try {
					Connection cont = getConnection();
					Statement state = cont.createStatement();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(stream);
					out.writeObject(config);
					out.flush();
					out.close();
					stream.close();
					byte[] data = stream.toByteArray();
					state.execute("INSERT INTO players(user, uuid, map) VALUES('" + con.getString("user") + "','" + f.getName().replace(".yml", "") + "','" + data + "')");
					state.close();
					cont.close();
					f.delete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			dir.delete();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "backend has been updated successfully!"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "workers thread processing time: " + (end-start)));
		}
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {}
	}

	/**
	 * @author xize
	 * @param creates the new tables into the database
	 */
	public void createTables() {
		String table = "CREATE TABLE IF NOT EXISTS `players` ("+ 
				"`id` INT," +
				"`user` TEXT NOT NULL, " +
				"`uuid` TEXT UNIQUE NOT NULL, " +
				"`map` LONGBLOB NOT NULL, " +
				"PRIMARY KEY (`id`) " +
				")";
		try {
			Connection con = getConnection();
			Statement state = con.createStatement();
			state.executeUpdate(table);
			state.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the database does not exists, else false
	 * @return Boolean
	 */
	public boolean isNewDatabase() {
		return !new File(xEssentials.getPlugin().getDataFolder() + File.separator + "databases" + File.separator + "players.db").exists();
	}
}

package tv.mineinthebox.essentials.events.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;

public class SpawnEggLogEvent implements Listener {
	
	private HashMap<String, String> idNames = new HashMap<String, String>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void throwEggLog(PlayerInteractEvent e) {
		if(idNames.isEmpty()) {
			setHashMap();
		}
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG || e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGGS) {
				Calendar cal = Calendar.getInstance();
				cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm] E dd/MM/yy z - HH:mm:ss");
				try {
					FileWriter fw = new FileWriter(xEssentials.getPlugin().getDataFolder() + File.separator + "spawnEgg_activity.log", true);
					w(fw,  sdf.format(cal.getTime()) + " " + e.getPlayer().getName() + " tried to throw " + idNames.get(e.getPlayer().getItemInHand().getTypeId() + ":" + e.getPlayer().getItemInHand().getData().getData()) + " in the world " + e.getPlayer().getWorld().getName());
					fw.close();
				} catch(Exception a) {
					a.printStackTrace();
				}
			}
		}
	}

	private void w(FileWriter writer, String string) throws IOException {
		writer.write(string + "\n");
	}

	public void setHashMap() {
		idNames.put("383:61", "Blaze_Egg");
		idNames.put("383:59", "Cave_Spider_Egg");
		idNames.put("383:50", "Creeper_Egg");
		idNames.put("383:58", "Enderman_Egg");
		idNames.put("383:56", "Ghast_Egg");
		idNames.put("383:62", "Magma_Cube_Egg");
		idNames.put("383:60", "SilverFish_Egg");
		idNames.put("383:51", "Skeleton_Egg");
		idNames.put("383:55", "Slime_Egg");
		idNames.put("383:52", "Spider_Egg");
		idNames.put("383:66", "Witch_Egg");
		idNames.put("383:54", "Zombie_Egg");
		idNames.put("383:57", "Zombie_Pig_Egg");
		idNames.put("383:65", "Bat_Egg");
		idNames.put("383:93", "Chicken_Egg");
		idNames.put("383:92", "Cow_Egg");
		idNames.put("383:100", "Horse_Egg");
		idNames.put("383:96", "Mooshroom_Cow_Egg");
		idNames.put("383:98", "Ocelot_Egg");
		idNames.put("383:90", "Pig_Egg");
		idNames.put("383:91", "Sheep_Egg");
		idNames.put("383:94", "Squid_Egg");
		idNames.put("383:95", "Wolf_Egg");
		idNames.put("383:120", "Villager_Egg");
	}

}

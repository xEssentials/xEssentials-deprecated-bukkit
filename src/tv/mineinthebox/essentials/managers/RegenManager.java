package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.material.MaterialData;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.RegenObject;

public class RegenManager {
	
	public HashSet<RegenObject> getList = new HashSet<RegenObject>();
	
	/**
	 * @author xize
	 * @param saves all the regen schedulers
	 */
	public void saveRegenObjects() {
		for(RegenObject regen : getList) {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "regen" + File.separator + "regen_"+regen.getId()+".dat");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			List<String> serializedLocations = getSerializedLocations(Arrays.asList(regen.getData().keySet().toArray(new Location[regen.getData().size()])));
			List<String> serializedMaterials = getSerializedMaterialData(Arrays.asList(regen.getData().values().toArray(new MaterialData[regen.getData().size()])));
			con.set("locations", serializedLocations);
			con.set("materials", serializedMaterials);
			
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @author xize
	 * @param loads all the regen schedulers!
	 */
	public void loadRegenObjects() {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "regen");
		if(dir.isDirectory()) {
			for(File f : dir.listFiles()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				
				List<Location> locdata = getDeserializedLocations(con.getStringList("locations"));
				List<MaterialData> data = getDeserializedMaterialData(con.getStringList("materials"));
				
				LinkedHashMap<Location, MaterialData> datamap = new LinkedHashMap<Location, MaterialData>();
				
				for(int i = 0; i < locdata.size(); i++) {
					datamap.put(locdata.get(i), data.get(i));
				}
				
				List<Location> locs = new ArrayList<Location>();
				
				for(Location loc : locdata) {
					locs.add(loc);
				}
				
				Collections.sort(locs, new Comparator<Location>() {

					@Override
					public int compare(Location o1, Location o2) {
						return Double.compare(o1.getY(), o2.getY());
					}
					
				});
				
				RegenObject regen = new RegenObject(datamap);
				getList.add(regen);
				
				f.delete();
				
			}
		}
	}
	
	private List<String> getSerializedLocations(List<Location> list) {
		List<String> serialized = new ArrayList<String>();
		for(Location loc : list) {
			String s = loc.getWorld().getName()+":"+loc.getBlockX() +":"+loc.getBlockY()+":"+loc.getBlockZ();
			serialized.add(s);
		}
		return serialized;
	}
	
	private List<Location> getDeserializedLocations(List<String> list) {
		List<Location> deSerialized = new ArrayList<Location>();
		for(String string : list) {
			String[] args = string.split(":");
			World w = Bukkit.getWorld(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			deSerialized.add(new Location(w, x, y, z));
		}
		return deSerialized;
	}
	
	@SuppressWarnings("deprecation")
	private List<String> getSerializedMaterialData(List<MaterialData> list) {
		List<String> serialized = new ArrayList<String>();
		for(MaterialData data : list) {
			String s = data.getItemType().name()+":"+data.getData();
			serialized.add(s);
		}
		return serialized;
	}
	
	@SuppressWarnings("deprecation")
	private List<MaterialData> getDeserializedMaterialData(List<String> list) {
		List<MaterialData> deSerialized = new ArrayList<MaterialData>();
		for(String string : list) {
			String[] args = string.split(":");
			Material mat = Material.getMaterial(args[0]);
			byte sub = Byte.parseByte(args[1]);
			deSerialized.add(new MaterialData(mat, sub));
		}
		return deSerialized;
	}

}

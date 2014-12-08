package tv.mineinthebox.bukkit.essentials.helpers;

import org.bukkit.Material;

public class MaterialHelper {

	@SuppressWarnings("deprecation")
	public static boolean isMaterial(String serialized, String splitter, boolean lores) {
		String[] args = serialized.split(splitter);
		if(lores) {
			if(args.length < 3) { //is Material:lores.
				try {
					Material.valueOf(args[0].toUpperCase().replaceAll(" ", "_"));
					return true;
				} catch(IllegalArgumentException e) {
					return false;
				}
			} else if(args.length >= 3) { //is number:??:lores
				Material mat = Material.getMaterial(Integer.parseInt(args[0]));
				return (mat != null ? true : false);
			}
		} else {
			if(args.length == 1) {
				try {
					Material.valueOf(args[0].toUpperCase().replaceAll(" ", "_"));
					return true;
				} catch(IllegalArgumentException e) {
					return false;
				}
			} else if(args.length == 2) {
				Material mat = Material.getMaterial(Integer.parseInt(args[0]));
				return (mat != null ? true : false);
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static Material getMaterial(String serialized, String splitter, boolean lores) {
		String[] args = serialized.split(splitter);
		if(lores) {
			if(args.length < 3) { //is Material:lores.
				try {
					return Material.valueOf(args[0].toUpperCase().replaceAll(" ", "_"));
				} catch(IllegalArgumentException e) {
					return null;
				}
			} else if(args.length >= 3) { //is number:??:lores
				return Material.getMaterial(Integer.parseInt(args[0]));
			}
		} else {
			if(args.length == 1) {
				try {
					return Material.valueOf(args[0].toUpperCase().replaceAll(" ", "_"));
				} catch(IllegalArgumentException e) {
					return null;
				}
			} else if(args.length == 2) {
				if(args[0].matches("[0-9]+")) {
					return Material.getMaterial(Integer.parseInt(args[0]));
				} else {
					return Material.getMaterial(args[0].toUpperCase().replaceAll(" ", "_"));	
				}
			}
		}
		return null;
	}

	public static short getSubData(String serialized, String splitter, boolean lores) {
		String[] args = serialized.split(splitter);
		if(lores) {
			if(args.length < 3) {
				return Short.parseShort(args[1]);
			} else if(args.length >= 3) {
				return Short.parseShort(args[1]);
			}
		} else {
			if(args.length == 1) {
				return (short)0;
			} else if(args.length == 2) {
				return Short.parseShort(args[1]);
			}
		}
		return (short)0;
	}



}

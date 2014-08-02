package tv.mineinthebox.essentials.instances;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

import tv.mineinthebox.essentials.xEssentials;

public class ShopSign {

	public Chest getChestFromSign(Block sign) {
		//TO-DO adding lwc, and lockette support, so a player cannot put a shop sign on a chest he don't owns.
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(sign.getRelative(face).getType() == Material.CHEST) {
				return (Chest) sign.getRelative(face).getState();
			}
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param returns the buy price of the sign
	 * @return Double
	 */
	public Double getBuyPrice(String s) {
		if(s.contains(" : ")) {
			String[] split = s.split(" : ");
			String buy = split[0];
			return Double.parseDouble(buy.replace("b ", "").replace("B ", ""));
		} else {
			return Double.parseDouble(s.replace("b ", "").replace("B ", ""));
		}
	}
	
	/**
	 * @author xize
	 * @param returns the sell price of the sign
	 * @return Double
	 */
	public Double getSellPrice(String s) {
		if(s.contains(" : ")) {
			String[] split = s.split(" : ");
			String sell = split[1];
			return Double.parseDouble(sell.replace("s ", "").replace("S ", ""));
		} else {
			return Double.parseDouble(s.replace("s ", "").replace("S ", ""));
		}
	}
	
	/**
	 * @author xize
	 * @param returns the Material and sub data value for better serializing
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public String getItemFromSign(String s) {
		if(s.contains(":")) {
			String[] split = s.split(":");
			String data = split[0];
			String subdata = split[1];
			if(isNumber(data)) {
				Material mat = Material.getMaterial(Integer.parseInt(data));
				String matname = (mat.name()+":"+subdata).toLowerCase();
				String output = matname.substring(0, 1).toUpperCase()+matname.substring(1);
				return output;
			} else {
				Material mat = Material.getMaterial(data.toUpperCase());
				String matname = (mat.name()+":"+subdata).toLowerCase();
				String output = matname.substring(0, 1).toUpperCase()+matname.substring(1);
				return output;
			}
		} else {
			if(isNumber(s)) {
				Material mat = Material.getMaterial(Integer.parseInt(s));
				String matname = (mat.name()+":"+0).toLowerCase();
				String output = matname.substring(0, 1).toUpperCase()+matname.substring(1);
				return output;
			} else {
				Material mat = Material.getMaterial(s.toUpperCase());
				String matname = (mat.name()+":"+0).toLowerCase();
				String output = matname.substring(0, 1).toUpperCase()+matname.substring(1);
				return output;
			}
		}
	}

	/**
	 * @author xize
	 * @param returns true whenever the sign is close enough to a chest
	 * @return Boolean
	 */
	public boolean isAttachedOnChest(Block sign) {
		//TO-DO adding lwc, and lockette support, so a player cannot put a shop sign on a chest he don't owns.
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(sign.getRelative(face).getType() == Material.CHEST) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns true if the material line of the sign is a valid Material.
	 * @return Boolean
	 */
	@SuppressWarnings("deprecation")
	public boolean isValidMaterial(String s) {
		if(s.contains(":")) {
			String[] split = s.split(":");
			String data = split[0];
			String subdata = split[1];
			if(isNumber(data)) {
				Material mat = Material.getMaterial(Integer.parseInt(data));
				return (mat != null && isNumber(subdata) && mat != Material.AIR);
			} else {
				Material mat = Material.getMaterial(data.toUpperCase());
				return (mat != null && mat != Material.AIR && isNumber(subdata));
			}
		} else {
			if(isNumber(s)) {
				Material mat = Material.getMaterial(Integer.parseInt(s));
				return (mat != null && mat != Material.AIR);
			} else {
				Material mat = Material.getMaterial(s.toUpperCase());
				return (mat != null && mat != Material.AIR);
			}
		}
	}

	/**
	 * @author xize
	 * @param validate the buy and sell on the shop sign, if invalid it returns false
	 * @return Boolean
	 */
	public boolean validateBuyAndSell(String s) {
		if(s.contains(" : ")) {
			String[] split = s.split(" : ");
			String buy = split[0];
			String sell = split[1];
			if(buy.toLowerCase().startsWith("b ") && sell.toLowerCase().startsWith("s ")) {
				if(isNumber(buy.toLowerCase().replace("b ", "")) && isNumber(sell.toLowerCase().replace("s ", ""))) {
					return true;
				}
			}
		} else {
			if(s.toLowerCase().startsWith("b ") || s.toLowerCase().startsWith("s ")) {
				if(isNumber(s.toLowerCase().replace("b ", ""))) {
					return true;
				} else if(isNumber(s.toLowerCase().replace("s ", ""))) {
					return true;	
				}
			}
		}
		return false;
	}

	/**
	 * @author xize
	 * @param convert the string into a number
	 * @return Integer
	 */
	public int getNumberFromString(String s) {
		try {
			Integer i = Integer.parseInt(s);
			if(i != null) {
				return i;
			}
		} catch(NumberFormatException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * @author xize
	 * @param returns true whenever the line number seems to be a number
	 * @return Boolean
	 */
	public boolean isNumber(String s) {
		try {
			Integer i = Integer.parseInt(s);
			if(i != null) {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param loc - Location of the sign
	 * @return Boolean
	 */
	public boolean isStoredShopSign(Location loc) {
		for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.containsShopSign(loc)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param loc - Location
	 * @return String
	 */
	public String getCompatUserName(Location loc) {
		for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.containsShopSign(loc)) {
				return off.getUser();
			}
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param loc - Location
	 * @param signline - the username based on the sign
	 * @return Boolean
	 */
	public boolean isUserChanged(Location loc, String signline) {
		for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.containsShopSign(loc)) {
				if(!off.getUser().equalsIgnoreCase(signline)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param block - the block around a sign
	 * @param returns true when a sign is found else false
	 * @return Boolean
	 */
	public boolean hasSignAttached(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getState() instanceof Sign) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param block - the block around a sign
	 * @param returns the sign
	 * @return Sign
	 */
	public Sign getSignFromAttachedBlock(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getState() instanceof Sign) {
				return (Sign)block.getRelative(face).getState();
			}
		}
		return null;
	}

}

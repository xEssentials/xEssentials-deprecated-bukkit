package tv.mineinthebox.bukkit.essentials.instances;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.bukkit.essentials.helpers.MaterialHelper;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;

public class Shop {
	
	private final Chest chest;
	private final Sign sign;
	private final XOfflinePlayer owner;
	
	public Shop(Chest chest, Sign sign, XOfflinePlayer off) {
		this.chest = chest;
		this.sign = sign;
		this.owner = off;
		if(!this.sign.getLine(0).equalsIgnoreCase(off.getUser())) {
			this.sign.setLine(0, off.getUser());
			this.sign.update();
		}
	}
	
	public Shop(Sign sign) {
		this.sign = sign;
		this.chest = null;
		this.owner = null;
	}
	
	public Chest getShopChest() {
		return chest;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public XOfflinePlayer getOwner() {
		return owner;
	}
	
	public int getAmount() {
		return Integer.parseInt(sign.getLine(1));
	}
	
	public boolean hasBuyPrice() {
		Pattern pat = Pattern.compile("b ([0-9]+)", Pattern.CASE_INSENSITIVE);
		Matcher match = pat.matcher(sign.getLine(2));
		while(match.find()) {
			return true;
		}
		return false;
	}
	
	public double getBuyPrice() {
		Pattern pat = Pattern.compile("b ([0-9]+)", Pattern.CASE_INSENSITIVE);
		Matcher match = pat.matcher(sign.getLine(2));
		while(match.find()) {
			return Double.parseDouble(match.group().replace("b ", "").replace("B ", ""));
		}
		return 0;
	}
	
	public boolean hasSellPrice() {
		Pattern pat = Pattern.compile("s ([0-9]+)", Pattern.CASE_INSENSITIVE);
		Matcher match = pat.matcher(sign.getLine(2));
		while(match.find()) {
			return true;
		}
		return false;
	}
	
	public double getSellPrice() {
		Pattern pat = Pattern.compile("s ([0-9]+)", Pattern.CASE_INSENSITIVE);
		Matcher match = pat.matcher(sign.getLine(2));
		while(match.find()) {
			return Double.parseDouble(match.group().replace("s ", "").replace("S ", ""));
		}
		return 0;
	}
	
	public boolean isSoldOut() {
		Material mat = MaterialHelper.getMaterial(sign.getLine(3), ":", false);
		short sub = MaterialHelper.getSubData(sign.getLine(3), ":", false);
		
		ItemStack item = new ItemStack(mat, getAmount(), sub);
		if(!chest.getInventory().containsAtLeast(item, getAmount())) {
			return true;
		}
		
		return false;
	}
	
	public ItemStack getItem() {
		return new ItemStack(MaterialHelper.getMaterial(sign.getLine(3), ":", false), getAmount(), MaterialHelper.getSubData(sign.getLine(3), ":", false));
	}

}

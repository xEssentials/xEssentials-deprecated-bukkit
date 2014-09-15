package tv.mineinthebox.bukkit.essentials.managers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.Bank;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class EconomyManager {

	/**
	 * @author xize
	 * @param player - the players name, can also be a offline players name
	 * @return Double
	 */
	public Double getMoney(String player) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(player)) {
			if(xEssentials.getManagers().getPlayerManager().isOnline(player)) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				return xp.getTotalEssentialsMoney();
			} else {
				xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				return off.getTotalEssentialsMoney();
			}
		}
		throw new NullPointerException("this player has never played before!");
	}
	
	/**
	 * @author xize
	 * @param player - the player name, can be a offline player to
	 * @param money - money, which get removed from the bank!
	 */
	public void withdrawMoney(String player, Double money) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(player)) {
			if(xEssentials.getManagers().getPlayerManager().isOnline(player)) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				Double moneyz = xp.getTotalEssentialsMoney();
				if(money > moneyz) {
					throw new IndexOutOfBoundsException("cannot withdraw money below zero");
				}
				xp.clearMoney();
				xp.addEssentialsMoney((moneyz-money));
			} else {
				xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				Double moneyz = off.getTotalEssentialsMoney();
				if(money > moneyz) {
					throw new IndexOutOfBoundsException("cannot withdraw money below zero");
				}
				off.clearMoney();
				off.addEssentialsMoney((moneyz-money));
			}
		} else {
			throw new NullPointerException("this player has never played before!");
		}
	}
	
	/**
	 * @author xize
	 * @param player - the player name, can be a offline player to
	 * @param money - money, which get added to the bank
	 */
	public void depositMoney(String player, Double money) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(player)) {
			if(xEssentials.getManagers().getPlayerManager().isOnline(player)) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				Double moneyz = xp.getTotalEssentialsMoney();
				xp.clearMoney();
				xp.addEssentialsMoney(moneyz+money);
			} else {
				xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				Double moneyz = off.getTotalEssentialsMoney();
				off.clearMoney();
				off.addEssentialsMoney(moneyz+money);
			}
		} else {
			throw new NullPointerException("this player has never played before!");
		}
	}
	
	/**
	 * @author xize
	 * @param bank - the bank account
	 * @param p - the player
	 */
	public void createBank(String bank, String playername) {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "banks" + File.separator + bank.toLowerCase() + ".yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				con.set("bank.owner", playername);
				con.set("bank.name", bank);
				con.set("bank.amount", 0.0);
				con.save(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param returns true whenever the bank account is found!
	 * @param name - the banks name
	 * @return Boolean
	 */
	public boolean isBank(String name) {
		File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "banks" + File.separator + name.toLowerCase() + ".yml");
		return f.exists();
	}

	/**
	 * @author xize
	 * @param returns the Bank object.
	 * @param name - the banks account
	 * @return Bank
	 * @throws NullPointerException - when the name doesn't exist
	 */
	public Bank getBank(String name) {
		if(isBank(name)) {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "banks" + File.separator + name.toLowerCase()+".yml");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			Bank bank = new Bank(f, con);
			return bank;
		}
		throw new NullPointerException("bank does not exist with that name!");
	}

	/**
	 * @author xize
	 * @param bank - the bank name
	 */
	public void deleteBank(String bank) {
		File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "banks" + File.separator + bank.toLowerCase()+".yml");
		if(f.exists()) {
			f.delete();
		}
	}

}

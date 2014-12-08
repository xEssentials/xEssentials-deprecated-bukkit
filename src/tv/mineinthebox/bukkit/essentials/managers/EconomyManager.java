package tv.mineinthebox.bukkit.essentials.managers;

import tv.mineinthebox.bukkit.essentials.xEssentials;
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
	
	public boolean hasEnough(String name, double price) {
		double money = getMoney(name);
		if(money >= price) {
			return true;
		}
		return false;
	}

}

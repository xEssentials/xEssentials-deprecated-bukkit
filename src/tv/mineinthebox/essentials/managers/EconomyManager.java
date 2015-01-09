package tv.mineinthebox.essentials.managers;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class EconomyManager {

	/**
	 * @author xize
	 * @param player - the players name, can also be a offline players name
	 * @return Double
	 */
	public Double getMoney(String player) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(player)) {
			if(xEssentials.getManagers().getPlayerManager().isOnline(player)) {
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				return xp.getMoney();
			} else {
				XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				return off.getMoney();
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
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				Double moneyz = xp.getMoney();
				if(money > moneyz) {
					throw new IndexOutOfBoundsException("cannot withdraw money below zero");
				}
				xp.clearMoney();
				xp.depositMoney((moneyz-money));
			} else {
				XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				Double moneyz = off.getMoney();
				if(money > moneyz) {
					throw new IndexOutOfBoundsException("cannot withdraw money below zero");
				}
				off.clearMoney();
				off.depositMoney((moneyz-money));
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
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(player);
				Double moneyz = xp.getMoney();
				xp.clearMoney();
				xp.depositMoney(moneyz+money);
			} else {
				XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
				Double moneyz = off.getMoney();
				off.clearMoney();
				off.depositMoney(moneyz+money);
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

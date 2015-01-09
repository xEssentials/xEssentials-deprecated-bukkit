package tv.mineinthebox.essentials.instances;

import java.text.DecimalFormat;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class VaultEcoHandler implements Economy, Listener {

	 private final String name = "xEssentials-Eco";
	 
	 
	 public VaultEcoHandler(Plugin pl) {
		 xEssentials.getPlugin().log("Vault has been hooked to xEssentials to support our economy system", LogType.INFO);
	 }


	@Override
	public boolean isEnabled() {
		return Configuration.getEconomyConfig().isEconomyEnabled();
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public boolean hasBankSupport() {
		return false;
	}


	@Override
	public int fractionalDigits() {
		return -1;
	}


	@Override
	public String format(double amount) {
		 DecimalFormat format = new DecimalFormat(("#,##0.00"));
		 String formatted = format.format(amount);
		 if(formatted.endsWith(".")) {
		 formatted = formatted.substring(0, formatted.length() - 1);
		 }
		 return formatted;
	}


	@Override
	public String currencyNamePlural() {
		return Configuration.getEconomyConfig().getCurency();
	}


	@Override
	public String currencyNameSingular() {
		return Configuration.getEconomyConfig().getCurency();
	}


	@Override
	public boolean hasAccount(String playerName) {
		return xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName);
	}


	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName);
	}


	@Override
	public double getBalance(String playerName) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			return off.getMoney();
		}
		throw new IllegalArgumentException("cannot get balance of player whilst player does not exist!");
	}


	@Override
	public double getBalance(String playerName, String world) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			return off.getMoney();
		}
		throw new IllegalArgumentException("cannot get balance of player whilst player does not exist!");
	}


	@Override
	public boolean has(String playerName, double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			return off.hasEnoughMoney(amount);
		}
		return false;
	}


	@Override
	public boolean has(String playerName, String worldName, double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			return off.hasEnoughMoney(amount);
		}
		return false;
	}


	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, null);	
			} else {
				return new EconomyResponse(amount, off.getMoney(), ResponseType.FAILURE, "not enough money to withdraw");
			}
		}
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "cannot withdraw money from a account what does not exist!");
	}


	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName,
			double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, null);	
			} else {
				return new EconomyResponse(amount, off.getMoney(), ResponseType.FAILURE, "not enough money to withdraw");
			}
		}
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "cannot withdraw money from a account what does not exist!");
	}


	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			off.depositMoney(amount);
			return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, null);
		}
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "cannot deposit money from a account what does not exist!");
	}


	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName,
			double amount) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
			off.depositMoney(amount);
			return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, null);
		}
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "cannot deposit money from a account what does not exist!");
	}


	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0,0,ResponseType.FAILURE, "banks are unsupported!");
	}


	@Override
	public List<String> getBanks() {
		throw new IllegalArgumentException("banks are not supported!");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String playerName) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer("town-"+playerName)) {
			return false;
		}
		OfflinePlayer off = Bukkit.getOfflinePlayer(playerName);
		XOfflinePlayer a = new xEssentialsOfflinePlayer(off);
		return (a instanceof XOfflinePlayer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer("town-"+playerName)) {
			return false;
		}
		OfflinePlayer off = Bukkit.getOfflinePlayer(playerName);
		XOfflinePlayer a = new xEssentialsOfflinePlayer(off);
		return (a instanceof XOfflinePlayer);
	}
}
package tv.mineinthebox.essentials.instances;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.managers.xEssentialsPlayerManager;

public class VaultEcoHandler implements Economy {

	private final String name = "xEssentials-eco";
	private final xEssentialsPlayerManager manager;
	
	private final xEssentials pl;

	public VaultEcoHandler(xEssentials pl) {
		this.pl = pl;
		this.manager = pl.getManagers().getPlayerManager();
		xEssentials.log(String.format("[%s][Economy] %s hooked.", name), LogType.INFO);
	}

	@Override
	public boolean isEnabled() {
		if(pl != null) {
			return pl.isEnabled();
		} else {
			return false;
		}
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
		DecimalFormat format = new DecimalFormat("##.##");
		return format.format(amount);
	}

	@Override
	public String currencyNamePlural() {
		return pl.getConfiguration().getEconomyConfig().getCurency();
	}

	@Override
	public String currencyNameSingular() {
		return pl.getConfiguration().getEconomyConfig().getCurency();
	}

	@Override
	public boolean hasAccount(String playerName) {
		return manager.isEssentialsPlayer(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return manager.isEssentialsPlayer(player.getName());
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return manager.isEssentialsPlayer(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return manager.isEssentialsPlayer(player.getName());
	}

	@Override
	public double getBalance(String playerName) {
		if(manager.isEssentialsPlayer(playerName)) {
			return manager.getOfflinePlayer(playerName).getMoney();
		}
		return 0.0;
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		if(manager.isEssentialsPlayer(player.getName())) {
			return manager.getOfflinePlayer(player.getName()).getMoney();
		}
		return 0.0;
	}

	@Override
	public double getBalance(String playerName, String world) {
		if(manager.isEssentialsPlayer(playerName)) {
			return manager.getOfflinePlayer(playerName).getMoney();
		}
		return 0.0;
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		if(manager.isEssentialsPlayer(player.getName())) {
			return manager.getOfflinePlayer(player.getName()).getMoney();
		}
		return 0.0;
	}

	@Override
	public boolean has(String playerName, double amount) {
		if(amount >= 0) {
			if(manager.isEssentialsPlayer(playerName)) {
				return manager.getOfflinePlayer(playerName).hasEnoughMoney(amount);
			}
		}
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		if(amount >= 0) {
			if(manager.isEssentialsPlayer(player.getName())) {
				return manager.getOfflinePlayer(player.getName()).hasEnoughMoney(amount);
			}
		}
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		if(amount >= 0) {
			if(manager.isEssentialsPlayer(playerName)) {
				return manager.getOfflinePlayer(playerName).hasEnoughMoney(amount);
			}
		}
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		if(amount >= 0) {
			if(manager.isEssentialsPlayer(player.getName())) {
				return manager.getOfflinePlayer(player.getName()).hasEnoughMoney(amount);
			}
		}
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		if(manager.isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = manager.getOfflinePlayer(playerName);
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
			} else {
				return new EconomyResponse(0, 0, ResponseType.FAILURE, "insuffient funds");
			}
			
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		if(manager.isEssentialsPlayer(player.getName())) {
			XOfflinePlayer off = manager.getOfflinePlayer(player.getName());
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
			} else {
				return new EconomyResponse(0, 0, ResponseType.FAILURE, "insuffient funds");
			}
			
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName,
			double amount) {
		if(manager.isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = manager.getOfflinePlayer(playerName);
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
			} else {
				return new EconomyResponse(0, 0, ResponseType.FAILURE, "insuffient funds");
			}
			
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player,
			String worldName, double amount) {
		if(manager.isEssentialsPlayer(player.getName())) {
			XOfflinePlayer off = manager.getOfflinePlayer(player.getName());
			if(off.hasEnoughMoney(amount)) {
				off.withdrawMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
			} else {
				return new EconomyResponse(0, 0, ResponseType.FAILURE, "insuffient funds");
			}
			
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		if(manager.isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = manager.getOfflinePlayer(playerName);
				off.depositMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		if(manager.isEssentialsPlayer(player.getName())) {
			XOfflinePlayer off = manager.getOfflinePlayer(player.getName());
				off.depositMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName,
			double amount) {
		if(manager.isEssentialsPlayer(playerName)) {
			XOfflinePlayer off = manager.getOfflinePlayer(playerName);
				off.depositMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player,
			String worldName, double amount) {
		if(manager.isEssentialsPlayer(player.getName())) {
			XOfflinePlayer off = manager.getOfflinePlayer(player.getName());
				off.depositMoney(amount);	
				return new EconomyResponse(amount, off.getMoney(), ResponseType.SUCCESS, "");
		}
		return new EconomyResponse(0,0,ResponseType.FAILURE, "account does not exist");
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.FAILURE, "banks are unsupported");
	}

	@Override
	public List<String> getBanks() {
		return new ArrayList<String>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String playerName) {
		if(manager.isEssentialsPlayer("town-"+playerName)) {
			return false;
		} else {
			XOfflinePlayer off = new xEssentialsOfflinePlayer(Bukkit.getOfflinePlayer(playerName), pl);
			return (off != null);
		}
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		if(manager.isEssentialsPlayer("town-"+player.getName())) {
			return false;
		} else {
			XOfflinePlayer off = new xEssentialsOfflinePlayer(player, pl);
			return (off != null);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		if(manager.isEssentialsPlayer("town-"+playerName)) {
			return false;
		} else {
			XOfflinePlayer off = new xEssentialsOfflinePlayer(Bukkit.getOfflinePlayer(playerName), pl);
			return (off != null);
		}
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		if(manager.isEssentialsPlayer("town-"+player.getName())) {
			return false;
		} else {
			XOfflinePlayer off = new xEssentialsOfflinePlayer(player, pl);
			return (off != null);
		}
	}
}
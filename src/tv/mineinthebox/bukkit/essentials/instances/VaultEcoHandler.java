package tv.mineinthebox.bukkit.essentials.instances;

import java.text.DecimalFormat;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;

public class VaultEcoHandler implements Economy, Listener {

	 private final String name = "xEssentials-Eco";
	 private Plugin pl;
	 
	 
	 public VaultEcoHandler(Plugin pl) {
		 this.pl = pl;
		 xEssentials.getPlugin().log("Vault has been hooked to xEssentials to support our economy system", LogType.INFO);
	 }


	    @Override
	    public boolean isEnabled() {
	        if (pl == null) {
	            return false;
	        } else {
	            return pl.isEnabled();
	        }
	    }


	    @Override
	    public String getName() {
	        return name;
	    }


	    @Override
	    public String format(double amount) {
	    	xEssentials.getPlugin().log("line 46 has been activated, it may means that the formatting is broken!", LogType.INFO);
	    	DecimalFormat format = new DecimalFormat(("#,##0.00"));
	       String formatted = format.format(amount);
	       if(formatted.endsWith(".")) {
	    	   formatted = formatted.substring(0, formatted.length() - 1);
	       }
	       return formatted;
	    }


	    @Override
	    public String currencyNameSingular() {
	        return Configuration.getEconomyConfig().getCurency();
	    }


	    @Override
	    public String currencyNamePlural() {
	    	return Configuration.getEconomyConfig().getCurency();
	    }


	    @Override
	    public double getBalance(String playerName) {
	        if (xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
	            if(xEssentials.getManagers().getPlayerManager().isOnline(playerName)) {
	            	return xEssentials.getManagers().getPlayerManager().getPlayer(playerName).getTotalEssentialsMoney();
	            } else {
	            	return xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName).getTotalEssentialsMoney();
	            }
	        } else {
	            return 0;
	        }
	    }


	    @Override
	    public EconomyResponse withdrawPlayer(String playerName, double amount) {
	        if (amount < 0) {
	            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
	        }
	        if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
	        	if(xEssentials.getManagers().getPlayerManager().isOnline(playerName)) {
	        		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(playerName);
	        		if(xp.hasPlayerEnoughMoneyFromPrice(amount)) {
	        			xp.payEssentialsMoney(amount);
	        			  return new EconomyResponse(amount, xp.getTotalEssentialsMoney(), ResponseType.SUCCESS, null);
	        		} else {
	        			 return new EconomyResponse(0, xp.getTotalEssentialsMoney(), ResponseType.FAILURE, "Insufficient funds");
	        		}
	        	} else {
	        		xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
	        		if(off.hasPlayerEnoughMoneyFromPrice(amount)) {
	        			off.payEssentialsMoney(amount);
	        			 return new EconomyResponse(amount, off.getTotalEssentialsMoney(), ResponseType.SUCCESS, null);
	        		} else {
	        			 return new EconomyResponse(0, off.getTotalEssentialsMoney(), ResponseType.FAILURE, "Insufficient funds");
	        		}
	        	}
	        } else {
	        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "account doesn't exist");
	        }
	    }


	    @Override
	    public EconomyResponse depositPlayer(String playerName, double amount) {
	    	if (amount <= 0) {
	            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot desposit negative funds");
	        }
	        
	        if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName)) {
	        	if(xEssentials.getManagers().getPlayerManager().isOnline(playerName)) {
	        		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(playerName);
	        		xp.addEssentialsMoney(amount);
	        		return new EconomyResponse(amount, xp.getTotalEssentialsMoney(), ResponseType.SUCCESS, null);
	        	} else {
	        		xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
	        		off.addEssentialsMoney(amount);
	        		return new EconomyResponse(amount, off.getTotalEssentialsMoney(), ResponseType.SUCCESS, null);
	        	}
	        } else {
	        	return new EconomyResponse(0,0, ResponseType.FAILURE, "Cannot give money to a player which doesn't exist!");
	        }
	    }


	    @Override
	    public boolean has(String playerName, double amount) {
	        return getBalance(playerName) >= amount;
	    }


	    @Override
	    public EconomyResponse createBank(String bank, String player) {
	    	 throw new UnsupportedOperationException("xEssentials economy does not support banks");
	    }


	    @Override
	    public EconomyResponse deleteBank(String bank) {
	        throw new UnsupportedOperationException("xEssentials economy does not support banks");
	    }


	    @Override
	    public EconomyResponse bankHas(String bank, double amount) {
	    	throw new UnsupportedOperationException("xEssentials economy does not support banks");
	    }


	    @Override
	    public EconomyResponse bankWithdraw(String name, double amount) {
	        if (amount < 0) {
	            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
	        }


	        return withdrawPlayer(name, amount);
	    }


	    @Override
	    public EconomyResponse bankDeposit(String name, double amount) {
	        if (amount < 0) {
	            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot desposit negative funds");
	        }
	        return depositPlayer(name, amount);
	    }


	    @Override
	    public EconomyResponse isBankOwner(String name, String playerName) {
	        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "xEssentials does not support Bank owners.");
	    }


	    @Override
	    public EconomyResponse isBankMember(String name, String playerName) {
	        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "xEssentials does not support Bank members.");
	    }


	    @Override
	    public EconomyResponse bankBalance(String bank) {
	    	throw new UnsupportedOperationException("xEssentials economy does not support banks");
	    }


	    @Override
	    public List<String> getBanks() {
	        throw new UnsupportedOperationException("xEssentials does not support listing of bank accounts");
	    }


	    @Override
	    public boolean hasBankSupport() {
	        return true;
	    }


	    @Override
	    public boolean hasAccount(String playerName) {
	        return xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(playerName);
	    }


		@Override
	    public boolean createPlayerAccount(String playerName) {
	        xEssentials.getPlugin().log("test: 248", LogType.INFO);
	    	if (hasAccount(playerName)) {
	            return false;
	        }
	        //creates a fake player.
	    	xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(playerName);
	    	
	        return (off instanceof xEssentialsOfflinePlayer);
	    }


		@Override
		public int fractionalDigits() {
			return -1;
		}


	    @Override
	    public boolean hasAccount(String playerName, String worldName) {
	        return hasAccount(playerName);
	    }


	    @Override
	    public double getBalance(String playerName, String world) {
	    	return getBalance(playerName);
	    }


	    @Override
	    public boolean has(String playerName, String worldName, double amount) {
	        return has(playerName, amount);
	    }


	    @Override
	    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
	        return withdrawPlayer(playerName, amount);
	    }


	    @Override
	    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
	    	return depositPlayer(playerName, amount);
	    }


	    @Override
	    public boolean createPlayerAccount(String playerName, String worldName) {
	        return true;
	    }
}

package tv.mineinthebox.essentials.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.VaultEcoHandler;

public class VaultHook {

	private Economy econ;
	private Chat chat;
	private Permission perm;

	public VaultHook() {
		RegisteredServiceProvider<Economy> ep = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		RegisteredServiceProvider<Chat> cp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		RegisteredServiceProvider<Permission> pp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);

		if(ep instanceof RegisteredServiceProvider) {
			this.econ = ep.getProvider();
		}
		if(cp instanceof RegisteredServiceProvider) {
			this.chat = cp.getProvider();
		}
		if(pp instanceof RegisteredServiceProvider) {
			this.perm = pp.getProvider();
		}

	}

	/**
	 * @author xize
	 * @param p - player
	 * @param amount - currency
	 * @param gives the player some money!
	 * @return void
	 */
	public void desposit(Player p, Double amount) {
		if(econ instanceof Economy) {
			econ.depositPlayer(p.getName(), amount);
		}
	}

	/**
	 * @author xize
	 * @param p - player name
	 * @param amount - currency
	 * @param gives the player some money!
	 * @return void
	 */
	public void desposit(String p, Double amount) {
		if(econ instanceof Economy) {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy econ = economyProvider.getProvider();
			econ.depositPlayer(p, amount);	
		}
	}

	/**
	 * @author xize
	 * @param p - player name
	 * @param amount - the amount which get removed from the bank account of this player
	 * @param withdraw money of the player
	 * @return void
	 */
	public void withdraw(String p, Double amount) {
		if(econ instanceof Economy) {
			econ.withdrawPlayer(p, amount);	
		}
	}

	/**
	 * @author xize
	 * @param p - player name
	 * @param amount - the amount which get checked if the player has enough
	 * @return Boolean
	 */
	public boolean hasEnough(String p, Double amount) {
		if(econ instanceof Economy) {
			return ((econ.getBalance(p) - amount) > 0.0);
		}
		return false;
	}

	/**
	 * @author xize
	 * @param player - the player who pays
	 * @param to - the player who receives the payment
	 * @param amount - the amount of money
	 */
	public void pay(String player, String to, Double amount) {
		withdraw(player, amount);
		desposit(player, amount);
	}

	/**
	 * @author xize
	 * @param w - World
	 * @param group - the name of the group
	 * @return String
	 */
	public String getSuffix(World w, String group) {
		if(!(chat instanceof Chat)) {
			return "";
		}
		return chat.getGroupSuffix(w, group);
	}

	/**
	 * @author xize
	 * @param p - gets the group by playeer
	 * @return String
	 */
	public String getGroup(Player p) {
		if(!(perm instanceof Permission)) {
			return "";
		}
		return perm.getPrimaryGroup(p);
	}

	/**
	 * @author xize
	 * @param w - the world
	 * @param player - the player name
	 * @return String
	 */
	public String getGroup(World w, String player) {
		if(!(perm instanceof Permission)) {
			return "";
		}
		return perm.getPrimaryGroup(w, player);
	}

	/**
	 * @author xize
	 * @param world - the world the player is in
	 * @param player - the player
	 * @param group - the group
	 */
	public void setGroup(World world, String player, String group) {
		perm.playerRemoveGroup(world, player, group);
		perm.playerAddGroup(world, player, group);
	}

	/**
	 * @author xize
	 * @return
	 */
	public String getDefaultGroup() {
		return chat.getGroups()[0];
	}

	/**
	 * @author xize
	 * @param player - the players name
	 * @param group - the group
	 * @return String
	 */
	public String getSuffix(String player, String group) {
		if(!(chat instanceof Chat)) {
			return "";
		}
		return chat.getGroupSuffix(player, group);
	}

	/**
	 * @author xize
	 * @param this will be used in onEnable!
	 */
	public void hookEconomyInVault() {
		//I give credits to niccholaspage the developer of Fe economy since I didn't discovered the possibility to let my plugin hook in Vault
		//to allow economy support through vault, so this method and the VaultEcoHandler() may be smiliar but are modified.
		if(econ != null) {
			Bukkit.getServer().getServicesManager().unregister(econ);
		}
		Bukkit.getServer().getServicesManager().register(Economy.class, new VaultEcoHandler(xEssentials.getPlugin()), xEssentials.getPlugin(), ServicePriority.Highest);
	}

}

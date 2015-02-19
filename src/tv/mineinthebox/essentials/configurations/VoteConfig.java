package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class VoteConfig extends Configuration {
	
	public VoteConfig(File f, FileConfiguration con) {
		super(f, con);
	}
	
	/**
	 * returns true if vote system is enabled
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isVoteEnabled() {
		return con.getBoolean("vote.enable");
	}
	
	/**
	 * returns a formatted vote personal message a player receives when he voted
	 * 
	 * @author xize
	 * @return String
	 */
	public String getVotePersonalMessage() {
		return con.getString("vote.personal-message");	
	}
	
	/**
	 * returns the vote broadcast when a player has voted
	 * 
	 * @author xize
	 * @return String
	 */
	public String getVoteBroadcastMessage() {
		return con.getString("vote.broadcast");
	}
	
	/**
	 * returns true if money reward is enabled once when a player votes
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isMoneyRewardEnabled() {
		return con.getBoolean("vote.reward-type.money.enable");
	}
	
	/**
	 * returns the money reward, when a player votes what got deposited on his bank account
	 * 
	 * @author xize
	 * @return double
	 */
	public double getMoneyReward() {
		return con.getDouble("vote.reward-type.money.price");
	}
	
	/**
	 * returns true if the reward will be a crate from ManCo
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRewardCrateEnabled() {
		return con.getBoolean("vote.reward-type.crate.enable") && Bukkit.getPluginManager().isPluginEnabled("ManCo");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.VOTE;
	}
	
	@Override
	public boolean isGenerated() {
		return f.exists();
	}
	
	@Override
	public boolean isGeneratedOnce() {
		return true;
	}

	@Override
	public void generateConfig() {
		if(!isGenerated()) {
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			con.set("vote.enable", false);
			con.set("vote.personal-message", "&6[&3VOTE&6]: &3you have been voted to mineinthebox!, because you did enjoy your %reward%!");
			con.set("vote.broadcast", "&6[&3VOTE&6]&3: %player% has voted for mineinthebox and earned %reward%, you can do to by visiting %uri%");
			con.set("vote.reward-type.money.enable", false);
			con.set("vote.reward-type.money.price", 80.0);
			con.set("vote.reward-type.crate.enable", false);
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reload() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}

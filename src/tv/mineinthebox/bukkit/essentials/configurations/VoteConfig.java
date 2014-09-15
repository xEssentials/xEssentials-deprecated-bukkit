package tv.mineinthebox.bukkit.essentials.configurations;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class VoteConfig {
	
	public boolean isVoteEnabled() {
		return (Boolean)Configuration.getConfigValue(ConfigType.VOTE, "enable");
	}
	
	public String getVotePersonalMessage() {
		return (String) Configuration.getConfigValue(ConfigType.VOTE, "personalmsg");			
	}
	
	public String getVoteBroadcastMessage() {
		return (String) Configuration.getConfigValue(ConfigType.VOTE, "broadcast");
	}
	
	public boolean isMoneyRewardEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.VOTE, "moneyrewardenable");
	}
	
	public double getMoneyReward() {
		return (Double) Configuration.getConfigValue(ConfigType.VOTE, "moneyrewardprice");
	}
	
	public boolean isRewardCrateEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.VOTE, "rewardcrateenabled");
	}

}

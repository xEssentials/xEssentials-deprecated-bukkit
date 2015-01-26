package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class VoteConfig {
	
	private final xEssentials pl;
	
	public VoteConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isVoteEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "enable");
	}
	
	public String getVotePersonalMessage() {
		return (String) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "personalmsg");			
	}
	
	public String getVoteBroadcastMessage() {
		return (String) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "broadcast");
	}
	
	public boolean isMoneyRewardEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "moneyrewardenable");
	}
	
	public double getMoneyReward() {
		return (Double) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "moneyrewardprice");
	}
	
	public boolean isRewardCrateEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.VOTE, "rewardcrateenabled");
	}

}

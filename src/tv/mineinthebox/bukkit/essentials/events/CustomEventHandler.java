package tv.mineinthebox.bukkit.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.events.customevents.CallAfkSchedulerEvent;
import tv.mineinthebox.bukkit.essentials.events.customevents.CallChunkMoveEvent;
import tv.mineinthebox.bukkit.essentials.events.customevents.CallPlayerChatSmilleyEvent;
import tv.mineinthebox.bukkit.essentials.events.customevents.CallPlayerHighLightEvent;
import tv.mineinthebox.bukkit.essentials.events.customevents.CallPlayerOpenBookEvent;

public class CustomEventHandler {

	public void startCustomEvents() {
		//this will loadup our custom movement for players
		setListener(new CallChunkMoveEvent());
		setListener(new CallPlayerOpenBookEvent());
		CallAfkSchedulerEvent scheduler = new CallAfkSchedulerEvent();
		setListener(scheduler);
		scheduler.onStartAfkScheduler();
		if(Configuration.getChatConfig().isRssBroadcastEnabled()) {
			xEssentials.getManagers().getRssManager().start();
		}
		if(Configuration.getChatConfig().isMojangStatusEnabled()) {
			xEssentials.getManagers().getMojangStatusManager().start();
		}
		if(Configuration.getChatConfig().isChatHighLightEnabled()) {setListener(new CallPlayerHighLightEvent());}
		if(Configuration.getChatConfig().isSmilleysEnabled()) {setListener(new CallPlayerChatSmilleyEvent());}
		if(Configuration.getBroadcastConfig().isBroadcastEnabled()) {xEssentials.getManagers().getBroadcastManager().start();}
	}

	public void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, xEssentials.getPlugin());
	}

}

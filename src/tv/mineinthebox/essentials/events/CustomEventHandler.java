package tv.mineinthebox.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.CallAfkSchedulerEvent;
import tv.mineinthebox.essentials.events.customevents.CallChunkMoveEvent;
import tv.mineinthebox.essentials.events.customevents.CallPlayerChatSmilleyEvent;
import tv.mineinthebox.essentials.events.customevents.CallPlayerHighLightEvent;
import tv.mineinthebox.essentials.events.customevents.CallPlayerOpenBookEvent;

public class CustomEventHandler {
	
	private final xEssentials pl;
	
	public CustomEventHandler(xEssentials pl) {
		this.pl = pl;
	}

	public void startCustomEvents() {
		//this will loadup our custom movement for players
		setListener(new CallChunkMoveEvent());
		setListener(new CallPlayerOpenBookEvent());
		CallAfkSchedulerEvent scheduler = new CallAfkSchedulerEvent(pl);
		setListener(scheduler);
		scheduler.onStartAfkScheduler();
		if(pl.getConfiguration().getChatConfig().isRssBroadcastEnabled()) {
			pl.getManagers().getRssManager().start();
		}
		if(pl.getConfiguration().getChatConfig().isChatHighLightEnabled()) {setListener(new CallPlayerHighLightEvent(pl));}
		if(pl.getConfiguration().getChatConfig().isSmilleysEnabled()) {setListener(new CallPlayerChatSmilleyEvent(pl));}
		if(pl.getConfiguration().getBroadcastConfig().isBroadcastEnabled()) {pl.getManagers().getBroadcastManager().start();}
	}

	public void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, pl);
	}

}

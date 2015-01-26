package tv.mineinthebox.essentials.events.motd;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;


public class MotdVanishEvent {
	
	private final xEssentials pl;
	private final ListIterator<String> it;
	
	public MotdVanishEvent(xEssentials pl) {
		this.pl = pl;
		this.it = pl.getConfiguration().getMotdConfig().getMotdMessages();
		
	}
	
	//TODO:method was static not sure why.
	public void initPacketListener(){
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(pl, PacketType.Status.Server.OUT_SERVER_INFO) {
					@SuppressWarnings("deprecation")
					@Override
					public void onPacketSending(PacketEvent event) {
						event.getPacket().getServerPings().getValues().get(0).setPlayersOnline(getOnlinePlayers());
						event.getPacket().getServerPings().getValues().get(0).setPlayersMaximum(Bukkit.getMaxPlayers());
						if(pl.getConfiguration().getMotdConfig().isRandomMotdEnabled()) {
							if(it.hasNext()) {
								event.getPacket().getServerPings().getValues().get(0).setMotD(ChatColor.translateAlternateColorCodes('&', it.next()));	
							} else {
								while(it.hasPrevious()) {
									it.previous();
								}
								if(it.hasNext()) {
									event.getPacket().getServerPings().getValues().get(0).setMotD(ChatColor.translateAlternateColorCodes('&', it.next()));	
								}
							}
						} else {
							event.getPacket().getServerPings().getValues().get(0).setMotD(ChatColor.translateAlternateColorCodes('&', pl.getConfiguration().getMotdConfig().getMotdMessage()));	
						}
						List<WrappedGameProfile> players = new ArrayList<WrappedGameProfile>();
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(pl.getManagers().getPlayerManager().isOnline(p.getName())) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
								if(!xp.isVanished()) {
									players.add(new WrappedGameProfile("id"+String.valueOf(players.size()+1), ChatColor.translateAlternateColorCodes('&', p.getName())));
								}
							}
						}
						if (!players.isEmpty()) event.getPacket().getServerPings().getValues().get(0).setPlayers(players);
					}

				});
	}

	private int getOnlinePlayers() {
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(pl.getManagers().getPlayerManager().isOnline(p.getName())) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(!xp.isVanished()) {
					i++;
				}
			}
		}
		return i;
	}
}

package tv.mineinthebox.bukkit.essentials.events.players;

import java.util.Iterator;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class PlayerForceRespawnEvent implements Listener {

	@EventHandler
	public void onrespawn(PlayerDeathEvent e) {
		Iterator<ItemStack> stacks = e.getDrops().iterator();
		while(stacks.hasNext()) {
			ItemStack stack = stacks.next();
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), stack);
			stacks.remove();
		}
		e.getEntity().setMetadata("deathReason", new FixedMetadataValue(xEssentials.getPlugin(), getKiller(e.getDeathMessage())));
		sentPacket(e.getEntity());
	}

	private void sentPacket(Player p) {
		try {
			Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
			Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
			Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
			for(Object ob : enumClass.getEnumConstants()){
				if(ob.toString().equals("PERFORM_RESPAWN")){
					packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
				}
			}
			Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getKiller(String message) {
		String[] args = message.split(" ");
		for(EntityType type : EntityType.values()) {
			for(String arg : args) {
				if(type.name().toLowerCase().startsWith(arg.toLowerCase()) || type.name().equalsIgnoreCase(arg)) {
					return type.name().toLowerCase();
				}
			}
		}
		return "unknown";
	}
}

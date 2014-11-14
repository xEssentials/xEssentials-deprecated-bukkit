package tv.mineinthebox.bukkit.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

//wip.

@SuppressWarnings("unused")
@Deprecated
public class FakeItem {
	
	private final ItemStack stack;
	private final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

	
	public FakeItem(ItemStack stack) {
		this.stack = stack;
	}
	
	public void sentFakeItemDrop(Location loc, int amount) throws Exception {
		
		Class<?> fclazz = Class.forName("net.minecraft.server.v"+version+".EntityItem");
		Class<?> wclazz = Class.forName("org.bukkit.craftbukkit.v"+version+".CraftWorld");
		Class<?> packet = Class.forName("net.minecraft.server.v"+version+".PacketPlayOutSpawnEntity");
		Class<?> craftitemstack = Class.forName("org.bukkit.craftbukkit.v"+version+".CraftItemStack");
		
		Object world = wclazz.cast(loc.getWorld()).getClass().getMethod("getHandle");
		
		Object fakeitem = fclazz.getConstructor(wclazz).newInstance(world);
		
		fakeitem.getClass().getMethod("setLocation", Double.class, Double.class, Double.class, float.class, float.class)
		.invoke(null, loc.getX(), loc.getY(), loc.getZ(), 0,0);
		
		//to-do
		//fakeitem.getClass().getMethod("setItemStack", craftitemstack.getClass().getField("asNMSCopy").s)
		
		
		Object packett = packet.getConstructor(fclazz, int.class).newInstance(fakeitem, amount);
		
		world.getClass().getField("sentPacket").set(null, packett);
	}

}

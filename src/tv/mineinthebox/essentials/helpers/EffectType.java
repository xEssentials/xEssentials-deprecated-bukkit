package tv.mineinthebox.essentials.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public enum EffectType {

	BARRIER,
	BLOCK_CRACK,
	BLOCK_DUST,
	CLOUD,
	CRIT,
	CRIT_MAGIC,
	DRIP_LAVA,
	DRIP_WATER,
	ENCHANTMENT_TABLE,
	EXPLOSION_HUGE,
	EXPLOSION_LARGE,
	EXPLOSION_NORMAL,
	FIREWORKS_SPARK,
	FLAME,
	FOOTSTEP,
	HEART,
	ITEM_CRACK,
	ITEM_TAKE,
	LAVA,
	MOB_APPEARANCE,
	NOTE,
	PORTAL,
	REDSTONE,
	SLIME,
	SMOKE_LARGE,
	SMOKE_NORMAL,
	SNOW_SHOVEL,
	SNOWBALL,
	SPELL,
	SPELL_INSTANT,
	SPELL_MOB,
	SPELL_MOB_AMBIENT,
	SPELL_WITCH,
	SUSPENDED,
	TOWN_AURA,
	VILLAGER_ANGRY,
	VILLAGER_HAPPY,
	WATER_BUBBLE,
	WATER_DROP,
	WATER_SPLASH,
	WATER_WAKE;

	/**
	 * plays a world particle effect clientside only
	 * 
	 * @author xize
	 * @param p - the player
	 * @param type - the effect type
	 * @param loc - the location where the particle will be played
	 * @param xoffset - the x offset
	 * @param yoffset - the y offset
	 * @param zoffset - the z offset
	 * @param particledata - the particle data
	 */
	public static final void playEffect(Player p, EffectType type, Location loc, double xoffset, double yoffset, double zoffset, int particledata, int amount) {
		Object packet = createPacket(type, loc, xoffset, yoffset, zoffset, particledata, amount);
		sendPacket(p, packet);
	}
	
	/**
	 * 
	 * 
	 * @author xize
	 * @param w - the world
	 * @param type - the effect type
	 * @param loc - the location
	 * @param xoffset - the x offset
	 * @param yoffset - the y offset
	 * @param zoffset - the z offset
	 * @param particledata - the particle data
	 */
	public static final void playEffect(World w, EffectType type, Location loc, double xoffset, double yoffset, double zoffset, int particledata, int amount) {
		Object packet = createPacket(type, loc, xoffset, yoffset, zoffset, particledata, amount);
		
		if(packet == null) {
			//packet is invalid perhaps outdated or enums are changed?
			return;
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getWorld().equals(w)) {
				sendPacket(p, packet);
			}
		}
	}

	private final static void sendPacket(Player p, Object packet) {
		try {
			Class<?> clazz = p.getClass(); //aslong this is an interface it returns CraftPlayer in runtime.
			Object entity = clazz.getMethod("getHandle").invoke(p); //this should return the Entity or an extend of Entity
			Object playerconnection = entity.getClass().getField("playerConnection").get(entity);
			playerconnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server."+getVersion()+".Packet")).invoke(playerconnection, packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private final static Object createPacket(EffectType type, Location loc, double xoffset, double yoffset, double zoffset, int particledata, int amount) {
		try {
			Class<?> enumClass = Class.forName("net.minecraft.server."+getVersion()+".EnumParticle");
			Object e = enumClass.getMethod("valueOf", String.class).invoke(null, type.name());

			float x = (float)loc.getX();
			float y = (float)loc.getY();
			float z = (float)loc.getZ();

			Object packet  = Class.forName("net.minecraft.server."+getVersion()+".PacketPlayOutWorldParticles").getConstructor(new Class<?>[] {
					Class.forName("net.minecraft.server."+getVersion()+".EnumParticle"),
					boolean.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					int.class,
					int[].class
			}).newInstance(e, true, x, y, z, (float)xoffset, (float)yoffset, (float)zoffset, (float)particledata, amount, new int[] {0, 0});
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final static String getVersion() {
		String packagen = Bukkit.getServer().getClass().getPackage().getName();
		String lastdot = packagen.substring(packagen.lastIndexOf('.'));
		return lastdot.substring(1, lastdot.length());
	}

}

package tv.mineinthebox.essentials.events.pvp;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public class ClientSideGraveYard implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void getGrave(PlayerDeathEvent e) {
		try {
			for(Entity entity : e.getEntity().getNearbyEntities(16, 120, 16)) {
				if(entity instanceof Player) {
					Player p = (Player) entity;
					Location loc = e.getEntity().getLocation();
					Block block = loc.getBlock();

					Block signBlock = block.getRelative(BlockFace.NORTH).getRelative(BlockFace.EAST);
					p.sendBlockChange(signBlock.getLocation(), Material.SIGN_POST, (byte) 0xD);

					String[] lines = {
							ChatColor.translateAlternateColorCodes('&', "&F&lR.I.P"),
							e.getEntity().getName(),
							"we'all miss-",
							"you."
					};
					//https://forums.bukkit.org/threads/change-the-direction-of-a-sign.31582/ relevant

					p.sendSignChange(signBlock.getLocation(), lines);
					//EntityPlayer player = ((CraftPlayer) p).getHandle();
					//player.playerConnection.sendPacket(new PacketPlayOutUpdateSign(signBlock.getX(), signBlock.getY(), signBlock.getZ(), lines));
					p.sendBlockChange(block.getLocation(), Material.MOSSY_COBBLESTONE, block.getData());
					Block blockSkeleton = block.getRelative(BlockFace.UP);
					p.sendBlockChange(blockSkeleton.getLocation(), Material.SKULL, (byte) 0);
					Block redstone1 = block.getRelative(BlockFace.EAST);
					p.sendBlockChange(redstone1.getLocation(), Material.REDSTONE_WIRE, redstone1.getData());
					Block redstone2 = redstone1.getRelative(BlockFace.EAST);
					p.sendBlockChange(redstone2.getLocation(), Material.REDSTONE_WIRE, redstone2.getData());
					Block redstone3 = redstone2.getRelative(BlockFace.SOUTH);
					p.sendBlockChange(redstone3.getLocation(), Material.REDSTONE_WIRE, redstone3.getData());
					Block fire = redstone3.getRelative(BlockFace.WEST);
					p.sendBlockChange(fire.getLocation(), Material.FIRE, fire.getData());
				}
			}
		} catch(NoClassDefFoundError r) {
			xEssentials.getPlugin().log("ClientSideGraveYards is only supported for Craftbukkit 1.7.2 R1, disabling configuration option...", LogType.SEVERE);
			xEssentials.getPlugin().log("if you want more compatability please install ProtocolLib or update to the correct craftbukkit.", LogType.SEVERE);
			try {
				File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "pvp.yml");
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				con.set("createClientSideGraveyard", false);
				con.save(f);
				Configuration.reloadConfiguration();
			} catch(Exception rr) {
				rr.printStackTrace();
			}
		}
	}

}

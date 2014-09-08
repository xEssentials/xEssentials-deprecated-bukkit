package tv.mineinthebox.essentials.instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class TrollBlock implements Block, Runnable {

	//note to self, the scheduler and the item drops are abstract calls this will make it easier to detect TrollBlocks in the future.

	private static Block block;
	private static BukkitTask task;
	private static ItemStack funDrop;
	private final Random rand = new Random();
	private static Player ignore;
	private final LinkedList<Integer> tasks = new LinkedList<Integer>();

	private int times = 0;

	private int soundTime = 0;

	public TrollBlock(Block b, ItemStack drop, Player ignores) {
		block = b;
		funDrop = drop;
		ignore = ignores;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(block.getType() == Material.AIR) {
			block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
			Block block2 = block.getLocation().getBlock().getRelative(BlockFace.UP).getLocation().getBlock();
			for(int i = 0; i < 16; i++) {
				block.getWorld().spawnEntity(block2.getLocation(), EntityType.BAT);			
			}

			block2.setType(Material.SKULL);
			Skull skull = (Skull)block2.getState();
			skull.setSkullType(SkullType.PLAYER);
			skull.setOwner("IronicTroll");
			block2.getState().update();
			block2.getWorld().dropItemNaturally(block2.getLocation(), funDrop);
			stopTroll();
		} else {
			for(Player p : getNearbyPlayers(15)) {
				if(!isBlockVisible(p)) {
					int n = rand.nextInt(6);
					if(n == 1) {
						paranoidSounds_1(p);
					} else if(n == 2) {
						paranoidSounds_2(p);
					} else if(n == 3) {
						paranoidSounds_3(p);
					} else if(n == 4) {
						paranoidSounds_4(p);
					} else if(n == 5) {
						paranoidSounds_5(p);
					} else if(n == 6) {
						paranoidSounds_6(p);
					}
					break;
				} else {
					if(times == 4) {
						block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
						Block block2 = block.getLocation().getBlock().getRelative(BlockFace.UP).getLocation().getBlock();
						for(int i = 0; i < 43; i++) {
							block.getWorld().spawnEntity(block2.getLocation(), EntityType.BAT);					
						}

						block2.setType(Material.SKULL);
						block2.setData((byte)3);
						Skull skull = (Skull)block2.getState();
						skull.setSkullType(SkullType.PLAYER);
						skull.setOwner("IronicTroll");
						block2.getState().update();
						p.sendBlockChange(block.getLocation(), Material.DIAMOND_BLOCK, (byte)0);
						block2.getWorld().dropItemNaturally(block2.getLocation(), funDrop);
						Bukkit.getScheduler().cancelTask(task.getTaskId());
						stopTroll();
						times = 0;
					} else {
						p.sendMessage(ChatColor.RED + "there are some strange seroundings...");
					}
					this.times++;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean isBlockVisible(Player p) {
		Location direction = p.getTargetBlock(null, 10).getLocation();
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6; x++) {
				for(int z = 0; z < 6; z++) {
					Block block1 = direction.add(x, y, z).getBlock();
					if(block1.equals(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void startTroll() {
		if(task == null) {
			task = Bukkit.getScheduler().runTaskTimer(xEssentials.getPlugin(), this, 0L, 10L);
		}
	}

	public void stopTroll() {
		if(task != null) {
			task.cancel();
			task = null;
			stopSounds();
		}
	}

	public boolean isTrollRunning() {
		return (task instanceof BukkitTask ? true : false);
	}

	private Player[] getNearbyPlayers(int range) {
		List<Player> pp = new ArrayList<Player>();
		for(Entity entity : block.getLocation().getChunk().getEntities()) {
			if(entity instanceof Player) {
				Location loc = block.getLocation();
				if(entity.getLocation().distance(loc) <= range) {
					Player p = (Player) entity;
					if(ignore instanceof Player) {
						if(!ignore.equals(p)) {
							pp.add(p);
						}
					} else {
						if(isTrollRunning()) {
							stopTroll();
						}
					}
				}
			}
		}
		return pp.toArray(new Player[pp.size()]);
	}
	
	private void stopSounds() {
		Iterator<Integer> it = tasks.iterator();
		while(it.hasNext()) {
			int i = it.next();
			if(Bukkit.getScheduler().isCurrentlyRunning(i)) {
				Bukkit.getScheduler().cancelTask(i);
			}
			it.remove();
		}
		tasks.clear();
	}

	private void playSound(final Player p, final Location loc, final Sound sound) {
		soundTime+=10;
		int i = Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {


			@Override
			public void run() {
				p.getWorld().playSound(loc, sound, 1F, 1F);
			}


		}, soundTime);
		
		tasks.add(i);
	}

	private void paranoidSounds_1(Player p) {
		playSound(p, p.getLocation(), Sound.WOLF_DEATH);
	}

	private void paranoidSounds_2(Player p) {
		playSound(p, p.getLocation(), Sound.CREEPER_HISS);
	}

	private void paranoidSounds_3(Player p) {
		playSound(p, p.getLocation(), Sound.GHAST_SCREAM);	
	}
	
	private void paranoidSounds_4(Player p) {
		playSound(p, p.getLocation(), Sound.COW_WALK);	
	}
	
	private void paranoidSounds_5(Player p) {
		playSound(p, p.getLocation(), Sound.FALL_BIG);	
	}
	
	private void paranoidSounds_6(Player p) {
		playSound(p, p.getLocation(), Sound.EXPLODE);	
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		return block.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata(String arg0) {
		return block.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		block.removeMetadata(arg0, arg1);
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		block.setMetadata(arg0, arg1);
	}

	@Override
	public boolean breakNaturally() {
		return block.breakNaturally();
	}

	@Override
	public boolean breakNaturally(ItemStack arg0) {
		return block.breakNaturally(arg0);
	}

	@Override
	public Biome getBiome() {
		return block.getBiome();
	}

	@Override
	public int getBlockPower() {
		return block.getBlockPower();
	}

	@Override
	public int getBlockPower(BlockFace arg0) {
		return block.getBlockPower(arg0);
	}

	@Override
	public Chunk getChunk() {
		return block.getChunk();
	}

	@Override
	@Deprecated
	public byte getData() {
		return block.getData();
	}

	@Override
	public Collection<ItemStack> getDrops() {
		return block.getDrops();
	}

	@Override
	public Collection<ItemStack> getDrops(ItemStack arg0) {
		return block.getDrops(arg0);
	}

	@Override
	public BlockFace getFace(Block arg0) {
		return block.getFace(arg0);
	}

	@Override
	public double getHumidity() {
		return block.getHumidity();
	}

	@Override
	public byte getLightFromBlocks() {
		return block.getLightFromBlocks();
	}

	@Override
	public byte getLightFromSky() {
		return block.getLightFromSky();
	}

	@Override
	public byte getLightLevel() {
		return block.getLightLevel();
	}

	@Override
	public Location getLocation() {
		return block.getLocation();
	}

	@Override
	public Location getLocation(Location arg0) {
		return block.getLocation(arg0);
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		return block.getPistonMoveReaction();
	}

	@Override
	public Block getRelative(BlockFace arg0) {
		return block.getRelative(arg0);
	}

	@Override
	public Block getRelative(BlockFace arg0, int arg1) {
		return block.getRelative(arg0, arg1);
	}

	@Override
	public Block getRelative(int arg0, int arg1, int arg2) {
		return block.getRelative(arg0, arg1, arg2);
	}

	@Override
	public BlockState getState() {
		return block.getState();
	}

	@Override
	public double getTemperature() {
		return block.getTemperature();
	}

	@Override
	public Material getType() {
		return block.getType();
	}

	@Override
	@Deprecated
	public int getTypeId() {
		return block.getTypeId();
	}

	@Override
	public World getWorld() {
		return block.getWorld();
	}

	@Override
	public int getX() {
		return block.getX();
	}

	@Override
	public int getY() {
		return block.getY();
	}

	@Override
	public int getZ() {
		return block.getZ();
	}

	@Override
	public boolean isBlockFaceIndirectlyPowered(BlockFace arg0) {
		return block.isBlockFaceIndirectlyPowered(arg0);
	}

	@Override
	public boolean isBlockFacePowered(BlockFace arg0) {
		return block.isBlockFacePowered(arg0);
	}

	@Override
	public boolean isBlockIndirectlyPowered() {
		return block.isBlockIndirectlyPowered();
	}

	@Override
	public boolean isBlockPowered() {
		return block.isBlockPowered();
	}

	@Override
	public boolean isEmpty() {
		return block.isEmpty();
	}

	@Override
	public boolean isLiquid() {
		return block.isLiquid();
	}

	@Override
	public void setBiome(Biome arg0) {
		block.setBiome(arg0);
	}

	@Override
	@Deprecated
	public void setData(byte arg0) {
		block.setData(arg0);
	}

	@Override
	@Deprecated
	public void setData(byte arg0, boolean arg1) {
		block.setData(arg0, arg1);
	}

	@Override
	public void setType(Material arg0) {
		block.setType(arg0);
	}

	@Override
	@Deprecated
	public boolean setTypeId(int arg0) {
		return block.setTypeId(arg0);
	}

	@Override
	@Deprecated
	public boolean setTypeId(int arg0, boolean arg1) {
		return block.setTypeId(arg0, arg1);
	}

	@Override
	@Deprecated
	public boolean setTypeIdAndData(int arg0, byte arg1, boolean arg2) {
		return block.setTypeIdAndData(arg0, arg1, arg2);
	}
}

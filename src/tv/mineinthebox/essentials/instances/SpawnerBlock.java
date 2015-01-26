package tv.mineinthebox.essentials.instances;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class SpawnerBlock implements Block {
	
	private final Block block;
	private final EntityType type;
	private final xEssentials pl;
	
	private BukkitTask task;
	private long end;
	
	@SuppressWarnings("deprecation")
	public SpawnerBlock(Block block, EntityType type, xEssentials pl) {
		this.block = block;
		this.type = type;
		this.pl = pl;
		Date date = new Date(System.currentTimeMillis());
		date.setSeconds(date.getSeconds()+5);
		this.end = date.getTime();
	}
	
	public void start() {
		if(!(this.task instanceof BukkitTask)) {
			this.task = new BukkitRunnable() {

				@Override
				public void run() {
					if(System.currentTimeMillis() >= SpawnerBlock.this.end) {
						cancel();
						SpawnerBlock.this.task = null;
					}
					SpawnerBlock.this.block.getWorld().spawnEntity(SpawnerBlock.this.block.getRelative(BlockFace.UP).getLocation(), SpawnerBlock.this.type);
				}
				
			}.runTaskTimer(pl, 0L, 1L);
		}
	}
	
	public void stop() {
		if(this.task instanceof BukkitTask) {
			this.task.cancel();
			this.task = null;
		}
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		return this.block.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata(String arg0) {
		return this.block.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		this.block.removeMetadata(arg0, arg1);
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		this.block.setMetadata(arg0, arg1);
	}

	@Override
	public boolean breakNaturally() {
		return this.block.breakNaturally();
	}

	@Override
	public boolean breakNaturally(ItemStack arg0) {
		return this.block.breakNaturally(arg0);
	}

	@Override
	public Biome getBiome() {
		return this.block.getBiome();
	}

	@Override
	public int getBlockPower() {
		return this.block.getBlockPower();
	}

	@Override
	public int getBlockPower(BlockFace arg0) {
		return this.block.getBlockPower(arg0);
	}

	@Override
	public Chunk getChunk() {
		return this.block.getChunk();
	}

	@Override
	@Deprecated
	public byte getData() {
		return this.block.getData();
	}

	@Override
	public Collection<ItemStack> getDrops() {
		return this.block.getDrops();
	}

	@Override
	public Collection<ItemStack> getDrops(ItemStack arg0) {
		return this.block.getDrops(arg0);
	}

	@Override
	public BlockFace getFace(Block arg0) {
		return this.block.getFace(arg0);
	}

	@Override
	public double getHumidity() {
		return this.block.getHumidity();
	}

	@Override
	public byte getLightFromBlocks() {
		return this.block.getLightFromBlocks();
	}

	@Override
	public byte getLightFromSky() {
		return this.block.getLightFromSky();
	}

	@Override
	public byte getLightLevel() {
		return this.block.getLightLevel();
	}

	@Override
	public Location getLocation() {
		return this.block.getLocation();
	}

	@Override
	public Location getLocation(Location arg0) {
		return this.block.getLocation(arg0);
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		return this.block.getPistonMoveReaction();
	}

	@Override
	public Block getRelative(BlockFace arg0) {
		return this.block.getRelative(arg0);
	}

	@Override
	public Block getRelative(BlockFace arg0, int arg1) {
		return this.block.getRelative(arg0, arg1);
	}

	@Override
	public Block getRelative(int arg0, int arg1, int arg2) {
		return this.block.getRelative(arg0, arg1, arg2);
	}

	@Override
	public BlockState getState() {
		return this.block.getState();
	}

	@Override
	public double getTemperature() {
		return this.block.getTemperature();
	}

	@Override
	public Material getType() {
		return this.block.getType();
	}

	@Override
	@Deprecated
	public int getTypeId() {
		return this.block.getTypeId();
	}

	@Override
	public World getWorld() {
		return this.block.getWorld();
	}

	@Override
	public int getX() {
		return this.block.getX();
	}

	@Override
	public int getY() {
		return this.block.getY();
	}

	@Override
	public int getZ() {
		return this.block.getZ();
	}

	@Override
	public boolean isBlockFaceIndirectlyPowered(BlockFace arg0) {
		return this.block.isBlockFaceIndirectlyPowered(arg0);
	}

	@Override
	public boolean isBlockFacePowered(BlockFace arg0) {
		return this.block.isBlockFacePowered(arg0);
	}

	@Override
	public boolean isBlockIndirectlyPowered() {
		return this.block.isBlockIndirectlyPowered();
	}

	@Override
	public boolean isBlockPowered() {
		return this.block.isBlockPowered();
	}

	@Override
	public boolean isEmpty() {
		return this.block.isEmpty();
	}

	@Override
	public boolean isLiquid() {
		return this.block.isLiquid();
	}

	@Override
	public void setBiome(Biome arg0) {
		this.block.setBiome(arg0);
	}

	@Override
	@Deprecated
	public void setData(byte arg0) {
		this.block.setData(arg0);
	}

	@Override
	@Deprecated
	public void setData(byte arg0, boolean arg1) {
		this.block.setData(arg0, arg1);
	}

	@Override
	public void setType(Material arg0) {
		this.block.setType(arg0);
	}

	@Override
	@Deprecated
	public boolean setTypeId(int arg0) {
		return this.block.setTypeId(arg0);
	}

	@Override
	@Deprecated
	public boolean setTypeId(int arg0, boolean arg1) {
		return this.block.setTypeId(arg0, arg1);
	}

	@Override
	@Deprecated
	public boolean setTypeIdAndData(int arg0, byte arg1, boolean arg2) {
		return this.block.setTypeIdAndData(arg0, arg1, arg2);
	}

	@Override
	public void setType(Material arg0, boolean arg1) {
		this.block.setType(arg0, arg1);
	}

}

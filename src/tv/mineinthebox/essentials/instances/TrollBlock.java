package tv.mineinthebox.essentials.instances;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class TrollBlock implements Runnable {

	//note to self, the scheduler and the item drops are abstract calls this will make it easier to detect TrollBlocks in the future.

	private final Block block;
	private final ItemStack funDrop;
	private final Random rand = new Random();
	private final Player ignore;
	private final LinkedList<Integer> tasks = new LinkedList<Integer>();
	private final xEssentials pl;
	
	private BukkitTask task;
	private int times = 0;
	private int soundTime = 0;

	public TrollBlock(Block b, ItemStack drop, Player ignores, xEssentials pl) {
		this.pl = pl;
		this.block = b;
		this.funDrop = drop;
		this.ignore = ignores;
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
		Location direction = p.getTargetBlock((HashSet<Byte>)null, 10).getLocation();
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
			task = Bukkit.getScheduler().runTaskTimer(pl, this, 0L, 10L);
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
		int i = Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {


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
}

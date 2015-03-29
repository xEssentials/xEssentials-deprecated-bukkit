package tv.mineinthebox.essentials.minigames.plugin;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.server.ServerCommandEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.interfaces.xEssentialsAPI;
import tv.mineinthebox.essentials.minigames.plugin.arena.MinigameArena;
import tv.mineinthebox.essentials.minigames.plugin.command.MinigameCommandExecutor;
import tv.mineinthebox.essentials.minigames.plugin.gui.MinigameGui;
import tv.mineinthebox.essentials.minigames.plugin.handler.MinigameHandler;

public abstract class MinigamePlugin implements Listener {

	private final HashMap<String, MinigameArena> arenas = new HashMap<String, MinigameArena>();
	private final Map<String, MinigameCommandExecutor> commands = new HashMap<String, MinigameCommandExecutor>();

	private xEssentials pl;
	private MinigameHandler handler;
	private String name;
	private String[] authors;
	private double version;
	private String description;
	private File datafolder;
	private boolean isEnabled = false;
	private ClassLoader loader;
	private MinigameGui gui;

	private ResourcePack resourcepack;

	/**
	 * the onEnable start method
	 * 
	 * @author xize
	 */
	public abstract void onEnable();

	/**
	 * the onDisable method
	 * 
	 * @author xize
	 */
	public abstract void onDisable();

	/**
	 * returns the minigame Gui
	 * 
	 * @author xize
	 * @return MinigameGui
	 */
	public MinigameGui getMinigameGui() {
		return gui;
	}


	public void setMinigameGui(MinigameGui gui) {
		this.gui = gui;
	}

	public boolean hasMinigameGui() {
		return (gui instanceof MinigameGui);
	}

	/**
	 * returns the name of the minigame
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the authors of this minigmae
	 * 
	 * @author xize
	 * @return String[]
	 */
	public String[] getAuthors() {
		return authors;
	}

	/**
	 * returns the version of the minigame
	 * 
	 * @author xize
	 * @return double
	 */
	public double getVersion() {
		return version;
	}

	/**
	 * returns the description of the game
	 * 
	 * @author xize
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * returns true if the plugin is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * enables or disables the plugin
	 * 
	 * @author xize
	 * @param bol - true if plugin is set to enabled, otherwise false
	 */
	public void setEnabled(boolean bol) {
		this.isEnabled = bol;
	}

	/**
	 * returns the datafolder of where the plugin is made
	 * 
	 * @author xize
	 * @return File
	 */
	public File getDataFolder() {
		if(!datafolder.isDirectory()) {
			datafolder.mkdir();
		}
		return datafolder;
	}

	/**
	 * registers the arena
	 * 
	 * @author xize
	 * @param arena the arena
	 */
	public void registerArena(MinigameArena arena) {
		arenas.put(arena.getName().toLowerCase(), arena);
	}

	/**
	 * unregisters the arena
	 * 
	 * @author xize
	 * @param arena - the arena
	 */
	public void unregisterArena(MinigameArena arena) {
		arenas.remove(arena.getName().toLowerCase());
	}

	/**
	 * tries to get a arena, and returns it if no arena is found null will be returned
	 * 
	 * @author xize
	 * @param name - the arena
	 * @return MinigameArena
	 */
	public MinigameArena getArena(String name) {
		return arenas.get(name.toLowerCase());
	}

	/**
	 * returns true if an arena is found with that name otherwise false
	 * 
	 * @author xize
	 * @param name - the name
	 * @return boolean
	 */
	public boolean isArena(String name) {
		return arenas.containsKey(name.toLowerCase());
	}

	/**
	 * returns all the arenas
	 * 
	 * @author xize
	 * @return MinigameArena[]
	 */
	public MinigameArena[] getArenas() {
		MinigameArena[] a = new MinigameArena[arenas.size()];
		int i = 0;
		for(MinigameArena arena : arenas.values()) {
			a[i] = arena;
			i++;	
		}
		Arrays.sort(a, new Comparator<MinigameArena>() {

			@Override
			public int compare(MinigameArena o1, MinigameArena o2) {
				if(o1.getPlayers().size() > o2.getPlayers().size() && !o1.isFull()) {
					return -1;
				}
				return 0;
			}

		});
		return a;
	}

	/**
	 * returns the minigame handler
	 * 
	 * @author xize
	 * @return MinigameHandler
	 */
	public MinigameHandler getHandlers() {
		return handler;
	}

	/**
	 * returns the xEssentials Api
	 * 
	 * @author xize
	 * @return xEssentialsAPI
	 */
	public xEssentialsAPI getEssentialsApi() {
		return (xEssentialsAPI) pl;
	}

	/**
	 * returns the xEssentials Plugin
	 * 
	 * @author xize
	 * @return xEssentials
	 */
	public xEssentials getEssentialsPlugin() {
		return pl;
	}

	/**
	 * sets a default resource pack for every player joining the arena!
	 * 
	 * @author xize
	 * @param f - the file
	 */
	public void setDefaultResourcePack(File f) {
		this.resourcepack = new ResourcePack(f);
	}

	/**
	 * returns the ResourcePack
	 * 
	 * @author xize
	 * @return ResourcePack
	 */
	public ResourcePack getResourcePack() {
		return resourcepack;
	}

	/**
	 * sents a log message to the console
	 * 
	 * @author xize
	 * @param message - the message
	 * @param type - the log type
	 */
	public void log(String message, GameLog type) {
		xEssentials.log(ChatColor.GREEN + "["+name+"]"+ChatColor.WHITE+message, type.getType());
	}

	/**
	 * gets the class loader
	 * 
	 * @author xize
	 * @return ClassLoader
	 */
	public ClassLoader getClassLoader() {
		return loader;
	}

	/**
	 * registers the listener
	 * 
	 * @author xize
	 * @param listener - the listener to be registered
	 */
	public void registerListener(Listener listener) {
		getHandlers().registerEvent(listener);
	}

	/**
	 * registers a command
	 * 
	 * @author xize
	 * @param command - the command
	 * @param executor - the executor
	 */
	public void setExecutor(String command, MinigameCommandExecutor executor) {
		commands.put(command.toLowerCase(), executor);
	}
	
	
	/**
	 * attemps to stop the whole plugin!
	 * 
	 * @author xize
	 */
	public void stopAll() {
		onDisable();
		getHandlers().stopListeners();
		commands.clear();
		arenas.clear();
		setDefaultResourcePack(null);
		setMinigameGui(null);
		setEnabled(false);
	}

	public enum GameLog {
		WARNING(LogType.MINIGAME_SEVERE),
		INFO(LogType.MINIGAME_INFO);

		private final LogType type;

		private GameLog(LogType type) {
			this.type = type;
		}

		public LogType getType() {
			return type;
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(hasMinigameGui()) {
			if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE + "game selector")) {
				if(e.getCurrentItem() != null) {
					Player p = (Player)e.getWhoClicked();
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());

					if(xp.isInArena()) {
						p.closeInventory();
						p.sendMessage(ChatColor.RED + "you are already joined in a game!");
						e.setCancelled(true);
						return;
					}
					
					String arenaname = e.getCurrentItem().getItemMeta().getLore().get(0).split(": ")[1];
					MinigameArena arena = getArena(arenaname);
					if(arena.isFull()) {
						p.closeInventory();
						p.sendMessage(ChatColor.RED + "this game is full!");
					} else {
						p.closeInventory();
						arena.joinArena(xp);
					}
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryCloseEvent e) {
		if(hasMinigameGui()) {
			if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE + "game selector")) {
				Player p = (Player)e.getPlayer();
				p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1F, 1F);
			}
		}
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String[] array = e.getMessage().split(" ");

		String[] args = new String[array.length-1];

		for(int i = 0; i < args.length; i++) {
			args[i] = array[i+1];
		}

		String cmd = array[0].replaceFirst("/", "");

		if(commands.containsKey(cmd.toLowerCase())) {
			commands.get(cmd.toLowerCase()).execute(e.getPlayer(), cmd, args);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCommand(ServerCommandEvent e) {
		String[] array = e.getCommand().split(" ");

		String[] args = new String[array.length-1];

		for(int i = 0; i < args.length; i++) {
			args[i] = array[i+1];
		}

		String cmd = array[0].replaceFirst("/", "");

		if(commands.containsKey(cmd.toLowerCase())) {
			commands.get(cmd.toLowerCase()).execute(e.getSender(), cmd, args);
			e.setCommand("emptycommand");
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTeleport(PlayerTeleportEvent e) {
		//TODO: rewrite event, so XPlayer will stores its arena as tempory meta.
		if(e.getCause() == TeleportCause.COMMAND) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isInArena()) {
				xp.getPlayer().sendMessage(ChatColor.RED + "you are inside an arena, you cannot teleport when being inside a game!");
				e.setCancelled(true);
			}
		}
	}

}

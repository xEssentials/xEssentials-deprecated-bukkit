package tv.mineinthebox.essentials.minigames;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public abstract class MinigameLoader {

	private final File datafolder;

	private final Set<MinigamePlugin> plugins = new HashSet<MinigamePlugin>();
	private final xEssentials pl;

	public MinigameLoader(xEssentials pl) {
		this.pl = pl;
		this.datafolder = new File(pl.getDataFolder() + File.separator + "minigames");
		if(!datafolder.isDirectory()) {
			datafolder.mkdir();
		}
	}

	/**
	 * loads all minigames
	 * 
	 * @author xize
	 */
	@SuppressWarnings("deprecation")
	public void enablePlugins() {
		File[] files = datafolder.listFiles();
		if(files.length > 0) {
			for(File f : files) {
			if(f.getName().endsWith(".jar")) {
				JarFile jar = null;
				InputStream input = null;
				try {
					jar = new JarFile(f);
					ZipEntry entry = jar.getEntry("module-info.yml");
					if(entry != null) {
						input = jar.getInputStream(entry);
						FileConfiguration con = YamlConfiguration.loadConfiguration(input);
						try {
							String name = con.getString("name");
							Class<?> main = Class.forName(con.getString("main"));
							Class<? extends MinigamePlugin> clazz;
							try {
								clazz = main.asSubclass(MinigamePlugin.class);
								if(!clazz.isAssignableFrom(JavaPlugin.class)) {
									try {
										MinigamePlugin plugin = (MinigamePlugin) clazz.newInstance();
										try {
											String[] authors = con.getString("authors").split(", ");
											double version = con.getDouble("version");
											String description = con.getString("description");

											Field xef = plugin.getClass().getDeclaredField("pl");
											xef.setAccessible(true);
											xef.set(plugin, pl);
											xef.setAccessible(false);
											
											Field handlerf = plugin.getClass().getDeclaredField("handler");
											handlerf.setAccessible(true);
											handlerf.set(plugin, new MinigameHandler(pl));
											handlerf.setAccessible(false);

											Field namef = plugin.getClass().getDeclaredField("name");
											namef.setAccessible(true);
											namef.set(plugin, name);
											namef.setAccessible(false);

											Field authorf = plugin.getClass().getDeclaredField("authors");
											authorf.setAccessible(true);
											authorf.set(plugin, authors);
											authorf.setAccessible(false);

											Field versionf = plugin.getClass().getDeclaredField("version");
											versionf.setAccessible(true);
											versionf.set(plugin, version);
											versionf.setAccessible(false);

											Field descriptionf = plugin.getClass().getDeclaredField("description");
											descriptionf.setAccessible(true);
											descriptionf.set(plugin, description);
											descriptionf.setAccessible(false);

											Field datafolderf = plugin.getClass().getDeclaredField("datafolder");
											datafolderf.setAccessible(true);
											datafolderf.set(plugin, datafolder);
											datafolderf.setAccessible(false);
										} catch(NoSuchFieldException e) {
											e.printStackTrace();
										}
										if(!plugins.contains(plugin)) {
											plugins.add(plugin);
											plugin.setEnabled(true);
											plugin.onEnable();
										} else {
											plugin.setEnabled(false);
											plugin.onDisable();
										}
									} catch (InstantiationException e) {
										xEssentials.log("cannot initalize minigame " + name + " if it has a constructor!", LogType.SEVERE);
									} catch (IllegalAccessException e) {
										xEssentials.log("cannot access main class for plugin " + name + " , probably it holds an sercurity manager?", LogType.SEVERE);
									}
								} else {
									throw new RuntimeException("cannot load a minigame plugin with javaplugin!!!");
								}
							} catch(ClassCastException e) {
								xEssentials.log("plugin " + name +" does not extend MinigamePlugin!", LogType.SEVERE);
							}
						} catch(ClassNotFoundException e) {
							xEssentials.log("could not find main class for plugin name!", LogType.SEVERE);
						}
					} else {
						xEssentials.log("invalid minigame: " + f.getName().replace(".yml", "") + " missing module-info.yml!", LogType.SEVERE);
					}
				} catch(IOException e) {
					e.printStackTrace();
				} finally {
					try {
						input.close();
						jar.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		} else {
			xEssentials.log("no minigame plugins found to be loaded by xEssentials", LogType.INFO);
		}
	}

	/**
	 * unloads all minigames
	 * 
	 * @author xize
	 */
	public void disablePlugins() {
		for(MinigamePlugin plugin : plugins) {
			plugin.setEnabled(false);
			plugin.onDisable();
		}
	}

	/**
	 * attemps to load a minigame
	 * 
	 * @author xize
	 * @param minigame - the attemped minigame to be loaded
	 */
	public void enablePlugin(MinigamePlugin minigame) {
		if(plugins.contains(minigame)) {
			minigame.setEnabled(true);
			minigame.onEnable();
		} else {
			throw new IllegalArgumentException("cannot run a minigame which is unknown");
		}
	}

	/**
	 * attemps to unload a minigame
	 * 
	 * @author xize
	 * @param minigame - the attempted minigame
	 */
	public void disablePlugin(MinigamePlugin minigame) {
		if(plugins.contains(minigame)) {
			minigame.setEnabled(false);
			minigame.onDisable();
		} else {
			throw new IllegalArgumentException("cannot unload a minigame which is unknown");
		}
	}

	/**
	 * attempts to find a plugin with the given name and returns, if there is no such plugin available with this name null will be returned
	 * 
	 * @author xize
	 * @param name - the possible name of the plugin
	 * @return MinigamePlugin
	 */
	public MinigamePlugin getPlugin(String name) {
		for(MinigamePlugin plugin : plugins) {
			if(plugin.getName().equalsIgnoreCase("name")) {
				return plugin;
			}
		}
		return null;
	}

	/**
	 * reload all the plugins
	 * 
	 * @author xize
	 */
	@SuppressWarnings("unused")
	private void reload() {
		Iterator<MinigamePlugin> it = plugins.iterator();
		while(it.hasNext()) {
			MinigamePlugin plugin = it.next();
			plugin.setEnabled(false);
			plugin.onDisable();
			it.remove();
		}
		enablePlugins();
	}

	/**
	 * returns a Set with all minigame plugins!
	 * 
	 * @author xize
	 * @return Set<MinigamePlugin>
	 */
	protected Set<MinigamePlugin> getPlugins() {
		return Collections.unmodifiableSet(plugins);
	}

}

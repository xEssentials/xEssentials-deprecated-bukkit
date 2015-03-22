package tv.mineinthebox.essentials.minigames;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public abstract class MinigameLoader {

	private final xEssentials pl;
	private final File data;

	protected final Set<MinigamePlugin> plugins = new HashSet<MinigamePlugin>();
	
	public MinigameLoader(xEssentials pl) {
		this.pl = pl;
		this.data = new File(pl.getDataFolder() + File.separator + "minigames");
		if(!this.data.isDirectory()) {
			this.data.mkdir();
		}
	}

	/**
	 * enables every plugin
	 * 
	 * @author xize
	 */
	@SuppressWarnings({ "deprecation" })
	public void enablePlugins() {
		File[] files = data.listFiles();
		for(File f : files) {
			if(f.getName().endsWith(".jar")) {
				try {
					JarFile jarfile = new JarFile(f);

					ZipEntry entry = jarfile.getEntry("module-info.yml");

					if(entry != null) {
						InputStream input = jarfile.getInputStream(entry);
						FileConfiguration con = YamlConfiguration.loadConfiguration(input);

						MinigameHandler handler = new MinigameHandler(pl);
						String name = con.getString("name");
						String[] authors = con.getString("authors").split(", ");
						String description = con.getString("description");
						double version = con.getDouble("version");
						String main = con.getString("main");
						input.close();

						try {
							URLClassLoader child = new URLClassLoader(new URL[] {f.toURI().toURL()}, getClass().getClassLoader());
							Class<?> clazz0 = Class.forName(main, true, child);
							
							Object obj = clazz0.newInstance();
							
							if(obj.getClass().getSuperclass() == MinigamePlugin.class) {
								MinigamePlugin game = (MinigamePlugin) obj;
								setField(game, "pl", pl);
								setField(game, "handler", handler);
								setField(game, "name", name);
								setField(game, "authors", authors);
								setField(game, "version", version);
								setField(game, "description", description);
								setField(game, "datafolder", new File(data + File.separator + name));
								setField(game, "isEnabled", true);
								setField(game, "loader", child);
								game.getHandlers().registerEvent(game);
								game.onEnable();
								plugins.add(game);
							} else {
								xEssentials.log("could not load plugin " + name + " main class does not extends MinigamePlugin!", LogType.MINIGAME_SEVERE);
							}
						} catch(ClassNotFoundException e) {
							xEssentials.log("could not load plugin " + name + " main class whas not found!", LogType.MINIGAME_SEVERE);
							e.printStackTrace();
						} catch(NoSuchFieldException e) {
							xEssentials.log("could not load plugin " + name + " an internal issue occuried see: ", LogType.MINIGAME_SEVERE);
							e.printStackTrace();
						} catch(IllegalAccessException e) {
							xEssentials.log("could not load plugin " + name + " is it protected by a sercurity manager?: ", LogType.MINIGAME_SEVERE);
							e.printStackTrace();
						} catch(InstantiationException e) {
							xEssentials.log("could not load plugin " + name + " make sure the main class has no constructors set!: ", LogType.MINIGAME_SEVERE);
							e.printStackTrace();
						}
					} else {
						xEssentials.log("could not load plugin " + f.getName() + " missing module-info.yml!", LogType.MINIGAME_SEVERE);
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * disables all the plugins
	 * 
	 * @author xize
	 */
	public void disablePlugins() {
		Iterator<MinigamePlugin> it = plugins.iterator();
		while(it.hasNext()) {
			MinigamePlugin pl = it.next();
			pl.onDisable();
			pl.getHandlers().stopListeners();
			it.remove();
		}
	}

	private void setField(MinigamePlugin mpl, String field, Object obj) throws NoSuchFieldException, IllegalAccessException {
		Field f1 = mpl.getClass().getSuperclass().getDeclaredField(field);
		f1.setAccessible(true);
		f1.set(mpl, obj);
		f1.setAccessible(false);
	}

}

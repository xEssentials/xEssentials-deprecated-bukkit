package tv.mineinthebox.essentials.events.customEvents;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.instances.RssFeed;

public class CallRssFeedEvent  {

	private volatile RssFeed feed;
	private volatile boolean stop = false;
	
	/**
	 * @author xize
	 * @param starts the scheduler
	 */
	private void onChatSentRssBroadcast() {
		new BukkitRunnable() {
			protected boolean isItemFound = false;

			private URL url;

			private HttpURLConnection httpcon;
			private InputStreamReader input;
			private BufferedReader reader;

			@Override
			public void run() {
				try {
					if(stop) {
						saveLastFeed();
						cancel();
					}
					loadLastFeed();
					String author = null;
					String link = null;
					String title = null;
					this.isItemFound = false;
					this.url = new URL(Configuration.getChatConfig().getRssUrl());
					this.httpcon = (HttpURLConnection) url.openConnection();
					this.httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
					this.httpcon.setUseCaches(false);
					this.input = new InputStreamReader(httpcon.getInputStream());
					this.reader = new BufferedReader(input);
					String line;
					String text = "";
					while((line = reader.readLine()) != null) {
						if(line.contains("<item>")) {
							isItemFound = true;
						}
						if(isItemFound) {
							if(line.contains("<title>")) {
								text = "";
								int firstPos = line.indexOf("<title>");
								String temp = line.substring(firstPos);
								temp = temp.replace("<title>", "");
								int lastPos = temp.indexOf("</title>");
								temp = temp.substring(0,lastPos);
								title = text+= temp;
							}
							if(line.contains("<link>")) {
								text = "";
								int firstPos = line.indexOf("<link>");
								String temp = line.substring(firstPos);
								temp = temp.replace("<link>", "");
								int lastPos = temp.indexOf("</link>");
								temp = temp.substring(0,lastPos);
								link = text+= temp;
							}
							if(line.contains("<author>")) {
								text = "";
								int firstPos = line.indexOf("<author>");
								String temp = line.substring(firstPos);
								temp = temp.replace("<author>", "");
								int lastPos = temp.indexOf("</author>");
								temp = temp.substring(0,lastPos);
								author = text+= temp;
								break;
							}
						}
					}
					RssFeed afeed = new RssFeed(title, author.replaceAll("[^a-zA-Z0-9]", ""), link);
					if(feed != null) {
						if(!feed.getTitle().equalsIgnoreCase(afeed.getTitle())) {
							feed = afeed;
							for(Player p : xEssentials.getOnlinePlayers()) {
								Bukkit.getPluginManager().callEvent(new RssFeedEvent(p, feed));        
							}        
						}
					} else {
						feed = afeed;
						for(Player p : xEssentials.getOnlinePlayers()) {
							Bukkit.getPluginManager().callEvent(new RssFeedEvent(p, feed));        
						}
					}
				} catch(NullPointerException e1) {
					xEssentials.getPlugin().log("couldn't create a stored RssFeed object, probably because you don't have a propper connection at first run.", LogType.SEVERE);
				} catch (MalformedURLException e1) {
					xEssentials.getPlugin().log("the url is wrong for the RSS!", LogType.SEVERE);
				} catch (IOException e1) {
					xEssentials.getPlugin().log("Connection timeout for the RSS event!", LogType.SEVERE);
				} finally {
					try {
						reader.close();
						input.close();
						httpcon.disconnect();
					} catch(Exception e) {}
				}
			}

		}.runTaskTimerAsynchronously(xEssentials.getPlugin(), 100L, 2500L);
	}

	/**
	 * @author xize
	 * @param saves the last possible RssFeed
	 */
	public void saveLastFeed() {
		try {
			if(this.feed instanceof RssFeed) {
				File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "lastRssFeed.yml");
				YamlConfiguration con = YamlConfiguration.loadConfiguration(f);
				con.set("author", feed.getAuthor().replaceAll("[^a-zA-Z0-9]", ""));
				con.set("title", feed.getTitle());
				con.set("link", feed.getLink());
				con.save(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param loads the last possible RssFeed
	 */
	public void loadLastFeed() {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "lastRssFeed.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				RssFeed rss = new RssFeed(con.getString("title"), con.getString("author").replaceAll("[^a-zA-Z0-9]", ""), con.getString("link"));
				this.feed = rss;
				f.delete();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author xize
	 * @param returns true if the task is running otherwise false.
	 * @return Boolean
	 */
	public boolean isRunning() {
		return !stop;
	}
	
	/**
	 * @author xize
	 * @param cancels the task on the next tick.
	 */
	public void stop() {
		this.stop = true;
	}
	
	/**
	 * @author xize
	 * @param starts the task.
	 */
	public void start() {
		this.onChatSentRssBroadcast();
	}
}


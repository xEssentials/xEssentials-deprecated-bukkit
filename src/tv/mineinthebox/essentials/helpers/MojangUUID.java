package tv.mineinthebox.essentials.helpers;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public class MojangUUID {

	private ExecutorService service;
	
	private final xEssentials pl;
	
	public MojangUUID(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isVersionSupported() {
		if(isClass("net.minecraft.util.com.mojang.authlib.GameProfile")) {
			//orginal craftbukkit
			return true;
		} else if(isClass("net.glowstone.GlowServer")) {
			//glowstone server
			return true;
		} else if(isClass("com.mojang.authlib.GameProfile")) {
			//is probably spigot (the 1.8 version non protocol hack)
			return true;
		} else {
			//could mean its a old version of craftbukkit so we use our own system.
			return false;
		}
	}

	private boolean isClass(String classurl) {
		try {
			Class.forName(classurl);
			return true;
		} catch(ClassNotFoundException r) {
			return false;
		}
	}
	
	/**
	 * @author xize
	 * @param returns true if the executorservice is running else false
	 * @return boolean
	 */
	public boolean isExecutorServiceRunning() {
		return service != null;
	}
	
	/**
	 * @author xize
	 * @param shutdowns the executor
	 */
	public void shutdownExecutorService() {
		service.shutdownNow();
	}

	/**
	 * @author xize
	 * @param returns the unique id, this class will be compatible for any version of minecraft.
	 * @param we will look to a special class file named GameProfile.java, if it doesn't exist we know that bukkit is in a old state, which means we have to fetch data our selves. 
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public String getUniqueId(String name) throws Exception {
		if(isVersionSupported()) {
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("version is supported, we are using the build in uuid system instead", LogType.DEBUG);
			}
			return Bukkit.getPlayer(name).getUniqueId().toString().replaceAll("-", "");
		} else {
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("this version of bukkit does not have a inbuild version of the uuid system, so we will fetch the uuid manually", LogType.DEBUG);
			}
			String uuid = "";
			if(service != null) {
				Future<UUID> result = service.submit(new CompatUUID(name));
				uuid = result.get(3, TimeUnit.SECONDS).toString().replaceAll("-", "");
				result.cancel(true);
			} else {
				ExecutorService service = Executors.newCachedThreadPool();
				Future<UUID> result = service.submit(new CompatUUID(name));
				uuid = result.get(3, TimeUnit.SECONDS).toString().replaceAll("-", "");
				result.cancel(true);
			}
			return uuid;
		}
	}
	
	public String getLastName(String uuid) throws Exception {
		String lastname = null;
		if(service != null) {
			Future<String> result = service.submit(new Lastname(uuid));
			uuid = result.get(3, TimeUnit.SECONDS).toString().replaceAll("-", "");
			result.cancel(true);
		} else {
			ExecutorService service = Executors.newCachedThreadPool();
			Future<String> result = service.submit(new Lastname(uuid));
			uuid = result.get(3, TimeUnit.SECONDS).toString().replaceAll("-", "");
			result.cancel(true);
		}
		return lastname;
	}
}

class Lastname implements Callable<String> {
	/**
	 * @author xize
	 * @param returns the uuid of the site within 3 seconds, if not it throws a TimeOutException
	 */

	private UUID uuid;
	private final  String REPO = " https://api.mojang.com/user/profiles/";
	private final JSONParser jsonParser = new JSONParser();

	public Lastname(String uuid) {
		this.uuid = UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
	}

	@Override
	public String call() throws Exception {
		HttpURLConnection connection = createConnection();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
		String id = (String) jsonObject.get("name");
		connection.disconnect();
		return id;
	}

	private HttpURLConnection createConnection() throws Exception {
		URL url = new URL(REPO+uuid.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}
}

class CompatUUID implements Callable<UUID> {

	/**
	 * @author xize
	 * @param returns the uuid of the site within 3 seconds, if not it throws a TimeOutException
	 */

	private String name;
	private final  String REPO = "https://api.mojang.com/users/profiles/minecraft/";
	private final JSONParser jsonParser = new JSONParser();

	public CompatUUID(String name) {
		this.name = name;
	}

	@Override
	public UUID call() throws Exception {
		HttpURLConnection connection = createConnection();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
		String id = (String) jsonObject.get("id");
		UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
		connection.disconnect();
		return uuid;
	}

	private HttpURLConnection createConnection() throws Exception {
		URL url = new URL(REPO+name);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

}
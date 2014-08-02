package tv.mineinthebox.essentials.instances;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class MojangUUID {

	private Player p;

	public MojangUUID(Player p) {
		this.p = p;
	}
	
	private boolean isVersionSupported() {
		try {
			Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
			return true;
		} catch (ClassNotFoundException e) {}
		return false;
	}
	
	/**
	 * @author xize
	 * @param returns the unique id, this class will be compatible for any version of minecraft.
	 * @param we will look to a special class file named GameProfile.java, if it doesn't exist we know that bukkit is in a old state, which means we have to fetch data our selves. 
	 * @return String
	 * @throws Exception
	 */
	public String getUniqueId() throws Exception {
		if(isVersionSupported()) {
			return p.getUniqueId().toString().replaceAll("-", "");
		} else {
			Callable<UUID> getUUID = new CompatUUID(p);
			return getUUID.call().toString().replaceAll("-", "");
		}
	}
}

class CompatUUID implements Callable<UUID> {
	
	/**
	 * @author evilmidget98
	 * hereby we give credits to evilmidget98 for this UUID lookup parser, this is a highly modified version of his work where the modifications are made by me (xize)
	 * orginal thread is at http://forums.bukkit.org/threads/player-name-uuid-fetcher.250926.
	 */
	
	private Player p;
	private final int MAX_SEARCH = 100;
	private final String REPO = "https://api.mojang.com/profiles/page/";
	private final String AGENT = "minecraft";
	private final JSONParser jsonParser = new JSONParser();
	
	public CompatUUID(Player p) {
		this.p = p;
	}
	
	@Override
	public UUID call() throws Exception {
		for(int i = 1; i < MAX_SEARCH; i++) {
			HttpURLConnection connection = createConnection(i);
			writeBody(connection, buildBody());
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
			JSONArray array = (JSONArray) jsonObject.get("profiles");
			Number count = (Number) jsonObject.get("size");
			if (count.intValue() == 0) {
				break;
			}
			for (Object profile : array) {
				JSONObject jsonProfile = (JSONObject) profile;
				String id = (String) jsonProfile.get("id");
				UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
				connection.disconnect();
				return uuid;
			}
		}
		throw new NullPointerException("");
	}

	@SuppressWarnings("unchecked")
	private String buildBody() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject obj = new JSONObject();
		obj.put("name", p.getName());
		obj.put("agent", AGENT);
		list.add(obj);
		return JSONValue.toJSONString(list);
	}

	private void writeBody(HttpURLConnection connection, String body) throws Exception {
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		writer.write(body.getBytes());
		writer.flush();
		writer.close();
	}

	private HttpURLConnection createConnection(int PAGE) throws Exception {
		URL url = new URL(REPO+PAGE);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}
	
}
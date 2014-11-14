package tv.mineinthebox.bukkit.essentials.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ZipManager {

	public static YamlConfiguration load(File f) {
		if(!f.exists()) {
			return YamlConfiguration.loadConfiguration(f);
		}
		YamlConfiguration con = null;
		try {
			InputStream fi = new FileInputStream(f);
			GZIPInputStream st = new GZIPInputStream(fi);
			ObjectInputStream os = new ObjectInputStream(st);
			con = (YamlConfiguration) os.readObject();
			os.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void save(File f, YamlConfiguration con) {
		try {
			OutputStream ous = new FileOutputStream(f);
			GZIPOutputStream zip = new GZIPOutputStream(ous);
			ObjectOutputStream out = new ObjectOutputStream(zip);
			out.writeObject(con);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void save(File f, FileConfiguration con) {
		try {

			YamlSerialize serial = new YamlSerialize(con);

			OutputStream ous = new FileOutputStream(f);
			GZIPOutputStream zip = new GZIPOutputStream(ous);
			ObjectOutputStream out = new ObjectOutputStream(zip);
			out.writeObject(serial);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class YamlSerialize extends YamlConfiguration implements Serializable {

	private static final long serialVersionUID = 8915255379330137598L;

	private final YamlConfiguration con;

	public YamlSerialize(YamlConfiguration con) {
		this.con = con;
	}

	public YamlSerialize(FileConfiguration con) {
		this.con = (YamlConfiguration) con;
	}

	public YamlConfiguration getConfig() {
		return con;
	}

	private void writeObject(final ObjectOutputStream out) throws IOException {  
		out.writeUTF(con.toString());  
	}
}

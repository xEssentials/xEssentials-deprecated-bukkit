package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public abstract class ProtectionDatabase {

	protected xEssentials pl;
	protected PreparedStatement state;
	
	private Connection con;
	
	
	public ProtectionDatabase(xEssentials pl) {
		this.pl = pl;
		File f = new File(pl.getDataFolder() + File.separator + "databases");
		if(!f.isDirectory()) {
			f.mkdir();
		}
		try {
			Class.forName("org.sqlite.JDBC");
			if(isNewDatabase()) {
				createTables();
			}
		} catch (Exception e) {
			xEssentials.log("couldn't find sqlite in craftbukkit, this is probably because you are running a outdated build!", LogType.SEVERE);
		}
	}

	enum SQLType {
		INSERT,
		UPDATE,
		DELETE,
		SELECT
	}


	protected synchronized Object doQuery(String query, SQLType type, Object... params) {

		Pattern pat = Pattern.compile("\\?");
		Matcher match = pat.matcher(query);

		int matchcount = 0;

		while(match.find()) {
			matchcount++;
		}

		if(params != null && params.length > 0 && matchcount == params.length) {
			try {
				Connection con = getConnection();
				this.state = con.prepareStatement(query);
				for(int index = 1; index < (matchcount+1); index++) {
					Object param = params[index-1];
					if(param instanceof String) {
						state.setString(index, (String)param);
					} else if(param instanceof Double) {
						state.setDouble(index, (Double)param);
					} else if(param instanceof Integer) {
						state.setInt(index, (Integer)param);
					} else if(param instanceof Boolean) {
						state.setBoolean(index, (Boolean)param);
					}
				}
				switch(type) {
				case DELETE:
					return state.execute();
				case INSERT:
					return state.execute();
				case SELECT:
					return state.executeQuery();
				case UPDATE:
					return state.execute();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IndexOutOfBoundsException("specified raw query needs the same length");
		}
		return null;
	}

	private synchronized Connection getConnection() {
		try {
			if(con != null) {
				return con;
			} else {
				this.con = DriverManager.getConnection("jdbc:sqlite:plugins/xEssentials/databases/protection.db");
				return con;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isNewDatabase() {
		File f = new File(pl.getDataFolder() + File.separator + "databases" + File.separator + "protection.db");
		return !f.exists();
	}

	private synchronized void createTables() {
		try {
			Connection con = getConnection();
			String table = "CREATE TABLE IF NOT EXISTS `blocks` ("+ 
					"`id_` INTEGER," +
					"`member` TEXT NOT NULL, " +
					"`x` int NOT NULL, " +
					"`y` int NOT NULL, " +
					"`z` int NOT NULL, " +
					"`world` text NOT NULL, " +
					"PRIMARY KEY (`id_`) " +
					")";
			this.state = con.prepareStatement(table);
			state.executeUpdate();
			state.close();
			this.state = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

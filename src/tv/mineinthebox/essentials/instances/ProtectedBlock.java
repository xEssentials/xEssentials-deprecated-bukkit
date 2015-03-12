package tv.mineinthebox.essentials.instances;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class ProtectedBlock extends ProtectionDatabase {

	private final Block block;

	public ProtectedBlock(xEssentials pl, Block block) {
		super(pl);
		this.pl = pl;
		this.block = block;
	}

	/**
	 * returns true if the player(s) are members of this protection
	 * 
	 * @author xize
	 * @param p - the player, or players
	 * @return boolean
	 */
	public boolean isMember(UUID p) {
		try {
			ResultSet set = (ResultSet) doQuery("SELECT member FROM blocks WHERE member=? AND x=? AND y=? AND z=? AND world=?", SQLType.SELECT, p.toString(), block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			if(set.isBeforeFirst()) {
				set.close();
				state.close();
				return true;
			} else {
				set.close();
				close();
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * returns true if the block is protect able
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isProtected() {
		ResultSet set = (ResultSet) doQuery("SELECT * FROM blocks WHERE x=? AND y=? AND z=? AND world=?", SQLType.SELECT, block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
		try {
			if(set.isBeforeFirst()) {
				close();
				return true;
			} else {
				close();
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * returns the members
	 * 
	 * @author xize
	 * @return String[]
	 */
	public List<String> getMembers() {
		List<String> members = new ArrayList<String>();
		try {
			ResultSet set = (ResultSet) doQuery("SELECT * FROM blocks WHERE x=? AND y=? AND z=? AND world=?", SQLType.SELECT, block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			while(set.next()) {
				UUID id = UUID.fromString(set.getString("member"));
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(id);
				members.add(off.getUser());
			}
			close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return members;
	}

	/**
	 * adds protection to the block for the player(s) being given in as parameter
	 * 
	 * @author xize
	 * @param player - the player or players
	 */
	public boolean addProtection(UUID... player) {
		if(player.length == 1) {
			UUID p = player[0];
			doQuery("INSERT INTO blocks(member, x, y, z, world) VALUES(?, ?, ?, ?, ?)", SQLType.INSERT, p.toString(), block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			close();
			return true;
		} else if(player.length > 1) {
			for(UUID p : player) {
				doQuery("INSERT INTO blocks(member, x, y, z, world) VALUES(?, ?, ?, ?, ?)", SQLType.INSERT, p.toString(), block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			}
			close();
			return true;
		} else {
			throw new IllegalArgumentException("cannot pass null parameter!");
		}
	}

	/**
	 * removes the protection for the player(s)
	 * 
	 * @author xize
	 * @param player - the player(s)
	 */
	public boolean removeProtection(UUID... player) {
		if(player.length == 1) {
			UUID p = player[0];
			doQuery("DELETE FROM blocks WHERE member=? AND x=? AND y=? AND z=? AND world=?", SQLType.INSERT, p.toString(), block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			close();
			return true;
		} else if(player.length > 1) {
			for(UUID p : player) {
				doQuery("DELETE FROM blocks WHERE member=? AND x=? AND y=? AND z=? AND world=?", SQLType.INSERT, p.toString(), block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			}
			close();
			return true;
		} else {
			throw new IllegalArgumentException("cannot pass null parameter!");
		}
	}

	/**
	 * removes the protection as its whole
	 * 
	 * @author xize
	 */
	public void removeAll() {
		doQuery("DELETE FROM blocks WHERE x=? AND y=? AND z=? AND world=?", SQLType.DELETE, block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
		close();
	}
	
	/**
	 * closes the global statement
	 * 
	 * @author xize
	 */
	public void close() {
		try {
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		state = null;
	}

}

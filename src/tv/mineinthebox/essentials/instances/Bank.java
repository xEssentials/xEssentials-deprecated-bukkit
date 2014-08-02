package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

public class Bank {
	
	private final File f;
	private final FileConfiguration con;
	
	public Bank(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	/**
	 * @author xize
	 * @param returns the owner
	 * @return String
	 */
	public String getOwner() {
		return con.getString("bank.owner");
	}
	
	/**
	 * @author xize
	 * @param returns the amount in total on the bank
	 * @return Double
	 */
	public double getAmount() {
		return con.getDouble("bank.amount");
	}
	
	/**
	 * @author xize
	 * @param amount
	 */
	public void setAmount(Double amount) {
		con.set("bank.amount", amount);
		try {
			con.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * @author xize
	 * @param returns the bank name
	 * @return  String
	 */
	public String getBankName() {
		return con.getString("bank.name");
	}
	
	/**
	 * @author xize
	 * @param removes the bank.
	 */
	public void remove() {
		f.delete();
	}
	
	private void update() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((con == null) ? 0 : con.hashCode());
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bank other = (Bank) obj;
		if (con == null) {
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bank [f=" + f + ", con=" + con + ", getAmount()=" + getAmount()
				+ ", getBankName()=" + getBankName() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
}

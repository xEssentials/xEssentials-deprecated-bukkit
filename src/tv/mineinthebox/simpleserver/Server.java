package tv.mineinthebox.simpleserver;

/**
 * a easy Server interface for usage inside the events
 */
public interface Server {
	
	/**
	 * returns the name of the server
	 * 
	 * @return String
	 */
	public String getName();
	
	/**
	 * returns true if the server runs otherwise false
	 * 
	 * @return boolean
	 */
	public boolean isRunning();
	
	/**
	 * returns the port of the server
	 * 
	 * @return int
	 */
	public int getPort();

}

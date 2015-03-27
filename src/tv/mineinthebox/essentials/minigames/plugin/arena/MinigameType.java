package tv.mineinthebox.essentials.minigames.plugin.arena;

public abstract class MinigameType {
	
	protected String name;
	
	protected MinigameType(String name) {
		this.name = name;
	}
	
	/**
	 * returns the simple name of the type safe enum
	 * 
	 * @author xize
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns a uppercase name of the enum
	 * 
	 * @author xize
	 * @return String
	 */
	public String name() {
		return name.toUpperCase();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.equals(name)) {
			return true;
		}
		return false;
	}
	
}

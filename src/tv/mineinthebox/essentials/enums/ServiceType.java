package tv.mineinthebox.essentials.enums;

public enum ServiceType {
	MCBOUNCER("mcbouncer"),
	MINEBANS("minebans"),
	GLIZER("glizer"),
	MCBLOCKIT("mcblockit"),
	MCBANS("mcbans");
	
	private final String service;
	
	private ServiceType(String service) {
		this.service = service;
	}
	
	public String getService() {
		return service;
	}
}

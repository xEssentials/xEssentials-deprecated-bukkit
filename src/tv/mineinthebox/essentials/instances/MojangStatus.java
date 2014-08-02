package tv.mineinthebox.essentials.instances;

public class MojangStatus {
	
	private boolean LoginSvr;
	private boolean SesSvr;
	private boolean SkinSvr;
	
	/**
	 * @author xize
	 * @param LoginSvr
	 * @param SesSvr
	 * @param SkinSvr
	 */
	public MojangStatus(Boolean LoginSvr, Boolean SesSvr, Boolean SkinSvr) {
		this.LoginSvr = LoginSvr;
		this.SesSvr = SesSvr;
		this.SkinSvr = SkinSvr;
	}

	/**
	 * @author xize
	 * @param returns true whenever the minecraft login servers are up otherwise false
	 * @return Boolean
	 */
	public boolean isLoginServerActive() {
		return LoginSvr;
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the session server of minecraft are up otherwise false
	 * @return Boolean
	 */
	public boolean isSessionServerActive() {
		return SesSvr;
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the skin server up or down
	 * @return Boolean
	 */
	public boolean isSkinServerActive() {
		return SkinSvr;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (LoginSvr ? 1231 : 1237);
		result = prime * result + (SesSvr ? 1231 : 1237);
		result = prime * result + (SkinSvr ? 1231 : 1237);
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
		MojangStatus other = (MojangStatus) obj;
		if (LoginSvr != other.LoginSvr)
			return false;
		if (SesSvr != other.SesSvr)
			return false;
		if (SkinSvr != other.SkinSvr)
			return false;
		return true;
	}

}

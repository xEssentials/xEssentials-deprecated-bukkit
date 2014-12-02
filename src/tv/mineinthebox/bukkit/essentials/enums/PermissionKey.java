package tv.mineinthebox.bukkit.essentials.enums;


public enum PermissionKey {
	
	IS_ADMIN("xEssentials.isAdmin"),
	CMD_KNOCK("xEssentials.command.knock"),
	CMD_CLEAR("xEssentials.command.clear"),
	CMD_WILD("xEssentials.command.wild"),
	CMD_GET_CHUNK_FILE("xEssentials.command.getchunkfile"),
	CMD_LOOKUP("xEssentials.command.lookup"),
	CMD_PORTALS("xEssentials.command.portals"),
	CMD_CPRIVATE("xEssentials.command.cprivate"),
	CMD_SPLEEF("xEssentials.command.spleef"),
	CMD_CREMOVE("xEssentials.command.cremove"),
	CMD_CMODIFY("xEssentials.command.cmodify"),
	CMD_NETHER("xEssentials.command.nether"),
	CMD_PROC("xEssentials.command.proc"),
	MULTIPLE_HOMES("xEssentials.MultipleHomes"),
	CMD_ARROW("xEssentials.command.arrow"),
	CMD_MONEY("xEssentials.command.money"),
	CMD_AFK("xEssentials.command.afk"),
	CMD_BAN("xEssentials.command.ban"),
	CMD_GREYLIST("xEssentials.command.greylist"),
	CMD_SPAWNER_BLOCK("xEssentials.command.spawnerblock"),
	CMD_DRUNK("xEssentials.command.drunk"),
	CMD_KIT("xEssentials.command.kit"),
	CMD_KITS("xEssentials.command.kits"),
	CMD_CHECK("xEssentials.command.check"),
	CMD_SILENCE("xEssentials.command.silence"),
	CMD_BROADCAST("xEssentials.command.broadcast"),
	CMD_WARP("xEssentials.command.warp"),
	CMD_SETWARP("xEssentials.command.setwarp"),
	CMD_DELWARP("xEssentials.command.delwarp"),
	CMD_BACKPACK("xEssentials.command.backpack"),
	CMD_WARPS("xEssentials.command.warps"),
	CMD_COMMAND_RESTRICT("xEssentials.command.commandrestrict"),
	CMD_TP_ID("xEssentials.command.tpid"),
	CMD_FREEZE("xEssentials.command.freeze"),
	CMD_CLAIM("xEssentials.command.claim"),
	CMD_PLAYER_INFO("xEssentials.command.playerinfo"),
	CMD_DONE("xEssentials.command.done"),
	CMD_FIREFLY("xEssentials.command.firefly"),
	CMD_FLY("xEssentials.command.fly"),
	CMD_GAMEMODE("xEssentials.command.gamemode"),
	CMD_IGNORE("xEssentials.command.ignore"),
	CMD_TROLLBLOCK("xEssentials.command.trollblock"),
	CMD_RULES("xEssentials.command.rules"),
	CMD_FLOOR("xEssentials.command.floor"),
	CMD_WALL("xEssentials.command.wall"),
	CMD_HOME("xEssentials.command.home"),
	CMD_HOME_INVITE("xEssentials.command.homeinvite"),
	CMD_INVSEE("xEssentials.command.invsee"),
	CMD_LIST("xEssentials.command.list"),
	CMD_MODREQ("xEssentials.command.modreq"),
	CMD_MUTE("xEssentials.command.mute"),
	CMD_SET_HOME("xEssentials.command.sethome"),
	CMD_SET_SPAWN("xEssentials.command.setspawn"),
	IGNORE_HUMAN_SPAM("xEssentials.commandspam"),
	CMD_SPAWN("xEssentials.command.spawn"),
	CMD_SPAWN_OTHERS("xEssentials.command.spawnothers"),
	CMD_TASK("xEssentials.command.task"),
	CMD_HEROBRINE("xEssentials.command.herobrine"),
	CMD_SPAWN_MOB("xEssentials.command.spawnmob"),
	CMD_GIVE("xEssentials.command.give"),
	CMD_TELEPORT("xEssentials.command.teleport"),
	CMD_TROLLMODE("xEssentials.command.trollmode"),
	CMD_TEMP_BAN("xEssentials.command.tempban"),
	CMD_KILL("xEssentials.command.kill"),
	CMD_HAT("xEssentials.command.hat"),
	CMD_TORCH("xEssentials.command.torch"),
	CMD_TPA("xEssentials.command.tpa"),
	CMD_SPEED("xEssentials.command.speed"),
	CMD_TP_ACCEPT("xEssentials.command.tpa"),
	CMD_MENU("xEssentials.command.menu"),
	CMD_TP_DENY("xEssentials.command.tpa"),
	CMD_TP_HERE("xEssentials.command.tphere"),
	CMD_COMPASS("xEssentials.command.compass"),
	CMD_UNBAN("xEssentials.command.unban"),
	CMD_UNMUTE("xEssentials.command.unmute"),
	CMD_VANISH("xEssentials.command.vanish"),
	CMD_XESSENTIALS("xEssentials.command.xessentials"),
	CMD_MORE("xEssentials.command.more"),
	CMD_SMITE("xEssentials.command.smite"),
	CMD_EXPLODE("xEssentials.command.explode"),
	CMD_POTATO("xEssentials.command.potato"),
	CMD_NICK("xEssentials.command.nick"),
	CMD_BACK("xEssentials.command.back"),
	CMD_BOOM("xEssentials.command.boom"),
	CMD_TPS("xEssentials.command.tps"),
	CMD_HAND("xEssentials.command.hand"),
	CMD_SPAWNER("xEssentials.command.spawner"),
	CMD_POWERTOOL("xEssentials.command.powertool"),
	CMD_NUKE("xEssentials.command.nuke"),
	CMD_FAKE_NUKE("xEssentials.command.fakenuke"),
	CMD_FIRE("xEssentials.command.fire"),
	CMD_DOUBLEJUMP("xEssentials.command.doublejump"),
	CMD_SPECTATE("xEssentials.command.spectate"),
	SIGN_SHOP("xEssentials.signs.shop"),
	SIGN_SHOP_USE("xEssentials.signs.shop.use"),
	SIGN_BOOM("xEssentials.signs.boom"),
	SIGN_FIREWORK("xEssentials.signs.firework"),
	SIGN_COLOR("xEssentials.signs.color"),
	SIGN_FREE("xEssentials.signs.free"),
	SIGN_WARP_SIGN("xEssentials.signs.warp"),
	SIGN_WILD_SIGN("xEssentials.signs.wild"),
	SIGN_WILD_SIGN_USE("xEssentials.signs.wild.use"),
	SIGN_GETYOURHEAD("xEssentials.signs.getyourhead");
	
	private final String permission;
	
	private PermissionKey(String permission) {
		this.permission = permission;
	}
	
	/**
	 * @author xize
	 * @param returns the permission
	 * @return String
	 */
	public final String getPermission() {
		return this.permission;
	}
	
	

}

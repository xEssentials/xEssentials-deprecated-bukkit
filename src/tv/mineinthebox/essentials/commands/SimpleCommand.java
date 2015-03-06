package tv.mineinthebox.essentials.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import tv.mineinthebox.essentials.xEssentials;

public class SimpleCommand implements CommandExecutor, TabCompleter {
	
	private final xEssentials pl;
	
	public SimpleCommand(xEssentials pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("home")) {
			CmdHome home = new CmdHome(pl, cmd, sender);
			return home.execute(sender, cmd, args);
		}else if(cmd.getName().equalsIgnoreCase("sethome")) {
			CmdSethome sethome = new CmdSethome(pl, cmd, sender);
			return sethome.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("teleport")) {
			CmdTeleport teleport = new CmdTeleport(pl, cmd, sender);
			return teleport.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setspawn")) {
			CmdSetspawn spawn = new CmdSetspawn(pl, cmd, sender);
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawn")) {
			CmdSpawn spawn = new CmdSpawn(pl, cmd, sender);
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("gamemode")) {
			CmdGamemode gamemode = new CmdGamemode(pl, cmd, sender);
			return gamemode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("vanish")) {
			CmdVanish vanish = new CmdVanish(pl, cmd, sender);
			return vanish.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee(pl, cmd, sender);
			return invsee.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("torch")) {
			CmdTorch torch = new CmdTorch(pl, cmd, sender);
			return torch.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			CmdSpawnmob spawnmob = new CmdSpawnmob(pl, cmd, sender);
			return spawnmob.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("firefly")) {
			CmdFirefly firefly = new CmdFirefly(pl, cmd, sender);
			return firefly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fly")) {
			CmdFly fly = new CmdFly(pl, cmd, sender);
			return fly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("modreq")) {
			CmdModreq modreq = new CmdModreq(pl, cmd, sender);
			return modreq.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("check")) {
			CmdCheck check = new CmdCheck(pl, cmd, sender);
			return check.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone(pl, cmd, sender);
			return done.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("claim")) {
			CmdClaim claim = new CmdClaim(pl, cmd, sender);
			return claim.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			CmdxEssentials xEssentialsC = new CmdxEssentials(pl, cmd, sender);
			return xEssentialsC.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan(pl, cmd, sender);
			return ban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unban")) {
			CmdUnban unban = new CmdUnban(pl, cmd, sender);
			return unban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tempban")) {
			CmdTempban temp = new CmdTempban(pl, cmd, sender);
			return temp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("mute")) {
			CmdMute mute = new CmdMute(pl, cmd, sender);
			return mute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unmute")) {
			CmdUnmute unmute = new CmdUnmute(pl, cmd, sender);
			return unmute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tphere")) {
			CmdTphere tphere = new CmdTphere(pl, cmd, sender);
			return tphere.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpa")) {
			CmdTpa tpa = new CmdTpa(pl, cmd, sender);
			return tpa.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpaccept")) {
			CmdTpAccept tpAccept = new CmdTpAccept(pl, cmd, sender);
			return tpAccept.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpdeny")) {
			CmdTpDeny tpDeny = new CmdTpDeny(pl, cmd, sender);
			return tpDeny.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("list")) {
			CmdList list = new CmdList(pl, cmd, sender); 
			return list.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("afk")) {
			CmdAfk afk = new CmdAfk(pl, cmd, sender);
			return afk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("homeinvite")) {
			CmdHomeInvite homeinvite = new CmdHomeInvite(pl, cmd, sender);
			return homeinvite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("more")) {
			CmdMore more = new CmdMore(pl, cmd, sender);
			return more.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("smite")) {
			CmdSmite smite = new CmdSmite(pl, cmd, sender);
			return smite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("explode")) {
			CmdExplode explode = new CmdExplode(pl, cmd, sender);
			return explode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("potato")) {
			CmdPotato potato = new CmdPotato(pl, cmd, sender);
			return potato.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("back")) {
			CmdBack back = new CmdBack(pl, cmd, sender);
			return back.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("boom")) {
			CmdBoom boom = new CmdBoom(pl, cmd, sender);
			return boom.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawner")) {
			CmdSpawner spawner = new CmdSpawner(pl, cmd, sender);
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("speed")) {
			CmdSpeed speed = new CmdSpeed(pl, cmd, sender);
			return speed.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hand")) {
			CmdHand hand = new CmdHand(pl, cmd, sender);
			return hand.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("task")) {
			CmdTask task = new CmdTask(pl, cmd, sender);
			return task.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("powertool")) {
			CmdPowerTool powertool = new CmdPowerTool(pl, cmd, sender);
			return powertool.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nuke")) {
			CmdNuke nuke = new CmdNuke(pl, cmd, sender);
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fakenuke")) {
			CmdFakeNuke nuke = new CmdFakeNuke(pl, cmd, sender);
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass(pl, cmd, sender);
			return compass.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hat")) {
			CmdHat hat = new CmdHat(pl, cmd, sender);
			return hat.execute(sender, args, cmd);
		} else if(cmd.getName().equalsIgnoreCase("herobrine")) {
			CmdHerobrine brine = new CmdHerobrine(pl, cmd, sender);
			return brine.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("greylist")) {
			CmdGreylist greylist = new CmdGreylist(pl, cmd, sender);
			return greylist.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu(pl, cmd, sender);
			return menu.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("rules")) {
			CmdRules rules = new CmdRules(pl, cmd, sender);
			return rules.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("broadcast")) {
			CmdBroadcast broadcast = new CmdBroadcast(pl, cmd, sender);
			return broadcast.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive(pl, cmd, sender);
			give.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId(pl, cmd, sender);
			return tpid.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("silence")) {
			CmdSilence silence = new CmdSilence(pl, cmd, sender);
			return silence.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore(pl, cmd, sender);
			return ignore.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze(pl, cmd, sender);
			return freeze.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollmode")) {
			CmdTrollMode mode = new CmdTrollMode(pl, cmd, sender);
			return mode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("commandrestrict")) {
			CmdCommandRestrict restrict = new CmdCommandRestrict(pl, cmd, sender);
			return restrict.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo(pl, cmd, sender);
			return info.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit(pl, cmd, sender);
			return kit.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kits")) {
			CmdKits kits = new CmdKits(pl, cmd, sender);
			return kits.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp(pl, cmd, sender);
			return warp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setwarp")) {
			CmdSetWarp setwarp = new CmdSetWarp(pl, cmd, sender);
			return setwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp(pl, cmd, sender);
			return delwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warps")) {
			CmdWarps warps = new CmdWarps(pl, cmd, sender);
			return warps.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wild")) {
			CmdWild wild = new CmdWild(pl, cmd, sender);
			return wild.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney(pl, cmd, sender);
			return money.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cprivate")) {
			CmdCprivate priv = new CmdCprivate(pl, cmd, sender);
			return priv.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cremove")) {
			CmdCremove remove = new CmdCremove(pl, cmd, sender);
			return remove.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cmodify")) {
			CmdCmodify modify = new CmdCmodify(pl, cmd, sender);
			return modify.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portals = new CmdPortals(pl, cmd, sender);
			return portals.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("arrow")) {
			CmdArrow arrow = new CmdArrow(pl, cmd, sender);
			return arrow.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("test")) {
			CmdTest test = new CmdTest();
			return test.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("lookup")) {
			CmdLookup lookup = new CmdLookup(pl, cmd, sender);
			return lookup.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc(pl, cmd, sender);
			return proc.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kill")) {
			CmdKill kill = new CmdKill(pl, cmd, sender);
			return kill.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack(pl, cmd, sender);
			return backpack.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollblock")) {
			CmdTrollBlock troll = new CmdTrollBlock(pl, cmd, sender);
			return troll.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("knock")) {
			CmdKnock knock = new CmdKnock(pl, cmd, sender);
			return knock.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("clear")) {
			CmdClear clear = new CmdClear(pl, cmd, sender);
			return clear.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fire")) {
			CmdFire fire = new CmdFire(pl, cmd, sender);
			return fire.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump doublejump = new CmdDoubleJump(pl, cmd, sender);
			return doublejump.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nick")) {
			CmdNick nick = new CmdNick(pl, cmd, sender);
			return nick.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spectate")) {
			CmdSpectate spectate = new CmdSpectate(pl, cmd, sender);
			return spectate.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("editsign")) {
			CmdEditSign sign = new CmdEditSign(pl, cmd, sender);
			return sign.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("drunk")) {
			CmdDrunk drunk = new CmdDrunk(pl, cmd, sender);
			return drunk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnerblock")) {
			CmdSpawnerBlock spawner = new CmdSpawnerBlock(pl, cmd, sender);
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("floor")) {
			CmdFloor floor = new CmdFloor(pl, cmd, sender);
			return floor.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wall")) {
			CmdWall wall = new CmdWall(pl, cmd, sender);
			return wall.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nether")) {
			CmdNether nether = new CmdNether(pl, cmd, sender);
			return nether.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("getchunkfile")) {
			CmdGetChunkFile chunk = new CmdGetChunkFile(pl, cmd, sender);
			return chunk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("opkit")) {
			CmdOpkit opkit = new CmdOpkit(pl, cmd, sender);
			return opkit.execute(sender, cmd, args);
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			CmdTeleport tp = new CmdTeleport(pl, cmd, sender);
			return tp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive(pl, cmd, sender);
			return give.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan(pl, cmd, sender);
			return ban.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("home")) {
			CmdHome home = new CmdHome(pl, cmd, sender);
			return home.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu(pl, cmd, sender);
			return menu.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone(pl, cmd, sender);
			return done.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId(pl, cmd, sender);
			return tpid.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee(pl, cmd, sender);
			return invsee.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore(pl, cmd, sender);
			return ignore.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass(pl, cmd, sender);
			return compass.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze(pl, cmd, sender);
			return freeze.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo(pl, cmd, sender);
			return info.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit(pl, cmd, sender);
			return kit.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp(pl, cmd, sender);
			return warp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp(pl, cmd, sender);
			return delwarp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney(pl, cmd, sender);
			return money.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portal = new CmdPortals(pl, cmd, sender);
			return portal.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc(pl, cmd, sender);
			return proc.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack(pl, cmd, sender);
			return backpack.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump jump = new CmdDoubleJump(pl, cmd, sender);
			return jump.onTabComplete(sender, cmd, args);
		}
		return null;
	}

}

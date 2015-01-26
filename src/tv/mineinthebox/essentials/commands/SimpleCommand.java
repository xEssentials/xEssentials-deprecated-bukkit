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
			CmdHome home = new CmdHome(pl);
			return home.execute(sender, cmd, args);
		}else if(cmd.getName().equalsIgnoreCase("sethome")) {
			CmdSethome sethome = new CmdSethome(pl);
			return sethome.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("teleport")) {
			CmdTeleport teleport = new CmdTeleport(pl);
			return teleport.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setspawn")) {
			CmdSetspawn spawn = new CmdSetspawn(pl);
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawn")) {
			CmdSpawn spawn = new CmdSpawn(pl);
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("gamemode")) {
			CmdGamemode gamemode = new CmdGamemode(pl);
			return gamemode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("vanish")) {
			CmdVanish vanish = new CmdVanish(pl);
			return vanish.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee(pl);
			return invsee.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("torch")) {
			CmdTorch torch = new CmdTorch(pl);
			return torch.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			CmdSpawnmob spawnmob = new CmdSpawnmob();
			return spawnmob.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("firefly")) {
			CmdFirefly firefly = new CmdFirefly(pl);
			return firefly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fly")) {
			CmdFly fly = new CmdFly(pl);
			return fly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("modreq")) {
			CmdModreq modreq = new CmdModreq(pl);
			return modreq.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("check")) {
			CmdCheck check = new CmdCheck(pl);
			return check.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone(pl);
			return done.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("claim")) {
			CmdClaim claim = new CmdClaim(pl);
			return claim.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			CmdxEssentials xEssentialsC = new CmdxEssentials(pl);
			return xEssentialsC.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan(pl);
			return ban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unban")) {
			CmdUnban unban = new CmdUnban(pl);
			return unban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tempban")) {
			CmdTempban temp = new CmdTempban(pl);
			return temp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("mute")) {
			CmdMute mute = new CmdMute(pl);
			return mute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unmute")) {
			CmdUnmute unmute = new CmdUnmute(pl);
			return unmute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tphere")) {
			CmdTphere tphere = new CmdTphere(pl);
			return tphere.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpa")) {
			CmdTpa tpa = new CmdTpa(pl);
			return tpa.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpaccept")) {
			CmdTpAccept tpAccept = new CmdTpAccept(pl);
			return tpAccept.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpdeny")) {
			CmdTpDeny tpDeny = new CmdTpDeny(pl);
			return tpDeny.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("list")) {
			CmdList list = new CmdList(pl); 
			return list.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("afk")) {
			CmdAfk afk = new CmdAfk(pl);
			return afk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("homeinvite")) {
			CmdHomeInvite homeinvite = new CmdHomeInvite(pl);
			return homeinvite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("more")) {
			CmdMore more = new CmdMore();
			return more.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("smite")) {
			CmdSmite smite = new CmdSmite(pl);
			return smite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("explode")) {
			CmdExplode explode = new CmdExplode(pl);
			return explode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("potato")) {
			CmdPotato potato = new CmdPotato(pl);
			return potato.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("back")) {
			CmdBack back = new CmdBack(pl);
			return back.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("boom")) {
			CmdBoom boom = new CmdBoom(pl);
			return boom.execute(sender, args, cmd);
		} else if(cmd.getName().equalsIgnoreCase("spawner")) {
			CmdSpawner spawner = new CmdSpawner();
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("speed")) {
			CmdSpeed speed = new CmdSpeed(pl);
			return speed.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hand")) {
			CmdHand hand = new CmdHand();
			return hand.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("task")) {
			CmdTask task = new CmdTask(pl);
			return task.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("powertool")) {
			CmdPowerTool powertool = new CmdPowerTool(pl);
			return powertool.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nuke")) {
			CmdNuke nuke = new CmdNuke(pl);
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fakenuke")) {
			CmdFakeNuke nuke = new CmdFakeNuke(pl);
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass(pl);
			return compass.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hat")) {
			CmdHat hat = new CmdHat();
			return hat.execute(sender, args, cmd);
		} else if(cmd.getName().equalsIgnoreCase("herobrine")) {
			CmdHerobrine brine = new CmdHerobrine(pl);
			return brine.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("greylist")) {
			CmdGreylist greylist = new CmdGreylist(pl);
			return greylist.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu(pl);
			return menu.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("rules")) {
			CmdRules rules = new CmdRules(pl);
			return rules.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("broadcast")) {
			CmdBroadcast broadcast = new CmdBroadcast(pl);
			return broadcast.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive(pl);
			give.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId(pl);
			return tpid.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("silence")) {
			CmdSilence silence = new CmdSilence(pl);
			return silence.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore(pl);
			return ignore.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze(pl);
			return freeze.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollmode")) {
			CmdTrollMode mode = new CmdTrollMode(pl);
			return mode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("commandrestrict")) {
			CmdCommandRestrict restrict = new CmdCommandRestrict(pl);
			return restrict.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo(pl);
			return info.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit(pl);
			return kit.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kits")) {
			CmdKits kits = new CmdKits(pl);
			return kits.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp(pl);
			return warp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setwarp")) {
			CmdSetWarp setwarp = new CmdSetWarp(pl);
			return setwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp(pl);
			return delwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warps")) {
			CmdWarps warps = new CmdWarps(pl);
			return warps.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wild")) {
			CmdWild wild = new CmdWild(pl);
			return wild.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney(pl);
			return money.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cprivate")) {
			CmdCprivate priv = new CmdCprivate(pl);
			return priv.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cremove")) {
			CmdCremove remove = new CmdCremove(pl);
			return remove.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cmodify")) {
			CmdCmodify modify = new CmdCmodify(pl);
			return modify.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portals = new CmdPortals(pl);
			return portals.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("arrow")) {
			CmdArrow arrow = new CmdArrow();
			return arrow.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("test")) {
			CmdTest test = new CmdTest();
			return test.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("lookup")) {
			CmdLookup lookup = new CmdLookup(pl);
			return lookup.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc(pl);
			return proc.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kill")) {
			CmdKill kill = new CmdKill(pl);
			return kill.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack(pl);
			return backpack.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollblock")) {
			CmdTrollBlock troll = new CmdTrollBlock(pl);
			return troll.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("knock")) {
			CmdKnock knock = new CmdKnock(pl);
			return knock.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("clear")) {
			CmdClear clear = new CmdClear(pl);
			return clear.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fire")) {
			CmdFire fire = new CmdFire();
			return fire.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump doublejump = new CmdDoubleJump(pl);
			return doublejump.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nick")) {
			CmdNick nick = new CmdNick(pl);
			return nick.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spectate")) {
			CmdSpectate spectate = new CmdSpectate(pl);
			return spectate.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("editsign")) {
			CmdEditSign sign = new CmdEditSign(pl);
			return sign.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("drunk")) {
			CmdDrunk drunk = new CmdDrunk(pl);
			return drunk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnerblock")) {
			CmdSpawnerBlock spawner = new CmdSpawnerBlock(pl);
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("floor")) {
			CmdFloor floor = new CmdFloor(pl);
			return floor.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wall")) {
			CmdWall wall = new CmdWall(pl);
			return wall.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nether")) {
			CmdNether nether = new CmdNether();
			return nether.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("getchunkfile")) {
			CmdGetChunkFile chunk = new CmdGetChunkFile();
			return chunk.execute(sender, cmd, args);
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			CmdTeleport tp = new CmdTeleport(pl);
			return tp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive(pl);
			return give.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan(pl);
			return ban.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("home")) {
			CmdHome home = new CmdHome(pl);
			return home.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu(pl);
			return menu.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone(pl);
			return done.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId(pl);
			return tpid.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee(pl);
			return invsee.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore(pl);
			return ignore.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass(pl);
			return compass.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze(pl);
			return freeze.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo(pl);
			return info.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit(pl);
			return kit.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp(pl);
			return warp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp(pl);
			return delwarp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney(pl);
			return money.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portal = new CmdPortals(pl);
			return portal.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc(pl);
			return proc.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack(pl);
			return backpack.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump jump = new CmdDoubleJump(pl);
			return jump.onTabComplete(sender, cmd, args);
		}
		return null;
	}

}

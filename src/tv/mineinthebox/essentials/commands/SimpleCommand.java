package tv.mineinthebox.essentials.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SimpleCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("home")) {
			CmdHome home = new CmdHome();
			return home.execute(sender, cmd, args);
		}else if(cmd.getName().equalsIgnoreCase("sethome")) {
			CmdSethome sethome = new CmdSethome();
			return sethome.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("teleport")) {
			CmdTeleport teleport = new CmdTeleport();
			return teleport.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setspawn")) {
			CmdSetspawn spawn = new CmdSetspawn();
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawn")) {
			CmdSpawn spawn = new CmdSpawn();
			return spawn.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("gamemode")) {
			CmdGamemode gamemode = new CmdGamemode();
			return gamemode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("vanish")) {
			CmdVanish vanish = new CmdVanish();
			return vanish.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee();
			return invsee.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("torch")) {
			CmdTorch torch = new CmdTorch();
			return torch.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			CmdSpawnmob spawnmob = new CmdSpawnmob();
			return spawnmob.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("firefly")) {
			CmdFirefly firefly = new CmdFirefly();
			return firefly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fly")) {
			CmdFly fly = new CmdFly();
			return fly.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("modreq")) {
			CmdModreq modreq = new CmdModreq();
			return modreq.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("check")) {
			CmdCheck check = new CmdCheck();
			return check.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone();
			return done.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("claim")) {
			CmdClaim claim = new CmdClaim();
			return claim.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			CmdxEssentials xEssentialsC = new CmdxEssentials();
			return xEssentialsC.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan();
			return ban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unban")) {
			CmdUnban unban = new CmdUnban();
			return unban.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tempban")) {
			CmdTempban temp = new CmdTempban();
			return temp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("mute")) {
			CmdMute mute = new CmdMute();
			return mute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("unmute")) {
			CmdUnmute unmute = new CmdUnmute();
			return unmute.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tphere")) {
			CmdTphere tphere = new CmdTphere();
			return tphere.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpa")) {
			CmdTpa tpa = new CmdTpa();
			return tpa.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpaccept")) {
			CmdTpAccept tpAccept = new CmdTpAccept();
			return tpAccept.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tpdeny")) {
			CmdTpDeny tpDeny = new CmdTpDeny();
			return tpDeny.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("list")) {
			CmdList list = new CmdList(); 
			return list.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("afk")) {
			CmdAfk afk = new CmdAfk();
			return afk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("homeinvite")) {
			CmdHomeInvite homeinvite = new CmdHomeInvite();
			return homeinvite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("more")) {
			CmdMore more = new CmdMore();
			return more.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("smite")) {
			CmdSmite smite = new CmdSmite();
			return smite.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("explode")) {
			CmdExplode explode = new CmdExplode();
			return explode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("potato")) {
			CmdPotato potato = new CmdPotato();
			return potato.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("back")) {
			CmdBack back = new CmdBack();
			return back.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("boom")) {
			CmdBoom boom = new CmdBoom();
			return boom.execute(sender, args, cmd);
		} else if(cmd.getName().equalsIgnoreCase("spawner")) {
			CmdSpawner spawner = new CmdSpawner();
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("speed")) {
			CmdSpeed speed = new CmdSpeed();
			return speed.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hand")) {
			CmdHand hand = new CmdHand();
			return hand.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("task")) {
			CmdTask task = new CmdTask();
			return task.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("powertool")) {
			CmdPowerTool powertool = new CmdPowerTool();
			return powertool.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nuke")) {
			CmdNuke nuke = new CmdNuke();
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fakenuke")) {
			CmdFakeNuke nuke = new CmdFakeNuke();
			return nuke.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass();
			return compass.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("hat")) {
			CmdHat hat = new CmdHat();
			return hat.execute(sender, args, cmd);
		} else if(cmd.getName().equalsIgnoreCase("herobrine")) {
			CmdHerobrine brine = new CmdHerobrine();
			return brine.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("greylist")) {
			CmdGreylist greylist = new CmdGreylist();
			return greylist.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu();
			return menu.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("rules")) {
			CmdRules rules = new CmdRules();
			return rules.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("broadcast")) {
			CmdBroadcast broadcast = new CmdBroadcast();
			return broadcast.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive();
			give.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId();
			return tpid.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("silence")) {
			CmdSilence silence = new CmdSilence();
			return silence.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore();
			return ignore.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze();
			return freeze.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollmode")) {
			CmdTrollMode mode = new CmdTrollMode();
			return mode.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("commandrestrict")) {
			CmdCommandRestrict restrict = new CmdCommandRestrict();
			return restrict.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo();
			return info.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit();
			return kit.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kits")) {
			CmdKits kits = new CmdKits();
			return kits.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp();
			return warp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("setwarp")) {
			CmdSetWarp setwarp = new CmdSetWarp();
			return setwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp();
			return delwarp.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warps")) {
			CmdWarps warps = new CmdWarps();
			return warps.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wild")) {
			CmdWild wild = new CmdWild();
			return wild.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney();
			return money.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cprivate")) {
			CmdCprivate priv = new CmdCprivate();
			return priv.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cremove")) {
			CmdCremove remove = new CmdCremove();
			return remove.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("cmodify")) {
			CmdCmodify modify = new CmdCmodify();
			return modify.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portals = new CmdPortals();
			return portals.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("arrow")) {
			CmdArrow arrow = new CmdArrow();
			return arrow.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("test")) {
			CmdTest test = new CmdTest();
			return test.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("lookup")) {
			CmdLookup lookup = new CmdLookup();
			return lookup.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc();
			return proc.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kill")) {
			CmdKill kill = new CmdKill();
			return kill.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack();
			return backpack.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("trollblock")) {
			CmdTrollBlock troll = new CmdTrollBlock();
			return troll.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("knock")) {
			CmdKnock knock = new CmdKnock();
			return knock.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("clear")) {
			CmdClear clear = new CmdClear();
			return clear.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("fire")) {
			CmdFire fire = new CmdFire();
			return fire.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump doublejump = new CmdDoubleJump();
			return doublejump.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("nick")) {
			CmdNick nick = new CmdNick();
			return nick.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spectate")) {
			CmdSpectate spectate = new CmdSpectate();
			return spectate.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("editsign")) {
			CmdEditSign sign = new CmdEditSign();
			return sign.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("drunk")) {
			CmdDrunk drunk = new CmdDrunk();
			return drunk.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("spawnerblock")) {
			CmdSpawnerBlock spawner = new CmdSpawnerBlock();
			return spawner.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("floor")) {
			CmdFloor floor = new CmdFloor();
			return floor.execute(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("wall")) {
			CmdWall wall = new CmdWall();
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
			CmdTeleport tp = new CmdTeleport();
			return tp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("give")) {
			CmdGive give = new CmdGive();
			return give.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ban")) {
			CmdBan ban = new CmdBan();
			return ban.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("home")) {
			CmdHome home = new CmdHome();
			return home.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("menu")) {
			CmdMenu menu = new CmdMenu();
			return menu.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("done")) {
			CmdDone done = new CmdDone();
			return done.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("tp-id")) {
			CmdTpId tpid = new CmdTpId();
			return tpid.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("invsee")) {
			CmdInvsee invsee = new CmdInvsee();
			return invsee.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("ignore")) {
			CmdIgnore ignore = new CmdIgnore();
			return ignore.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("compass")) {
			CmdCompass compass = new CmdCompass();
			return compass.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("freeze")) {
			CmdFreeze freeze = new CmdFreeze();
			return freeze.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			CmdPlayerInfo info = new CmdPlayerInfo();
			return info.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("kit")) {
			CmdKit kit = new CmdKit();
			return kit.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("warp")) {
			CmdWarp warp = new CmdWarp();
			return warp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("delwarp")) {
			CmdDelWarp delwarp = new CmdDelWarp();
			return delwarp.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("money")) {
			CmdMoney money = new CmdMoney();
			return money.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("portals")) {
			CmdPortals portal = new CmdPortals();
			return portal.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("proc")) {
			CmdProc proc = new CmdProc();
			return proc.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("backpack")) {
			CmdBackpack backpack = new CmdBackpack();
			return backpack.onTabComplete(sender, cmd, args);
		} else if(cmd.getName().equalsIgnoreCase("doublejump")) {
			CmdDoubleJump jump = new CmdDoubleJump();
			return jump.onTabComplete(sender, cmd, args);
		}
		return null;
	}

}

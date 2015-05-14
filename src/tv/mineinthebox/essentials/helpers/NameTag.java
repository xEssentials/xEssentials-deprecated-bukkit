package tv.mineinthebox.essentials.helpers;
/*
import net.minecraft.server.v1_8_R2.IScoreboardCriteria;
import net.minecraft.server.v1_8_R2.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R2.Scoreboard;
import net.minecraft.server.v1_8_R2.ScoreboardObjective;
import net.minecraft.server.v1_8_R2.ScoreboardTeam;
import net.minecraft.server.v1_8_R2.ScoreboardTeamBase.EnumNameTagVisibility;
*/
import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class NameTag {
	/*
	public static void sentPacket(Player p, String name) {		
		//Class<?> clazz = Class.forName("net.minecraft.server."+getVersion()+".PacketPlayOutScoreboardTeam")
		Scoreboard board = new Scoreboard();
		ScoreboardTeam team = board.createTeam("nametag");
		team.setNameTagVisibility(EnumNameTagVisibility.ALWAYS);
		team.setPrefix("test");
		ScoreboardObjective obj = board.registerObjective("dummy", IScoreboardCriteria.b);
		PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
	
	private final static String getVersion() {
		String packagen = Bukkit.getServer().getClass().getPackage().getName();
		String lastdot = packagen.substring(packagen.lastIndexOf('.'));
		return lastdot.substring(1, lastdot.length());
	}
	*/

}

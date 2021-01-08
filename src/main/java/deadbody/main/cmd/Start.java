package deadbody.main.cmd;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Start implements CommandExecutor {
	private static ArrayList<NPC> SleepNPC = new ArrayList<>();

	public static ArrayList<NPC> getSleepNPC(){
		return SleepNPC;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			for(NPC npc : getSleepNPC()){
				npc.destroy();
			}
			sender.sendMessage(ChatColor.RED+"시체가 정리 되었습니다 :D");
			Start.getSleepNPC().clear();
		}
		return false;
	}
}

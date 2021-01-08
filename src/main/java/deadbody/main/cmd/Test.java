package deadbody.main.cmd;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class Test implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		Entity e = p.getWorld().spawn(p.getLocation(), Pig.class);
	 	e.getBoundingBox().resize(1, 1, 1, 2, 2, 2);
		return false;

	}
}

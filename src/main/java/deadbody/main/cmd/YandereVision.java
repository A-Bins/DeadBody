package deadbody.main.cmd;

import deadbody.main.Glow;
import deadbody.main.Main;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class YandereVision implements CommandExecutor {
	private static HashMap<UUID, Boolean> YandereVision = new HashMap<>();

	public static HashMap<UUID, Boolean> getYandereVision(){
		return YandereVision;
	}
	private static Boolean b;

	public static Boolean getb(){
		return b;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			if (args.length > 0) {
				if (args[0].equals("on")) {
					getYandereVision().put(((Player) sender).getUniqueId(), true);
				} else if (args[0].equals("off")) {
					getYandereVision().put(((Player) sender).getUniqueId(), false);
				}


				getYandereVision().putIfAbsent(((Player) sender).getUniqueId(), true);
				if (getYandereVision().get(((Player) sender).getUniqueId())) {
					for (NPC npc : Start.getSleepNPC()) {
						Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
							if (b) {
								Glow.setGlow((Player) sender, npc.getEntity(), true);
							}
						}, 1, 1);
					}
					b = true;
					Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
						if (b) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p != sender) {
									Glow.setGlow((Player) sender, p, true);
								}
							}
						}
					}, 1, 1);


					getYandereVision().put(((Player) sender).getUniqueId(), false);

				} else {
					b = false;
					for (NPC npc : Start.getSleepNPC()) {
						Glow.setGlow((Player) sender, npc.getEntity(), false);
					}
					for (Player p : Bukkit.getOnlinePlayers()) {
						Glow.setGlow((Player) sender, p, false);
					}
					getYandereVision().put(((Player) sender).getUniqueId(), true);

				}
			} else{
				getYandereVision().putIfAbsent(((Player) sender).getUniqueId(), true);
				if (getYandereVision().get(((Player) sender).getUniqueId())) {
					for (NPC npc : Start.getSleepNPC()) {
						Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
							if (b) {
								Glow.setGlow((Player) sender, npc.getEntity(), true);
							}
						}, 1, 1);
					}
					b = true;
					Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
						if (b) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p != sender) {
									Glow.setGlow((Player) sender, p, true);
								}
							}
						}
					}, 1, 1);


					getYandereVision().put(((Player) sender).getUniqueId(), false);

				} else {
					b = false;
					for (NPC npc : Start.getSleepNPC()) {
						Glow.setGlow((Player) sender, npc.getEntity(), false);
					}
					for (Player p : Bukkit.getOnlinePlayers()) {
						Glow.setGlow((Player) sender, p, false);
					}
					getYandereVision().put(((Player) sender).getUniqueId(), true);

				}
			}


		}
		return false;
	}
}

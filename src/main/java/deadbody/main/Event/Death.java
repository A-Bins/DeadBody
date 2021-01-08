package deadbody.main.Event;

import deadbody.main.Npcspawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener {
	@EventHandler
	public void dead(PlayerDeathEvent e){
		Player p  = e.getEntity();
		Npcspawn.Npcspawn(p);
	}
}

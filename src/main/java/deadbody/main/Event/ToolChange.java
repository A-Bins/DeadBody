package deadbody.main.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.UUID;

public class ToolChange implements Listener {
	@EventHandler
	public void SlotChangeEvent(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		if(RightClick.getPicupNPC().get(uuid) != null){
			e.setCancelled(true);
		}
	}
}

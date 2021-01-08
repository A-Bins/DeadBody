package deadbody.main.Event;

import deadbody.main.Main;
import deadbody.main.cmd.Start;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class RightClick implements Listener{

	private static HashMap<UUID, NPC> PicupNPC = new HashMap<UUID, NPC>();

	public static HashMap<UUID, NPC> getPicupNPC(){
		return PicupNPC;
	}

	@EventHandler
	public void RightClick(NPCRightClickEvent e){
		Player p = e.getClicker();
		UUID uuid = p.getUniqueId();
		if(p.isSneaking()) {
			if(Start.getSleepNPC().contains(e.getNPC())) {
				if(PicupNPC.get(uuid) == null) {
					if(e.getClicker().getInventory().getItemInMainHand().getType() == Material.AIR) {
						PicupNPC.put(uuid, e.getNPC());
						e.getNPC().destroy();
						p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);

						p.sendMessage(ChatColor.RED + "시체를 들었습니다!");
						ItemStack CorpseItem = new ItemStack(Material.FERMENTED_SPIDER_EYE);
						ItemMeta CorPseItemMeta = CorpseItem.getItemMeta();
						CorPseItemMeta.setDisplayName(ChatColor.DARK_RED + "시체");
						CorpseItem.setItemMeta(CorPseItemMeta);
						p.getInventory().setItemInMainHand(CorpseItem);
						Sneak.getNPCsneak().put(uuid, false);
					}else p.sendMessage(ChatColor.RED+"손이 비워져 있어야합니다!");
				}else p.sendMessage(ChatColor.RED+"시체는 하나만 들 수있습니다!");
			}else p.sendMessage(ChatColor.RED+"시체만 들 수있습니다!");
		}

	}
}

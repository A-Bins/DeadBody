package deadbody.main.Event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import deadbody.main.Main;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Sneak implements Listener {
	private static HashMap<UUID, Boolean> SleepNPC = new HashMap<>();

	public static HashMap<UUID, Boolean> getNPCsneak(){
		return SleepNPC;
	}
	@EventHandler
	public void Sneaked(PlayerToggleSneakEvent e){

		if(!e.getPlayer().isSneaking()) {
			if(RightClick.getPicupNPC() != null) {
				getNPCsneak().put(e.getPlayer().getUniqueId(), true);
				Bukkit.getScheduler().scheduleSyncDelayedTask( Main.getInstance() , () -> {
					if(e.getPlayer().isSneaking()) {
						if(RightClick.getPicupNPC().get(e.getPlayer().getUniqueId()) != null) {
							if(getNPCsneak().get(e.getPlayer().getUniqueId())){
								NPC npc = RightClick.getPicupNPC().get(e.getPlayer().getUniqueId());
								npc.spawn(e.getPlayer().getLocation());
								e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								Bukkit.getScheduler().scheduleSyncDelayedTask( Main.getInstance() , () -> {
									for (Player pp : Bukkit.getOnlinePlayers()) {



										ProtocolManager pm = ProtocolLibrary.getProtocolManager();

										WrappedDataWatcher dw = WrappedDataWatcher.getEntityWatcher(npc.getEntity());

										WrappedDataWatcher.WrappedDataWatcherObject obj = new WrappedDataWatcher.WrappedDataWatcherObject(6, WrappedDataWatcher.Registry.get(EnumWrappers.getEntityPoseClass()));

										dw.setObject(obj, EnumWrappers.EntityPose.SLEEPING.toNms());

										PacketContainer packet = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA);

										packet.getIntegers().write(0, npc.getEntity().getEntityId());

										packet.getWatchableCollectionModifier().write(0, dw.getWatchableObjects());

										try {
											pm.sendServerPacket(pp, packet);
											Bukkit.getScheduler().scheduleSyncDelayedTask( Main.getInstance() , () -> {
												ScoreboardTeam scoreboard = new ScoreboardTeam(new Scoreboard(), "NPC");

												scoreboard.setColor(EnumChatFormat.DARK_RED);

												scoreboard.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

												ArrayList <String> playerToAdd = new ArrayList<>();

												playerToAdd.add(npc.getName());
												PlayerConnection connection = ((CraftPlayer)pp).getHandle().playerConnection;
												connection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboard, 0));
												connection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboard, playerToAdd, 3));
											}, 10);
										} catch (InvocationTargetException i) {
											i.printStackTrace();
										}
									}
									RightClick.getPicupNPC().put(e.getPlayer().getUniqueId(), null);
								}, 10);

							}
						}
					}
				}, 50);
			}
		}else{
			getNPCsneak().put(e.getPlayer().getUniqueId(), false);

		}
	}
}

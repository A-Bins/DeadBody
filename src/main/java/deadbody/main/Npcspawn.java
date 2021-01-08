package deadbody.main;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import deadbody.main.Event.RightClick;
import deadbody.main.cmd.Start;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Npcspawn {
	public static void Npcspawn(Player p){
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		NPC npc = registry.createNPC(EntityType.PLAYER, p.getName());
		npc.spawn(p.getLocation());
		Bukkit.broadcastMessage(""+p.getInventory().getItemInMainHand().getType().getMaxDurability());

		Start.getSleepNPC().add(npc);

		Bukkit.getScheduler().scheduleSyncDelayedTask( Main.getInstance() , () -> {
			for(Player pp : Bukkit.getOnlinePlayers()) {
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
						npc.getEntity().getBoundingBox().resize(1, 1, 1, 1, 1, 1);

					}, 20);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}, 50);
	}
}

package deadbody.main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Glow {

	private static HashMap<Player, WrappedDataWatcher> watcher = new HashMap<Player, WrappedDataWatcher>();

	public static HashMap<Player, WrappedDataWatcher> getWatcher() {
		return watcher;
	}

	public static void setGlow(Player p, Entity e, boolean glow) {

		ProtocolManager pm = ProtocolLibrary.getProtocolManager();
		watcher.put(p, new WrappedDataWatcher());

		WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
		watcher.get(p).setEntity(e);

		if (glow) {
			if (watcher.get(p).getObject(0) == null) {
				watcher.get(p).setObject(0, serializer, (byte) 0x40);
			} else
				watcher.get(p).setObject(0, serializer, (byte) watcher.get(p).getObject(0) | 1 << 6);
		} else {
			if (watcher.get(p).getObject(0) == null) {
				watcher.get(p).setObject(0, serializer, (byte) 0);
			}
			else
				watcher.get(p).setObject(0, serializer, (byte) watcher.get(p).getObject(0) & ~1 << 6);
		}

		PacketContainer packet = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA);

		packet.getIntegers().write(0, e.getEntityId());

		packet.getWatchableCollectionModifier().write(0, watcher.get(p).getWatchableObjects());

		try {
			pm.sendServerPacket(p, packet);
		} catch (InvocationTargetException i) {
			i.printStackTrace();
		}
	}
}

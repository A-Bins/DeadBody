package deadbody.main;


import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import deadbody.main.Event.Death;
import deadbody.main.Event.RightClick;
import deadbody.main.Event.Sneak;
import deadbody.main.Event.ToolChange;
import deadbody.main.cmd.Sleep;
import deadbody.main.cmd.Start;
import deadbody.main.cmd.Test;
import deadbody.main.cmd.YandereVision;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {
	private static Main instance;

	private static ProtocolManager manager;

	public static ProtocolManager getManager() {
		return manager;
	}

	public static Main getInstance(){
		return instance;
	}

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("[  Corpse  ]" + ChatColor.GREEN +" 플러그인 활성화 copyright ©  < Bins#1004 > all rights reserved");
		instance = this;
		getServer().getPluginManager().registerEvents(new RightClick(), this);
		getCommand("시체").setExecutor(new Sleep());
		getServer().getPluginManager().registerEvents(new ToolChange(), this);
		getServer().getPluginManager().registerEvents(new Sneak(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getCommand("시체지우기").setExecutor(new Start());
		getCommand("얀데레비전").setExecutor(new YandereVision());
		getCommand("Test").setExecutor(new Test());

		manager = ProtocolLibrary.getProtocolManager();

	}


	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("[  Corpse  ]" + ChatColor.RED +" 플러그인 비활성화 copyright ©  < Bins#1004 > all rights reserved");

	}
}

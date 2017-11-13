package br.com.igorst.backend;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.igorst.backend.API.MySQL;
import br.com.igorst.backend.Commands.BanCommand;
import br.com.igorst.backend.Events.BanEvents;





public class Main extends JavaPlugin {
	
	private static Main plugin;
	public static Main getPlugin() {return plugin;}
	public static String logo = "§e§l§f§lMC §f: §f";
	public static String logoAviso = "§e§l§f§lMC §f: §7";
	public static MySQL sql;
	
	public void onEnable() {

		plugin = this;
		MySQL.sqlConnect();
		MySQL.createTableIf();
		MySQL.createTableIfBan();
		Bukkit.getPluginManager().registerEvents(new BanEvents(), this);
		getCommand("ban").setExecutor(new BanCommand());		
		getCommand("tempban").setExecutor(new BanCommand());
		getCommand("unban").setExecutor(new BanCommand());
	}
	
	public void onDisable() {
		
	}
	
	public void onLoad() {
		
	}
	
	public static MySQL getSQL() {
		return Main.sql;
	}

	public static void setSQL(final MySQL sql) {
		Main.sql = sql;
	}
	
	
	//Projeto Criado por: Igor
	//Copyright em nome do FDC 1482420-10
	//Iniciado 12/11/2017
	//Projeto: I BACKEND
	//Servidor: IDN

}

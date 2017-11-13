package br.com.igorst.backend.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.igorst.backend.API.MySQL;

public class BanManager {

	public static String razao;
	public static long tempo;
	

	public static String getReason() { return razao;}

	public static long getTempo() {return tempo;}
	
	

	public static void createTempo(Player p, String razao, String tempo, Player moderador) {

		Bukkit.getServer().broadcastMessage("§cO Player " + p.getName() + " foi banido por " + razao + " por " + moderador.getName());

		MySQL.setBanned(p.getUniqueId().toString(), "true");
		MySQL.setRazao(p.getUniqueId().toString(), razao);
		MySQL.setTempo(p.getUniqueId().toString(),tempo);
		MySQL.setMod(p.getUniqueId().toString(), p.getName());
	}

	public static void create(Player p, String razao, Player moderador) {
	
		Bukkit.getServer().broadcastMessage("§cO Player " + p.getName() + " foi banido por " + razao + " por " + moderador.getName());
		MySQL.setBanned(p.getUniqueId().toString(), "true");
		MySQL.setRazao(p.getUniqueId().toString(), razao);
		MySQL.setTempo(p.getUniqueId().toString(), "PERMANENTE");
		MySQL.setMod(p.getUniqueId().toString(), p.getName());
	}
	
}

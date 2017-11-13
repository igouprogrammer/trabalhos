package br.com.igorst.backend.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import br.com.igorst.backend.Main;
import br.com.igorst.backend.API.MySQL;

public class BanEvents implements Listener {
	
	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		Player p =e.getPlayer();
		MySQL.addPlayerToTable(p.getUniqueId(), "bans", new String[] {p.getName() , "","0","","false"}, false, true);
		MySQL.addPlayerToTable(p.getUniqueId(), "backend", new String[] {p.getName() , "false"}, false, true);
		e.setJoinMessage(null);
	}
	
	@EventHandler
	void onEnter(PlayerLoginEvent e) {
		Player p  = e.getPlayer();
		if (Main.getSQL().getBanned(p.getUniqueId().toString()).equals("true")) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage("§cVocê está banido de nossa rede\n"
					+ "§cDuração: " + Main.getSQL().getTempo(p.getUniqueId().toString()) + " Dias\n"
					+ "§cRazão: " + Main.getSQL().getRazao(p.getUniqueId().toString()) + " \n"
					+ "§eCompre unban em: loja.igorst.com.br");
			
		}

		if (Main.getSQL().getTempo(p.getUniqueId().toString()).equals("PERMANENTE")) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage("§cVocê está banido de nossa rede\n"
					+ "§cDuração: PERMANENTE" +"\n"
					+ "§cRazão: " + Main.getSQL().getRazao(p.getUniqueId().toString()) + " \n"
					+ "§eCompre unban em: loja.igorst.com.br");
	
		}
	}

}

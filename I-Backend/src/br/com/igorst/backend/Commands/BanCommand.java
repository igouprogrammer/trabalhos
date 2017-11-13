package br.com.igorst.backend.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.igorst.backend.Main;
import br.com.igorst.backend.API.MySQL;
import br.com.igorst.backend.Manager.BanManager;

public class BanCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("unban")) {
			if (!sender.hasPermission("backend.admin")) {
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage("§cUse: /unban <player>");
				return true;
			}
			@SuppressWarnings("deprecation")
			OfflinePlayer acao = Bukkit.getOfflinePlayer(args[0]);
			if (acao == null) {
				sender.sendMessage("§cJogador não existe ou não está banido.");
				return true;
			}
			if (acao.isOnline()) {
				sender.sendMessage("§cJogador não está banido!");
				return true;
			}
			if (Main.getSQL().getBanned(acao.getUniqueId().toString()).equals("true")) {
				MySQL.setBanned(acao.getUniqueId().toString(), "false");
				MySQL.setMod(acao.getUniqueId().toString(), "");
				MySQL.setRazao(acao.getUniqueId().toString(), "");
				MySQL.setTempo(acao.getUniqueId().toString(), "0");
				sender.sendMessage("§cJogador desbanido com sucesso!");
				for (Player pall : Bukkit.getOnlinePlayers()) {
					if (pall.hasPermission("backend.admin")) {
						pall.sendMessage("§7O Jogador " + p.getName() + " desbaniu o jogador " + acao.getName());
					}
				}
			}else if (Main.getSQL().getBanned(acao.getUniqueId().toString()).equals("false")) {
				sender.sendMessage("§cJogador não está banido!");
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("tempban")) {
			if (!p.hasPermission("backend.admin")) {
				return true;
			}
			if (args.length == 0) {
				p.sendMessage("§cTemporariamente desativado\n§cUse /ban");
				return true;
			}
			/*
			if (args.length > 2) {
				Player acao = Bukkit.getPlayerExact(args[0]);
				if (acao == null) {
					p.sendMessage("§cPlayer offline!");
					return true;
				}
	       

	            BanManager.createTempo(acao, args[1], args[2].toString(), p);
	            p.kickPlayer("§cVocê está banido de nossa rede\n"
						+ "§cDuração: " + Main.getSQL().getTempo(p.getUniqueId().toString()) + " DIAS\n"
						+ "§cRazão: " + Main.getSQL().getRazao(p.getUniqueId().toString()) + " \n"
						+ "§eCompre unban em: loja.igorst.com.br");
			}
			*/
		}
		if (cmd.getName().equalsIgnoreCase("ban")) {
			if (!p.hasPermission("backend.admin")) {
				return true;
			}
			if (args.length == 0) {
				p.sendMessage("§cUse: /ban <player> <razao>");
				return true;
			}
			if (args.length > 1) {
				Player acao = Bukkit.getPlayerExact(args[0]);
				if (acao == null) {
					p.sendMessage("§cPlayer offline!");
					return true;
				}
	            StringBuilder razao = new StringBuilder();
	            for (int i = 1; i < razao.length(); i++) {
	                razao.append(args[i]).append(" ");
	            }

	            BanManager.create(acao, args[1], p);
	            p.kickPlayer("§cVocê está banido de nossa rede\n"
					+ "§cDuração: " + Main.getSQL().getTempo(p.getUniqueId().toString()) + "\n"
					+ "§cRazão: " + Main.getSQL().getRazao(p.getUniqueId().toString()) + " \n"
					+ "§eCompre unban em: loja.igorst.com.br");
			}
		}
		return false;
	}

}

package br.com.igorst.backend.API;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.igorst.backend.Main;




public class MySQL {
	public static boolean ativo;
	private static FileConfiguration dbConfigFile;
	private String ip;
	private int porta;
	private String usuario;
	private String senha;
	private String banco;
	private Connection connection;

	static {
		MySQL.ativo = false;
	}

	@SuppressWarnings("unused")
	public MySQL() throws Exception {
		final File file = new File("plugins/ICentral-Config/", "mysql.yml");
		final FileConfiguration cfg = MySQL.dbConfigFile = (FileConfiguration) YamlConfiguration
				.loadConfiguration(file);
		final String db = "MySQL.";
		cfg.addDefault("MySQL.ativo", "true");
		cfg.addDefault("MySQL.ip", "localhost");
		cfg.addDefault("MySQL.porta", 3306);
		cfg.addDefault("MySQL.usuario", "root");
		cfg.addDefault("MySQL.senha", "");
		cfg.addDefault("MySQL.banco", "servidor");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isAtivo()) {
			MySQL.ativo = true;
		}
		if (MySQL.ativo) {
			this.ip = cfg.getString("MySQL.ip");
			this.porta = cfg.getInt("MySQL.porta");
			this.usuario = cfg.getString("MySQL.usuario");
			this.senha = cfg.getString("MySQL.senha");
			this.banco = cfg.getString("MySQL.banco");
			this.openConnection();
		}
	}

	public static boolean isAtivo() {
		return !MySQL.dbConfigFile.getString("MySQL.ativo").equalsIgnoreCase("false");

	}

	public static void sqlConnect() {
		try {
			Main.setSQL(new MySQL());
			if (MySQL.ativo) {
				System.out.println("/ Conectado com sucesso!.");
			} else {
				System.out.println("/ Conex�o desativada, MYSQL DESATIVADO!");
			}
		} catch (Exception ex) {
			System.out.println("/ Erro ao conectar!");
			MySQL.ativo = false;
			ex.printStackTrace();
		}
	}
	public static void createTableIfBan() {
		try {
			
			Statement s = Main.getSQL().connection.createStatement();
			
			s.executeUpdate("CREATE TABLE IF NOT EXISTS bans(`uuid` VARCHAR(255), `nick` VARCHAR(255), `razao` TEXT, `tempo` VARCHAR(255), `moderador` VARCHAR(255), `banido` TEXT)");
			
			s.close();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("�cOcorreu um erro:" + e.getLocalizedMessage());
		}
	}
	public static void createTableIf() {
		try {
			
			Statement s = Main.getSQL().connection.createStatement();
			
			s.executeUpdate("CREATE TABLE IF NOT EXISTS backend(`uuid` VARCHAR(255), `nick` VARCHAR(255), `muted` TEXT)");
			
			s.close();
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("�cOcorreu um erro:" + e.getLocalizedMessage());
		}
	}
	
	public String getData(String data) {
		if (!ativo) {
			return " ";
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT " + data + " FROM status;");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(data);
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}

	public int getWins(UUID id) {
		if (!ativo) {
			System.out.println("eae");
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT wins FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("wins");
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("�cOcorreu um erro ao getar as WINS");
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int getMoney(UUID id) {
		if (!ativo) {
			System.out.println("eae");
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT money FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("money");
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;

	}

	public int getDeaths(UUID id) {
		if (!ativo) {
			System.out.println("eae");
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT deaths FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("deaths");
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;

	}

	public int getKills(UUID id) {
		if (!ativo) {
			System.out.println("eae");
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT kills FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("kills");

			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;

	}
	public String getTempo(String id) {

		if (!ativo) {
			return " ";
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT tempo FROM bans WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("tempo");

			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}
	public String getRazao(String id) {

		if (!ativo) {
			return " ";
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT razao FROM bans WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("razao");

			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}
	public String getMod(String id) {

		if (!ativo) {
			return " ";
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT moderador FROM bans WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("moderador");

			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}
	public String getBanned(String id) {

		if (!ativo) {
			return " ";
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT banido FROM bans WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("banido");

			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}
	public String getCargo(String id) {

		if (!ativo) {
			
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT cargo FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("cargo");
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}
	public String getRank(String id) {

		if (!ativo) {
			return " ";
		}
		try {

			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT rank FROM status WHERE uuid='" + id + "'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("rank");
			}
			rs.close();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return " ";
	}

	public static void addPlayerToTables(final UUID id, final String table, final String[] columns,
			final String[] valuesToInsert, final boolean useAI) {
		new BukkitRunnable() {
			public void run() {
				if (!MySQL.ativo) {
					return;
				}
				try {
					final PreparedStatement ps = Main.getSQL().getConnection()
							.prepareStatement("SELECT uuid FROM " + table + " WHERE uuid='" + id + "'");
					final ResultSet rs = ps.executeQuery();
					if (!rs.next()) {
						String colunas = (useAI ? "id, " : "") + "uuid, ";
						String values = (useAI ? "'0', " : "") + id + ", ";
						for (int i = 0; i < valuesToInsert.length; ++i) {
							String virgula = "";
							if (i < valuesToInsert.length - 1) {
								virgula = ", ";
							}
							colunas = colunas + columns[i] + virgula;
							values = values + "'" + valuesToInsert[i] + "'" + virgula;
						}
						final PreparedStatement ps2 = Main.getSQL().getConnection()
								.prepareStatement("INSERT INTO " + table + " (" + colunas + ") VALUES(" + values + ")");
						ps2.executeUpdate();
						ps2.close();
					}
					rs.close();
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					try {
						Main.getSQL().openConnection();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		}.runTaskAsynchronously(Main.getPlugin());
	}
	public static void setRazao(final String id, String ban) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT razao FROM bans WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE bans SET razao = ? WHERE uuid = ?");
					ps2.setString(1, ban);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void setTempo(final String id, String ban) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT tempo FROM bans WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE bans SET tempo = ? WHERE uuid = ?");
					ps2.setString(1, ban);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setMod(final String id, String ban) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT moderador FROM bans WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE bans SET moderador = ? WHERE uuid = ?");
					ps2.setString(1, ban);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setBanned(final String id, String ban) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT banido FROM bans WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE bans SET banido = ? WHERE uuid = ?");
					ps2.setString(1, ban);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setRank(final String id, String rank) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT rank FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET rank = ? WHERE uuid = ?");
					
					ps2.setString(1, rank);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void setCargo(final String id, String rank) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT cargo FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET cargo = ? WHERE uuid = ?");
					ps2.setString(1, rank);
					ps2.setString(2, id);
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void removeMoney(final UUID id, int valor) {
		if (MySQL.ativo) {
			try {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT money FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					final PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET money='"
									+ Integer.toString(rs.getInt("money") - valor) + "' WHERE uuid='" + id + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addWin(final UUID id, int valor) {
			try {
				if (MySQL.ativo) {
					final PreparedStatement ps = Main.sql.getConnection().prepareStatement("SELECT wins FROM status WHERE uuid='" + id + "'");
					final ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						final PreparedStatement ps2 = Main.sql.getConnection().prepareStatement("UPDATE status SET wins='" + Integer.toString(rs.getInt("wins") + valor) + "'WHERE uuid='" + id + "'");
						ps2.executeUpdate();
						ps2.close();
					}
					rs.close();
					ps.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void addMoney(final UUID id, int valor) {
		try {
			if (MySQL.ativo) {
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT money FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					final PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET money='"
									+ Integer.toString(rs.getInt("money") + valor) + "' WHERE uuid='" + id + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addDeath(final UUID id, int valor) {
		if (MySQL.ativo) {
			try {

				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT deaths FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					final PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET deaths='"
									+ Integer.toString(rs.getInt("deaths") + valor) + "' WHERE uuid='" + id + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addKill(final UUID id, int valor) {
		if (MySQL.ativo) {
			try {
				
				final PreparedStatement ps = Main.sql.getConnection()
						.prepareStatement("SELECT kills FROM status WHERE uuid='" + id + "'");
				final ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					final PreparedStatement ps2 = Main.sql.getConnection()
							.prepareStatement("UPDATE status SET kills='"
									+ Integer.toString(rs.getInt("kills") + valor) + "' WHERE uuid='" + id + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean addPlayerToTable(UUID id, String table, String[] valuesToInsert, boolean useAI,
			boolean checkIfNotExists) {
		if (!ativo) {
			return false;
		}
		try {
			PreparedStatement ps = Main.getSQL().getConnection()
					.prepareStatement("SELECT uuid FROM " + table + " WHERE uuid='" + id + "'");

			ResultSet rs = ps.executeQuery();
			if ((!checkIfNotExists) || (!rs.next())) {
				String values = (useAI ? "'0', " : "") + "'" + id + "', ";
				for (int i = 0; i < valuesToInsert.length; i++) {
					String virgula = "";
					if (i < valuesToInsert.length - 1) {
						virgula = ", ";
					}
					values = values + "'" + valuesToInsert[i] + "'" + virgula;
				}

				PreparedStatement ps1 = Main.getSQL().getConnection()
						.prepareStatement("INSERT INTO " + table + " VALUES(" + values + ")");
				ps1.executeUpdate();
				ps1.close();
				rs.close();
				ps.close();
				return true;
			}
			rs.close();
			ps.close();
			return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				Main.getSQL().openConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		final Connection connection = DriverManager.getConnection(
				"jdbc:mysql://" + this.ip + ":" + this.porta + "/" + this.banco, this.usuario, this.senha);
		return this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public boolean hasConnection() {
		try {
			return this.connection != null || this.connection.isValid(1);
		} catch (SQLException e) {
			return false;
		}
	}

	public void queryUpdate(final String query) {
		new BukkitRunnable() {
			public void run() {
				final Connection conn = Main.getSQL().getConnection();
				PreparedStatement st = null;
				try {
					st = conn.prepareStatement(query);
					st.executeUpdate();
					st.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					try {
						if (Main.getSQL().hasConnection()) {
							Main.getSQL().openConnection();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					return;
				} finally {
					Main.getSQL().closeResources(null, st);
				}
				Main.getSQL().closeResources(null, st);
			}
		}.runTaskAsynchronously(Main.getPlugin());
	}

	public void closeResources(final ResultSet rs, final PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException ex2) {
			}
		}
	}

	public void closeConnection() {
		try {
			if (!MySQL.ativo) {
				return;
			}
			if (this.connection.isClosed()) {
				return;
			}
			this.connection.close();
		} catch (SQLException ex) {
			return;
		} finally {
			this.connection = null;
		}
		this.connection = null;
	}
	
	//Projeto Criado por: Igor
	//Copyright em nome do FDC 1482420-10
	//Iniciado 12/11/2017
	//Projeto: I CENTRAL
	//Servidor: IDN
}






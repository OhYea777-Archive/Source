package com.mcjailed.jailedrp.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import com.mcjailed.jailedrp.JailedRP;
import com.mcjailed.jailedrp.MoneyPrinter;
import com.mcjailed.jailedrp.MoneyPrinter.PrinterType;
import com.mcjailed.jailedrp.RPDoor;
import com.mcjailed.jailedrp.RPPlayer;

public class SQLLoader {

	private JailedRP plugin;
	private Connection conn;
	private Map<String, RPPlayer> players;
	private Map<Location, RPDoor> doors;
	private Map<Location, MoneyPrinter> printers;

	public SQLLoader() {
		plugin = JailedRP.instance;
		players = new HashMap<String, RPPlayer>();
		doors = new HashMap<Location, RPDoor>();
		printers = new HashMap<Location, MoneyPrinter>();
		try {
			File file = new File(plugin.getDataFolder(), "Data.db");
			file.getParentFile().mkdirs();
			if (!file.exists())
				file.createNewFile();

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:plugins/" + plugin.getDescription().getName() + "/Data.db");
			createTables();
		} catch (SQLException e) {
			plugin.getLogger().severe("SQLite File Failed to Load! Disabling Plugin! Stacktrace Follows:");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		} catch (ClassNotFoundException e) {
			plugin.getLogger().severe("Failed to Find SQLite Driver! Disabling Plugin! Stacktrace Follows:");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		} catch (IOException e) {
			plugin.getLogger().severe("Failed to Save SQLite Database! Stacktrace Follows:");
			e.printStackTrace();
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}

	private void createTables() throws SQLException {
		conn.prepareStatement("CREATE TABLE IF NOT EXISTS `Players` (`name` text(17) NOT NULL PRIMARY KEY, `json` text)").execute();
		conn.prepareStatement("CREATE TABLE IF NOT EXISTS `Doors` (`id` text NOT NULL PRIMARY KEY, `json` text)").execute();
		conn.prepareStatement("CREATE TABLE IF NOT EXISTS `MoneyPrinters` (`uuid` text NOT NULL PRIMARY KEY, `json` text)").execute();
		loadData();
	}

	private void loadData() throws SQLException {
		ResultSet set = executeQuery(conn.prepareStatement("SELECT * FROM `Players`"));
		while (set.next()) {
			String json = set.getString(2);
			RPPlayer player = Utils.getGson().fromJson(json, RPPlayer.class);
			players.put(player.getName(), player);
		}
		set.close();
		set = executeQuery(conn.prepareStatement("SELECT * FROM `Doors`"));
		while (set.next()) {
			try {
				String[] s = set.getString(1).split(":");
				World world = Bukkit.getWorld(s[0]);
				int x = Integer.valueOf(s[1]);
				int y = Integer.valueOf(s[2]);
				int z = Integer.valueOf(s[3]);
				Location loc = new Location(world, x, y, z);
				RPDoor door = Utils.getGson().fromJson(set.getString(2), RPDoor.class);
				door.setLocation(loc);
				doors.put(loc, door);
			} catch (Exception e) { }
		}
		set.close();
		set = executeQuery(conn.prepareStatement("SELECT * FROM `MoneyPrinters`"));
		while (set.next()) {
			String uuid = set.getString(1);
			MoneyPrinter printer = Utils.getGson().fromJson(set.getString(2), MoneyPrinter.class);
			World world = Bukkit.getWorld(printer.getWorld());
			for (Entity e : world.getEntities()) {
				if (e.getUniqueId().toString().equals(uuid)) {
					printer.setLocation(e.getLocation().getBlock().getLocation());
					printers.put(e.getLocation().getBlock().getLocation(), printer);
					printer.schedule();
				}
			}
		}
	}
	
	private synchronized ResultSet executeQuery(PreparedStatement statement) throws SQLException {
		return statement.executeQuery();
	}

	public void onDisable() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public RPPlayer getPlayer(String name) {
		if (players.containsKey(name)) {
			return players.get(name);
		} else {
			RPPlayer player = new RPPlayer(name);
			updateJson("Players", name, Utils.getGson().toJson(player));
			players.put(name, player);
			return player;
		}
	}

	public RPPlayer getPlayer(Player player) {
		return getPlayer(player.getName());
	}

	public RPDoor getDoor(Location location) {
		if (doors.containsKey(location))
			return doors.get(location);
		else {
			RPDoor door = new RPDoor(location);
			String loc = location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
			updateJson("Doors", loc, Utils.getGson().toJson(door));
			doors.put(location, door);
			return door;
		}
	}

	public MoneyPrinter getPrinter(ItemFrame e, PrinterType type) {
		if (printers.containsKey(e.getLocation().getBlock().getLocation())) {
			return printers.get(e.getLocation().getBlock().getLocation());
		} else {
			MoneyPrinter printer = new MoneyPrinter(e, type);
			String uuid = printer.getUUID();
			updateJson("MoneyPrinters", uuid, Utils.getGson().toJson(printer));
			printers.put(e.getLocation().getBlock().getLocation(), printer);
			return printer;
		}
	}

	public MoneyPrinter getPrinter(ItemFrame e) {
		return printers.get(e.getLocation().getBlock().getLocation());
	}
	
	public void updateDoor(RPDoor door) {
		Location location = door.getLocation();
		String loc = location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
		updateJson("Doors", loc, Utils.getGson().toJson(door));
	}

	public void updatePrinter(MoneyPrinter printer) {
		String uuid = printer.getUUID().toString();
		updateJson("MoneyPrinters", uuid, Utils.getGson().toJson(printer));
	}

	public boolean isPrinter(ItemFrame e) {
		return printers.containsKey(e.getLocation().getBlock().getLocation());
	}

	public Collection<MoneyPrinter> getPrinters() {
		return printers.values();
	}

	public synchronized void updateJson(String table, String id, String json) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("INSERT OR REPLACE INTO {table} VALUES (?, ?)".replace("{table}", table));
			statement.setString(1, id);
			statement.setString(2, json);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) { }
		}
	}
	
	public void removePrinter(String uuid) {
		for (int i = 0; i < printers.size(); i ++) {
			MoneyPrinter printer = (MoneyPrinter) printers.values().toArray()[i];
			Location location = (Location) printers.keySet().toArray()[i];
			if (printer.getUUID().equals(uuid)) {
				printer.stop();
				printer = null;
				printers.remove(location);
				remove("MoneyPrinters", uuid, "uuid");
			}
		}
	}
	
	public void removeDoor(Location location) {
		if (doors.containsKey(location)) {
			String loc = location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
			remove("Doors", loc, "id");
		}
	}
	
	public synchronized void remove(String table, String id, String type) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("DELETE FROM {table} WHERE {type} = ?".replace("{table}", table).replace("{type}", type));
			statement.setString(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) { }
		}
	}

	public void updatePlayer(String name) {
		if (players.containsKey(name)) {
			updateJson("Players", name, Utils.getGson().toJson(players.get(name)));
		}
	}

	public void updatePlayer(Player player) {
		updatePlayer(player.getName());
	}

}

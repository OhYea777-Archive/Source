package com.DeadChex;

import com.earth2me.essentials.Essentials;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HorseTools extends JavaPlugin {
	Essentials ess;
	FileConfiguration conf;
	FileConfiguration horseData;
	String prefix = "§3[§6Horse§8Tools§3]§7";
	File horseDataFile;
	boolean protectHorses;
	boolean serverLock;
	boolean useEss;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventHandle(), this);
		getServer().getPluginManager().registerEvents(new preCMDHandle(), this);
		getServer().getPluginManager().registerEvents(new onInteractHandler(),
				this);
		getServer().getPluginManager().registerEvents(
				new onEntityDamageEvent(), this);

		ess = ((Essentials) getServer().getPluginManager().getPlugin(
				"Essentials"));
		saveDefaultConfig();
		conf = getConfig();
		if (ess == null) {
			useEss = false;
		} else {
			useEss = true;
		}
		horseDataFile = new File(getDataFolder().getAbsolutePath()
				+ "/horses.yml");
		if (conf.contains("Horses")) {
			horseData = conf;
			try {
				horseData.save(horseDataFile);
			} catch (Exception e) {
				getLogger().info(
						"FATAL ERROR: Unable to migrate horse data base.");
			}
			File oldConf = new File(getDataFolder().getAbsolutePath()
					+ "/config.yml");
			if (!oldConf.delete()) {
				getLogger().info("Error resetting config. Please delete it.");
			}
			saveDefaultConfig();

			conf = getConfig();
		}
		if (getConfig().getInt("Version") != 7) {
			boolean backprotectHorses = getConfig().getBoolean("protectHorses");
			getConfig()
					.set("protectHorses", Boolean.valueOf(backprotectHorses));
			getConfig().set("mustLock", Boolean.valueOf(true));
			getConfig().set("Version", Integer.valueOf(7));
			saveConfig();
		}
		try {
			horseData = getConfig();
			horseData.load(horseDataFile);
		} catch (Exception e) {
			try {
				horseData.save(horseDataFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		horseData.set("protectHorses", null);
		horseData.set("mustLock", null);
		try {
			horseData.save(horseDataFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		reloadConfig();

		protectHorses = getConfig().getBoolean("protectHorses");
		serverLock = getConfig().getBoolean("mustLock");
		conf = getConfig();
	}

	public void log(String s) {
	}

	public void onDisable() {
		try {
			horseData.save(horseDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Plugin getthis() {
		return this;
	}

	public class preCMDHandle implements Listener {
		public preCMDHandle() {
		}

		@EventHandler
		public void onPreCommand(PlayerCommandPreprocessEvent evt) {
			log("Commanded " + evt.getMessage());

			String[] pack = evt.getMessage().split(" ");
			log("Packaged: " + Arrays.toString(pack));
			if ((pack.length != 2) || (!evt.getPlayer().isInsideVehicle())) {
				return;
			}
			if (((pack[0].equalsIgnoreCase("/warp")) || (pack[0]
					.equalsIgnoreCase("/warps"))) && (useEss)) {
				Location warpLoc = null;
				try {
					warpLoc = ess.getWarps().getWarp(pack[1]);
				} catch (Exception e) {
					log("Except with getting warp.");
					evt.getPlayer().sendMessage(
							"§cError: §4That warp does not exsist.");
					return;
				}
				if (warpLoc == null) {
					log("NULL location. Abort.");
					return;
				}
				if (((evt.getPlayer().getVehicle() instanceof Horse))
						|| (evt.getPlayer().getVehicle().getType().toString()
								.equals("UNKNOWN"))) {
					evt.setCancelled(true);
				} else {
					log("Not on a horse! On a: "
							+ evt.getPlayer().getVehicle().getType().toString());
					return;
				}
				Animals horse = (Animals) evt.getPlayer().getVehicle();
				if (horse.eject()) {
					log("Ejected player, begin TP and mount.");
					evt.getPlayer().sendMessage(
							prefix + " §6Warping to §c" + pack[1]
									+ "§6 with horse.");
					@SuppressWarnings("unused")
					BukkitTask localBukkitTask = new HorseTools.HorseTele(evt.getPlayer(), horse, warpLoc).runTaskLater(getthis(), 2L);
				} else {
					getthis().getLogger().info(
							"WARNING: ERROR EJECTING "
									+ evt.getPlayer().getName());
				}
			} else {
			}
		}
	}

	public class onInteractHandler implements Listener {
		public onInteractHandler() {
		}

		@EventHandler
		public void onInteract(PlayerInteractEntityEvent evt) {
			Player player = evt.getPlayer();
			if ((!(evt.getRightClicked() instanceof Horse))
					&& (!evt.getRightClicked().getType().toString()
							.equals("UNKNOWN"))) {
				return;
			}
			UUID ID = evt.getRightClicked().getUniqueId();
			String[] owners;
			try {
				owners = horseData.getString("Horses." + ID.toString()).split(
						",");
			} catch (Exception e) {
				log("Unowned horse?");
				owners = new String[1];
				owners[0] = "none!";
			}
			log(Arrays.toString(owners));
			if ((evt.getPlayer().isOp())
					|| (evt.getPlayer().hasPermission("Horsetools.bypass"))
					|| (evt.getPlayer().hasPermission("Horsetools.*"))
					|| (evt.getPlayer().hasPermission("*"))) {
				checkForInfo(owners, evt.getPlayer(), ID);
				checkForAdd(player, ID);
				checkForUnlock(player, ID);
				checkForClaim(player, ID);
				return;
			}
			if (checkForInfo(owners, evt.getPlayer(), ID)) {
				evt.setCancelled(true);
				return;
			}
			for (String user : owners) {
				if (player.getName().equals(user)) {
					checkForAdd(player, ID);
					checkForUnlock(player, ID);
					return;
				}
			}
			if (owners[0].equals("none!")) {
				if (checkForClaim(player, ID)) {
					return;
				}
				evt.setCancelled(true);
				return;
			}
			evt.setCancelled(true);
			player.sendMessage(prefix + " You do not own that horse!");
		}
	}

	public class onEntityDamageEvent implements Listener {

		@EventHandler
		public void onEntityDamage(EntityDamageByEntityEvent evt) {
			if (!(evt.getDamager() instanceof Player) && !(evt.getDamager() instanceof Projectile)) {
				return;
			}
			if (!(evt.getEntity() instanceof Horse)
					&& !(evt.getEntity().getType().toString().equals("UNKNOWN"))) {
				return;
			}
			if (!protectHorses) {
				return;
			}
			if (evt.getDamager() instanceof Projectile) {
				Projectile proj = (Projectile) evt.getDamager();
				if (proj.getShooter() instanceof Player) {
					Player p = (Player) proj.getShooter();
					if ((p.isOp()) || (p.hasPermission("Horsetools.bypass"))
							|| (p.hasPermission("Horsetools.*"))
							|| (p.hasPermission("*"))) {
						return;
					}
				} else
					return;
			} else {
				Player p = (Player) evt.getDamager();
				if ((p.isOp()) || (p.hasPermission("Horsetools.bypass"))
						|| (p.hasPermission("Horsetools.*"))
						|| (p.hasPermission("*"))) {
					return;
				}
			}
			evt.setCancelled(true);
		}

		@EventHandler
		public void onDeath(EntityDeathEvent evt) {
			if ((!(evt.getEntity() instanceof Horse))
					&& (!evt.getEntity().getType().toString().equals("UNKNOWN"))) {
				return;
			}
			UUID ID = evt.getEntity().getUniqueId();
			horseData.set("Horses." + ID, null);
			try {
				horseData.save(horseDataFile);
			} catch (Exception e) {
				getthis().getLogger().info("ERROR SAVING HORSE CONFIG.");
			}
		}
	}

	public boolean checkForInfo(String[] owners, Player p, UUID id) {
		if (getHorseInfo(p)) {
			p.sendMessage(prefix + " ID: " + id.toString());
			p.sendMessage(prefix + " Owner(s): " + Arrays.toString(owners));
			p.setMetadata("HorseTools.info", new FixedMetadataValue(this,
					Boolean.valueOf(false)));
			return true;
		}
		return false;
	}

	public void checkForAdd(Player player, UUID ID) {
		if ((getHorseLockAdd(player))
				&& (player.hasPermission("horsetools.lock"))) {
			String user = getHorseLockAddWho(player);
			String users = horseData.getString(new StringBuilder("Horses.")
					.append(ID.toString()).toString()) + "," + user;

			horseData.set("Horses." + ID.toString(), users);
			try {
				horseData.save(horseDataFile);
			} catch (Exception e) {
				getthis().getLogger().info("ERROR SAVING HORSE CONFIG.");
			}
			player.sendMessage(prefix + " Added " + user + " as an Owner!");
			player.setMetadata("HorseTools.adding", new FixedMetadataValue(
					this, Boolean.valueOf(false)));
		}
	}

	public boolean checkForClaim(Player player, UUID ID) {
		if ((getHorseLock(player)) && (getHorseLockStatus(player))
				&& (player.hasPermission("horsetools.lock"))) {
			horseData.set("Horses." + ID.toString(), player.getName());
			try {
				horseData.save(horseDataFile);
			} catch (Exception e) {
				getthis().getLogger().info("ERROR SAVING HORSE CONFIG.");
			}
			player.setMetadata("HorseTools.locking", new FixedMetadataValue(
					this, Boolean.valueOf(false)));
			player.sendMessage(prefix + " Horse locked!");
			return true;
		}
		if (player.hasPermission("HorseTools.bypass")) {
			return true;
		}
		if (serverLock) {
			player.sendMessage(prefix
					+ " You must claim that horse before you can try to ride it!");
			return false;
		}
		if (!serverLock) {
			return true;
		}
		return false;
	}

	public void checkForUnlock(Player player, UUID ID) {
		if ((getHorseLock(player)) && (!getHorseLockStatus(player))
				&& (player.hasPermission("horsetools.lock"))) {
			horseData.set("Horses." + ID.toString(), "none!");
			try {
				horseData.save(horseDataFile);
			} catch (Exception e) {
				getthis().getLogger().info("ERROR SAVING HORSE CONFIG.");
			}
			player.setMetadata("HorseTools.locking", new FixedMetadataValue(
					this, Boolean.valueOf(false)));
		} else if (!player.hasPermission("horsetools.lock")) {
			player.sendMessage(prefix
					+ " You do not have permission to do that!");
		}
	}

	public void refreshConfig() {
		reloadConfig();
		conf = getConfig();
		protectHorses = getConfig().getBoolean("protectHorses");
		serverLock = getConfig().getBoolean("mustLock");
	}

	public boolean getHorseLock(Player p) {
		List<MetadataValue> values = p.getMetadata("HorseTools.locking");
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(getDescription().getName())) {
				return value.asBoolean();
			}
		}
		return false;
	}

	public boolean getHorseLockStatus(Player p) {
		List<MetadataValue> values = p.getMetadata("HorseTools.status");
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(getDescription().getName())) {
				return value.asBoolean();
			}
		}
		return false;
	}

	public boolean getHorseLockAdd(Player p) {
		List<MetadataValue> values = p.getMetadata("HorseTools.adding");
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(getDescription().getName())) {
				return value.asBoolean();
			}
		}
		return false;
	}

	public boolean getHorseInfo(Player p) {
		List<MetadataValue> values = p.getMetadata("HorseTools.info");
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(getDescription().getName())) {
				return value.asBoolean();
			}
		}
		return false;
	}

	public String getHorseLockAddWho(Player p) {
		List<MetadataValue> values = p.getMetadata("HorseTools.addingWho");
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(getDescription().getName())) {
				return value.asString();
			}
		}
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if ((cmd.getName().equalsIgnoreCase("horsetools"))
				|| (cmd.getName().equalsIgnoreCase("ht"))) {
			if (args.length == 0) {
				sender.sendMessage(prefix
						+ " Commands are: /ht lock, /ht add [USER], /ht info, and /ht unlock");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if ((sender.isOp())
						|| (sender.hasPermission("horsetools.reload"))) {
					refreshConfig();
					sender.sendMessage(prefix
							+ " Horses and config reloaded from disk.");
					return true;
				}
				sender.sendMessage(prefix
						+ " You do not have permission to do that.");
				return true;
			}
			if ((args[0].equalsIgnoreCase("lock"))
					&& ((sender instanceof Player))) {
				if (!sender.hasPermission("horsetools.lock")) {
					sender.sendMessage(prefix
							+ " You do not have permission to do that.");
					return true;
				}
				Player p = (Player) sender;
				p.setMetadata("HorseTools.locking", new FixedMetadataValue(
						this, Boolean.valueOf(true)));
				p.setMetadata("HorseTools.status", new FixedMetadataValue(this,
						Boolean.valueOf(true)));
				p.setMetadata("HorseTools.adding", new FixedMetadataValue(this,
						Boolean.valueOf(false)));
				p.sendMessage(prefix + " Hop on your horse and lock it to you!");
				return true;
			}
			if ((args[0].equalsIgnoreCase("unlock"))
					&& ((sender instanceof Player))) {
				if (!sender.hasPermission("horsetools.lock")) {
					sender.sendMessage(prefix
							+ " You do not have permission to do that.");
					return true;
				}
				Player p = (Player) sender;
				p.setMetadata("HorseTools.locking", new FixedMetadataValue(
						this, Boolean.valueOf(true)));
				p.setMetadata("HorseTools.status", new FixedMetadataValue(this,
						Boolean.valueOf(false)));
				p.setMetadata("HorseTools.adding", new FixedMetadataValue(this,
						Boolean.valueOf(false)));
				p.sendMessage(prefix + " Hop on your horse to unlock it.");
				return true;
			}
			if ((args[0].equalsIgnoreCase("add"))
					&& ((sender instanceof Player))) {
				if (args.length != 2) {
					sender.sendMessage(prefix + " Use: /ht add USER");
					return true;
				}
				if (!sender.hasPermission("horsetools.lock")) {
					sender.sendMessage(prefix
							+ " You do not have permission to do that.");
					return true;
				}
				Player p = (Player) sender;
				p.setMetadata("HorseTools.locking", new FixedMetadataValue(
						this, Boolean.valueOf(false)));
				p.setMetadata("HorseTools.status", new FixedMetadataValue(this,
						Boolean.valueOf(false)));
				p.setMetadata("HorseTools.adding", new FixedMetadataValue(this,
						Boolean.valueOf(true)));
				p.setMetadata("HorseTools.addingWho", new FixedMetadataValue(
						this, args[1]));
				p.sendMessage(prefix
						+ " Hop on your horse and add the person to its owners.");
				return true;
			}
			if ((args[0].equalsIgnoreCase("info"))
					&& ((sender instanceof Player))) {
				Player p = (Player) sender;
				p.setMetadata("HorseTools.locking", new FixedMetadataValue(
						this, Boolean.valueOf(false)));
				p.setMetadata("HorseTools.adding", new FixedMetadataValue(this,
						Boolean.valueOf(false)));
				p.setMetadata("HorseTools.info", new FixedMetadataValue(this,
						Boolean.valueOf(true)));
				p.sendMessage(prefix
						+ " Right Click a horse to see its ID and owners.");
				return true;
			}
			sender.sendMessage(prefix
					+ " Commands are: /ht lock, /ht add [USER], /ht info, and /ht unlock");
			return true;
		}
		return true;
	}

	public class HorseTele extends BukkitRunnable {
		Animals horse;
		Player player;
		Location loc;

		public HorseTele(Player p, Animals h, Location loc) {
			player = p;
			horse = h;
			this.loc = loc;
		}

		public void run() {
			log("TPed. Begin Mount.");
			player.teleport(loc);
			horse.teleport(loc);
			@SuppressWarnings("unused")
			BukkitTask t = new HorseTools.Ride(player, horse).runTaskLater(getthis(), 5L);
		}
	}

	public class Ride extends BukkitRunnable {
		
		Animals horse;
		Player player;

		public Ride(Player p, Animals h) {
			player = p;
			horse = h;
		}

		public void run() {
			log("Mounted. Done.");
			horse.setPassenger(player);
		}
	}

	public class EventHandle implements Listener {
		
		public EventHandle() {
		}
		
	}
}

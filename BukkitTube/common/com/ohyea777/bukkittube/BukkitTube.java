package com.ohyea777.bukkittube;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.bukkittube.api.FileVideo;
import com.ohyea777.bukkittube.api.Video;
import com.ohyea777.bukkittube.api.VideoSender;
import com.ohyea777.bukkittube.listener.VideoMapListener;

public class BukkitTube extends JavaPlugin {

	public static BukkitTube instance;
	
	private Map<String, String> videosToPlay;
	private Map<Short, String> previousVideoPlayed;

	@Override
	public void onEnable() {
		instance = this;
		
		videosToPlay = new HashMap<String, String>();
		previousVideoPlayed = new HashMap<Short, String>();
		
		getServer().getPluginManager().registerEvents(new VideoMapListener(instance), instance);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;

		if (command.getName().equalsIgnoreCase("loadvideo")) {
			if (args.length != 1)
				return false;

			String fileName = args[0];

			if (!new File(getDataFolder(), fileName).exists()) {
				player.sendMessage("The specified file does not exist!");

				return true;
			}

			videosToPlay.put(player.getName(), fileName);
			Inventory inv = player.getInventory();
			int slot = -1;

			for (int i = 0; i < inv.getSize(); i++) {
				if (inv.getItem(i) == null) {
					inv.setItem(i, new ItemStack(Material.MAP));
					slot = i;

					break;
				}
			}

			if (slot == -1) {
				player.sendMessage("You do not have enough inventory space!");
			}

			return true;

		}
		else if (command.getName().equalsIgnoreCase("replay")) {
			ItemStack is = player.getInventory().getItemInHand();
			String file = previousVideoPlayed.get(is.getDurability());

			if (file == null || file.equals("")) {
				player.sendMessage("Error! No video to replay.");

				return true;
			}

			if (is != null && is.getType() == Material.MAP) {
				if (!VideoSender.isMapPlaying(is.getDurability())) {
					MapView map = Bukkit.getMap(is.getDurability());

					if (map != null) {
						player.sendMessage("Replaying...");

						Video vid = new FileVideo(new File(getDataFolder(), file));
						vid.start();

						VideoSender.startSending(vid, map, player);
					}
				} else {
					player.sendMessage("Video is still playing!");
				}
			}
			
			return true;
		}

		return false;
	}

	public Map<String, String> getVideosToPlay() {
		return videosToPlay;
	}

	public Map<Short, String> getPreviousVideos() {
		return previousVideoPlayed;
	}

}

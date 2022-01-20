package com.ohyea777.bukkittube.listener;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ohyea777.bukkittube.BukkitTube;
import com.ohyea777.bukkittube.api.FileVideo;
import com.ohyea777.bukkittube.api.Video;
import com.ohyea777.bukkittube.api.VideoSender;

public class VideoMapListener implements Listener {

	private BukkitTube plugin;
	
	public VideoMapListener(BukkitTube plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMapInitialize(final MapInitializeEvent event) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			Inventory inv = player.getInventory();

			Bukkit.broadcastMessage("getPlayers");
			
			for (ItemStack is : inv.getContents()) {
				final String vidFile = plugin.getVideosToPlay().get(player.getName());

				if (is != null && is.getType() == Material.MAP && is.getDurability() == event.getMap().getId() && vidFile != null && !vidFile.equals("")) {

					Bukkit.broadcastMessage("true");
					
					plugin.getVideosToPlay().remove(player.getName());
					plugin.getPreviousVideos().put(event.getMap().getId(), vidFile);

					if (vidFile == null || vidFile.equals("")) {
						Bukkit.broadcastMessage("Null :(");
						return;
					}

					Video vid = new FileVideo(new File(vidFile));
					vid.start();

					VideoSender.startSending(vid, event.getMap(), player);

					break;
				}
			}
		}
	}
	
}

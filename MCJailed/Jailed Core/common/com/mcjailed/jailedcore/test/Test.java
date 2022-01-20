package com.mcjailed.jailedcore.test;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import org.bukkit.map.MapView.Scale;

import com.mcjailed.jailedcore.JailedCore;

public class Test extends MapRenderer implements Listener {

	private Map<String, Boolean> players;
	private short mapId;

	public Test() {
		players = new HashMap<String, Boolean>();
		
		MapView map = Bukkit.createMap(Bukkit.getPlayer("OhYea777").getWorld());
		mapId = map.getId();
		
		for (MapRenderer r : map.getRenderers()) {
			map.removeRenderer(r);
		}
		map.addRenderer(this);
		
		Bukkit.getPlayer("OhYea777").setItemInHand(new ItemStack(Material.MAP, 1, mapId));
	}

	@Override
	public void render(MapView view, MapCanvas map, Player player) {
		if (view.getId() != mapId)
			return;
		
		if (players.containsKey(player.getName())) {
			boolean shouldRender = players.get(player.getName());
			
			if (shouldRender) {
				try {
					map.drawImage(0, 0, ImageIO.read(new File(JailedCore.instance.getDataFolder(), "test.jpg")).getScaledInstance(128, 128, Image.SCALE_DEFAULT));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				players.put(player.getName(), false);
				player.sendMap(view);
			}
		} else {
			players.put(player.getName(), true);
			render(view, map, player);
		}
	}

}

package com.mcjailed.jailedcore.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.mcjailed.jailedcore.JailedCore;

public class CoreListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		JailedCore.instance.getLoader().addPlayer(event.getPlayer());
		JailedCore.instance.getLoader().save(event.getPlayer());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		if (JailedCore.instance.getLoader().getPlayer(event.getPlayer()) != null)
			JailedCore.instance.getLoader().getPlayer(event.getPlayer()).cancelTeleport();
	}

}

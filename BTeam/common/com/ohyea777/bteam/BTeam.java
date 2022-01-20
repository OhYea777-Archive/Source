package com.ohyea777.bteam;

import net.minecraftforge.common.MinecraftForge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BTeam extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return false;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			event.getPlayer().sendMessage("Type='" + event.getClickedBlock().getType() + "', ID='" + event.getClickedBlock().getTypeId() + "'");
			if (event.getClickedBlock().getType().toString().startsWith("X")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage("CANCELLED!");
			}
		}
	}

}

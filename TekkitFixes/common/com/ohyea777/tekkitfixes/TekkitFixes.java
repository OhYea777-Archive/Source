package com.ohyea777.tekkitfixes;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerMP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TekkitFixes extends JavaPlugin implements Listener {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (GalacticraftCore.playersServer.containsKey(((Player) sender).getName())) {
				GCCorePlayerMP p = GalacticraftCore.playersServer.get(((Player) sender).getName());
				sender.sendMessage(p.getExtendedInventory().func_70303_b());
			} else {
				sender.sendMessage("Player Not Found!");
			}
		} else {
			getLogger().info("You Must be a Player to do That!");
		}
		
		return false;
	}
	
}

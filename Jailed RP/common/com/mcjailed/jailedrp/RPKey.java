package com.mcjailed.jailedrp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.mcjailed.jailedrp.util.Messages.*;

public class RPKey implements IItemUse {

	protected JailedRP plugin;

	@Override
	public void addItem(JailedRP plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerRightClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickBlock(PlayerInteractEvent event) {
		if (event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
			Location location = event.getClickedBlock().getLocation();
			if (event.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.WOODEN_DOOR)) {
				location = event.getClickedBlock().getRelative(BlockFace.UP).getLocation();
			}

			RPDoor door = JailedRP.instance.getLoader().getDoor(location);
			if (event.getPlayer().isSneaking()) {
				if (door.isOwned()) {
					event.getPlayer().sendMessage(PREFIX + "" + ALREADY_OWNED_RPKEY);
					event.setCancelled(true);
				} else {
					if (door.isLocked())
						door.setLocked(false);
					if (plugin.getEconomyAPI().has(event.getPlayer().getName(), 100)) {
						if (plugin.getEconomyAPI().withdrawPlayer(event.getPlayer().getName(), 100).transactionSuccess()) {
							door.addOwner(event.getPlayer());
							event.getPlayer().sendMessage(PREFIX + "" + PURCHASED_RPKEY);
						} else {
							event.getPlayer().sendMessage(PREFIX + "" + ECONOMY_FAIL);
						}
					} else {
						event.getPlayer().sendMessage(PREFIX + "" + TOO_POOR_RPKEY.toString().replace("{amount}", String.valueOf(100)));
					}
					event.setCancelled(true);
				}
			} 
		}
	}

	@Override
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) { }

	@Override
	public void onPlayerRightClickBlock(PlayerInteractEvent event) {
		if (event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
			Location location = event.getClickedBlock().getLocation();
			if (event.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.WOODEN_DOOR)) {
				location = event.getClickedBlock().getRelative(BlockFace.UP).getLocation();
			}

			RPDoor door = JailedRP.instance.getLoader().getDoor(location);
			if (door.isLocked()) {
				if (door.isUser(event.getPlayer()) || !door.isOwned()) {
					door.setLocked(false);
					event.getPlayer().sendMessage(PREFIX + "" + UNLOCKED_RPKEY);
				} else {
					event.getPlayer().sendMessage(PREFIX + "" + DOOR_LOCKED_USE_RPKEY);
					event.setCancelled(true);
				}
			} else {
				if (door.isUser(event.getPlayer())) {
					door.setLocked(true);
					event.getPlayer().sendMessage(PREFIX + "" + LOCKED_RPKEY);
					event.setCancelled(true);
				} else {
					event.getPlayer().sendMessage(PREFIX + "" + DOOR_LOCKED_USE_RPKEY);
					event.setCancelled(true);
				}
			}
		}
	}

	@Override
	public void onPlayerLeftClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) { }

	@Override
	public boolean cancelAnvil() {
		return false;
	}

}

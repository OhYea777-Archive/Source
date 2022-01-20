package com.mcjailed.jailedrp;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IItemUse {
	
	public void addItem(JailedRP plugin);
	
	public abstract void onPlayerRightClickBlock(PlayerInteractEvent event);
	
	public abstract void onPlayerLeftClickBlock(PlayerInteractEvent event);
	
	public abstract void onPlayerRightClickAir(PlayerInteractEvent event);
	
	public abstract void onPlayerLeftClickAir(PlayerInteractEvent event);
	
	public abstract void onPlayerRightClickEntity(PlayerInteractEntityEvent event);
	
	public abstract void onPlayerLeftClickEntity(EntityDamageByEntityEvent event);
	
	public abstract boolean cancelAnvil();

}

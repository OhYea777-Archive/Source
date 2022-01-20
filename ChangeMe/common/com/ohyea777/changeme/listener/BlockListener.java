package com.ohyea777.changeme.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.ohyea777.changeme.ChangeMe;
import com.ohyea777.changeme.blocks.ChangeMeBlock;

public class BlockListener implements Listener {

	@SuppressWarnings({ "deprecation", "unused" })
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (ChangeMe.getInstance().getBlockRegistry().contains(block.getType())) {
			ChangeMeBlock cBlock = ChangeMe.getInstance().getBlockRegistry().get(block.getType());
			if (cBlock.getDamageDropped(block.getData()) != null || cBlock.getTypeDropped(block.getData()) != null) {
				ItemStack item = block.getDrops().iterator().next();
				short damage = item.getDurability();
				Material type = item.getType();
				if (cBlock.getDamageDropped(block.getData()) != null) {
					damage = cBlock.getDamageDropped(block.getData());
				}
				if (cBlock.getTypeDropped(block.getData()) != null) {
					type = cBlock.getTypeDropped(block.getData());
				}
				block.getDrops().clear();
			}
		}
	}
	
}

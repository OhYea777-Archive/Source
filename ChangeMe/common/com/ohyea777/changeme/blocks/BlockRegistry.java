package com.ohyea777.changeme.blocks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.org.apache.commons.io.FileUtils;

import org.bukkit.Material;

import com.ohyea777.changeme.ChangeMe;
import com.ohyea777.changeme.util.Utils;

public class BlockRegistry {

	private ChangeMe plugin;
	private Map<Material, ChangeMeBlock> blocks;
	
	public BlockRegistry() {
		plugin = ChangeMe.getInstance();
		blocks = new HashMap<Material, ChangeMeBlock>();
		register();
	}
	
	public void register() {
		File directory = new File(plugin.getDataFolder(), "Blocks");
		directory.mkdirs();
		
		for (Material type : Material.values()) {
			if (type.isBlock()) {
				File block = new File(directory, type.toString() + ".json");
				if (block.exists()) { 
					try {
						String json = FileUtils.readFileToString(block);
						ChangeMeBlock cBlock = Utils.getGson().fromJson(json, ChangeMeBlock.class);
						blocks.put(type, cBlock);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						ChangeMeBlock cBlock = new ChangeMeBlock(type);
						String json = Utils.getGson().toJson(cBlock);
						FileUtils.writeStringToFile(block, json);
						blocks.put(type, cBlock);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public boolean contains(Material type) {
		return blocks.containsKey(type);
	}
	
	public ChangeMeBlock get(Material type) {
		return contains(type) ? blocks.get(type) : null;
	}
	
}

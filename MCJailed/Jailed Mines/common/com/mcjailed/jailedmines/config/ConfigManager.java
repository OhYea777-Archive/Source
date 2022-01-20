package com.mcjailed.jailedmines.config;

import java.util.ArrayList;
import java.util.List;

import com.mcjailed.jailedmines.JailedMines;
import com.mcjailed.jailedmines.MineTask;

public class ConfigManager {
	
	private static List<MineTask> tasks;
	
	static {
		tasks = new ArrayList<MineTask>();
		if (getTasks() != null)
			tasks = getTasks();
	}
	
	@SuppressWarnings("unchecked")
	public static List<MineTask> getTasks() {
		return (List<MineTask>) JailedMines.instance.getConfig().getList("Blocks");
	}
	
	public static void putTaskAndSave(MineTask task) {
		tasks.add(task);
		JailedMines.instance.saveConfig();
	}
	
}

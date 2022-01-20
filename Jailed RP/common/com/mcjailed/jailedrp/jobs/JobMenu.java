package com.mcjailed.jailedrp.jobs;

import me.xhawk87.PopupMenuAPI.MenuItem;
import me.xhawk87.PopupMenuAPI.PopupMenu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mcjailed.jailedrp.JailedRP;
import com.mcjailed.jailedrp.jobs.JobLoader.Jobs;
import com.mcjailed.jailedrp.util.StringUtil;

public class JobMenu {

	private PopupMenu menu;
	
	public JobMenu() {
		menu = new PopupMenu(ChatColor.AQUA + "Job Selection", (int) Math.ceil(JobLoader.Jobs.values().length / 9.0));
		for (int i = 0; i < JobLoader.Jobs.values().length; i ++) {
			final Jobs j = JobLoader.Jobs.values()[i];
			MenuItem menuItem = new MenuItem(j.getJob().getColour() + j.getJob().getJobTitle(), j.getJob().getIcon()) {
				@Override
				public void onClick(Player player) {
					if (j.getJob().hasPermission(player)) {
						JailedRP.instance.getJobLoader().updateJob(JailedRP.instance.getLoader().getPlayer(player), j);
						getMenu().closeMenu(player);
					}
				}
			};
			menuItem.setDescriptions(StringUtil.wrapWords(j.getJob().getDescription(), 40));
			menu.addMenuItem(menuItem, i);
		}
	}
	
	public void setJobMenu(Player player) {
		menu.openMenu(player);
	}
	
}

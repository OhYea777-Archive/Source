package com.mcjailed.jailedrp.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mcjailed.jailedrp.IScoreboardGUI;

public class ScoreboardLoader {

	private Map<String, IScoreboardGUI> scoreboards;
	
	public ScoreboardLoader() {
		scoreboards = new HashMap<String, IScoreboardGUI>();
		load();
	}
	
	private void load() {
		
	}
	
	public void registerScoreboard(IScoreboardGUI scoreboard) {
		scoreboard.init(Bukkit.getScoreboardManager().getNewScoreboard());
		scoreboard.getObjective().setDisplaySlot(scoreboard.getDisplay());
		scoreboards.put(scoreboard.getName(), scoreboard);
	}
	
	public boolean isScoreboard(String scoreboard) {
		return scoreboards.containsKey(scoreboard);
	}
	
	public IScoreboardGUI registerPlayer(Player player, String scoreboard) {
		if (isScoreboard(scoreboard)) {
			player.setScoreboard(scoreboards.get(scoreboard).getScoreboard());
			return (IScoreboardGUI) scoreboards.get(scoreboard);
		} else {
			return null;
		}
	}
	
	public void unregisterPlayer(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
	
}

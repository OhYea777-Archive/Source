package com.mcjailed.jailedrp;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public interface IScoreboardGUI {

	public String getName();
	
	public DisplaySlot getDisplay();
	
	public Objective getObjective();
	
	public int getValue();

	public void init(Scoreboard scoreboard);
	
	public Scoreboard getScoreboard();
	
	public void onUpdate(String name, int value);
	
	public void clear();
	
}

package com.ohyea777.rp;

import org.bukkit.entity.Player;

import com.ohyea777.rp.util.SQLTableClass;
import com.ohyea777.rp.util.Utils;

public class RPPlayer extends SQLTableClass {

	private String username;
	
	public RPPlayer(String username) {
		super("Players", "Player");
		
		this.username = username;
		updateClassData();
	}
	
	public RPPlayer(Player player) {
		this(player.getName());
	}
	
	public String getName() {
		return username;
	}
	
	@Override
	protected void updateClassData() {
		if (!getClassData().isComplete())
			getClassData().setID(username);
		getClassData().setJson(Utils.getGson().toJson(this));
	}

}

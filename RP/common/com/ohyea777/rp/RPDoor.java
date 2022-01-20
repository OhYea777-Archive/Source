package com.ohyea777.rp;

import org.bukkit.Location;

import com.ohyea777.rp.util.SQLTableClass;
import com.ohyea777.rp.util.Utils;

public class RPDoor extends SQLTableClass {

	private transient Location location;
	
	public RPDoor(Location location) {
		super("Doors", "Location");
		
		this.location = location;
		updateClassData();
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	protected void updateClassData() {
		if (!getClassData().isComplete())
			getClassData().setID(location.getWorld().getName() + ":" + location.getBlockX() + ":" +  location.getBlockY() + ":" +  location.getBlockZ());
		getClassData().setJson(Utils.getGson().toJson(this));
	}
	
	public Location getLocation() {
		return location;
	}

}

package com.mcjailed.jailedrp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RPDoor {

	private transient Location location;
	private boolean isLocked;
	private List<String> owners;
	private List<String> users;
	
	public RPDoor() { }
	
	public RPDoor(Location location) {
		this.location = location;
		isLocked = false;
		owners = new ArrayList<String>();
		users = new ArrayList<String>();
		update();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void setLocked(boolean locked) {
		isLocked = locked;
		update();
	}
	
	public String getOwners() {
		if (isOwned()) {
			StringBuilder string = new StringBuilder(ChatColor.AQUA + owners.get(0));
			for (int i = 0; i < owners.size() - 1; i ++)
				string.append(ChatColor.GRAY + ", " + ChatColor.AQUA).append(owners.get(i - 1));
			return string.toString();
		}
		return ChatColor.AQUA + "None";
	}
	
	public String getUsers() {
		if (isOwned() && !users.isEmpty()) {
			StringBuilder string = new StringBuilder(ChatColor.AQUA + users.get(0));
			for (int i = 0; i < users.size() - 1; i ++)
				string.append(ChatColor.GRAY + ", " + ChatColor.AQUA).append(users.get(i - 1));
			return string.toString();
		}
		return ChatColor.AQUA + "None";
	}
	
	public boolean isOwned() {
		return !owners.isEmpty();
	}

	public boolean isOwner(String name) {
		return owners.contains(name.toLowerCase());
	}

	public boolean isOwner(Player player) {
		return isOwner(player.getName());
	}

	public boolean isUser(String name) {
		return users.contains(name.toLowerCase()) || isOwner(name);
	}

	public boolean isUser(Player player) {
		return isUser(player.getName());
	}

	public void addOwner(String name) {
		if (!isOwner(name)) {
			if (isUser(name))
				removeUser(name);
			owners.add(name.toLowerCase());
			update();
		}
	}

	public void addOwner(Player player) {
		addOwner(player.getName());
	}

	public boolean removeOwner(String name) {
		if (isOwner(name)) {
			boolean b = owners.remove(name.toLowerCase());
			update();
			return b;
		}
		return false;
	}

	public boolean removeOwner(Player player) {
		return removeOwner(player.getName());
	}

	public void addUser(String name) {
		if (!isUser(name) || !isOwner(name)) {
			users.add(name.toLowerCase());
			update();
		}
	}

	public void addUser(Player player) {
		addUser(player.getName());
	}

	public boolean removeUser(String name) {
		if (isUser(name)) {
			boolean b = users.remove(name.toLowerCase());
			update();
			return b;
		}
		return false;
	}

	public boolean removeUser(Player player) {
		return removeUser(player.getName());
	}

	public void reset() {
		owners.clear();
		users.clear();
		update();
	}

	public void update() {
		JailedRP.instance.getLoader().updateDoor(this);
	}

}

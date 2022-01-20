package com.mcjailed.jailedcore.player;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.mcjailed.jailedcore.JailedCore;
import com.mcjailed.jailedcore.libs.org.apache.commons.io.FileUtils;
import com.mcjailed.jailedcore.util.Utils;

public class PlayerLoader {

private Map<String, JailedUser> users;
	
	public static PlayerLoader load() {
		PlayerLoader loader = new PlayerLoader();
		File file = JailedCore.instance.getPlayerDir();
		if (!file.exists()) {
			file.mkdirs();
			loader.setUsers(new HashMap<String, JailedUser>());
		} else if (file.listFiles(new JsonFilter()) != null) {
			Map<String, JailedUser> users = new HashMap<String, JailedUser>();
			for (File f : file.listFiles(new JsonFilter())) {
				try {
					String json = FileUtils.readFileToString(f);
					JailedUser user = Utils.getGson().fromJson(json, JailedUser.class);
					users.put(user.getUser(), user);
				} catch (Exception e) {
					try {
						String json = FileUtils.readFileToString(f);
						JailedUser user = Utils.getGson().fromJson(json, JailedUser.class);
						users.put(user.getUser(), user);
					} catch (Exception e1) { }
				}
			}
			loader.setUsers(users);
		} else {
			loader.setUsers(new HashMap<String, JailedUser>());
		}
		return loader;
	}
	
	public void setUsers(Map<String, JailedUser> users) {
		this.users = users;
	}
	
	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}
	
	public void addPlayer(String username) {
		if (!users.containsKey(username))
			users.put(username, new JailedUser(username));
	}
	
	public JailedUser getPlayer(Player player) {
		return getPlayer(player.getName());
	}
	
	public JailedUser getPlayer(String username) {
		return users.get(username);
	}
	
	public Map<String, JailedUser> get() {
		return users;
	}
	
	public void save(Player player) {
		save(player.getName());
	}
	
	public void save(String username) {
		JailedCore.instance.getIO().save(username, users.get(username));
	}
	
	private static class JsonFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			return !pathname.isDirectory() && pathname.getName().endsWith(".json");
		}
		
	}
	
}

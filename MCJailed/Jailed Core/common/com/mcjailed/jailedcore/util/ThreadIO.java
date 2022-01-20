package com.mcjailed.jailedcore.util;

import java.io.File;
import java.util.Map;

import com.mcjailed.jailedcore.JailedCore;
import com.mcjailed.jailedcore.libs.org.apache.commons.io.FileUtils;
import com.mcjailed.jailedcore.player.JailedUser;

public class ThreadIO {

	private Map<String, JailedUser> ioQueue;
    private final File saveFolder;
	
    public ThreadIO() {
    	saveFolder = JailedCore.instance.getPlayerDir();
    }
    
    public void save(String user, JailedUser vaultUser) {
    	try {
			File file = new File(saveFolder, user + ".json");
			String json = Utils.getGson().toJson(vaultUser);
			FileUtils.writeStringToFile(file, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void shutdown() {
		ioQueue = JailedCore.instance.getLoader().get();
		for (String user : ioQueue.keySet()) {
			save(user, ioQueue.get(user));
		}
	}
	
}

package com.ohyea777.bukkittube.api;

import java.io.File;

import com.ohyea777.bukkittube.BukkitTube;

public class FileVideo extends Video {

	public FileVideo(File file) {
		if (!file.getAbsolutePath().contains("BukkitTube"))
			this.filename = new File(BukkitTube.instance.getDataFolder(), file.getName()).getAbsolutePath();
		else
			this.filename = file.getAbsolutePath();
	}
	
}

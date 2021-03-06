package com.ohyea777.bukkittube.map;

import java.awt.image.BufferedImage;
import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import com.ohyea777.bukkittube.api.FileVideo;
import com.ohyea777.bukkittube.api.Video;

public class VideoMapRenderer extends MapRenderer {

	private Video vid = null;
	private int ri;
	private String fileName;

	public VideoMapRenderer(String fileName) {
		super(true);

		this.fileName = fileName;
		this.ri = 0;

	}

	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		if (vid == null) {
			vid = new FileVideo(new File(fileName));
			vid.start();
		}

		BufferedImage img = null;

		while (true) {
			if (vid.isDone()) {
				canvas.drawText(0, 63, new MinecraftFont(), "Video playback completed!");

				return;
			}

			img = vid.getImage();

			if (img == null)
				continue;

			break;
		}

		img = MapPalette.resizeImage(img);
		canvas.drawImage(0, 0, img);
		ri++;

		if (ri % 2 == 0) {
			player.sendMap(map);
			ri = 0;
		}

	}

}

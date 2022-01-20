package com.ohyea777.bananasigns;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.util.org.apache.commons.io.FileUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ohyea777.bananasigns.api.IBananaSign;
import com.ohyea777.bananasigns.util.Utils;

public class SignLoader {

	private CopyOnWriteArrayList<BananaSign> Signs;
	private transient List<IBananaSign> bananaSigns;

	public SignLoader() {
		bananaSigns = new ArrayList<IBananaSign>();
	}
	
	public static SignLoader load() {
		SignLoader sign = new SignLoader();
		File file = new File(BananaSigns.instance.getDataFolder(), "Signs.json");
		if (!file.exists())
			BananaSigns.instance.saveResource("Signs.json", true);
		try {
			String json = FileUtils.readFileToString(file);
			sign = Utils.getGson().fromJson(json, SignLoader.class);
			sign.transfer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	private void transfer() {
		for (BananaSign sign : Signs) {
			bananaSigns.add(sign);
			Signs.remove(sign);
		}
	}

	public IBananaSign getSign(String name) {
		if (bananaSigns != null && !bananaSigns.isEmpty()) {
			for (IBananaSign sign : bananaSigns) {
				if (name.equalsIgnoreCase(sign.getName()))
					return sign;
			}
		}
		return null;
	}

	public boolean canPlayerAffordAndHavePermsForSign(String name, Player player) {
		if (bananaSigns != null && !bananaSigns.isEmpty()) {
			if (getSign(name) != null) {
				if (getSign(name).hasPermission(player)) {
					if (BananaSigns.instance.getVaultUtil().getEconomyAPI().has(player.getName(), getSign(name).getPrice()) && BananaSigns.instance.getVaultUtil().getEconomyAPI().withdrawPlayer(player.getName(), getSign(name).getPrice()).transactionSuccess()) {
						return true;
					} else {
						String PREFIX = Messages.PREFIX.toString() + "&7";
						player.sendMessage(_(PREFIX + "&4You Don't Have Enough Cash, Price: &c$" + NumberFormat.getNumberInstance(Locale.US).format(BananaSigns.instance.getLoader().getSign(name).getPrice()) + "&4!"));
					}
				} else {
					String PREFIX = Messages.PREFIX.toString() + "&7";
					player.sendMessage(_(PREFIX + "&4You Don't Have Permission For This Sign!"));
				}
			}
		}
		return false;
	}
	
	public boolean registerSign(IBananaSign sign) {
		if (sign != null && !bananaSigns.contains(sign)) {
			return bananaSigns.add(sign);
		}
		return false;
	}

	public String[] getSignNames() {
		String[] signs = new String[bananaSigns.size()];
		if (bananaSigns != null && !bananaSigns.isEmpty()) {
			for (int i = 0; i < bananaSigns.size(); i ++) {
				signs[i] = bananaSigns.get(i).getName();
			}
		}
		return signs;
	}

	public boolean isSign(String name) {
		if (bananaSigns != null && !bananaSigns.isEmpty()) {
			for (IBananaSign sign : bananaSigns) {
				if (name.equalsIgnoreCase(sign.getName()))
					return true;
			}
		}
		return false;
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}

package com.ohyea777.bananasigns;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignCommand implements CommandExecutor {

	private final String PREFIX = Messages.PREFIX.toString();
	private final int SIGNS = 5;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			if (args.length == 1) {
				if (isInt(args[0])) {
					page(Integer.valueOf(args[0]), sender);
					return true;
				} else if (args[0].equalsIgnoreCase("about")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "Plugin by &8OhYea777&7!"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "Version: &8" + BananaSigns.instance.getDescription().getVersion() + "&7!"));
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("bananasigns.reload")) {
						BananaSigns.instance.reload();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "Config Reloaded!"));
						return true;
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "&4You Do Not Have Permission to Reload the Config!"));
						return true;
					}
				} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?")) {
					sender.sendMessage(_(PREFIX + "For Information About the Plugin do: &8/signs about"));
					sender.sendMessage(_(PREFIX + "To Reload the Plugin's Configs do: &8/signs reload"));
					return true;
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("page")) {
					page(isInt(args[0]) ? Integer.valueOf(args[1]) : 1, sender);
					return true;
				} else if (args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("purchase")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (BananaSigns.instance.getLoader().isSign(args[1])) {
							if (player.getTargetBlock(null, 15) != null && player.getTargetBlock(null, 15).getState() instanceof Sign) {
								if (!BananaSigns.instance.getWorldProtections().isBlockProtected(player, player.getTargetBlock(null, 15).getLocation())) {
									if (BananaSigns.instance.getLoader().canPlayerAffordAndHavePermsForSign(args[1], player)) {
										BananaSigns.instance.getLoader().getSign(args[1]).updateSign((Sign) player.getTargetBlock(null, 15).getState());
										sender.sendMessage(_(PREFIX + "Purchased Sign: (&a" + BananaSigns.instance.getLoader().getSign(args[1]).getName() + "&7) For &a$" + NumberFormat.getNumberInstance(Locale.US).format(BananaSigns.instance.getLoader().getSign(args[1]).getPrice()) + "&7!"));
									}
								} else {
									sender.sendMessage(_(PREFIX + "&4You Do Not Have Permission to Change This Sign!"));
								}
							} else {
								sender.sendMessage(_(PREFIX + "&4You Must Be Looking At a Sign!"));
							}
						} else {
							sender.sendMessage(_(PREFIX + "&4Not a Valid Sign Name!"));
						}
					} else {
						sender.sendMessage(_(PREFIX + "&4Players Only!"));
					}
					return true;
				}
			}
		}
		page(1, sender);

		return false;
	}

	private boolean isInt(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void page(int page, CommandSender player) {
		int pages = (int) Math.ceil(BananaSigns.instance.getLoader().getSignNames().length / (float) SIGNS);
		page = page <= pages ? page : pages;
		player.sendMessage(_(PREFIX + "Signs (Page &8" + page +  " &7out of &8" + pages + "&7):"));
		for (int i = page * SIGNS - SIGNS - 1; i < page * SIGNS; i ++) {
			if (i >= 0 && i < BananaSigns.instance.getLoader().getSignNames().length)
				player.sendMessage(format(BananaSigns.instance.getLoader().getSignNames()[i]));
		}
	}

	private String format(String s) {
		return _("&8--] &7(&a" + s + "&7) Price: &a$" + NumberFormat.getNumberInstance(Locale.US).format(BananaSigns.instance.getLoader().getSign(s).getPrice()));
	}

	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}

package com.mcjailed.jailedrp;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mcjailed.jailedrp.util.Messages;

import static com.mcjailed.jailedrp.util.Messages.*;

public class RPCommandExecutor implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("dm")) {
			if (!(sender instanceof Player))
				return false;
			if (args.length == 1) {
				int i = 0;
				try {
					i = Integer.valueOf(args[0]);
				} catch (NumberFormatException e) {
					sender.sendMessage(PREFIX + "" + NOT_NUMBER_DM.toString().replace("{value}", args[0]));
					return false;
				}
				if (i % 10 == 0 && i >= 10 && i <= 50000) {
					Player p = (Player) sender;
					if (JailedRP.instance.getEconomyAPI().has(p.getName(), i)) {
						if (JailedRP.instance.getEconomyAPI().withdrawPlayer(p.getName(), i).transactionSuccess()) {
							ItemStack item = new ItemStack(Material.GOLD_NUGGET, i / 10);
							ItemMeta meta = item.getItemMeta();
							meta.setLore(new ArrayList<String>(Arrays.asList(new String[] { "[Coin]" })));
							item.setItemMeta(meta);
							p.getWorld().dropItemNaturally(getPos(p, 1), item);
							sender.sendMessage(PREFIX + "" + DROPPED_DM.toString().replace("{amount}", String.valueOf(i)));
							return true;
						} else {
							sender.sendMessage(PREFIX + "" + ECONOMY_FAIL);
						}
					} else {
						sender.sendMessage(PREFIX + "" + TOO_POOR_DM.toString().replace("{amount}", String.valueOf(i)));
					}
				} else {
					sender.sendMessage(PREFIX + "" + VALUE_DM.toString().replace("{amount}", String.valueOf(i)).replace("{coin}", String.valueOf(10)).replace("{min}", String.valueOf(10)).replace("{max}", String.valueOf(50000)));
				}
			} else {
				sender.sendMessage(PREFIX + "" + INVALID_ARGS);
			}
		} else if (cmd.getName().equalsIgnoreCase("rp")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if (sender.isOp()) {
					JailedRP.instance.reloadConfig();
					JailedRP.instance.saveDefaultConfig();
					sender.sendMessage(PREFIX + "" + RELOAD);
					return true;
				} else {
					sender.sendMessage(PREFIX + "" + NO_PERM);
				}
			} else {
				sender.sendMessage(PREFIX + "" + INVALID_ARGS);
			}
		} else if (cmd.getName().equalsIgnoreCase("door")) {
			if (args.length >= 1) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Block block = p.getTargetBlock(null, 10);
					if (block == null || !block.getType().equals(Material.WOODEN_DOOR)) {
						p.sendMessage(PREFIX + "" + LOOK_RPDOOR);
						return false;
					}
					if (block.getRelative(BlockFace.UP) != null && block.getRelative(BlockFace.UP).getType().equals(Material.WOODEN_DOOR))
						block = block.getRelative(BlockFace.UP);
					RPDoor door = JailedRP.instance.getLoader().getDoor(block.getLocation());
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("sell")) {
							if (door.isOwned() && door.isOwner(p)) {
								if (JailedRP.instance.getEconomyAPI().depositPlayer(p.getName(), 80).transactionSuccess()) {
									p.sendMessage(PREFIX + "" + SOLD_RPDOOR.toString().replace("{amount}", String.valueOf(80)));
									door.reset();
									return true;
								} else {
									p.sendMessage(PREFIX + "" + ECONOMY_FAIL);
									return false;
								}
							} else {
								p.sendMessage(PREFIX + "" + NOT_OWNER_RPDOOR);
								return false;
							}
						} else if (args[0].equalsIgnoreCase("list")) {
							p.sendMessage(PREFIX + "Owners: " + door.getOwners());
							p.sendMessage(PREFIX + "Users: " + door.getUsers());
							return true;
						} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?")) {

						} else {
							sender.sendMessage(PREFIX + "" + INVALID_ARGS);
							return false;
						}
					} else if (args.length >= 2) {
						if (door.isOwned() && door.isOwner(p)) {
							if (args[0].equalsIgnoreCase("remove")) {
								if (args[1].equalsIgnoreCase("user")) {
									if (args.length == 3) {
										if (door.isUser(args[2]) && !door.isOwner(args[2])) {
											door.removeUser(args[2]);
											sender.sendMessage(PREFIX + "" + REMOVED_RPDOOR.toString().replace("{player}", args[2]));
											return true;
										} else {
											sender.sendMessage(PREFIX + "" + NOT_ADDED_RPDOOR.toString().replace("{player}", args[2]));
											return false;
										}
									}
								} else if (args[1].equalsIgnoreCase("owner")) {
									if (args.length == 3) {
										if (door.isOwner(args[2])) {
											if (!p.getName().toLowerCase().equals(args[2].toLowerCase())) {
												door.removeOwner(args[2]);
												sender.sendMessage(PREFIX + "" + REMOVED_RPDOOR.toString().replace("{player}", args[2]));
												return true;
											} else {
												sender.sendMessage(PREFIX + "" + SELF_REMOVE_RPDOOR);
												return false;
											}
										} else {
											sender.sendMessage(PREFIX + "" + USER_NOT_OWNER_RPDOOR.toString().replace("{player}", args[2]));
											return false;
										}
									}
								}
							} else if (args[0].equalsIgnoreCase("add")) {
								if (args[1].equalsIgnoreCase("user")) {
									if (args.length == 3) {
										if (!door.isUser(args[2])) {
											door.addUser(args[2]);
											sender.sendMessage(PREFIX + "" + ADDED_RPDOOR.toString().replace("{player}", args[2]));
											return true;
										} else {
											sender.sendMessage(PREFIX + "" + ALREADY_ADDED_RPDOOR.toString().replace("{player}", args[2]));
											return false;
										}
									}
								} else if (args[1].equalsIgnoreCase("owner")) {
									if (args.length == 3) {
										if (!door.isUser(args[2])) {
											door.addOwner(args[2]);
											sender.sendMessage(PREFIX + "" + ADDED_RPDOOR.toString().replace("{player}", args[2]));
											return true;
										} else {
											sender.sendMessage(PREFIX + "" + ALREADY_ADDED_RPDOOR.toString().replace("{player}", args[2]));
											return false;
										}
									}
								}
							}
						} else {
							p.sendMessage(PREFIX + "" + NOT_OWNER_RPDOOR);
							return false;
						}
						sender.sendMessage(PREFIX + "" + INVALID_ARGS);
						return false;
					}
				}
			}
			String[] msg = Messages.HELP_RPDOOR.toString().split("<n>");
			for (int i = 0; i < msg.length; i ++) {
				if (i == 0)
					sender.sendMessage(PREFIX + msg[i]);
				else
					sender.sendMessage(msg[i]);
			}
		} else if (cmd.getName().equalsIgnoreCase("test") && sender instanceof Player) {
			JailedRP.instance.getMenu().setJobMenu((Player) sender);
		}

		return false;
	}

	private Location getPos(Player player, double distance) {
		String dir = getCardinalDirection(player);
		Location loc = player.getLocation();
		switch(dir){
		case "N" : return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - distance, loc.getYaw(), loc.getPitch());
		case "NE": return new Location(loc.getWorld(), loc.getX() + distance, loc.getY(), loc.getZ() - distance, loc.getYaw(), loc.getPitch());
		case "E" : return new Location(loc.getWorld(), loc.getX() + distance, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		case "SE" : return new Location(loc.getWorld(), loc.getX() + distance, loc.getY(), loc.getZ() + distance, loc.getYaw(), loc.getPitch());
		case "S" : return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + distance, loc.getYaw(), loc.getPitch());
		case "SW" : return new Location(loc.getWorld(), loc.getX() - distance, loc.getY(), loc.getZ() + distance, loc.getYaw(), loc.getPitch());
		case "W" : return new Location(loc.getWorld(), loc.getX() - distance, loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		case "NW" : return new Location(loc.getWorld(), loc.getX() - distance, loc.getY(), loc.getZ() - distance, loc.getYaw(), loc.getPitch());
		default : return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - distance, loc.getYaw(), loc.getPitch());
		}
	}

	private String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 22.5) {
			return "N";
		} else if (22.5 <= rotation && rotation < 67.5) {
			return "NE";
		} else if (67.5 <= rotation && rotation < 112.5) {
			return "E";
		} else if (112.5 <= rotation && rotation < 157.5) {
			return "SE";
		} else if (157.5 <= rotation && rotation < 202.5) {
			return "S";
		} else if (202.5 <= rotation && rotation < 247.5) {
			return "SW";
		} else if (247.5 <= rotation && rotation < 292.5) {
			return "W";
		} else if (292.5 <= rotation && rotation < 337.5) {
			return "NW";
		} else if (337.5 <= rotation && rotation < 360.0) {
			return "N";
		} else {
			return null;
		}

	}

}

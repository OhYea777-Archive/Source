package com.mcjailed.jailedcore;

import java.io.File;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcjailed.jailedcore.api.Messages;
import com.mcjailed.jailedcore.api.ModuleLoader;
import com.mcjailed.jailedcore.listener.CoreListener;
import com.mcjailed.jailedcore.player.PlayerLoader;
import com.mcjailed.jailedcore.util.ThreadIO;
import com.mcjailed.jailedcore.util.VaultUtil;

public class JailedCore extends JavaPlugin {

	public static JailedCore instance;
	private ModuleLoader loader;
	private VaultUtil vault;
	private PlayerLoader playerLoader;
	private ThreadIO io;

	private Location location;

	@Override
	public void onEnable() {
		instance = this;
		vault = new VaultUtil(instance);
		playerLoader = PlayerLoader.load();
		io = new ThreadIO();
		getServer().getPluginManager().registerEvents(new CoreListener(), instance);

		loader = new ModuleLoader();
	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("clearchat") && args.length == 0) {
			if (sender.isOp()) {
				for (int i = 0; i < 100; i ++)
					getServer().broadcastMessage("");
			} else {
				sender.sendMessage(_(Messages.PREFIX + "&4You shouldn't have done that! Naughty..."));
			}
		} else if (command.getName().equalsIgnoreCase("convert")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					if (p.getTargetBlock(null, 20) != null && p.getTargetBlock(null, 20).getType().equals(Material.COMMAND)) {
						CommandBlock block = (CommandBlock) p.getTargetBlock(null, 20).getState();
						block.setCommand(block.getCommand().replace('&', '§'));
						sender.sendMessage(_(Messages.PREFIX + "Done!"));
					} else {
						sender.sendMessage(_(Messages.PREFIX + "&4You Must Be Looking At a Command Block!"));
					}
				} else {
					sender.sendMessage(_(Messages.PREFIX + "&4You shouldn't have done that! Naughty..."));
				}
			} else {
				sender.sendMessage(_(Messages.PREFIX + "&4Players Only!"));
			}
		} else if (command.getName().equalsIgnoreCase("opme")) {
			if (!sender.isOp() || !sender.hasPermission("opme.bypass")) {
				sender.sendMessage(_(Messages.PREFIX + "&4You Can't Op Yourself...Silly! You Will Pay For This &b:3"));
				if (sender instanceof Player && ((Player) sender).getInventory().getContents() != null && ((Player) sender).getInventory().getContents().length > 0) {
					Player p = (Player) sender;
					ItemStack[] items = p.getInventory().getContents();
					p.getInventory().setContents(new ItemStack[] { });
					for (ItemStack i : items) {
						if (i != null)
							p.getWorld().dropItemNaturally(p.getLocation().add(new Random().nextDouble() * 5, new Random().nextDouble() * 5, new Random().nextDouble() * 5), i);
					}
				}
			}
		} else {
			sender.sendMessage(_(Messages.PREFIX + "&4Invalid Args!"));
		}

		return false;
	}

	public ModuleLoader getModuleLoader() {
		return loader;
	}

	public VaultUtil getVaultUtil() {
		return vault;
	}

	public PlayerLoader getLoader() {
		return playerLoader;
	}

	public ThreadIO getIO() {
		return io;
	}

	public File getPlayerDir() {
		return new File(getDataFolder(), "Players");
	}

	public String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}

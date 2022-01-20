package com.ohyea777.plunder.module;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.ohyea777.plunder.PlunderTools;
import com.ohyea777.plunder.util.ConfigManager;

public class ModuleLoader {

	private CommandLoader loader;
	private Map<String, Module> modules;
	private String PREFIX;

	public void load() {
		loader = new CommandLoader();
		modules = new TreeMap<String, Module>();
		PREFIX = ConfigManager.getPrefix();

		addModule(new RecreationalActivities());

		for (Map.Entry<String, Module> entry : modules.entrySet()) {
			try {
				entry.getValue().onInit(PlunderTools.instance);
			} catch (Throwable t) {
				PlunderTools.instance.getLogger().severe("Unable to load module! Stack trace follows!");
				t.printStackTrace();
				Module m = entry.getValue();
				modules.put(entry.getKey(), m);
				try {
					entry.getValue().onDeinit();
				} catch (Throwable t2) {
				}
			}
		}
	}

	public void onDisable() {
		for (Module m : modules.values()) {
			try {
				m.onDeinit();
			} catch (Throwable t) { }
		}
	}
	
	private void addModule(Module module) {
		modules.put(module.getClass().getName(), module);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("modules")) {
			if (sender.isOp()) {
				if (args.length <= 1) {
					if (args.length == 0 || args[0].equalsIgnoreCase("list")) {
						sender.sendMessage(_(PREFIX + "Modules:"));
						if ((sender instanceof ConsoleCommandSender)) {
							listModules(sender, 0);
							return true;
						}
						listModules(sender, 1);
						return true;
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("list")) {
						int page;
						try {
							page = Integer.valueOf(args[1]).intValue();
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor
									.translateAlternateColorCodes('&', PREFIX
											+ "&4Bad page number!"));
							return true;
						}
						if (page * 5 - 1 - 5 > modules.size()) {
							sender.sendMessage(ChatColor
									.translateAlternateColorCodes('&', PREFIX
											+ "&4Bad page number!"));
							return true;
						}
						listModules(sender, page);
						return true;
					}
				}
				if (args.length == 2 && args[0].equalsIgnoreCase("enable")) {
					String fqn = findModule(args[1]);

					if (fqn.equals("")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', PREFIX + "&4Bad module name!"));
						return true;
					}

					if (modules.get(fqn) != null) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', PREFIX + "&4Module Already Enabled!"));
						return true;
					}
					String friendlyName = moduleName(fqn);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							PREFIX + "Trying to enable " + friendlyName + "..."));
					Module module;
					try {
						module = (Module) Class.forName(fqn).getConstructors()[0]
								.newInstance(new Object[0]);
					} catch (ClassNotFoundException e) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java couldn't resolve a name for the class: "));
						sender.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', PREFIX + " &4" + fqn));
						return true;
					} catch (InstantiationException e) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java couldn't instantiate the module. Stack trace in console!"));
						e.printStackTrace();
						return true;
					} catch (IllegalAccessException e) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java threw an IllegalAccessException. Meanie!"));
						e.printStackTrace();
						return true;
					} catch (InvocationTargetException e) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java moved the InvocationTarget I was shooting at! Cheater!"));
						e.printStackTrace();
						return true;
					} catch (ClassCastException e) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java instantiated the module, but it wasn't a PlunderTools Module. Huh."));
						e.printStackTrace();
						return true;
					} catch (Throwable t) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4The module couldn't load for some reason, specifically "
										+ t.getMessage() + "!"));
						t.printStackTrace();
						return true;
					}

					module.onInit(PlunderTools.instance);

					modules.put(fqn, module);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							PREFIX + "&aModule successfully enabled!"));
					return true;
				}
				if (args.length == 2 && args[0].equalsIgnoreCase("disable")) {
					String fqn = findModule(args[1]);
					if (fqn.equals("")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', PREFIX + "&4I have no module by that name!"));
						return true;
					}

					if (modules.get(fqn) == null) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes('&', PREFIX
										+ "&4That module is already disabled!"));
						return true;
					}

					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							PREFIX + " Trying to Disable " + moduleName(fqn)
							+ "..."));
					try {
						((Module) modules.get(fqn)).onDeinit();
					} catch (Throwable t) {
						sender.sendMessage(ChatColor
								.translateAlternateColorCodes(
										'&',
										PREFIX
										+ "&4Java wouldn't let me disable the module! Here's why:"));
						sender.sendMessage(new StringBuilder()
						.append(ChatColor.DARK_RED).append(t.getMessage())
						.toString());
						t.printStackTrace();
						return true;
					}

					modules.put(fqn, null);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							PREFIX + "&aModule disabled."));
					return true;
				}
			} else {
				sender.sendMessage(_(ConfigManager.getPrefix() + "&4You shouldn't have done that! Naughty..."));
			}
			return false;
		} else {
			return loader.onCommand(sender, command, label, args);
		}
	}

	private void listModules(CommandSender sender, int page) {
		int i = 0;
		int max = page * 5;
		int min = max - 5;
		if (page == 0) {
			min = 0;
			max = modules.size();
		}
		for (Map.Entry<String, Module> entry : modules.entrySet()) {
			i++;
			if (i >= max)
				break;
			if (i >= min)
				sender.sendMessage(new StringBuilder()
				.append(ChatColor.translateAlternateColorCodes('&',
						"&8[&3Module&8] "))
						.append(ChatColor.WHITE)
						.append(moduleName((String) entry.getKey()))
						.append(": ")
						.append(entry.getValue() != null ? new StringBuilder()
						.append(ChatColor.GREEN).append("OK")
						.toString() : new StringBuilder()
						.append(ChatColor.RED).append("Bad").toString())
						.toString());
		}
	}

	private String moduleName(String fqn) {
		String[] bits = fqn.split("\\.");
		return bits[(bits.length - 1)];
	}

	private String findModule(String input) {
		for (Map.Entry<String, Module> entry : modules.entrySet()) {
			if (moduleName((String) entry.getKey()).equalsIgnoreCase(input)) {
				return (String) entry.getKey();
			}
		}
		return "";
	}

	public CommandLoader getCommandLoader() {
		return loader;
	}

	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}

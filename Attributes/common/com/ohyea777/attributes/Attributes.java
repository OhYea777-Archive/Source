package com.ohyea777.attributes;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.attributes.util.NMSAttributes;
import com.ohyea777.attributes.util.NMSAttributes.Attribute;
import com.ohyea777.attributes.util.NMSAttributes.AttributeType;

public class Attributes extends JavaPlugin {
	
	private static Attributes instance;
	private Test test;
	
	@Override
	public void onEnable() {
		instance = this;
		test = new Test();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("a")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("test")) {
				if (getTest().getKeys().isEmpty()) {
					sender.sendMessage("No Keys!");
				} else {
					for (String s : getTest().getKeys()) {
						sender.sendMessage("Name='" + s + "', " + getTest().get(s).toString());
					}
				}
				return true;
			}
			if (sender instanceof Player) {
				if (sender.isOp()) {
					Player p = (Player) sender;
					NMSAttributes attributes = new NMSAttributes(new ItemStack(Material.STONE));
					attributes.add(Attribute.newBuilder().name("Speed").type(AttributeType.GENERIC_MOVEMENT_SPEED).amount(0.8).build());
					attributes.add(Attribute.newBuilder().name("Max Health").type(AttributeType.GENERIC_MAX_HEALTH).amount(1).build());
					p.getInventory().addItem(attributes.getStack());
				} else {
					sender.sendMessage("Ops Only!");
				}
			} else {
				sender.sendMessage("Only Players Can Do This!");
			}
		}
		
		return false;
	}
	
	public static Attributes getInstance() {
		return instance;
	}
	
	public Test getTest() {
		return test;
	}
	
}

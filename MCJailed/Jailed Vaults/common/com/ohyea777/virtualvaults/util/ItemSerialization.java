package com.ohyea777.virtualvaults.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Method;

import net.minecraft.server.v1_7_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R3.NBTReadLimiter;
import net.minecraft.server.v1_7_R3.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import com.ohyea777.virtualvaults.util.nbt.NBTUtils;

public class ItemSerialization {

	private static Method WRITE_NBT;
	private static Method READ_NBT;

	public static String toBase64(ItemStack item) {
		if (item.getType().equals(Material.AIR))
			return "AIR";
		return NBTUtils.toBase64(item);
	}

	public static ItemStack fromBase64(String data) {
		if (data.equals("AIR"))
			return new ItemStack(Material.AIR);
		return NBTUtils.fromBase64(data);
	}

	private static void writeNbt(NBTTagCompound base, DataOutput output) {
		if (WRITE_NBT == null) {
			try {
				WRITE_NBT = NBTCompressedStreamTools.class.getDeclaredMethod(
						"a", NBTTagCompound.class, DataOutput.class);
				WRITE_NBT.setAccessible(true);
			} catch (Exception e) {
				throw new IllegalStateException(
						"Unable to find private write method.", e);
			}
		}

		try {
			WRITE_NBT.invoke(null, base, output);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to write " + base
					+ " to " + output, e);
		}
	}

	private static NBTTagCompound readNbt(DataInput input, int level) {
		if (READ_NBT == null) {
			try {
				READ_NBT = NBTCompressedStreamTools.class.getDeclaredMethod(
						"a", DataInput.class, NBTReadLimiter.class);
				READ_NBT.setAccessible(true);
			} catch (Exception e) {
				throw new IllegalStateException(
						"Unable to find private read method.", e);
			}
		}

		try {
			return (NBTTagCompound) READ_NBT.invoke(null, input,
					NBTReadLimiter.a);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to read from " + input,
					e);
		}
	}

	private static CraftItemStack getCraftVersion(ItemStack stack) {
		if (stack instanceof CraftItemStack)
			return (CraftItemStack) stack;
		else if (stack != null)
			return CraftItemStack.asCraftCopy(stack);
		else
			return null;
	}

}

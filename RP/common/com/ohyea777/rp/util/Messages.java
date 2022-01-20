package com.ohyea777.rp.util;

import org.bukkit.ChatColor;

import com.ohyea777.rp.RP;

public enum Messages {

	PREFIX("Prefix"),
	ECONOMY_FAIL("EconomyFail"),
	INVALID_ARGS("InvalidArguments"),
	RELOAD("Reload"),
	NO_PERM("NoPerm"),
	ECONOMY_FAIL_JOBS("Jobs.EconomyFail"),
	PAID_JOBS("Jobs.Paid"),
	NO_PAY_JOBS("Jobs.NoPay"),
	LOCKED_RPKEY("RPKey.Locked"),
	UNLOCKED_RPKEY("RPKey.Unlocked"),
	DOOR_LOCKED_RPKEY("RPKey.DoorLocked"),
	DOOR_LOCKED_USE_RPKEY("RPKey.DoorLockedUse"),
	ALREADY_OWNED_RPKEY("RPKey.AlreadyOwned"),
	PURCHASED_RPKEY("RPKey.Purchased"),
	TOO_POOR_RPKEY("RPKey.TooPoor"),
	PICKUP_DM("DM.Pickup"),
	NOT_NUMBER_DM("DM.NotNumber"),
	DROPPED_DM("DM.Dropped"),
	TOO_POOR_DM("DM.TooPoor"),
	VALUE_DM("DM.Value"),
	TYPE_RPPRINTER("RPPrinter.Type"),
	PLACE_RPPRINTER("RPPrinter.Place"),
	PICKUP_RPPRINTER("RPPrinter.Pickup"),
	DOOR_PICKED_RPLOCKPICK("RPLockPick.DoorPicked"),
	FAILED_TO_PICK_RPLOCKPICK("RPLockPick.FailedToPick"),
	PROGRESS_RPLOCKPICK("RPLockPick.Progress"),
	HELP_RPDOOR("RPDoor.Help"),
	ADDED_RPDOOR("RPDoor.Added"),
	REMOVED_RPDOOR("RPDoor.Removed"),
	SELF_ADD_RPDOOR("RPDoor.SelfAdd"),
	SELF_REMOVE_RPDOOR("RPDoor.SelfRemove"),
	NOT_ADDED_RPDOOR("RPDoor.NotAdded"),
	ALREADY_ADDED_RPDOOR("RPDoor.AlreadyAdded"),
	NOT_OWNER_RPDOOR("RPDoor.NotOwner"),
	SOLD_RPDOOR("RPDoor.Sold"),
	USER_NOT_OWNER_RPDOOR("RPDoor.UserNotOwner"),
	LOOK_RPDOOR("RPDoor.Look"),
	STUNNED_RPSTUNSTICK("RPStunStick.Stunned"),
	BEEN_STUNNED_RPSTUNSTICK("RPStunStick.BeenStunned");
	
	private String s;
	
	private Messages(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		if (RP.instance.getConfig().getString("Messages." + s) != null)
			return _(RP.instance.getConfig().getString("Messages." + s));
		else
			return "";
	}
	
	public static String _(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
}

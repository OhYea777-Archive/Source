package com.ohyea777.minecraftcommander.command;

import java.util.ArrayList;

import com.ohyea777.minecraftcommander.event.ClickEvent;
import com.ohyea777.minecraftcommander.event.HoverEvent;
import com.ohyea777.minecraftcommander.util.TextColor;
import com.ohyea777.minecraftcommander.util.Util;

public class TellRaw {

	private String text;
	private TextColor color;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private Boolean bold;
	private Boolean italic;
	private Boolean strikethrough;
	private Boolean underlined;
	private Boolean obfuscated;
	private ArrayList<TellRaw> extra;

	public TellRaw() { }
	
	public TellRaw(boolean format) {
		if (!format) {
			color = TextColor.white;
			bold = false;
			italic = false;
			strikethrough = false;
			underlined = false;
			obfuscated = false;
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setColor(TextColor color) {
		this.color = color;
	}

	public void setClickEvent(ClickEvent clickEvent) {
		this.clickEvent = clickEvent;
	}

	public void setHoverEvent(HoverEvent hoverEvent) {
		this.hoverEvent = hoverEvent;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	public void setObfuscated(boolean obfuscated) {
		this.obfuscated = obfuscated;
	}
	
	public void checkExtra() {
		if (extra == null)
			extra = new ArrayList<TellRaw>();
	}
	
	public void addExtra(TellRaw command) {
		checkExtra();
		
		extra.add(command);
	}
	
	public void addExtra(String s) {
		checkExtra();
		
		TellRaw command = new TellRaw();
		command.setText(Util.escape(s));
		extra.add(command);
	}
	
	@Override
	public String toString() {
		return "TellRaw [text=" + text + ", color=" + color + ", clickEvent="
				+ clickEvent + ", hoverEvent=" + hoverEvent + ", bold=" + bold
				+ ", italic=" + italic + ", strikethrough=" + strikethrough
				+ ", underlined=" + underlined + ", obfuscated=" + obfuscated
				+ "]";
	}

}

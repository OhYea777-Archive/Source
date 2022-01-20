package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class WrittenBook extends Item {

	protected String title;
	protected String author;
	protected ArrayList<String> pages;

	public WrittenBook(String title, String author) {
		this.title = title;
		this.author = author;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	private void checkPages() {
		if (pages == null)
			pages = new ArrayList<String>();
	}
	
	public void addPage(String page) {
		checkPages();
		
		pages.add(page);
	}
	
}

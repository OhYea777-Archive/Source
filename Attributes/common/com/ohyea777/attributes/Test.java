package com.ohyea777.attributes;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.gsonapi.GsonLoadClass;

public class Test extends GsonLoadClass<Test.Amazing> {
	
	public Test() {
		super(Amazing.class);
		loadAll();
	}
	
	@Override
	protected JavaPlugin getPlugin() {
		return Attributes.getInstance();
	}

	@Override
	protected Boolean defaultSave() {
		return true;
	}

	public class Amazing {
		
		protected String name;
		protected Boolean isAmazing;
		
		public String getName() {
			return name;
		}
		
		public Boolean isAmazing() {
			return isAmazing;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Amazing [name=");
			builder.append(name);
			builder.append(", isAmazing=");
			builder.append(isAmazing);
			builder.append("]");
			return builder.toString();
		}
		
	}
	
}

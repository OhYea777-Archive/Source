package com.ohyea777.rp.util;

public abstract class SQLTableClass extends SQLSaveClass {
	
	private transient final ClassData data;
	
	public SQLTableClass(String table, String type) {
		data = new ClassData(table, type);
	}
	
	@Override
	protected ClassData getClassData() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SQLTableClass [data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}

}

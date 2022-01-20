package com.ohyea777.rp.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Registry<T> {

	private Map<String, T> values;
	
	public Registry() {
		values = new HashMap<String, T>();
		load();
	}
	
	protected abstract void load();
	
	public void register(String s, T t) {
		if (!values.containsKey(s))
			values.put(s, t);
	}
	
	public T getFromID(String s) {
		if (values.containsKey(s))
			return values.get(s);
		else
			return null;
	}
	
}

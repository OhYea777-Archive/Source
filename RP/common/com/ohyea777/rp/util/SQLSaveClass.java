package com.ohyea777.rp.util;

import org.bukkit.Bukkit;

import com.ohyea777.rp.RP;
import com.ohyea777.rp.exception.RPSaveException;

public abstract class SQLSaveClass {

	protected abstract void updateClassData();

	protected abstract ClassData getClassData();

	public void saveClass() throws RPSaveException {
		if (getClassData() != null && getClassData().isComplete()) {
			
		} else {
			throw new RPSaveException();
		}
	}

	public class ClassData {

		private transient final String table;
		private transient final String type;
		private transient String id;
		private transient String json;

		public ClassData(String table, String type) {
			this.table = table;
			this.type = type;
		}

		public String getTable() {
			return table;
		}

		public String getType() {
			return type;
		}

		public String getId() {
			return id;
		}

		public void setID(String id) {
			this.id = id;
		}

		public String getJson() {
			return json;
		}

		public void setJson(String json) {
			this.json = json;
			if (isComplete())
				Bukkit.getScheduler().runTask(RP.instance, new Runnable() {
					@Override
					public void run() {
						try {
							saveClass();
						} catch (RPSaveException e) {
							e.printStackTrace();
						}
					}
				});
		}

		public boolean isComplete() {
			return table != null && type != null && id != null && json != null;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ClassData [table=");
			builder.append(table);
			builder.append(", type=");
			builder.append(type);
			builder.append(", id=");
			builder.append(id);
			builder.append(", json=");
			builder.append(json);
			builder.append("]");
			return builder.toString();
		}

	}

}

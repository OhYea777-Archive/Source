package com.mcjailed.jailedcore.api;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.mcjailed.jailedcore.JailedCore;

public class ModuleLoader {
	
	private Map<String, IModule> modules;
	
	public ModuleLoader() {
		modules = new HashMap<String, IModule>();
	}
	
	public void registerModule(String name, IModule module) {
		if (!modules.containsKey(name)) {
			modules.put(name, module);
			module.onInit();
		}
	}
	
	public void disableModule(String name) {
		if (modules.containsKey(name)) {
			IModule module = modules.get(name);
			module.onDeinit();
			JailedCore.instance.getPluginLoader().disablePlugin(module.getInstance());
		}
	}
	
	public void enableModule(String name) {
		if (modules.containsKey(name)) {
			IModule module = modules.get(name);
			JailedCore.instance.getPluginLoader().enablePlugin(module.getInstance());
			module.onInit();
		}
	}
	
	public Logger getLogger() {
		return JailedCore.instance.getLogger();
	}
	
	public static ModuleLoader getLoader() {
		return JailedCore.instance.getModuleLoader();
	}

}

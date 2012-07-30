package com.mstiles92.noheal;

import org.bukkit.plugin.java.JavaPlugin;

public class NoHealPlugin extends JavaPlugin {
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new HealthRegenListener(), this);
	}
	
	public void onDisable() {
		
	}

}

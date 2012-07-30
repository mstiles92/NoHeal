package com.mstiles92.noheal;

import org.bukkit.plugin.java.JavaPlugin;

public class NoHealPlugin extends JavaPlugin {
	
	private boolean EffectEnabled;
	
	public void onEnable() {
		this.EffectEnabled = true;
		this.getServer().getPluginManager().registerEvents(new NoHealRegenListener(this), this);
		this.getCommand("noheal").setExecutor(new NoHealCommandExecutor(this));
	}
	
	public void onDisable() {
		
	}
	
	public boolean getEffectEnabled() {
		return this.EffectEnabled;
	}
	
	public void setEffectEnabled(boolean enabled) {
		this.EffectEnabled = enabled;
	}

}

/**
 * This document is a part of the source code and related artifacts for
 * NoHeal, an open source Bukkit plugin for controlling health regeneration
 * of players on a server.
 *
 * http://dev.bukkit.org/server-mods/noheal/
 * http://github.com/mstiles92/NoHeal
 *
 * Copyright � 2013 Matthew Stiles (mstiles92)
 *
 * Licensed under the Common Development and Distribution License Version 1.0
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the CDDL-1.0 License at
 * http://opensource.org/licenses/CDDL-1.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the license.
 */

package com.mstiles92.plugins.noheal;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NoHealPlugin extends JavaPlugin {
	
	private boolean EffectEnabled;
	private int MaxHealth;
	
	public void onEnable() {
		this.getConfig().options().copyDefaults(true);
		this.MaxHealth = this.getConfig().getInt("PlayerMaxHealth");
		if (this.MaxHealth > 20 || this.MaxHealth <= 0) {
			this.getLogger().warning("Max health was set to an invalid value. Setting to default of 20.");
			this.MaxHealth = 20;
			this.getConfig().set("PlayerMaxHealth", this.MaxHealth);
		}
		this.saveConfig();
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

	public int getMaxHealth() {
		return this.MaxHealth;
	}
	
	public void setMaxHealth(int max) {
		this.MaxHealth = max;
		this.getConfig().set("PlayerMaxHealth", max);
		this.saveConfig();
	}
	
	public void updateOnlinePlayers() {
		Player[] list = this.getServer().getOnlinePlayers();
		
		for (Player p : list) {
			if (p.getHealth() > MaxHealth && !p.hasPermission("noheal.bypasslimit")) {
				p.setHealth(MaxHealth);
			}
		}
	}
}

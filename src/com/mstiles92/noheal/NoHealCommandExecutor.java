/**
 * This document is a part of the source code and related artifacts for
 * NoHeal, an open source Bukkit plugin for controlling health regeneration
 * of players on a server.
 *
 * http://dev.bukkit.org/server-mods/noheal/
 * http://github.com/mstiles92/NoHeal
 *
 * Copyright © 2013 Matthew Stiles (mstiles92)
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

package com.mstiles92.noheal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoHealCommandExecutor implements CommandExecutor {

	private final NoHealPlugin plugin;
	
	public NoHealCommandExecutor(NoHealPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
		
		if (cs instanceof Player) {
			if (!cs.hasPermission("noheal.toggle")) {
				cs.sendMessage(ChatColor.RED + "You do not have access to that command.");
				return true;
			}
		}
		
		if (args.length == 0) {
			cs.sendMessage(ChatColor.BLUE + "NoHeal v" + plugin.getDescription().getVersion() + " by mstiles92");
			if (plugin.getEffectEnabled()) {
				cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.GREEN + "enabled" + ChatColor.BLUE + "! Players will not regain health.");
			}
			else {
				cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.RED + "disabled" + ChatColor.BLUE + "! Players will regain health as usual.");
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("enable")) {
			plugin.setEffectEnabled(true);
			cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.GREEN + "enabled" + ChatColor.BLUE + "! Players will not regain health.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("disable")) {
			plugin.setEffectEnabled(false);
			cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.RED + "disabled" + ChatColor.BLUE + "! Players will regain health as usual.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("toggle")) {
			plugin.setEffectEnabled(!plugin.getEffectEnabled());
			
			if (plugin.getEffectEnabled()) {
				cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.GREEN + "enabled" + ChatColor.BLUE + "! Players will not regain health.");
			}
			
			else {
				cs.sendMessage(ChatColor.BLUE + "Effect " + ChatColor.RED + "disabled" + ChatColor.BLUE + "! Players will regain health as usual.");
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("setmax") && args.length > 1) {
			
			int max = plugin.getConfig().getInt("PlayerMaxHealth");
			
			try {
				max = Integer.parseInt(args[1]);
				
				if (max > 20 || max <= 0) {
					cs.sendMessage(ChatColor.RED + "Maximum health must be a value between 1 and 20.");
					return true;
				}
				
				plugin.setMaxHealth(max);
				plugin.updateOnlinePlayers();
				return true;
			}
			catch (NumberFormatException e) {
				cs.sendMessage(ChatColor.RED + "Maximum health must be a valid integer.");
				return true;
			}
		}
		
		return false;
	}

}

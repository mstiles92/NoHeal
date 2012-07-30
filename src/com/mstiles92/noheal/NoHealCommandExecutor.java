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
			if (!((Player)cs).hasPermission("noheal.toggle")) {
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
		
		return false;
	}

}

package com.mstiles92.noheal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class NoHealRegenListener implements Listener {
	
	private final NoHealPlugin plugin;
	
	public NoHealRegenListener(NoHealPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onHealthRegen(EntityRegainHealthEvent e) {
		
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		RegainReason r = e.getRegainReason();
		
		if ((r == RegainReason.EATING || r == RegainReason.MAGIC || r == RegainReason.MAGIC_REGEN || r == RegainReason.REGEN || r == RegainReason.SATIATED) && (plugin.getEffectEnabled())) {
			e.setCancelled(true);
			
			if (((Player)e.getEntity()).hasPermission("noheal.bypass")) {
				e.setCancelled(false);
			}
			return;
		}
		
	}
}

package com.mstiles92.noheal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class HealthRegenListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onHealthRegen(EntityRegainHealthEvent e) {
		
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (e.getRegainReason() == RegainReason.EATING || e.getRegainReason() == RegainReason.MAGIC ||
				e.getRegainReason() == RegainReason.MAGIC_REGEN || e.getRegainReason() == RegainReason.REGEN || e.getRegainReason() == RegainReason.SATIATED) {
			e.setCancelled(true);
			return;
		}
		
	}
}

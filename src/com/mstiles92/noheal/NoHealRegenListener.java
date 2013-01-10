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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoHealRegenListener implements Listener {
	
	private final NoHealPlugin plugin;
	
	public NoHealRegenListener(NoHealPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerHeal(EntityRegainHealthEvent e) {
		
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player)e.getEntity();
		RegainReason r = e.getRegainReason();
		
		if (p.getHealth() > plugin.getMaxHealth()) {
			p.setHealth(plugin.getMaxHealth());
		}
		
		if ((r == RegainReason.EATING || r == RegainReason.MAGIC || r == RegainReason.MAGIC_REGEN || r == RegainReason.REGEN || r == RegainReason.SATIATED) && (plugin.getEffectEnabled())) {
			e.setCancelled(true);
			
			if (p.hasPermission("noheal.bypass")) {
				e.setCancelled(false);
			}
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().getHealth() > plugin.getMaxHealth()) {
			e.getPlayer().setHealth(plugin.getMaxHealth());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e)	{
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (p.getHealth() > plugin.getMaxHealth()) {
				p.setHealth(plugin.getMaxHealth());
			}
		}
	}
}

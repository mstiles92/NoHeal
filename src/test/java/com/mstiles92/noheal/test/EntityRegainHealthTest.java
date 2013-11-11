package com.mstiles92.noheal.test;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

import com.mstiles92.noheal.NoHealPlugin;
import com.mstiles92.noheal.NoHealRegenListener;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
	NoHealPlugin.class,
	EntityRegainHealthEvent.class,
	Player.class,
	Entity.class,
	RegainReason.class
})
public class EntityRegainHealthTest {
	private static final int MAX_HEALTH = 20;
	
	private NoHealPlugin pluginMock;
	private EntityRegainHealthEvent eventMock;
	private Player playerMock;
	private NoHealRegenListener listener;
	
	@Before
	public void init() {
		pluginMock = mock(NoHealPlugin.class);
		eventMock = PowerMockito.mock(EntityRegainHealthEvent.class);
		playerMock = mock(Player.class);
		listener = new NoHealRegenListener(pluginMock);
		
		when(pluginMock.getMaxHealth()).thenReturn(20);
		when(pluginMock.getEffectEnabled()).thenReturn(true);
	}
	
	@Test
	public void testNonPlayer() {
		Entity entityMock = mock(Entity.class);
		when(eventMock.getEntity()).thenReturn(entityMock);
		
		listener.onPlayerHeal(eventMock);
		
		verify(eventMock, times(1)).getEntity();
	}
	
	@Test
	public void testNormalCase() {
		when(eventMock.getEntity()).thenReturn(playerMock);
		when(eventMock.getRegainReason()).thenReturn(RegainReason.SATIATED);
		when(playerMock.getHealth()).thenReturn(15d);
		when(playerMock.hasPermission("noheal.bypass")).thenReturn(false);
		
		listener.onPlayerHeal(eventMock);
		
		verify(playerMock, never()).setHealth(MAX_HEALTH);
		verify(eventMock, times(1)).setCancelled(true);
		verify(eventMock, never()).setCancelled(false);
	}
	
	@Test
	public void testPlayerBypassPermission() {
		when(eventMock.getEntity()).thenReturn(playerMock);
		when(eventMock.getRegainReason()).thenReturn(RegainReason.SATIATED);
		when(playerMock.getHealth()).thenReturn(15d);
		when(playerMock.hasPermission("noheal.bypass")).thenReturn(true);
		
		listener.onPlayerHeal(eventMock);
		
		verify(playerMock, never()).setHealth(MAX_HEALTH);
		verify(eventMock, times(1)).setCancelled(true);
		verify(eventMock, times(1)).setCancelled(false);
	}
}

package com.mstiles92.noheal.test;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
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
	EntityDamageEvent.class,
	Player.class,
	Entity.class
})
public class EntityDamageTest {
	private static final int MAX_HEALTH = 20;

	private NoHealPlugin pluginMock;
	private EntityDamageEvent eventMock;
	private Player playerMock;
	private NoHealRegenListener listener;
	
	@Before
	public void init() {
		pluginMock = mock(NoHealPlugin.class);
		eventMock = PowerMockito.mock(EntityDamageEvent.class);
		playerMock = mock(Player.class);
		listener = new NoHealRegenListener(pluginMock);
		
		when(pluginMock.getMaxHealth()).thenReturn(MAX_HEALTH);
	}
	
	@Test
	public void testNonPlayer() {
		Entity entityMock = mock(Entity.class);
		when(eventMock.getEntity()).thenReturn(entityMock);
		
		listener.onPlayerDamage(eventMock);
		
		verify(eventMock, times(1)).getEntity();
	}
	
	@Test
	public void testPlayerHealthOk() {
		when(eventMock.getEntity()).thenReturn(playerMock);
		when(playerMock.getHealth()).thenReturn(15d);
		
		listener.onPlayerDamage(eventMock);
		
		verify(eventMock, times(2)).getEntity();
		verify(playerMock, never()).setHealth(MAX_HEALTH);
	}
	
	@Test
	public void testPlayerHealthTooHigh() {
		when(eventMock.getEntity()).thenReturn(playerMock);
		when(playerMock.getHealth()).thenReturn(25d);
		
		listener.onPlayerDamage(eventMock);
		
		verify(eventMock, times(2)).getEntity();
		verify(playerMock, times(1)).setHealth(MAX_HEALTH);
	}
}

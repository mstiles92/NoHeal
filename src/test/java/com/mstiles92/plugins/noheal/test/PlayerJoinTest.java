package com.mstiles92.plugins.noheal.test;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

import com.mstiles92.plugins.noheal.NoHealPlugin;
import com.mstiles92.plugins.noheal.NoHealRegenListener;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PlayerJoinEvent.class })
public class PlayerJoinTest {
	private static final int MAX_HEALTH = 20;

	private NoHealPlugin pluginMock;
	private PlayerJoinEvent eventMock;
	private Player playerMock;
	
	private NoHealRegenListener listener;
	
	@Before
	public void init() {
		pluginMock = mock(NoHealPlugin.class);
		eventMock = PowerMockito.mock(PlayerJoinEvent.class);
		playerMock = mock(Player.class);
		listener = new NoHealRegenListener(pluginMock);

		when(pluginMock.getMaxHealth()).thenReturn(MAX_HEALTH);
		when(eventMock.getPlayer()).thenReturn(playerMock);
	}
	
	@Test
	public void testPlayerHealthOk() {
		when(playerMock.getHealth()).thenReturn(15d);
		
		listener.onPlayerJoin(eventMock);
		
		verify(playerMock, never()).setHealth(MAX_HEALTH);
	}
	
	@Test
	public void testPlayerHealthTooHigh() {
		when(playerMock.getHealth()).thenReturn(25d);
		
		listener.onPlayerJoin(eventMock);
		
		verify(playerMock).setHealth(MAX_HEALTH);
	}
}

package com.mstiles92.noheal.test;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
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
	PlayerJoinEvent.class,
	NoHealPlugin.class,
	Player.class
})
public class PlayerJoinTest {
	private static final int MAX_HEALTH = 20;
	
	private PlayerJoinEvent eventMock;
	private NoHealPlugin pluginMock;
	private Player playerMock;
	private NoHealRegenListener listener;
	
	@Before
	public void setUp() {
		eventMock = PowerMockito.mock(PlayerJoinEvent.class);
		pluginMock = mock(NoHealPlugin.class);
		playerMock = mock(Player.class);
		listener = new NoHealRegenListener(pluginMock);
	
		when(eventMock.getPlayer()).thenReturn(playerMock);
		when(pluginMock.getMaxHealth()).thenReturn(MAX_HEALTH);
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

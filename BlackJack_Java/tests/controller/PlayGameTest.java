package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import model.Game;
import view.SimpleView;

public class PlayGameTest {

	PlayGame sut;
	SimpleView mockView = mock(SimpleView.class);
	Game mockGame = mock(Game.class);
	
	@Before
	public void setUp() throws Exception {
		sut = new PlayGame();
	}
	
	@Test
	public void shouldReturnTrueAndDisplayWelcomeMessageDealerAndPlayerHand() {
		assertTrue(sut.play(mockView, mockGame));
		verify(mockView, times(1)).displayWelcomeMessage();
		verify(mockView, times(1)).displayDealerHand(mockGame.getDealerHand(), mockGame.getDealerScore());
		verify(mockView, times(1)).displayPlayerHand(mockGame.getPlayerHand(), mockGame.getPlayerScore());
	}
	
	@Test
	public void shouldReturnTrueAndGameOver() {
		when(mockGame.isGameOver()).thenReturn(true);
		
		assertTrue(sut.play(mockView, mockGame));
		verify(mockView, times(1)).displayGameOver(mockGame.isDealerWinner());
	}
}
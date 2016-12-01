package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.InputStream;
import org.junit.*;
import model.Game;
import view.SimpleView;

public class PlayGameTest {

	PlayGame sut;
	SimpleView mockView = mock(SimpleView.class);
	Game mockGame = mock(Game.class);
	InputStream mockInput = mock(InputStream.class);

	@Before
	public void setUp() throws Exception {
		sut = new PlayGame();
	}

	@Test
	public void shouldReturnTrueAndDisplayWelcomeMessageDealerAndPlayerHand() {
		assertTrue(sut.play(mockView, mockGame, mockInput));
		verify(mockView, times(1)).displayWelcomeMessage();
		verify(mockView, times(1)).displayDealerHand(mockGame.getDealerHand(), mockGame.getDealerScore());
		verify(mockView, times(1)).displayPlayerHand(mockGame.getPlayerHand(), mockGame.getPlayerScore());
	}

	@Test
	public void shouldReturnTrueAndGameOver() {
		when(mockGame.isGameOver()).thenReturn(true);

		assertTrue(sut.play(mockView, mockGame, mockInput));
		verify(mockView, times(1)).displayGameOver(mockGame.isDealerWinner());
	}

	@Test
	public void shouldReturnTrueAndGetInputAndPlayNewGame() {
		userInput('p');
		verify(mockGame, times(1)).newGame();
	}

	@Test
	public void shouldReturnTrueAndGetInputAndHit() {
		userInput('h');
		verify(mockGame, times(1)).hit();
	}

	@Test
	public void shouldReturnTrueAndGetInputAndStand() {
		userInput('s');
		verify(mockGame, times(1)).stand();
	}

	private void userInput(char c) {
		when(mockView.getInput(mockInput)).thenReturn((int) c);
		assertTrue(sut.play(mockView, mockGame, mockInput));
		verify(mockView, times(1)).getInput(mockInput);
	}
}
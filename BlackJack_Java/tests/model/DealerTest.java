package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import rules.AmericanNewGameRule;
import rules.BasicHitRule;
import rules.DealerWinRule;
import rules.RulesFactory;

public class DealerTest {

	Dealer sut;
	Card mockCard;
	Player mockPlayer;
	Deck mockDeck;
	final int maxScore = 21;
	RulesFactory mockFactory;
	DealerWinRule mockWinRule;
	BasicHitRule mockHitRule;
	AmericanNewGameRule mockNewGameRule;

	@Before
	public void setUp() throws Exception {
		mockDependencies();
		when(mockFactory.getWinRule()).thenReturn(mockWinRule);
		when(mockFactory.getHitRule()).thenReturn(mockHitRule);
		when(mockFactory.getNewGameRule()).thenReturn(mockNewGameRule);

		sut = new Dealer(mockDeck, mockFactory);
	}

	@Test
	public void dealerShouldBeTheWinner() {
		when(mockWinRule.isDealerWinner(mockPlayer, sut, maxScore)).thenReturn(true);

		assertTrue(sut.isDealerWinner(mockPlayer));
		verify(mockWinRule, times(1)).isDealerWinner(mockPlayer, sut, maxScore);
	}

	@Test
	public void gameShouldBeOver() {
		when(mockHitRule.doHit(sut)).thenReturn(false);

		assertTrue(sut.isGameOver());
		verify(mockHitRule, times(1)).doHit(sut);
	}

	@Test
	public void playerShouldHitAndDealANewCardWhenScoreIsBelowLimitAndGameIsNotOver() {
		when(mockPlayer.calcScore()).thenReturn(11);
		when(mockHitRule.doHit(sut)).thenReturn(true);
		when(mockDeck.getCard()).thenReturn(mockCard);

		assertTrue(sut.hit(mockPlayer, true));
		verify(mockPlayer, times(1)).calcScore();
		verify(mockDeck, times(1)).getCard();
		verify(mockCard, times(1)).show(true);
		verify(mockPlayer, times(1)).dealCard(mockCard);
	}

	@Test
	public void shouldReturnFalseAndNotPlayNewGameBecauseGameIsNotOver() {
		when(mockHitRule.doHit(sut)).thenReturn(true);

		assertFalse(sut.newGame(mockPlayer));
		verify(mockHitRule, times(1)).doHit(sut);
	}

	@Test
	public void shouldReturTrueAndPlayNewGameBecauseGameIsOver() {
		Dealer spy = spy(sut);

		doNothing().when((Player) spy).clearHand();
		when(mockHitRule.doHit(sut)).thenReturn(false);
		when(mockNewGameRule.newGame(spy, mockPlayer)).thenReturn(true);

		assertTrue(spy.newGame(mockPlayer));
		verify(spy, times(1)).clearHand();
		verify(mockPlayer, times(1)).clearHand();
	}

	private void mockDependencies() {
		mockCard = mock(Card.class);
		mockPlayer = mock(Player.class);
		mockDeck = mock(Deck.class);
		mockFactory = mock(RulesFactory.class);
		mockWinRule = mock(DealerWinRule.class);
		mockHitRule = mock(BasicHitRule.class);
		mockNewGameRule = mock(AmericanNewGameRule.class);
	}
}
package model;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Card.Value;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

	@SuppressWarnings("unchecked")
	ArrayList<Card> mockedPlayerHand = mock(ArrayList.class);
	Card mockedCard = mock(Card.class);
	Player sut;
	final int times = 4;

	@Before
	public void setUp() throws Exception {
		sut = new Player();
		sut = sut.makeNewPalyer(mockedPlayerHand);
		addThisCardToPlayerHand(mockedCard, times);

		when(mockedPlayerHand.size()).thenReturn(times);
		when(mockedPlayerHand.get(any(Integer.class))).thenReturn(mockedCard);
	}

	@Test
	public void shouldCreateAndReturnANewPlayer() {
		assertNotNull(sut);
	}

	@Test
	public void shouldAddCardInPlayerHandList() {
		verify(mockedPlayerHand, times(times)).add(mockedCard);
	}

	@Test
	public void shouldClearPlayerHandList() {
		sut.clearHand();
		verify(mockedPlayerHand, times(1)).clear();
	}

	@Test
	public void shouldUnhideTheCardsInTheList() {
		when(mockedCard.getValue()).thenReturn(Value.Hidden);
		doNothing().when(mockedCard).show(true);

		sut.showHand();

		verify(mockedCard, times(times)).getValue();
		verify(mockedCard, times(times)).show(true);
	}

	@Test
	public void shouldCalculateAndReturnScore14() {
		when(mockedCard.getValue()).thenReturn(Value.Ace);

		int score = sut.calcScore();
		assertEquals(14, score);
	}

	@Test
	public void shouldReturnArrayListContainingFourCards() {
		assertEquals(4, sut.getHand().size());
	}

	private void addThisCardToPlayerHand(Card card, int times) {
		for (int i = 0; i < times; i++) {
			sut.dealCard(card);
		}
	}
}
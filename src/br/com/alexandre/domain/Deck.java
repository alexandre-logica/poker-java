package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alexandre.domain.aux.RankEnum;
import br.com.alexandre.domain.aux.SuitsEnum;

public class Deck {

	// maximum number of cards
	public static final int deckLength = SuitsEnum.values().length * RankEnum.values().length;
	private static Card card;
	public static final List<Card> FULLDECK = new ArrayList<>();
	
	static{
		for(SuitsEnum suit : SuitsEnum.values()) {
			for(RankEnum rank : RankEnum.values()) {
				card = new Card();
				card.setRank(rank.getValue());
				card.setSuit(suit.getValue());
				FULLDECK.add(card);
			}
		}
	}
	
    public static void shuffle() {
        Collections.shuffle(FULLDECK);
    }
    
    public static void showDeck() {
        for(int i = 0; i < deckLength; i++) {
            System.out.println(FULLDECK.get(i));
        }
    }
}

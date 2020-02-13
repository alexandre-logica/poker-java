package br.com.alexandre.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alexandre.domain.aux.RankEnum;
import br.com.alexandre.domain.aux.SuitsEnum;

public class Deck {

	// maximum number of cards
	public static final int deckLength = SuitsEnum.values().length * RankEnum.values().length;
	private Card card;
	private List<Card> FULLDECK = new ArrayList<>();
	
	public Deck() {
		for(SuitsEnum suit : SuitsEnum.values()) {
			for(RankEnum rank : RankEnum.values()) {
				card = new Card();
				card.setRank(rank.getValue());
				card.setSuit(suit.getValue());
				FULLDECK.add(card);
			}
		}
		Collections.shuffle(FULLDECK);
	}
	
    public void shuffle() {
        Collections.shuffle(FULLDECK);
    }
    
    public void showDeck() {
        for(int i = 0; i < deckLength; i++) {
            System.out.println(FULLDECK.get(i));
        }
    }

	public List<Card> getFULLDECK() {
		return FULLDECK;
	}

}

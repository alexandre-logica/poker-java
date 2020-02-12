package br.com.alexandre.test;

import br.com.alexandre.domain.Deck;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.out.println(SuitsEnum.CLUBS.getValue());
		Deck.showDeck();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Deck.shuffle();
		Deck.showDeck();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Deck.shuffle();
		Deck.showDeck();
	}

}

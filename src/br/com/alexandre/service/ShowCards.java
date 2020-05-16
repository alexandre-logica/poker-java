package br.com.alexandre.service;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.enuns.TypeCardEnum;

public class ShowCards {

	private Hand hand;
	
	public ShowCards(Hand hand) {
		super();
		this.hand = hand;
	}

	public void showDeck() {
		System.out.println();
		System.out.println("*************** Cards ***************");
		System.out.println();
		for (Card card : hand.getDeck().getFULLDECK()) {
			System.out.println(card.getCharacter() + "-" + card.getSymbol());
		}
	}

	public void showHandCards() {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
			else if (card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			} else if (card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
	}
	
	public void showFlop() {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
	}
	
	public void showTurn() {
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			} 
		}
	}
	
	public void showRiver() {
		for (HandCard card : hand.getHandCards()) {
		if (card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
	}
	
	public void showTableCards(Integer roundNumber) {
		switch (roundNumber) {
			case 2:
				showFlop();
				break;
			case 3:
				showTurn();
				break;
			case 4:
				showRiver();
			default:
				break;
		}
	}

	public void showPlayerCards() {
		System.out.println();
		System.out.println("************** Players Cards **************");
		System.out.println();
		for (HandPlayer player : hand.getHandPlayers()) {
			System.out.println("Player: " + player.getTablePlayer().getPlayer().getNickname());
			System.out.println("Position: " + player.getTablePlayer().getPosition());
			if(player.getTablePlayer().getDealer())
				System.out.println("Dealer");
			System.out.print("Cards: | " + player.getCards().get(0).getCharacter() + "-" + player.getCards().get(0).getSymbol());
			System.out.println(" | " + player.getCards().get(1).getCharacter() + "-" + player.getCards().get(1).getSymbol() + " | ");
			System.out.println();
		}
	}

	public void showPlayerCards(HandPlayer player) {
			System.out.print("Player cards: | " + player.getCards().get(0).getCharacter() + "-" + player.getCards().get(0).getSymbol());
			System.out.println(" | " + player.getCards().get(1).getCharacter() + "-" + player.getCards().get(1).getSymbol() + " | ");
			System.out.println();
	}
	
	public void showWinners() {
		System.out.println();
		if(hand.getWinners().size() > 1)
			System.out.println("************** Winners **************");
		else
			System.out.println("************** Winner **************");
		System.out.println();
		for (HandPlayer player : hand.getWinners()) {
			System.out.println("Player: " + player.getTablePlayer().getPlayer().getNickname());
			System.out.println("Pot: " + (hand.getPot() / hand.getWinners().size()));
			System.out.println("Hand game: " + player.getHandRanking().getType().getName());
			System.out.println("Score: " + player.getHandRanking().getValue());
			showPlayerCards(player);
			showHandCards();
			System.out.println();
			System.out.println("************** Winner hand cards **************");
			System.out.println();
			showPlayerHandCards(player);
			System.out.println();
		}
	}
	
	private void showPlayerHandCards(HandPlayer player) {
		for(Card card : player.getHandRanking().getHandCards()) {
			System.out.print(" | " + card.getCharacter() + "-" + card.getSymbol() + " | ");
		}
	}

	public void showCards() {
		showHandCards();
		showPlayerCards();
	}
}

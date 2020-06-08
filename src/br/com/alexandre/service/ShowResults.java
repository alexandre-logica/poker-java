package br.com.alexandre.service;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.Table;
import br.com.alexandre.enuns.StatusEnum;
import br.com.alexandre.enuns.TypeCardEnum;

public class ShowResults {

	private Hand hand;
	
	public ShowResults(Hand hand) {
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
		System.out.println();
	}

	public void showHandCards() {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		System.out.println();
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
		System.out.println();
	}
	
	public void showFlop() {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		System.out.println();
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
			}
		}
		System.out.println();
	}
	
	public void showTurn() {
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println();
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
				System.out.println();
			} 
		}
	}
	
	public void showRiver() {
		for (HandCard card : hand.getHandCards()) {
			if (card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println();
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter() + "-" + card.getCard().getSymbol());
				System.out.println();
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
			System.out.println("Chips: " + player.getTablePlayer().getChips());
			if(player.getDealer())
				System.out.println("Dealer");
			System.out.print("Cards: | " + player.getCards().get(0).getCharacter() + "-" + player.getCards().get(0).getSymbol());
			System.out.println(" | " + player.getCards().get(1).getCharacter() + "-" + player.getCards().get(1).getSymbol() + " | ");
			System.out.println();
		}
		System.out.println();
	}

	public void showPlayerCards(HandPlayer player) {
		System.out.println();
		System.out.print("Player cards: | " + player.getCards().get(0).getCharacter() + "-" + player.getCards().get(0).getSymbol());
		System.out.println(" | " + player.getCards().get(1).getCharacter() + "-" + player.getCards().get(1).getSymbol() + " | ");
		System.out.println();
	}
	
	public void showHandWinners() {
		System.out.println();
		if(hand.getWinners().size() > 1)
			System.out.println("************** Hand Winners **************");
		else
			System.out.println("************** Hand Winner **************");
		System.out.println();
		for (HandPlayer player : hand.getWinners()) {
			System.out.println("Player: " + player.getTablePlayer().getPlayer().getNickname());
			System.out.println("Pot: " + (hand.getPot() / hand.getWinners().size()));
			if(player.getStatus().equals(StatusEnum.CARD_WINNER)) {
				System.out.println("Hand game: " + player.getHandRanking().getType().getName());
				System.out.println("Score: " + player.getHandRanking().getValue());
				showPlayerCards(player);
				showHandCards();
				System.out.println();
				System.out.println("************** Winner hand cards **************");
				System.out.println();
				showPlayerHandCards(player);
			}else {
				System.out.println("All the other players fold");
			}
			System.out.println();
		}
	}
	
	public void showWinner(Table table) {
		System.out.println();
		System.out.println("************** Winner **************");
		System.out.println();
		System.out.println("Player: " + table.getTablePlayers().get(0).getPlayer().getNickname());
		System.out.println();
		System.out.println("Player total prize: " + table.getTablePlayers().get(0).getChips());
		System.out.println();
	}
	
	private void showPlayerHandCards(HandPlayer player) {
		System.out.println();
		for(Card card : player.getHandRanking().getHandCards()) {
			System.out.print(" | " + card.getCharacter() + "-" + card.getSymbol() + " | ");
		}
		System.out.println();
	}

	public void showCards() {
		showHandCards();
		showPlayerCards();
	}
}

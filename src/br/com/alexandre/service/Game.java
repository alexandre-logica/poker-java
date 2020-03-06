package br.com.alexandre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.CardCount;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.Person;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Round;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.TablePlayer;
import br.com.alexandre.domain.aux.GenderEnum;
import br.com.alexandre.domain.aux.RankEnum;
import br.com.alexandre.domain.aux.SuitsEnum;
import br.com.alexandre.domain.aux.TypeCardEnum;
import br.com.alexandre.util.Util;

public class Game {
	public void playGame(Table table, List<Player> players) {
		Long count = 0L;
		table.setTablePlayers(setTablePlayers(table, players));
		while(!table.getGameOver()) {
			Deck deck = new Deck();
			deck.shuffle();
			Hand hand = new Hand(++count, deck);
			setCards(hand, table.getTablePlayers());
			hand.setRounds(runRounds(hand));
			table.getHands().add(hand);
			//showCards(hand);
			table.setGameOver(true);
		}
	}
	
	public List<TablePlayer> setTablePlayers(Table table, List<Player> players){
		List<TablePlayer> tablePlayers = new ArrayList<TablePlayer>();
		for(int i = 0; i < players.size(); i++) {
			tablePlayers.add(new TablePlayer(new Long(i), table, players.get(i), i+1, false));
		}
		tablePlayers.get(new Random().nextInt(tablePlayers.size())).setDealer(true);
		return tablePlayers;
	}
	
	public void setCards(Hand hand, List<TablePlayer> tablePlayers) {
		HandCard handcard;
		HandPlayer handPlayer;
		for(int x = 0; x < tablePlayers.size(); x++){
			handPlayer = new HandPlayer(new Long(x), hand, tablePlayers.get(x));
			for(int i = 0; i < 2; i++ ){
				handPlayer.getCards().add(hand.getDeck().getFULLDECK().get(0));
				hand.getDeck().getFULLDECK().remove(0);
			}
			hand.getHandPlayers().add(handPlayer);
		}
		for(int z = 0; z < 5; z++ ) {
			handcard = new HandCard();
			handcard.setCard(hand.getDeck().getFULLDECK().get(0));
			hand.getDeck().getFULLDECK().remove(0);
			if(z < 3)
				handcard.setType(TypeCardEnum.FLOP.getValue());
			else if(z < 4)
				handcard.setType(TypeCardEnum.TURN.getValue());
			else
				handcard.setType(TypeCardEnum.RIVER.getValue());
			hand.getHandCards().add(handcard);
		}
	}
	
	public void showDeck(Deck deck) {
		System.out.println();
		System.out.println("*************** Cards ***************");
		System.out.println();
		for(Card card : deck.getFULLDECK()) {
			System.out.println(card.getCharacter()+"-"+card.getSymbol());
		}
	}
	
	public void showHandCards(Hand hand) {
		System.out.println();
		System.out.println("*************** Table Cards ***************");
		System.out.println();
		System.out.println("##### Flop #####");
		for(HandCard card : hand.getHandCards()) {
			if(card.getType().equals(TypeCardEnum.FLOP.getValue())) {
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			}
		}
		for(HandCard card : hand.getHandCards()) {
			if(card.getType().equals(TypeCardEnum.TURN.getValue())) {
				System.out.println("##### Turn #####");
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			} else if(card.getType().equals(TypeCardEnum.RIVER.getValue())) {
				System.out.println("##### River #####");
				System.out.println(card.getCard().getCharacter()+"-"+card.getCard().getSymbol());
			}
		}
	}
	
	public void showPlayerCards(Hand hand) {
		System.out.println();
		System.out.println("************** Players Cards **************");
		System.out.println();
		for(HandPlayer player : hand.getHandPlayers()) {
			System.out.println("##### Player: "+player.getTablePlayer().getPlayer().getNickname()+" #####");
			System.out.println(player.getCards().get(0).getCharacter()+"-"+player.getCards().get(0).getSymbol());
			System.out.println(player.getCards().get(1).getCharacter()+"-"+player.getCards().get(1).getSymbol());
		}
	}
	
	public void showCards(Hand hand) {
		showHandCards(hand);
		showPlayerCards(hand);
	}
	
	public List<Round> runRounds(Hand hand) {
		int count = 0;
		List<Round> rounds = new ArrayList<Round>();
		Boolean winner = false;
		List<Card> handCards = new ArrayList<>();
		for(HandCard handCard : hand.getHandCards()) {
			handCards.add(handCard.getCard());
		}
		for(HandPlayer player : hand.getHandPlayers()) {
			List<Card> playerHand = player.getCards();
			playerHand.addAll(handCards);
			applyRuleToWinners(playerHand);
		}

		return new ArrayList<Round>();
	}
	
	public int applyRuleToWinners(List<Card> playerHand){
		List<Integer> rankNumber = new ArrayList<>();
		StringBuilder suitChar = new StringBuilder();
		StringBuilder rankChar = new StringBuilder();
		HashMap<String, Long> suitCount = new HashMap<String, Long>();
		HashMap<String, Long> rankCount = new HashMap<String, Long>();
		for(Card card : playerHand) {
			rankNumber.add(card.getRank());
			suitChar.append(card.getSuit());
			rankChar.append(card.getRank());
		}
		for(SuitsEnum suit : SuitsEnum.values()) {
			suitCount.put(suit.getValue(), rankChar.toString().chars().filter(ch -> ch == suit.getValue().charAt(0)).count());
		}
		suitCount = Util.sortByValue(suitCount);
		for(RankEnum rank : RankEnum.values()) {
			rankCount.put(rank.getValue().toString(), rankChar.toString().chars().filter(ch -> ch == rank.getValue()).count());
		}
		rankCount = Util.sortByValue(rankCount);
		
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Person p1 = new Person(1, "Alexandre", GenderEnum.MASCULINO.getValue());
		Person p2 = new Person(2, "Thales", GenderEnum.MASCULINO.getValue());
		Person p3 = new Person(3, "Julia", GenderEnum.FEMININO.getValue());
		Person p4 = new Person(4, "Italo", GenderEnum.MASCULINO.getValue());
		
		Player pl1 = new Player(1, "Xandão", p1);
		Player pl2 = new Player(2, "Thalão", p2);
		Player pl3 = new Player(3, "Ju", p3);
		Player pl4 = new Player(4, "Bonititalo", p4);
		
		List<Player> players = new ArrayList<>();
		players.add(pl1);
		players.add(pl2);
		players.add(pl3);
		players.add(pl4);
		
		Table table = new Table(1L, 20.0, LocalDateTime.now());
		
		Game game = new Game();
		game.playGame(table, players);
		
	}
}

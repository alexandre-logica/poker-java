package br.com.alexandre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.alexandre.domain.Card;
import br.com.alexandre.domain.Deck;
import br.com.alexandre.domain.Hand;
import br.com.alexandre.domain.HandCard;
import br.com.alexandre.domain.HandPlayer;
import br.com.alexandre.domain.HandRanking;
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
			//table.setGameOver(true);
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
		
		HandRanking handRanking = new HandRanking();
		
		/* 
		 * Apply the rules to count ranks and suits with the same value 
		 */
		List<Integer> rankNumbers = new ArrayList<Integer>();
		StringBuilder suitChar = new StringBuilder();
		String flush = "";
		HashMap<String, Long> suitCount = new HashMap<String, Long>();
		HashMap<String, Long> rankCount = new HashMap<String, Long>();
		for(Card card : playerHand) {
			rankNumbers.add(card.getRank());
			suitChar.append(card.getSuit());
		}
		Collections.sort(rankNumbers, Collections.reverseOrder());
		for(SuitsEnum suit : SuitsEnum.values()) {
			suitCount.put(suit.getValue(), suitChar.chars().filter(ch -> ch == suit.getValue().charAt(0)).count());
		}
		suitCount = Util.sortByValue(suitCount);
		for(RankEnum rank : RankEnum.values()) {
			rankCount.put(rank.getValue().toString(), rankNumbers.stream().filter(ch -> ch == rank.getValue()).count());
		}
		rankCount = Util.sortByValue(rankCount);
		
		/*
		 * Apply the rules to find flush
		 */
		if(suitCount.entrySet().stream().findFirst().get().getValue() >= 5)
			flush = suitCount.entrySet().stream().findFirst().get().getKey();
		
		/*
		 * Apply the rules to find straight
		 */
		Set<Integer> straightNumbers = new LinkedHashSet<Integer>();
		List<Integer> straightSequence = new ArrayList<Integer>();
		if(rankNumbers.contains(1)) {
			straightNumbers.add(14);
		}
		straightNumbers.addAll(rankNumbers);
		if(straightNumbers.size() >= 5) {
			straightSequence = orderedWithNoGap(straightNumbers);
		}
		
		/*
		 * Apply the rules to find straight flush
		 */
		if(straightSequence.size() == 5 && !flush.isEmpty()) {
			List<Card> straightCards = new ArrayList<Card>();
			Set<String> straightFlush = new HashSet<String>();
			if(straightSequence.contains(14)) {
				straightSequence.remove(0);
				straightSequence.add(0, 1);
			}
			for(Integer straightItem : straightSequence) {
				for(Card card : playerHand) {
					if(card.getRank() == straightItem) {
						straightCards.add(card);
						straightFlush.add(card.getSuit());
					}
				}
			}
			
			if(straightFlush.size() == 1) {
				System.out.println("Straight Flush!!!");
			}
		}

		return 0;
	}
	
    private List<Integer> orderedWithNoGap(Set<Integer> list) {       
        Integer prev = null;
        int seq = 0;
        List<Integer> straight = new ArrayList<Integer>();
        for(Integer i : list) {
            if(prev != null && prev-1 == i) {
                if(seq == 0) {
                	seq = 2;
                	straight.add(i);
                	straight.add(0, prev);
                }else {
                	seq++;
                	straight.add(i);
                }
            }else {
            	seq = 0;
            	straight.clear();
            }
            if(seq == 5)
            	return straight;
            prev = i;
        }
        return straight;
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

package br.com.alexandre.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.alexandre.domain.ActionPlayer;
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
import br.com.alexandre.domain.aux.StatusEnum;
import br.com.alexandre.domain.aux.TypeCardEnum;

public class Test {
	
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
			handPlayer = new HandPlayer(new Long(x), hand, tablePlayers.get(x), StatusEnum.IN.toString());
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
	
	public List<Round> runRounds(Hand hand) {
		/*
		 * int count = 0; List<Round> rounds = new ArrayList<Round>();
		 * while(hand.getWinners() == null || hand.getWinners().size() == 0) { Round
		 * round = new Round(1, ++count, 0, hand, null, hand.getPlayers().size());
		 * List<ActionPlayer> actionPlayers = new ArrayList<ActionPlayer>();
		 * ActionPlayer actionPlayer; for(int i = 0; i < round.getTotalPlayers(); i++) {
		 * actionPlayer = new ActionPlayer(round, player, position, smallBlind,
		 * bigBlind, action, bets) }
		 * 
		 * 
		 * 
		 * }
		 */
		
		return new ArrayList<Round>();
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
		
		Table table = new Table(1L, 20.0, LocalDateTime.now(), false);
		
		Test test = new Test();
		test.playGame(table, players);
		
		
	}

}

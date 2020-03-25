package br.com.alexandre.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.domain.Person;
import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Table;
import br.com.alexandre.domain.aux.GenderEnum;
import br.com.alexandre.service.PokerGameAutomator;

public class Test {
	
	public static void main(String[] args) {
		
		
		
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
		PokerGameAutomator game = new PokerGameAutomator(table, players);
		game.playGame();
		
	}
}

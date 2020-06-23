package br.com.alexandre.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.RoundPlayer;
import br.com.alexandre.domain.Table;
import br.com.alexandre.service.PokerGameAutomator;

public class Test {
	
	public static void main(String[] args) {
		
		
		
		Player p1 = new RoundPlayer(1, "Xandão");
		Player p2 = new RoundPlayer(2, "Thalão");
		Player p3 = new RoundPlayer(3, "Ju");
		Player p4 = new RoundPlayer(4, "Bonititalo");
		
		List<Player> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		
		Table table = new Table(1L, 20.0, 500.0, LocalDateTime.now());
		PokerGameAutomator game = new PokerGameAutomator(table, players);
		game.playGame();
		
	}
}

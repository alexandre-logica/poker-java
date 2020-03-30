package br.com.alexandre.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alexandre.domain.Player;
import br.com.alexandre.domain.Table;
import br.com.alexandre.enuns.GenderEnum;
import br.com.alexandre.service.PokerGameAutomator;

public class Test {
	
	public static void main(String[] args) {
		
		
		
		Player p1 = new Player(1, "Alexandre", GenderEnum.MASCULINO.getValue(), "Xandão");
		Player p2 = new Player(2, "Thales", GenderEnum.MASCULINO.getValue(), "Thalão");
		Player p3 = new Player(3, "Julia", GenderEnum.FEMININO.getValue(), "Ju");
		Player p4 = new Player(4, "Italo", GenderEnum.MASCULINO.getValue(), "Bonititalo");
		
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

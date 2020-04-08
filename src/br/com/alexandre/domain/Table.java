package br.com.alexandre.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {

	private Long id;
	private List<TablePlayer> tablePlayers;
	private List<Hand> hands;
	private Double initialBigBlind;
	private Double initialChipsPlayers;
	private LocalDateTime gameStart;
	private LocalDateTime gameFinish;
	private Boolean gameOver;
	private Integer increaseBlindMinutes;
	private Double currentBigBlind;
	
	public Table(Long id, List<TablePlayer> tablePlayers, List<Hand> hands, Double initialBigBlind, Double initialChipsPlayers,  
			     LocalDateTime gameStart, LocalDateTime gameFinish, Boolean gameOver, Integer increaseBlindMinutes, Double currentBigBlind) {
		this.id = id;
		this.tablePlayers = tablePlayers;
		this.hands = hands;
		this.initialBigBlind = initialBigBlind;
		this.initialChipsPlayers= initialChipsPlayers;
		this.gameStart = gameStart;
		this.gameFinish = gameFinish;
		this.gameOver = gameOver;
		this.increaseBlindMinutes = increaseBlindMinutes;
		this.currentBigBlind = currentBigBlind;
	}
	
	public Table(Long id, Double initialBigBlind, Double initialChipsPlayers, LocalDateTime gameStart) {
		this(id, new ArrayList<TablePlayer>(), new ArrayList<Hand>(), initialBigBlind, initialChipsPlayers, gameStart, null, false, 2, initialBigBlind);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<TablePlayer> getTablePlayers() {
		return tablePlayers;
	}

	public void setTablePlayers(List<TablePlayer> tablePlayers) {
		this.tablePlayers = tablePlayers;
	}

	public List<Hand> getHands() {
		return hands;
	}

	public void setHands(List<Hand> hands) {
		this.hands = hands;
	}

	public Double getInitialBigBlind() {
		return initialBigBlind;
	}

	public void setInitialBigBlind(Double initialBigBlind) {
		this.initialBigBlind = initialBigBlind;
	}
	
	public Double getInitialChipsPlayers() {
		return initialChipsPlayers;
	}

	public void setInitialChipsPlayers(Double initialChipsPlayers) {
		this.initialChipsPlayers = initialChipsPlayers;
	}

	public LocalDateTime getGameStart() {
		return gameStart;
	}

	public void setGameStart(LocalDateTime gameStart) {
		this.gameStart = gameStart;
	}

	public LocalDateTime getGameFinish() {
		return gameFinish;
	}

	public void setGameFinish(LocalDateTime gameFinish) {
		this.gameFinish = gameFinish;
	}

	public Boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Integer getIncreaseBlindMinutes() {
		return increaseBlindMinutes;
	}

	public void setIncreaseBlindMinutes(Integer increaseBlindMinutes) {
		this.increaseBlindMinutes = increaseBlindMinutes;
	}

	public Double getCurrentBigBlind() {
		return currentBigBlind;
	}

	public void setCurrentBigBlind(Double currentBigBlind) {
		this.currentBigBlind = currentBigBlind;
	}
	
}

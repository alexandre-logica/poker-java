package br.com.alexandre.domain;

public class RoundPlayer {

	private Long id;
	private Round round;
	private HandPlayer handPlayer;
	private Boolean smallBlind;
	private Boolean bigBlind;
	private Boolean dealer;
	private ActionPlayer action;
	private Boolean allIn;
	private Double totalBet;
	
	public RoundPlayer() {
		this.totalBet = 0.0;
	}

	public RoundPlayer(Long id, Round round, HandPlayer handPlayer, Boolean smallBlind, Boolean bigBlind,
			Boolean dealer, ActionPlayer action, Boolean allIn, Double totalBet) {
		super();
		this.id = id;
		this.round = round;
		this.handPlayer = handPlayer;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.dealer = dealer;
		this.action = action;
		this.allIn = allIn;
		this.totalBet = totalBet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public HandPlayer getHandPlayer() {
		return handPlayer;
	}

	public void setHandPlayer(HandPlayer handPlayer) {
		this.handPlayer = handPlayer;
	}

	public Boolean getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(Boolean smallBlind) {
		this.smallBlind = smallBlind;
	}

	public Boolean getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(Boolean bigBlind) {
		this.bigBlind = bigBlind;
	}

	public Boolean getDealer() {
		return dealer;
	}

	public void setDealer(Boolean dealer) {
		this.dealer = dealer;
	}

	public ActionPlayer getAction() {
		return action;
	}

	public void setAction(ActionPlayer action) {
		this.action = action;
	}

	public Boolean getAllIn() {
		return allIn;
	}

	public void setAllIn(Boolean allIn) {
		this.allIn = allIn;
		round.setAllIn(allIn);
	}

	public Double getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(Double totalBet) {
		this.totalBet += totalBet;
		handPlayer.setTotalBet(totalBet);
	}
	
}

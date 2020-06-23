package br.com.alexandre.domain;

import java.util.Comparator;

public class Player {
	
	protected Integer id;
	protected String nickname;
	protected Table table;
	protected Integer tablePosition;
	protected Integer dealerPosition;
	protected Double chips;
	protected Boolean winner;
	
	public static DealerPositionCompare dealerPositionCompare = new DealerPositionCompare();
	
	public Player() {
		
	}
	
	public Player(Integer id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
	
	public Player(Integer id, String nickname, Table table, Integer tablePosition, Integer dealerPosition, Double chips, Boolean winner) {
		this.id = id;
		this.nickname = nickname;
		this.table = table;
		this.tablePosition = tablePosition;
		this.dealerPosition = dealerPosition;
		this.chips = chips;
		this.winner = winner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Integer getTablePosition() {
		return tablePosition;
	}

	public void setTablePosition(Integer tablePosition) {
		this.tablePosition = tablePosition;
	}
	
	public Integer getDealerPosition() {
		return dealerPosition;
	}

	public void setDealerPosition(Integer dealerPosition) {
		this.dealerPosition = dealerPosition;
	}

	public Double getChips() {
		return chips;
	}

	public void setChips(Double chips) {
		this.chips = chips;
	}
	
	public void increaseChips(Double chips) {
		this.chips += chips;
	}
	
	public void decreaseChips(Double chips) {
		this.chips -= chips;
	}
	
	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

}

class DealerPositionCompare implements Comparator<Player>{
	@Override
	public int compare(Player o1, Player o2) {
        if (o1.getDealerPosition() < o2.getDealerPosition()) return -1; 
        if (o1.getDealerPosition() > o2.getDealerPosition()) return 1; 
        else return 0; 
	}
}
	

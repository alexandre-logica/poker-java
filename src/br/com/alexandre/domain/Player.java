package br.com.alexandre.domain;

public class Player extends Client{
	private static final long serialVersionUID = 1L;
	
	private String nickname;

	public Player(String nickname) {
		this.nickname = nickname;
	}

	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Player(int id, String nome, String genero, String nickname) {
		super(id, nome, genero);
		this.nickname = nickname;
	}



	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}

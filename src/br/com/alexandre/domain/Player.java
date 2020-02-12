package br.com.alexandre.domain;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nickname;
	private Person person;
	
	public Player(int id, String nickname, Person person) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}

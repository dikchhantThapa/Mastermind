package com.linkedin.interview.mastermind.api.dto;

public class MastermindGameInitiationRequest {

	public String[] players;
	
	public int difficulty;
	
	public int totalTries;
	
	

	public String[] players() {
		return players;
	}

	public void setExtraPlayers(String[] players) {
		this.players = players;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getTotalTries() {
		return totalTries;
	}

	public void setTotalTries(int totalTries) {
		this.totalTries = totalTries;
	}

	public String[] getPlayers() {
		return players;
	}

	public void setPlayers(String[] players) {
		this.players = players;
	}

	
}

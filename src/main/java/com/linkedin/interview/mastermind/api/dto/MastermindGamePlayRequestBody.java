package com.linkedin.interview.mastermind.api.dto;


public class MastermindGamePlayRequestBody {
	
	private String userId;
	
	private int[] guess;
	
	public int[] getGuess() {
		return guess;
	}
	
	public void setGuess(int[] guess) {
		this.guess = guess;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}

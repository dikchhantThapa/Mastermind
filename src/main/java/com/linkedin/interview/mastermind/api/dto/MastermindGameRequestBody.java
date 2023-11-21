package com.linkedin.interview.mastermind.api.dto;


public class MastermindGameRequestBody {
	
	private int[] guess;
	
	public int[] getGuess() {
		return guess;
	}
	
	public void setGuess(int[] guess) {
		this.guess = guess;
	}
	
}

package com.linkedin.interview.mastermind.api.dto;

public class Move {

	private int[] guessedNumbers;
	
	private int moveNumber;
	
	private String moveResult;

	public int[] getGuessedNumbers() {
		return guessedNumbers;
	}

	public void setGuessedNumbers(int[] guessedNumbers) {
		this.guessedNumbers = guessedNumbers;
	}

	public int getMoveNumber() {
		return moveNumber;
	}

	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

	public String getMoveResult() {
		return moveResult;
	}

	public void setMoveResult(String moveResult) {
		this.moveResult = moveResult;
	}
	
}

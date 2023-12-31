package com.linkedin.interview.mastermind.api.dto;

public class Move {

	private int[] guessedNumbers;
	
	private int moveNumber;
	
	private String moveResult;

	public int[] getGuessedNumbers() {
		return guessedNumbers;
	}

	public Move(int[] guessedNumbers, int moveNumber, String moveResult) {
		this.guessedNumbers = guessedNumbers;
		this.moveNumber = moveNumber;
		this.moveResult = moveResult;
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

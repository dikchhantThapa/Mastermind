package com.linkedin.interview.mastermind.api.dto;

import java.util.List;

public class MastermindGameResponse {
	
	
	private int numOfTriesleft;
	
	private boolean hasWon;
	
	private List<Move> moveHistory;
	
	public int getNumOfTriesleft() {
		return numOfTriesleft;
	}

	public void setNumOfTriesleft(int numOfTriesleft) {
		this.numOfTriesleft = numOfTriesleft;
	}

	public boolean isHasWon() {
		return hasWon;
	}

	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	public List<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(List<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}


}

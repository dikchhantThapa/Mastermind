package com.linkedin.interview.mastermind.api.dto;

import java.util.List;
import java.util.Map;

public class MastermindGameResponse {
	
	
	private int numOfTriesleft;
	
	private boolean hasWon;
	
	private List<Move> moveHistory;

	private Map<String, List<Move>> history;

	public Map<String, List<Move>> getHistory() {
		return history;
	}

	public void setHistory(Map<String, List<Move>> history) {
		this.history = history;
	}

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

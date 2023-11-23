package com.linkedin.interview.mastermind.api.dto;

import java.util.List;

public class MastermindGameResponse {
	
	
	private int numOfTriesleft;
	
	private boolean hasWon;
	
	private String msg;
	
	private String id;
	
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(List<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}  
	
	
	

}

package com.linkedin.interview.mastermind.api.dto;

import java.util.HashMap;
import java.util.List;

public class Player {
	
	
	public String userId;
		
	public List<Move> moveHistory;
	
	public int totalTriesLeft;
	
	

	public Player(String userId, List<Move> moveHistory, int totalTriesLeft) {
		super();
		this.userId = userId;
		this.moveHistory = moveHistory;
		this.totalTriesLeft = totalTriesLeft;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(List<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}
     
	

}

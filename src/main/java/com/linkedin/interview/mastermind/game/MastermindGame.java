package com.linkedin.interview.mastermind.game;

import com.linkedin.interview.mastermind.api.dto.Player;

import java.util.HashMap;
import java.util.Map;


//oops concept
public class MastermindGame {
	
	private String id;
	
	private int[] randomNumbers;
		
	Map<Integer, Integer> occurrencesInComputer = new HashMap<>();
	
	private boolean isOver; 
	
	private String winner;
				
	private HashMap<String,Player> players;
		
	public void initRandomNumbers(int[] randomNumbers) {
		
		this.randomNumbers = randomNumbers;

		// computer random values Map (Key = number, Value = total occurrences)
		for (int num : this.randomNumbers) {
			occurrencesInComputer.put(num, occurrencesInComputer.getOrDefault(num, 0) + 1);
		}

	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String guessNumbers(int[] userSupplied, Player player) {
		
		int correctNumberGuesses = 0;
		int correctPositionGuesses = 0;
		
		HashMap<Integer,Integer> occurrencesInUserSupplied = new HashMap<>();

		// User supplied Map (Key = number, Value = total occurrences)
		for (int num : userSupplied) {
			occurrencesInUserSupplied.put(num, occurrencesInUserSupplied.getOrDefault(num, 0) + 1);
		}


		for(int key: occurrencesInUserSupplied.keySet()) {
			if(occurrencesInComputer.containsKey(key)) {
				correctNumberGuesses += Math.min(occurrencesInComputer.get(key), occurrencesInUserSupplied.get(key));
			}
		}
		
		// search for correct position guesses
		 for (int i = 0; i < userSupplied.length; i++) {
			 
	            if (randomNumbers[i] == userSupplied[i]) {
                    correctPositionGuesses++;
                }
	        }
		 
		 
        if(correctNumberGuesses == 0) {
        	return "all incorrect";
        } 
        
        else {	// winning case
			// correct numbers = 4, correct positions = 4 (both need to match the original array length to be winner)
        	if(correctNumberGuesses == this.randomNumbers.length && correctPositionGuesses == this.randomNumbers.length) {
        		isOver = true;
        		player.setHasWon(true);
        		this.winner = player.getUserId();
        	}
        	
        	return String.format("%s correct number and %s correct location", correctNumberGuesses,correctPositionGuesses);
        }
		
		
	}


	public int[] getRandomNumbers() {
		return randomNumbers;
	}


	public void setRandomNumbers(int[] randomNumbers) {
		this.randomNumbers = randomNumbers;
	}





	public Map<Integer, Integer> getOccurrencesInComputer() {
		return occurrencesInComputer;
	}

	public void setOccurrencesInComputer(Map<Integer, Integer> occurrencesInComputer) {
		this.occurrencesInComputer = occurrencesInComputer;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(HashMap<String, Player> players) {
		this.players = players;
	}

	public void evaluateDraw() {
		for(Player player : players.values()) {
			if(player.getTotalTriesLeft() > 0) {
				return;
			} 
		}
		this.isOver = true;
		this.winner = null;
	}


}

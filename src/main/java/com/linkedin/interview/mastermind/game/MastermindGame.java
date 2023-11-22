package com.linkedin.interview.mastermind.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;


//oops concept
public class MastermindGame {
	
	private String id;
	
	private int[] randomNumbers;
	
	//private HashSet<Integer> randomNumbersSet; //extra
	
	Map<Integer, Integer> occurrencesInComputer = new HashMap<>();
	
	private boolean hasWon; 
		
	private int totalTriesLeft;
	
	
	
	public void initRandomNumbers(int[] randomNumbers) {
		this.randomNumbers = randomNumbers;	
		
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


	public String guessNumbers(int[] userSupplied) {
		
		int correctNumberGuesses = 0;
		int correctPositionGuesses = 0;
		
		HashMap<Integer,Integer> occurrencesInUserSupplied = new HashMap<>();
		
		for (int num : userSupplied) {
			occurrencesInUserSupplied.put(num, occurrencesInUserSupplied.getOrDefault(num, 0) + 1);
		}
		
		for(int key: occurrencesInUserSupplied.keySet()) {
			if(occurrencesInComputer.containsKey(key)) {
				correctNumberGuesses += Math.min(occurrencesInComputer.get(key), occurrencesInUserSupplied.get(key));
			}
		}
		
			
		
		 for (int i = 0; i < userSupplied.length; i++) {
			 
	            if (randomNumbers[i] == userSupplied[i]) {
                    correctPositionGuesses++;
                }

	        }
        
        if(correctNumberGuesses==0) {
        	return "all incorrect";
        } 
        
        else {
        	if(correctNumberGuesses == this.randomNumbers.length && correctPositionGuesses == this.randomNumbers.length) {
        		hasWon = true;
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


	public boolean isHasWon() {
		return hasWon;
	}


	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}


	public int getTotalTriesLeft() {
		return totalTriesLeft;
	}


	public void setTotalTriesLeft(int totalTriesLeft) {
		this.totalTriesLeft = totalTriesLeft;
	}


	



	 
	
	
	
	

}

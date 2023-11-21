package com.linkedin.interview.mastermind.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;


//oops concept
public class MastermindGame {
	
	private String id;
	
	private int[] randomNumbers;
	
	private HashSet<Integer> randomNumbersSet; //extra
	
	private boolean hasWon; 
		
	private int totalTriesLeft;
	
	
	
	public void initRandomNumbers(int[] randomNumbers) {
		this.randomNumbers = randomNumbers;
		this.randomNumbersSet = Arrays.stream(randomNumbers).boxed().collect(Collectors.toCollection(HashSet::new));
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
		
        for (int i = 0; i < userSupplied.length; i++) {
            int userNumber = userSupplied[i];

            // Check for correct number guess
            if (randomNumbersSet.contains(userNumber)) {
                correctNumberGuesses++;

                // Check for correct position guess
                if (randomNumbers[i] == userNumber) {
                    correctPositionGuesses++;
                }
            }
        }
		
       this.totalTriesLeft--;
        
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

package com.linkedin.interview.mastermind.services;

import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linkedin.interview.mastermind.api.dto.MastermindGameResponse;
import com.linkedin.interview.mastermind.game.MastermindGame;

@Service
public class MastermindService {

	
	
	private final String RANDOM_NUM_URL = "https://www.random.org/integers/?num=4&min=1&max=6&col=1&base=10&format=plain&rnd=new";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MastermindGame game;
	
	public MastermindGameResponse initializeGame() {
		
			ResponseEntity<String> response
			  = restTemplate.getForEntity(RANDOM_NUM_URL, String.class);
			
			String responseBody = response.getBody();
			
			if(responseBody!=null) {
				String[] test = responseBody.split("\n");
				int[] intArray = Arrays.stream(test).mapToInt(Integer::parseInt).toArray();
				
				if(intArray!=null && intArray.length > 0) {
					
					System.out.println(Arrays.toString(intArray));
					
					game.initRandomNumbers(intArray);
					game.setHasWon(false);
					game.setTotalTriesLeft(10);
					
					String gameId = java.util.UUID.randomUUID().toString();
					
					game.setId(gameId);
					
					MastermindGameResponse responseOut = new MastermindGameResponse(); 
					responseOut.setHasWon(false);
					responseOut.setMsg("New Game Initiated!");
					responseOut.setNumOfTriesleft(10);
					responseOut.setId(gameId);
					
					return responseOut;
				}

			}
			
			
		return null;
	}
	
	public MastermindGameResponse guessNumbers(int[] userGuess) {
		
		if(!game.isHasWon() && game.getTotalTriesLeft()>0) {
		MastermindGameResponse response = new MastermindGameResponse();
		
		String result = game.guessNumbers(userGuess);
		game.setTotalTriesLeft(game.getTotalTriesLeft()-1);
		response.setHasWon(game.isHasWon());
		response.setNumOfTriesleft(game.getTotalTriesLeft());
		response.setId(game.getId());
		response.setMsg(result);
		
		
		return response;
		
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}

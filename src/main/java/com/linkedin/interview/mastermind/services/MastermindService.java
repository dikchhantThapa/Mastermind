package com.linkedin.interview.mastermind.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linkedin.interview.mastermind.api.dto.MastermindGameInitiatationResponse;
import com.linkedin.interview.mastermind.api.dto.MastermindGameResponse;
import com.linkedin.interview.mastermind.api.dto.Player;
import com.linkedin.interview.mastermind.game.MastermindGame;

@Service
public class MastermindService {

	
	
	private final String RANDOM_NUM_URL = "https://www.random.org/integers/?num=%s&min=1&max=6&col=1&base=10&format=plain&rnd=new";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MastermindGame game;
	
	
	// this method initializes a new game
	public MastermindGameInitiatationResponse initializeGame(String[] playerIds, int numberOfTotalTries, int difficulty) {
		
		MastermindGameInitiatationResponse responseOut = null;
		
		HashMap<String, Player> players = new HashMap<>();
		for(int i=0;i<playerIds.length;i++) {
			players.put(playerIds[i], new Player(playerIds[i], new ArrayList<>(), numberOfTotalTries));
		}
		
		game.setPlayers(players); // setting all players who will be playing
		game.setOver(false); // initiating the initial state of the game to be not over
		String gameId = java.util.UUID.randomUUID().toString(); // creating a new id for the game to identify and connect to the game
		game.setId(gameId);
		
		
		/** API CALL TO RANDOM NUMBER API START **/
		ResponseEntity<String> response = restTemplate.getForEntity(String.format(RANDOM_NUM_URL, difficulty),
				String.class); // making a rest api call to  the third party get api as provided by iunstruction document

		String responseBody = response.getBody();
		/** API CALL TO RANDOM NUMBER API END **/
		
		//fetching the random numbers from within the api call response and setting them in the game
		if (responseBody != null) {
			String[] test = responseBody.split("\n");
			int[] intArray = Arrays.stream(test).mapToInt(Integer::parseInt).toArray();

			if (intArray != null && intArray.length == difficulty) {

				System.out.println(Arrays.toString(intArray));

				game.initRandomNumbers(intArray); // set random number array chosen by computer to the game
				responseOut = new MastermindGameInitiatationResponse();
				responseOut.setGameId(gameId);
				responseOut.setMsg("Game Sucessfully Created!");
				return responseOut;
			}

		}
		
		
		//if the api call did not return any random numbers we will return a null response obj to the client
		return responseOut;
	}
	
//	public MastermindGameResponse guessNumbers(int[] userGuess) {
//		
//		if(!game.isOver() && game.getTotalTriesLeft()>0) {
//		MastermindGameResponse response = new MastermindGameResponse();
//		
//		String result = game.guessNumbers(userGuess);
//		game.setTotalTriesLeft(game.getTotalTriesLeft()-1);
//		response.setHasWon(game.isOver());
//		response.setNumOfTriesleft(game.getTotalTriesLeft());
//		response.setId(game.getId());
//		response.setMsg(result);
//		
//		
//		return response;
//		
//		}
//		
//		return null;
//	}
	
	
	
	
	
	
	
	
	
	
	
}

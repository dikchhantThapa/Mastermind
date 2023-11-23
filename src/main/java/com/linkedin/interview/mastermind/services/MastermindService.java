package com.linkedin.interview.mastermind.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linkedin.interview.mastermind.api.dto.MastermindGameInitiatationResponse;
import com.linkedin.interview.mastermind.api.dto.MastermindGameResponse;
import com.linkedin.interview.mastermind.api.dto.Move;
import com.linkedin.interview.mastermind.api.dto.Player;
import com.linkedin.interview.mastermind.api.exception.GamePlayException;
import com.linkedin.interview.mastermind.game.MastermindGame;


/**
 * 
 * @author Dikchhant Thapa
 *
 */
@Service
public class MastermindService {

	private static final Logger logger = LoggerFactory.getLogger(MastermindService.class);
	
	private final String RANDOM_NUM_URL = "https://www.random.org/integers/?num=%s&min=1&max=6&col=1&base=10&format=plain&rnd=new";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Map<String, MastermindGame> gameDirectory;
	
	/**
	 * 
	 * @param playerIds
	 * @param numberOfTotalTries
	 * @param difficulty
	 * @return
	 */
	public MastermindGameInitiatationResponse initializeGame(String[] playerIds, int numberOfTotalTries, int difficulty) {
		
		MastermindGame game = new MastermindGame();
		
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
				
				gameDirectory.put(gameId, game);
				
				return responseOut;
			}

		}
		
		
		//if the api call did not return any random numbers we will return a null response obj to the client
		return responseOut;
	}
	
	
	/**
	 * @author Dikchhant Thapa
	 * @param guess
	 * @param userId
	 * @param gameId
	 * @return
	 * @throws Exception 
	 */
	public MastermindGameResponse guessNumbers(int[] guess, String userId, String gameId) throws Exception {
		
		if(gameDirectory.containsKey(gameId)) {
			
			MastermindGame game = gameDirectory.get(gameId);
			game.evaluateDraw();
			
			if(!game.isOver()) {
				HashMap<String,Player> players = game.getPlayers();
				if (players.containsKey(userId)) {
					Player player = players.get(userId);
					if (player.getTotalTriesLeft() > 0) {
						String outcome = game.guessNumbers(guess,player);
						player.setTotalTriesLeft(player.getTotalTriesLeft() - 1);
						List<Move> playerMoves = player.getMoveHistory();
						playerMoves.add(new Move(guess, playerMoves.size() + 1, outcome));
						MastermindGameResponse response = new MastermindGameResponse();
						
						
						response.setHasWon(player.isHasWon());
						response.setNumOfTriesleft(player.getTotalTriesLeft());
						response.setMoveHistory(player.getMoveHistory());
						return response;
					} else {
						throw new GamePlayException("Player has no more tries left");
					}
				} 
				else {
					throw new GamePlayException("Player is not part of this game!");
				}
				
			} else {
				logger.info("game is not over");
								
				String result = null;
				
				if(game.getWinner()!=null) {
					result = game.getWinner() + " won";
				} else {
					result = "game was a draw!";
				}
				
				
				throw new GamePlayException("This game is already over! Result: " + result);
			}
		} 
		
		return null;
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

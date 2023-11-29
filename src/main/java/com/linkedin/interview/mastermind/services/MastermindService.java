package com.linkedin.interview.mastermind.services;

import com.linkedin.interview.mastermind.api.dto.MastermindGameInitiatationResponse;
import com.linkedin.interview.mastermind.api.dto.MastermindGameResponse;
import com.linkedin.interview.mastermind.api.dto.Move;
import com.linkedin.interview.mastermind.api.dto.Player;
import com.linkedin.interview.mastermind.api.exception.GamePlayException;
import com.linkedin.interview.mastermind.game.MastermindGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * 
 * @author Dikchhant Thapa
 *
 */
@Service
public class MastermindService {

//	private static final Logger logger = LoggerFactory.getLogger(MastermindService.class);
	
	private final String RANDOM_NUM_URL = "https://www.random.org/integers/?num=%s&min=1&max=6&col=1&base=10&format=plain&rnd=new";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Map<String, MastermindGame> gameDirectory;
	// key = gameId , value = game
	
	/**
	 * 
	 * @param playerIds
	 * @param numberOfTotalTries
	 * @param difficulty
	 * @return
	 */
	public MastermindGameInitiatationResponse initializeGame(String[] playerIds, int numberOfTotalTries, int difficulty) {

		// create instance
		MastermindGame game = new MastermindGame();

		// start with Response output null (until game is completely initialized)
		MastermindGameInitiatationResponse responseOut = null;

		// initialize each player name as key, player info (user Id, move History, tries left) as value
		HashMap<String, Player> players = new HashMap<>();
		for(int i = 0 ; i < playerIds.length; i++) {
			players.put(playerIds[i], new Player(playerIds[i], new ArrayList<>(), numberOfTotalTries));
		}
		
		game.setPlayers(players); // setting all players who will be playing
		game.setOver(false); // initiating the initial state of the game to be not over
		String gameId = java.util.UUID.randomUUID().toString(); // creating a new id for the game to identify and connect to the game
		game.setId(gameId);


		/** API CALL TO RANDOM NUMBER API START **/
		ResponseEntity<String> response = restTemplate.getForEntity(String.format(RANDOM_NUM_URL, difficulty),
				String.class); // making a rest api call to the third party get api as provided by instruction document

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
				responseOut.setMsg("Game Successfully Created!");
				
				gameDirectory.put(gameId, game);

//				System.out.println("Game directory = " + gameDirectory);
//				System.out.println("Response out = " + responseOut);

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

//		System.out.println("Game id = " + gameId);

		// only start the game if Game Key matches directory's Value
		if(gameDirectory.containsKey(gameId)) {

			MastermindGameResponse response = new MastermindGameResponse();
			MastermindGame game = gameDirectory.get(gameId);
			game.evaluateDraw();

			
			if(!game.isOver()) {

				HashMap<String,Player> players = game.getPlayers();
				System.out.println("HashMap = " + players);

				HashMap<String, List<Move>> historyMap = new HashMap<>();



				for (Player player : players.values()) {

					historyMap.put(player.getUserId(), player.getMoveHistory());



					System.out.println(player.getUserId() + " User history =  " + player.getMoveHistory().size());



//					System.out.println(player.getMoveHistory());
				}

				response.setHistory(historyMap);


//				toString()



				// Continue Only if entered User is in the original list
				if (players.containsKey(userId)) {
					// get Player object from collection of players
					Player player = players.get(userId);
					// keep continuing until user has tries left
					if (player.getTotalTriesLeft() > 0) {

						String outcome = game.guessNumbers(guess,player);

//						System.out.println("Outcome = " + outcome);

						player.setTotalTriesLeft(player.getTotalTriesLeft() - 1);

						List<Move> playerMoves = player.getMoveHistory();
						playerMoves.add(new Move(guess, playerMoves.size() + 1, outcome));

//						System.out.println("Player moves = " + playerMoves);

//						MastermindGameResponse response = new MastermindGameResponse();
						
						
						response.setHasWon(player.isHasWon());
						response.setNumOfTriesleft(player.getTotalTriesLeft());
						// get move history
						response.setMoveHistory(player.getMoveHistory());



//						System.out.println("Directory = " + gameDirectory);
//						gameDirectory.




						return response;
					} else {
						throw new GamePlayException("Player has no more tries left");
					}
				} 
				else {
					throw new GamePlayException("Player is not part of this game!");
				}
				
			} else {
//				logger.info("game is not over");
								
				String result = null;
				
				if(game.getWinner() != null) {
					result = game.getWinner() + " won";
				} else {
					result = "game was a draw!";
				}
				
				
				throw new GamePlayException("This game is already over! Result: " + result);
			}
		} 
		
		return null;
	}

	public String hint(String gameId) {

		 MastermindGame game = gameDirectory.get(gameId);
		 int[] randomNumbers = game.getRandomNumbers();
		 // [4, 1, 2, 3]

		 int rnd = new Random().nextInt(randomNumbers.length);
		 // index 2

		 if (randomNumbers[rnd] % 2 == 0) {
			 return "Number at " + rnd + " = even";
		 }	else {
			 return "Number at " + rnd + " = odd";
		 }


//			int rnd = new Random().nextInt(array.length);
//			return array[rnd];



	}





}

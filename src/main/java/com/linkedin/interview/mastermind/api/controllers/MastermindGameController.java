package com.linkedin.interview.mastermind.api.controllers;

import com.linkedin.interview.mastermind.api.dto.*;
import com.linkedin.interview.mastermind.api.exception.FailedInitiationException;
import com.linkedin.interview.mastermind.api.exception.GamePlayException;
import com.linkedin.interview.mastermind.services.MastermindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/mastermind")
public class MastermindGameController {
	
	
	@Autowired
	private MastermindService service;
	
	@PostMapping("/game")
	public ResponseEntity<MastermindGameInitiatationResponse> initGame(@RequestBody MastermindGameInitiationRequest requestBody) {
		

		MastermindGameInitiatationResponse responseBody = service.initializeGame(requestBody.getPlayers(),requestBody.getTotalTries(),requestBody.getDifficulty());

		// request successfully processed
		if (responseBody!=null) {
			return new ResponseEntity<MastermindGameInitiatationResponse>(responseBody, HttpStatus.OK);
		}

		//if a null response was sent back throw a failed initiation exception
		throw new FailedInitiationException("Could not initialize game, try again later!");
	}
	

	@PostMapping("/game/{gameId}/play")
	public ResponseEntity<MastermindGameResponse> guessNumbers(@PathVariable String gameId, @RequestBody MastermindGamePlayRequestBody reqBody) throws Exception {

		MastermindGameResponse responseBody = service.guessNumbers(reqBody.getGuess(),reqBody.getUserId(),gameId);

		if (responseBody != null) {
			return new ResponseEntity<MastermindGameResponse>(responseBody, HttpStatus.OK);
		}
//
//		throw new NoGameFoundException("Please create a new game!");
		return null;
	}


	@GetMapping("/game/{gameId}/hint")
	public String hint(@PathVariable String gameId) {
		String responseBody = service.hint(gameId);
		return responseBody;
	}


	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleFailedInitiationException(FailedInitiationException exception) {
		ErrorResponse err = new ErrorResponse();
		err.setCode(500);
		err.setMsg(exception.getMessage());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNoGameFoundException(GamePlayException exception) {
		ErrorResponse err = new ErrorResponse();
		err.setCode(400);
		err.setMsg(exception.getMessage());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
	}

}

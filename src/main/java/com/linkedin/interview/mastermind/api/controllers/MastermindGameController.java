package com.linkedin.interview.mastermind.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.interview.mastermind.api.dto.MastermindGameRequestBody;
import com.linkedin.interview.mastermind.api.dto.MastermindGameResponse;
import com.linkedin.interview.mastermind.services.MastermindService;



@RestController
@RequestMapping("/api/v1/mastermind")
public class MastermindGameController {
	
	
	@Autowired
	private MastermindService service;
	
	@PostMapping("/initialization")
	public ResponseEntity<MastermindGameResponse> initGame() {

		MastermindGameResponse responseBody = service.initializeGame();

		if (responseBody!=null) {
			return new ResponseEntity<MastermindGameResponse>(responseBody, HttpStatus.OK);
		}

		throw new RuntimeException("Could not initialize game, try again later!");
	}
	
	
	@PostMapping("/attempt")
	public ResponseEntity<MastermindGameResponse> guessNumbers(@RequestBody MastermindGameRequestBody reqBody) {

		MastermindGameResponse responseBody = service.guessNumbers(reqBody.getGuess());

		if (responseBody!=null) {
			return new ResponseEntity<MastermindGameResponse>(responseBody, HttpStatus.OK);
		}

		throw new RuntimeException("Failed attempt");
	}
	
	

}

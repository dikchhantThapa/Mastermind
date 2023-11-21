package com.linkedin.interview.mastermind.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.linkedin.interview.mastermind.game.MastermindGame;

@Configuration
public class MastermindGameConfigs {


	@Bean
	@Scope("prototype") //research scopes of spring bean
	public MastermindGame chooseNewNumbers() {
		return new MastermindGame();
	}
	
	@Bean // needed to make request to the random number api that is provided
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
}

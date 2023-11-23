package com.linkedin.interview.mastermind.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.linkedin.interview.mastermind.game.MastermindGame;

@Configuration
public class MastermindGameConfigs {


	@Bean
	public Map<String,MastermindGame> gameDirectory() {
		return new HashMap<>();
	}
	
	@Bean // needed to make request to the random number api that is provided
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
}

package com.silpaservices.ml.data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.silpaservices.ml.data.utils.TwitterData;

@RestController
@PropertySource("classpath:application.properties")
public class TwitterControllers {

	@Autowired
	private ApplicationContext context;

	@GetMapping(path = "/testme")
	public @ResponseBody ResponseEntity<Object> test_service(){

		return new ResponseEntity<>("Ran ok", HttpStatus.OK);
	}


	@GetMapping
	public @ResponseBody ResponseEntity<Object> test(){

		List<String> list = null;
		TwitterData tw = (TwitterData) context.getBean("twitterData");
		try {
			list = tw.searchtweets("Impeachment", "2019-12-10", "2019-12-19");
		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(list);
		return new ResponseEntity<>("Ran ok", HttpStatus.OK);
	}

	/*
	Body of the request:
	{
		"query":"bitcoin",
		"since":"2019-12-10",
		"until":"2019-12-18"
		}
	 */
	@PostMapping(path = "/query")
	public @ResponseBody ResponseEntity<JsonNode> getDataForQuery(@RequestBody JsonNode json){

		List<String> list = null;
		JsonNode response = null;
		TwitterData tw = (TwitterData) context.getBean("twitterData");
		try {
			list = tw.searchtweets(json.get("query").asText(), json.get("since").asText(), json.get("until").asText());
			response = new ObjectMapper().readTree(new ObjectMapper().writeValueAsString(list));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

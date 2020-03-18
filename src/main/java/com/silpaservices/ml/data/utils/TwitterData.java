package com.silpaservices.ml.data.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@PropertySource("classpath:application.properties")
public class TwitterData {
	
	@Value("${oauth.consumerKey}")
	private String consumerKey;
	@Value("${oauth.consumerSecret}")
	private String consumerSecret;
	@Value("${oauth.accessToken}")
	private String accessToken;
	@Value("${oauth.accessTokenSecret}")
	private String tokenSecret;

	@Autowired
    private SparkSession sparkSession;
	
	private Twitter authenticateTwitter() {
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(consumerKey)
			  .setOAuthConsumerSecret(consumerSecret)
			  .setOAuthAccessToken(accessToken)
			  .setOAuthAccessTokenSecret(tokenSecret);
			TwitterFactory tf = new TwitterFactory(cb.build());
			return tf.getInstance();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			
		}
	}
	
	public List<String> searchtweets(String words, String since, String until) throws TwitterException {
		  
	    Twitter twitter = authenticateTwitter();
	    Query query = new Query(words);
	    query.since(since);
        query.until(until);
	    query.setCount(100);
	    QueryResult result = twitter.search(query);
	    
	    return processTweets(result);
	    
	}
	
	private List<String> processTweets(QueryResult result) {
		
		Dataset<String> ds = sparkSession.createDataset(result.getTweets().stream()
	  	      .map(item -> item.getText())
	  	      .collect(Collectors.toList()), Encoders.STRING());
		
		ds.map((MapFunction<String, String>) row -> {
			return "For Testing : " + row;
			
		}, Encoders.STRING());
		
	    return ds.collectAsList();
		
	}
}

package com.codepath.apps.basictwitter.models;

import com.codepath.apps.basictwitter.util.ParseRelativeDate;

public class Tweet implements Serializable {

  private static final long serialVersionUID = 1L;
	private String body;
	private String uid;
	private String createdAt;
	private String relativeDate;
	private User user;

	public String getBody() {
		return body;
	}
	
	public void setBody(String text) {
		body = text;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String id) {
		uid = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreateAt(String cAt) {
		createdAt = cAt;
	}

	public String getRelativeDate() {
		return relativeDate;
	}
	public void setRelativeDate(String rDate) {
		relativeDate = rDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		int len = jsonArray.length();
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(len);
		Tweet tweet;
		JSONObject tweetJson;

		for (int i=0; i<len; i++) {
			tweetJson = null;
			try{
				tweetJson = jsonArray.getJSONObject(i);				
			}catch (JSONException e){
				e.printStackTrace();
			} 
			tweet = Tweet.fromJSON(tweetJson);
			if (tweet!= null)
				tweets.add(tweet);
		}
		return tweets;
	}

	public static Tweet fromJSON(JSONObject jsonObject) {
		JSONObject userJson;
		
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getString("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.relativeDate = new ParseRelativeDate().getRelativeTimeAgo(tweet.createdAt);
			userJson = jsonObject.getJSONObject("user");
			tweet.user = User.fromJSON(userJson);
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

}

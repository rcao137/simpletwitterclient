package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{

  private static final long serialVersionUID = 1L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String tag;
	private String followers;
	private String following;

	public User(String name, String screenName, String profileImage) {
		this.name = name;
		this.screenName = screenName;
		this.profileImageUrl = profileImage;
	}

	public User() {
	  // TODO Auto-generated constructor stub
  }

	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");	  
			u.tag = jsonObject.getString("description");
			u.followers = jsonObject.getString("followers_count");
			u.following = jsonObject.getString("friends_count");
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return u;
	}



	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getTag() {
		return tag;
	}
	
	public String getfollowers() {
		return followers;
	}
	
	public String getfollowing() {
		return following;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
}

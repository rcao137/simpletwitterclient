package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.util.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private final int REQUEST_CODE = 20;

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;

	private Tweet newTweet;
	private String profileImage;
	private String screenName;
	private String name;
	private String max_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		setupViews();
		attachListener();
		client = TwitterApp.getRestClient();
		// Get initial tweets
		max_id = "0";
		populateTimeline();
		getUserInformation();

	}

	protected void setupViews(){
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
	}

	protected void attachListener() {
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				customLoadMoreDataFromApi(totalItemsCount); 
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet, menu);
		return true;
	}

	public void onTweetAction(MenuItem mi) {
		User u = new User(name, screenName, profileImage);

		Intent i = new Intent(this, NewTweetActivity.class);
		i.putExtra("user", u);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			newTweet = (Tweet) data.getSerializableExtra("tweet");
			postNewTweet();
		}
	} 

	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int offset) {
		// This method probably sends out a network request and appends new data items to your adapter. 
		// Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		// Deserialize API response and then construct new objects to append to the adapter

		//		Log.d("debug", Integer.toString(offset));
		max_id = (String) tweets.get(offset-1).getUid();
		Long opt_max_id = Long.valueOf(max_id) -1;
		max_id = opt_max_id.toString();
		populateTimeline();
	}

	public void populateTimeline(){
		client.getHomeTimeline(max_id, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray json){
				if (max_id.equals("0"))
					aTweets.clear();
				aTweets.addAll(Tweet.fromJSONArray(json)); 	
			}

			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	public void getUserInformation() {
		client.getUserInfo(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json){
				try {
					profileImage = json.getString("profile_image_url");
					screenName = json.getString("screen_name");
					name = json.getString("name");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	public void postNewTweet() {
		max_id = "0";
		populateTimeline();	
		client.postNewTweet(newTweet.getBody(), new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json){
				aTweets.insert(newTweet, 0);	
//				aTweets.notifyDataSetChanged();
				lvTweets.setSelection(0);				
			}

			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}


}

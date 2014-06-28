package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.util.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	private final int REQUEST_CODE = 20;

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private Tweet newTweet;
	private String profileImage;
	private String screenName;
	private String name;
	private Typeface font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		setupViews();
		attachListener();
		client = TwitterApp.getRestClient();
		// Get initial tweets
		populateTimeline("0", false);
		// Get user information
		getUserInformation();


	}

	protected void setupViews(){
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		//		lvTweets = (ListView) findViewById(R.id.lvTweets);
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

		lvTweets.setOnRefreshListener(new OnRefreshListener() { 
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents
				// Make sure you call listView.onRefreshComplete()
				// once the loading is done. This can be done from here or any
				// place such as when the network request has completed successfully.
				boolean refresh = true;
				populateTimeline("0", refresh);

			}
		});

	}


	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int offset) {
		String max_id;
		// This method probably sends out a network request and appends new data items to your adapter. 
		// Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		// Deserialize API response and then construct new objects to append to the adapter

		int tweetLen = tweets.size();
		if (tweetLen > 0) {
//			max_id = (String) tweets.get(offset-2).getUid();
			max_id = (String) tweets.get(tweetLen-1).getUid();
			Long opt_max_id = Long.valueOf(max_id) -1;
			max_id = opt_max_id.toString();
			populateTimeline(max_id, false);
		}
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

	public void populateTimeline(final String max_id, final boolean refreshFlag){
		client.getHomeTimeline(max_id, new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray json){
				if (max_id.equals("0"))
					aTweets.clear();
				aTweets.addAll(Tweet.fromJSONArray(json)); 	
				if (refreshFlag)
					lvTweets.onRefreshComplete();
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
		client.postNewTweet(newTweet.getBody(), new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json){
				aTweets.insert(Tweet.fromJSON(json), 0);	
				lvTweets.setSelection(0);				
			}

			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

}

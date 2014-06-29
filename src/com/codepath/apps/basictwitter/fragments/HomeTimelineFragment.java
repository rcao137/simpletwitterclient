package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.util.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();

		// Get initial tweets
		populateTimeline("0", false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
			//				max_id = (String) tweets.get(offset-2).getUid();
			max_id = (String) tweets.get(tweetLen-1).getUid();
			Long opt_max_id = Long.valueOf(max_id) -1;
			max_id = opt_max_id.toString();
			populateTimeline(max_id, false);
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
}

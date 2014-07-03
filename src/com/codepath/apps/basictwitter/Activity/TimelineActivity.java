package com.codepath.apps.basictwitter.Activity;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public class TimelineActivity extends FragmentActivity {
	private final int REQUEST_CODE = 20;

	private TwitterClient client;
	private Tweet newTweet;
	private User profileUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		// get client
		client = TwitterApp.getRestClient();
		// Get user information
		getUserInformation();
		setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flLayout, this, "Home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flLayout, this, "Mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet, menu);
		return true;
	}

	public void onTweetAction(MenuItem mi) {
		Intent i = new Intent(this, NewTweetActivity.class);
		i.putExtra("user", profileUser);
		startActivityForResult(i, REQUEST_CODE);
	}

	public void onProfileAction(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", profileUser);
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

	public void postNewTweet() {
		client.postNewTweet(newTweet.getBody(), new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json){
				Tweet t = Tweet.fromJSON(json);
				
				HomeTimelineFragment currentFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("Home");
				currentFragment.insertTweettoTop(t);		
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
					profileUser = User.fromJSON(json);
			}

			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

}

package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewTweetActivity extends Activity {
	private Tweet newTweet;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);

		newTweet = new Tweet();
		user = (User) getIntent().getSerializableExtra("user");
		newTweet.setUser(user);

		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivUserProfile);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		// populate views with tweet data
		imageLoader.displayImage(newTweet.getUser().getProfileImageUrl(), ivProfileImage);
		
		TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		tvScreenName.setText(user.getScreenName());
		TextView tvName = (TextView) findViewById(R.id.tvUserName);
		tvName.setText(user.getName());		
	}

	public void onCreateAction(View v) {
    EditText edTweet = (EditText) findViewById(R.id.etTweet);
    newTweet.setBody(edTweet.getText().toString());
    
    Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("tweet", newTweet);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}

	public void onCancelAction(View v) {
		finish();
	}
}

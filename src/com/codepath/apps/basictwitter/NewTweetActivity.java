package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
	TextView tvCount;
	EditText edTweet;

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
		
	  edTweet = (EditText) findViewById(R.id.etTweet);
		tvCount = (TextView) findViewById(R.id.tvCount);
		
		edTweet.addTextChangedListener(new TextWatcher(){
        public void afterTextChanged(Editable s) {
            int len = s.length();
            int lenleft = 140 - len;
            tvCount.setText(String.valueOf(lenleft));
            if (lenleft<=0)
            	tvCount.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            else
            	tvCount.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count){}
    }); 
	}

	public void onCreateAction(View v) {
    
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

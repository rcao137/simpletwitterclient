package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getSerializableExtra("user");
		setTitle("@"+user.getScreenName());
		populateUserInfo();
	}

	private void populateUserInfo() {
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		// populate views with tweet data
		imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);		
		TextView tvName = (TextView) findViewById(R.id.tvUsername);
		tvName.setText(user.getName());
		TextView tvTag = (TextView) findViewById(R.id.tvTag);
		tvTag.setText(user.getTag());
		TextView tvFollower = (TextView) findViewById(R.id.tvFollower);
		tvFollower.setText(user.getfollowers()+" followers");	
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvFollowing.setText(user.getfollowing()+" following");	
  }
	
	
}

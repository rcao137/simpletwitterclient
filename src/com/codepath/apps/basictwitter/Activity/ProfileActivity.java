package com.codepath.apps.basictwitter.Activity;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.fragments.UserprofileTimelineFragment;
import com.codepath.apps.basictwitter.models.User;

public class ProfileActivity extends FragmentActivity {
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getSerializableExtra("user");
		String screenName = user.getScreenName();
		setTitle("@"+screenName);
		populateUserInfo(screenName);
	}

	private void populateUserInfo(String screenName) {
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
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserprofileTimelineFragment profileFragment = UserprofileTimelineFragment.newInstance(screenName);
		ft.replace(R.id.fragment_profile, profileFragment);
		ft.commit();
  }
	
	
}

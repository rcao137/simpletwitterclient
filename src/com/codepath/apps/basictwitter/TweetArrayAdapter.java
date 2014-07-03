package com.codepath.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.Activity.ProfileActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	private final int REQUEST_CODE = 20;
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet_item, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;

		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}

		// find the views within template
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUsername);
		TextView tvName = (TextView) v.findViewById(R.id.tvname);

		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvRelativeDate = (TextView) v.findViewById(R.id.tvRelativeDate);
		ivProfileImage.setImageResource(android.R.color.transparent);



		ImageLoader imageLoader = ImageLoader.getInstance();

		// populate views with tweet data
		User user = tweet.getUser();
		imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);	
		tvUserName.setText(user.getScreenName());
		tvName.setText(user.getName());
		tvBody.setText(tweet.getBody());
		if (tweet.getRelativeDate() != null)
			tvRelativeDate.setText(tweet.getRelativeDate());
		
		// pass user to activity view
		ivProfileImage.setTag(user);

		ivProfileImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				User user = (User) v.getTag();
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("user", user);
				getContext().startActivity(i);
			}
		});
		
		return v;

	}


}

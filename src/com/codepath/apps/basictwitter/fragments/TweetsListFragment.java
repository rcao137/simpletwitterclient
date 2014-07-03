package com.codepath.apps.basictwitter.fragments;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	protected ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweets;
	protected PullToRefreshListView lvTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		// Setup handles to view objects here
		// etFoo = (EditText) v.findViewById(R.id.etFoo);
		return view;
	}

	public void insertTweettoTop(Tweet t) {
		aTweets.insert(t, 0);
//		lvTweets.setSelection(0);		
	}
}


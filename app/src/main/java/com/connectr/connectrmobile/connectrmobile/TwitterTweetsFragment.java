package com.connectr.connectrmobile.connectrmobile;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Johnny on 3/5/16.
 */
public class TwitterTweetsFragment extends Fragment {

    private static final String TWITTER_TRENDS_KEY = "TWITTER_TRENDS_KEY";

    private ArrayList<String> topTenTweetsJSON;
    private ArrayList<TrendingTweet> topTenTweets;

    private Firebase mFirebaseRef;
    private RecyclerView mRecyclerView;


    public static TwitterTweetsFragment newInstance(ArrayList<String> topTenTweets) {
        Bundle args = new Bundle();
        args.putStringArrayList(TWITTER_TRENDS_KEY, topTenTweets);
        TwitterTweetsFragment fragment = new TwitterTweetsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TwitterTweetsFragment newInstance() {
        return new TwitterTweetsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_twitter_tweets, container, false);

        Firebase.setAndroidContext(getActivity());
        mFirebaseRef = new Firebase("https://dazzling-inferno-6316.firebaseio.com/");

        topTenTweets = new ArrayList<>();
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));
        topTenTweets.add(new TrendingTweet("#hackathon", "30000"));

        mRecyclerView = (RecyclerView) v.findViewById(R.id.twitter_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TweetAdapter());

        return v;
    }

    private class TweetHolder extends RecyclerView.ViewHolder {
        TextView hashtagTextView;
        TextView tweetVolumeTextView;

        public TweetHolder(View itemView) {
            super(itemView);
            hashtagTextView = (TextView) itemView.findViewById(R.id.hashtag_text_view);
            tweetVolumeTextView = (TextView) itemView.findViewById(R.id.tweet_volume_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    try {
                        // get the Twitter app if possible
                        getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com/search/?q=%22Apple%20$1.5%22"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com/search/?q=%22Apple%20$1.5%22"));
                    }
                    startActivity(intent);
                }
            });
        }

        public void setTweet(TrendingTweet t) {
            hashtagTextView.setText(t.getHashtag());
            tweetVolumeTextView.setText(t.getTweetVolume());
        }
    }

    private class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {

        @Override
        public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_twitter_tweets, parent, false);
            return new TweetHolder(view);
        }

        @Override
        public void onBindViewHolder(TweetHolder holder, int position) {
            holder.setTweet(topTenTweets.get(position));
        }

        @Override
        public int getItemCount() {
            return topTenTweets.size();
        }
    }

    // Private class for holding Twitter tweet data
    private class TrendingTweet {
        private String mHashtag;
        private String mTweetVolume;

        public TrendingTweet(String hashtag, String tweetVolume) {
            mHashtag = hashtag;
            mTweetVolume = tweetVolume;
        }

        public String getHashtag() {
            return mHashtag;
        }

        public String getTweetVolume() {
            return mTweetVolume;
        }
    }
}

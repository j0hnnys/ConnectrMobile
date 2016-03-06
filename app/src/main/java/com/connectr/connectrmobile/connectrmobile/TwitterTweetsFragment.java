package com.connectr.connectrmobile.connectrmobile;

import android.animation.ObjectAnimator;
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
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Johnny on 3/5/16.
 */
public class TwitterTweetsFragment extends Fragment {

    private static final String TWITTER_TRENDS_KEY = "TWITTER_TRENDS_KEY";

    private ArrayList<TwitterData> topTenTweets;
    private ArrayList<Integer> progressList;

    private Firebase mFirebaseRef;
    private RecyclerView mRecyclerView;


    public static TwitterTweetsFragment newInstance(ArrayList<TwitterData> topTenTweets) {
        Bundle args = new Bundle();
        args.putSerializable(TWITTER_TRENDS_KEY, topTenTweets);
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


        progressList = new ArrayList<>();
        topTenTweets = (ArrayList<TwitterData>) getArguments().getSerializable(TWITTER_TRENDS_KEY);

        //we have to check if there is no tweetvolume man, maybe setting a default value to 0?
        int maxTweetVolume = 0;
        for (TwitterData t : topTenTweets) {
            if (t.getTweetVolume() > maxTweetVolume) {
                maxTweetVolume = t.getTweetVolume();
            }
        }
        int progress=0;
        // Now, having the max tweet volume, apply this calculation for each one
        for (TwitterData t : topTenTweets) {
            progress = Math.round( ( t.getTweetVolume() / (float) maxTweetVolume) * 100);
            progressList.add(progress);
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.twitter_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TweetAdapter(progress));

        return v;
    }

    private class TweetHolder extends RecyclerView.ViewHolder {
        TextView hashtagTextView;
        TextView tweetVolumeTextView;
        String twitterURL;
        ProgressBar progressBar;


        public TweetHolder(View itemView) {
            super(itemView);
            hashtagTextView = (TextView) itemView.findViewById(R.id.hashtag_text_view);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            tweetVolumeTextView = (TextView) itemView.findViewById(R.id.tweet_volume_text_view);
            progressBar.setMax(100);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    try {
                        // get the Twitter app if possible
                        getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
                    }
                    startActivity(intent);
                }
            });
        }

        public void setTweet(TwitterData t, int progress) {
            hashtagTextView.setText(t.getHashtag());
            twitterURL = t.getUrl();
            progressBar.setMax(100);
            tweetVolumeTextView.setText(String.valueOf(t.getTweetVolume() / 1000) + "k");

            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress);
            progressAnimator.setDuration(2000);
            progressAnimator.setInterpolator(new LinearInterpolator());
            progressAnimator.start();
        }
    }

    private class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {

        int progress;

        public TweetAdapter(int maxTweetVolume) {
            progress = maxTweetVolume;
        }

        @Override
        public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_twitter_tweets, parent, false);
            return new TweetHolder(view);
        }

        @Override
        public void onBindViewHolder(TweetHolder holder, int position) {
            holder.setTweet(topTenTweets.get(position), progressList.get(position));
        }

        @Override
        public int getItemCount() {
            return topTenTweets.size();
        }
    }

}

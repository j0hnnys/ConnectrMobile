package com.connectr.connectrmobile.connectrmobile;

import java.io.Serializable;

/**
 * Created by Johnny on 3/6/16.
 */
public class TwitterData implements Serializable {

    private String mHashtag;
    private int mTweetVolume;
    private String mUrl;

    public TwitterData(String hashtag, int tweetVolume, String url) {
        mHashtag = hashtag;
        mTweetVolume = tweetVolume;
        mUrl = url;
    }

    public String getHashtag() {
        return mHashtag;
    }

    public int getTweetVolume() {
        return mTweetVolume;
    }

    public String getUrl() {
        return mUrl;
    }
}

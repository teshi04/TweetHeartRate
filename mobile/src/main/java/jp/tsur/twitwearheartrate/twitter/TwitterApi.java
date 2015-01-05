package jp.tsur.twitwearheartrate.twitter;

import android.content.Context;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;


public class TwitterApi {

    public static void updateStatus(Context context, String statusText) throws TwitterException {
        StatusUpdate statusUpdate = new StatusUpdate(statusText);
        TwitterUtils.getTwitterInstance(context).updateStatus(statusUpdate);
    }
}

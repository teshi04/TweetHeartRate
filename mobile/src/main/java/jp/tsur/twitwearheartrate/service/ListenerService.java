package jp.tsur.twitwearheartrate.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import jp.tsur.twitwearheartrate.twitter.TwitterApi;
import twitter4j.TwitterException;


public class ListenerService extends WearableListenerService {

    public static final String DATA_MAP_PATH_UPDATE_STATUS = "/heartrate";

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onMessageReceived(MessageEvent event) {
        if (event.getPath().equals(DATA_MAP_PATH_UPDATE_STATUS)) {
            byte[] data = event.getData();

            try {
                TwitterApi.updateStatus(this, new String(data));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }


}
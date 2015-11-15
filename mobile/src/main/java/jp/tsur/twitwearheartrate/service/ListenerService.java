package jp.tsur.twitwearheartrate.service;

import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import jp.tsur.twitwearheartrate.ui.ResultActivity;


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

            String text = new String(data);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ResultActivity.EXTRA_HEART_RATE, text);
            startActivity(intent);
        }
    }
}
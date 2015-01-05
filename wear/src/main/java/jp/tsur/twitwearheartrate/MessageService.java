package jp.tsur.twitwearheartrate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

public class MessageService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String EXTRA_HEART_RATE = "heartrate";
    public static final String PATH_HEART_RATE = "/heartrate";


    private GoogleApiClient mGoogleApiClient;

    public MessageService() {
        super(MessageService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mGoogleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);
        NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : result.getNodes()) {
            Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), PATH_HEART_RATE,
                    intent.getStringExtra(EXTRA_HEART_RATE).getBytes());
        }

        if (result.getStatus().isSuccess()) {
            startConfirmationActivity(this,
                    ConfirmationActivity.SUCCESS_ANIMATION, getString(R.string.confirmation_animation_open_on_phone));
        } else {
            startConfirmationActivity(this,
                    ConfirmationActivity.FAILURE_ANIMATION, getString(R.string.confirmation_animation_failure));
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static void startConfirmationActivity(Context context, int animationType, String message) {
        Intent confirmationActivity = new Intent(context, ConfirmationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animationType)
                .putExtra(ConfirmationActivity.EXTRA_MESSAGE, message);
        context.startActivity(confirmationActivity);
    }
}

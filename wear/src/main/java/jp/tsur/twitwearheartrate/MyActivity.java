package jp.tsur.twitwearheartrate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private String mHartRate;

    @InjectView(R.id.text_view)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.inject(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Sensor and sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null){
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager!=null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            Log.d("TAG", String.valueOf(event.values[0]));
            if ((int) event.values[0] > 0) {
                mHartRate =  String.valueOf((int) event.values[0]);
                mTextView.setText(mHartRate);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @OnClick(R.id.completion_button)
    void completion() {
        Intent intent = new Intent(this, MessageService.class);
        intent.putExtra(MessageService.EXTRA_HEART_RATE, "私の現在の心拍数は " + String.valueOf(mHartRate) + " bpm です #心拍数ツイート https://play.google.com/store/apps/details?id=jp.tsur.twitwearheartrate");
        startService(intent);
    }
}

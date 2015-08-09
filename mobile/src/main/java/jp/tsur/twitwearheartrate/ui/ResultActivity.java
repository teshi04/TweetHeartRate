package jp.tsur.twitwearheartrate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.tsur.twitwearheartrate.R;


public class ResultActivity extends ActionBarActivity {

    public static final String EXTRA_HEART_RATE = "heart_rate";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.heart_rate)
    TextView heartRateText;

    private String heartRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        heartRate = getIntent().getStringExtra(EXTRA_HEART_RATE);
        heartRateText.setText(heartRate);
    }

    @OnClick(R.id.share_button)
    void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, heartRate);
        startActivity(intent);
    }
}

package com.zivame.zivamechallenege;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zivame.zivamechallenege.utils.AnimUtils;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        title = findViewById(R.id.app_title);

        AnimUtils.slideUpFromBottom(SplashActivity.this,title);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AnimUtils.slideOutToTop(SplashActivity.this,title);
                    }
                });
            }
        },2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                    }
                });


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivityTransition();
//                } else {
//                    startMainActivity();
//                }
            }
        },2500);
    }

    private void startMainActivity() {

        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void startActivityTransition() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}

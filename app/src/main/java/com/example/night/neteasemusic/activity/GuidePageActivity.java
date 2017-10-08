package com.example.night.neteasemusic.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;


/**
 * Created by Night on 2017/9/30.
 * Desc:
 */

public class GuidePageActivity extends AppCompatActivity {
    private ProgressBar mPbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide_page);
        initView();
        NetEaseApplication.fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i += i) {
                    progress(i);
                    i += 10;
                }
                startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    private void progress(int i) {
        mPbProgress.setProgress(i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mPbProgress = (ProgressBar) findViewById(R.id.pb_progress);
    }
}

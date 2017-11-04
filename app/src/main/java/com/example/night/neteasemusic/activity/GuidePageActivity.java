package com.example.night.neteasemusic.activity;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.contract.GuidePageContract;
import com.example.night.neteasemusic.mvp.MVPBaseActivity;
import com.example.night.neteasemusic.presenter.GuidePagePresenter;


/**
 * Created by Night on 2017/9/30.
 * Desc:
 */

public class GuidePageActivity extends MVPBaseActivity<GuidePageContract.View, GuidePagePresenter> implements GuidePageContract.View {
    private ProgressBar mPbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide_page);
        mPbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        mPresenter.load(GuidePageActivity.this, MainActivity.class);
    }

    @Override
    public void updateProgress(int i) {
        mPbProgress.setProgress(i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

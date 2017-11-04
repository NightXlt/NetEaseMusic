package com.example.night.neteasemusic.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;

import com.example.night.neteasemusic.contract.MainActivityContract;
import com.example.night.neteasemusic.model.PermissionModel;
import com.example.night.neteasemusic.mvp.BasePresenterImpl;
import com.example.night.neteasemusic.utils.CommonUtils;
import com.example.night.neteasemusic.utils.Constants;
import com.example.night.neteasemusic.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Night
 * @desc MainActivity Presenter
 * @since 2017/10/25 20:54
 */

public class MainActivityPresenter extends BasePresenterImpl<MainActivityContract.View> implements MainActivityContract.Presenter {

    private List<PermissionModel> mPermissionModels = new ArrayList<>();
    PermissionHelper mPermissionHelper;

    @Override
    public void requestPermission(Activity activity) {
        if (CommonUtils.isLollipop()) {
            mPermissionModels.add(new PermissionModel("电话", Manifest.permission.READ_PHONE_STATE, "我们需要读取手机信息的权限来标识您的身份", Constants.READ_PHONE_STATE_CODE));
            mPermissionModels.add(new PermissionModel("存储空间", Manifest.permission.WRITE_EXTERNAL_STORAGE, "我们需要您允许我们读写你的存储卡，以方便我们临时保存一些数据", Constants.WRITE_EXTERNAL_STORAGE_CODE));

            mPermissionHelper = new PermissionHelper(mPermissionModels, activity);
            mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.onApplyPermissionListener() {
                @Override
                public void onAfterApplyAllPermission() {
                    Log.i("TAG", "all permission has been granted");
                }
            });
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                Log.i("TAG", "all permission has been granted");
            } else {
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean keyDown(int keyCode, KeyEvent event) {
        return mView.KeyDown(keyCode, event);
    }
}

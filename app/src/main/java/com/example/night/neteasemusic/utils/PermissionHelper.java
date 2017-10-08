package com.example.night.neteasemusic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.night.neteasemusic.model.PermissionModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.night.neteasemusic.utils.Constants.REQUEST_OPEN_APPLICATION_SETTINGS_CODE;


/**
 * Created by Night on 2017/10/5.
 * Desc:权限申请工具
 */

public class PermissionHelper {

    private onApplyPermissionListener mOnApplyPermissionListener;
    private List<PermissionModel> permissions = new ArrayList<>();
    private Activity mActivity;

    public PermissionHelper(@Nullable List permissions, Activity activity) {
    /*    permissions.add(new PermissionModel("电话", Manifest.permission.READ_PHONE_STATE,"我们需要读取手机信息的权限来标识您的身份",Constants.READ_PHONE_STATE_CODE));
        permissions.add(new PermissionModel("存储空间", Manifest.permission.WRITE_EXTERNAL_STORAGE, "我们需要您允许我们读写你的存储卡，以方便我们临时保存一些数据", Constants.WRITE_EXTERNAL_STORAGE_CODE));
    */
        this.permissions = permissions;
        this.mActivity = activity;
    }

    public void applyPermissions() {
        for (PermissionModel permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, permission.getPermisson())) {
                ActivityCompat.requestPermissions(mActivity, new String[]{permission.getPermisson()}, permission.getRequestCode());
                return;
            }
            if (mOnApplyPermissionListener != null) {
                mOnApplyPermissionListener.onAfterApplyAllPermission();
            }
        }
    }

    /**
     * 对应Activity的 {@code onRequestPermissionsResult(...)} 方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_PHONE_STATE_CODE:
            case Constants.WRITE_EXTERNAL_STORAGE_CODE:
                //如果不允许发起第二次请求
                if (PackageManager.PERMISSION_GRANTED != grantResults[0]) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(mActivity).setTitle("权限申请").setMessage(findPermissionExplain(permissions[0]))
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                applyPermissions();
                                            }
                                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                    //到这已经是3+次的请求了
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setTitle("权限申请")
                                .setMessage("请在打开的窗口的权限中开启" + findPermissionName(permissions[0]) + "权限，以正常使用本应用")
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openApplicationSettings(REQUEST_OPEN_APPLICATION_SETTINGS_CODE);
                                    }


                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mActivity.finish();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.show();
                    }
                    return;
                }
                //到这说明申请权限成功
                if (isAllRequestedPermissionGranted()) {
                    if (mOnApplyPermissionListener != null) {
                        mOnApplyPermissionListener.onAfterApplyAllPermission();
                    }
                } else {
                    applyPermissions();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 对应Activity的 {@code onActivityResult(...)} 方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_OPEN_APPLICATION_SETTINGS_CODE:
                if (isAllRequestedPermissionGranted()) {
                    if (mOnApplyPermissionListener != null) {
                        mOnApplyPermissionListener.onAfterApplyAllPermission();
                    }
                } else {
                    mActivity.finish();
                }
                break;
        }
    }

    private boolean openApplicationSettings(int requestCode) {
        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // Android L 之后Activity的启动模式发生了一些变化
            // 如果用了下面的 Intent.FLAG_ACTIVITY_NEW_TASK ，并且是 startActivityForResult
            // 那么会在打开新的activity的时候就会立即回调 onActivityResult
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Throwable e) {
            Log.e("TAG", "", e);
        }
        return false;
    }

    private String findPermissionName(String permission) {
        for (PermissionModel permissionModel : permissions) {
            if (permissionModel != null && permissionModel.getPermisson() != null && TextUtils.equals(permissionModel.getPermisson(), permission)) {
                return permissionModel.getName();
            }
        }
        return null;
    }

    private String findPermissionExplain(String permission) {
        for (PermissionModel permissionModel : permissions) {
            if (permissionModel != null && permissionModel.getPermisson() != null && TextUtils.equals(permissionModel.getPermisson(), permission)) {
                return permissionModel.getReason();
            }
        }
        return null;
    }

    /**
     * 判断是否所有的权限都被授权了
     *
     * @return
     */
    public boolean isAllRequestedPermissionGranted() {
        for (PermissionModel model : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, model.getPermisson())) {
                return false;
            }
        }
        return true;
    }

    public void setOnApplyPermissionListener(onApplyPermissionListener onApplyPermissionListener) {
        this.mOnApplyPermissionListener = onApplyPermissionListener;
    }


    public interface onApplyPermissionListener {
        //申请到所有权限后进行的操作
        void onAfterApplyAllPermission();
    }
}

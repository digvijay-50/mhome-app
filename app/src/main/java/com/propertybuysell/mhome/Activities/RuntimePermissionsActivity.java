package com.propertybuysell.mhome.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.propertybuysell.mhome.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public abstract class RuntimePermissionsActivity extends AppCompatActivity {


    public static final int GALLERY_REQUEST_PERMISSIONS = 200;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
            try {

                Snackbar bar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.runtime_permission_msg),
                        Snackbar.LENGTH_LONG)
                        .setAction("SETTING",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SplashActivity.disablefor1sec(v);
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                                        intent.setData(Uri.parse("package:" + getPackageName()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        startActivity(intent);
                                    }
                                })
                        .setActionTextColor(Color.RED);
//                TextView tv = bar.getView().findViewById(R.id.snackbar_text);
//                tv.setTextColor(Color.CYAN);
                bar.show();
                onPermissionsDenied(requestCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int requestCode) {

        final String[] requestedPermissionsNew = checkPermissionNeedToTake(requestedPermissions);
        if (requestedPermissionsNew.length != 0) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            boolean shouldShowRequestPermissionRationale = false;
            for (String permission : requestedPermissionsNew) {
                permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
                shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
            }
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, requestedPermissionsNew, requestCode);
            } else {
                onPermissionsGranted(requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    public abstract void onPermissionsDenied(int requestCode);


    public String[] checkPermissionNeedToTake(String[] requestedPermissions) {
        List<String> arrPermissions = new ArrayList<String>();

        for (int i = 0; i < requestedPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), requestedPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                arrPermissions.add(requestedPermissions[i]);
            }
        }

        return arrPermissions.toArray(new String[arrPermissions.size()]);

    }


    public void startGalleryCheck() {
        requestAppPermissions(new
                String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, GALLERY_REQUEST_PERMISSIONS);
    }


}

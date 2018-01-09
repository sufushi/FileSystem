package com.rdc.project.filesystem.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HiPermission.create(getApplicationContext())
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}

package com.sunggil.mypermissionapp

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

class PermissionCheck {
    companion object {
        val TAG = "PermissionCheck"
        val PERMISSION_REQUEST_CODE = 9999

        fun checkAll(activity : Activity, permissions : Array<String>, callback : PermissionCallback) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                callback.grant()
                return
            }

            val needPermissionIndex = ArrayList<String>()

            for (p : String in permissions) {
                if (activity.checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    needPermissionIndex.add(p)
                }
            }

            if (needPermissionIndex.size > 0) {
                activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE)
            } else {
                callback.grant()
            }
        }

        fun check(activity : Activity, permission : String, callback : PermissionCallback) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                callback.grant()
                return
            }

            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    callback.need(permission)
                } else {
                    // No explanation needed, we can request the permission.
                    activity.requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                callback.grant()
            }
        }

        fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>, grantResults : Array<Int>) : ArrayList<String>{
            val denied = ArrayList<String>()

            when (requestCode) {
                PERMISSION_REQUEST_CODE -> {
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            Log.e(TAG, "permission require : ${permissions[grant]}")
                            denied.add(permissions[grant])
                        }
                    }
                }
            }

            return denied
        }

    }
}
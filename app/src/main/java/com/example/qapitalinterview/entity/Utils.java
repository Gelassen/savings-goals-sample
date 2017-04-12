package com.example.qapitalinterview.entity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.Window;

import com.example.qapitalinterview.App;

/**
 * Information why status bar height can't be obtained via property on modern devices
 * http://stackoverflow.com/questions/3407256/height-of-status-bar-in-android
 * */
public class Utils {

    private static int statusBarHeight;

    public static void initStatusBarHeight(Window window) {
        statusBarHeight = getStatusBarHeight(window);
        Log.d(App.TAG, "statusBarHeight: " + statusBarHeight);
    }

    public static int getStatusBarHeight() {
        return statusBarHeight;
    }

    public static int getStatusBarHeight(Window window) {
        Rect rectangle = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        final int titleBarHeight = contentViewTop - statusBarHeight;
        return titleBarHeight;
    }

    public static int getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

package name.dickie.android.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

/**
 * Created by wuxiaodong on 18/3/15.
 */

public class ActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {
    private int activeCount;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activeCount++;
        if(activeCount == 1){
            //说明有一个Activity 进入前台
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activeCount--;
        if(activeCount == 0){
            //所有 activity都退出到幕后
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}

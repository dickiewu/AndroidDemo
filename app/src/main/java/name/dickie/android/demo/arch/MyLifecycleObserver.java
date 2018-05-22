package name.dickie.android.demo.arch;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;


public class MyLifecycleObserver implements LifecycleObserver {
    private static final String TAG = "wxd";
    @OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_START)
    public void onStart(){
        Log.e(TAG, String.format("observe start event"));
    }

    @OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.e(TAG, String.format("observe resume event"));
    }
}
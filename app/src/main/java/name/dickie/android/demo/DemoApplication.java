package name.dickie.android.demo;

import android.app.Application;
import android.content.Intent;

import me.ele.uetool.UETool;
import name.dickie.android.demo.service.MusicService;

/**
 * Created by wuxiaodong on 18/3/13.
 */

public class DemoApplication extends Application {

    private static final String TAG = "WXD-DEMO";

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallback());

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MusicService.class);
        UETool.showUETMenu();
    }
}
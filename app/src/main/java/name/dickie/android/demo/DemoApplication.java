package name.dickie.android.demo;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * Created by wuxiaodong on 18/3/13.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallback());
    }
}

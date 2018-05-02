package name.dickie.android.demo.receiver;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.common.base.Strings;

import java.util.List;

/**
 * Created by wuxiaodong on 18/4/10.
 */

public class OtherReceiver extends BroadcastReceiver {
    public static final String TAG="WXD";

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, "testlock")) {
            Log.e("wxd-demo", String.format("receive broadcast.."));
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            boolean interactive = pm.isScreenOn();
            if (!interactive) {
                Log.e("wxd-demo", String.format("screen not on ...!!!!"));
                // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire(); // 点亮屏幕
                wl.release(); // 释放
            }
        }else if(TextUtils.equals(action,"runningtasks")){
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(20);
            for (ActivityManager.RunningTaskInfo runningTask : runningTasks) {
                Log.e(TAG, String.format("num activities:%d, topActivity:%s,baseActivity:%s", runningTask.numActivities,runningTask.topActivity,runningTask.baseActivity));
            }
        }
    }

    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }
}

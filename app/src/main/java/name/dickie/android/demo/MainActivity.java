package name.dickie.android.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.google.common.collect.Lists;

import name.dickie.android.demo.activity.AActivity;
import name.dickie.android.demo.activity.AnimationActivity;
import name.dickie.android.demo.activity.AudioActivity;
import name.dickie.android.demo.activity.DrawerActivity;
import name.dickie.android.demo.activity.RecyclerViewTestActivity;
import name.dickie.android.demo.activity.Test2Activity;
import name.dickie.android.demo.activity.TestActivity;
import name.dickie.android.demo.activity.TouchTestActivity;
import name.dickie.android.demo.drawable.CouponDrawable;
import name.dickie.android.demo.model.RenderVideoList;
import name.dickie.android.demo.receiver.WifiReceiver;
import name.dickie.android.demo.service.MusicService;
import name.dickie.android.demo.utils.PxUtils;
import name.dickie.android.demo.widget.AutoLineLayout;

public class MainActivity extends Activity {
    public static final String TAG = "WXD-DEMO";
    public static MainActivity activity;

    private ImageView ivTest;
    private MusicService musicService;
    private Messenger messenger;
    private AutoLineLayout autoLineLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDefaultScreenDensity(this);
        getScreenDensity_ByResources(this);
        getScreenDensity_ByWindowManager(this);
        activity = this;
        View main = findViewById(R.id.main);

        Log.e(TAG, String.format("版本号:%s",android.os.Build.MODEL));
        Log.e(TAG, String.format("手机厂商:%s", Build.BRAND));


        WallpaperManager instance = WallpaperManager.getInstance(getApplicationContext());
        int wallpaperId = instance.getWallpaperId(WallpaperManager.FLAG_SYSTEM);


        BitmapDrawable drawable = (BitmapDrawable) instance.getDrawable();
        Log.e(TAG, String.format("drawable is :%s,size:%s", drawable, Formatter.formatFileSize(getApplicationContext(),drawable.getBitmap().getByteCount())));
        findViewById(android.R.id.content).setBackground(drawable);

        CouponDrawable couponDrawable = new CouponDrawable();
        couponDrawable.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable who) {

            }

            @Override
            public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

            }
        });

        autoLineLayout = findViewById(R.id.autoLineLayout);

    }

    public void enterTouchTest(View view) {


        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TouchTestActivity.class);
        startActivity(intent);
        //改变动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }

    public void testIntent(View view) {
        //隐式intent
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("name.dickie.android.demo", "name.dickie.android.demo.TouchTestActivity");
        intent.setClass(getApplicationContext(), TouchTestActivity.class);
        startActivity(intent);
    }

    public void enterAActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), AActivity.class);
        startActivity(intent);
    }

    public void testAnimation(View view) {

        //AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.default_alpha);
        //ivTest.startAnimation(animation);

        /*//测试translate 动画
        TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.default_trans);
        ivTest.startAnimation(translateAnimation);*/


        //测试scale动画
        ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.default_scale);
        ivTest.startAnimation(scaleAnimation);
        ivTest.postDelayed(() -> ivTest.clearAnimation(), 1500);
    }

    public void startService(View view) {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }

    public void testService(View view) {
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            musicService.action();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("demo", "service disconnected...,name:" + name.getClassName());
        }
    };

    private ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("demo", "remote service connected...");
            messenger = new Messenger(service);
            Message message = Message.obtain(null, 0);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("demo", "service disconnected...,name:" + name.getClassName());

        }
    };

    public void bindService(View view) {
        /*Intent intent = new Intent(getApplicationContext(), MusicService2.class);
        bindService(intent,connection,0);
*/
        Intent intent = new Intent();
        intent.setAction("name.dickiewu.remote_action");
        intent.setPackage(getPackageName());
        bindService(intent, connection2, BIND_AUTO_CREATE);


    }

    public void unbindService(View view) {
        unbindService(connection);
    }

    public void enterAnimationTest(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), AnimationActivity.class);
        startActivity(intent);
    }

    public void enterDrawer(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), DrawerActivity.class);
        startActivity(intent);
    }


    public void dynamicAdd(View view) {
        Button button = new Button(getApplicationContext());
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        marginLayoutParams.leftMargin = PxUtils.dp2px(20);
        button.setLayoutParams(marginLayoutParams);
        button.setText("hell oworld");
        autoLineLayout.addView(button);
    }

    public void enterAudioActivity(View view) {
        Intent intent = new Intent();
        RenderVideoList renderVideoList = new RenderVideoList();
        renderVideoList.setTitle("is a test title");
        RenderVideoList.VideoItem videoItem = new RenderVideoList.VideoItem();
        videoItem.setVideoItemId("1111111");
        renderVideoList.setVideoItems(Lists.newArrayList(videoItem));
        intent.putExtra("key_data", renderVideoList);
        intent.setClass(getApplicationContext(), AudioActivity.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void testAlarmManager(View view) {
        Log.e(TAG, String.format("execute testAlarmManager..."));
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Intent intent = new Intent(getApplicationContext(), AActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(getApplicationContext(), WifiReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long waketime = System.currentTimeMillis() + 10 * 1000;// 10s 以后

        //alarmManager.set(AlarmManager.RTC_WAKEUP, waketime, pendingIntent);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, waketime, pendingIntent);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,waketime,pendingIntent);
    }

    public void testRecyclerView(View view) {
        Intent intent = new Intent(getApplicationContext(), RecyclerViewTestActivity.class);
        startActivity(intent);
    }

    public void enterTestActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(intent);
    }

    public void enterTest2(View view) {
        Intent intent = new Intent(getApplicationContext(), Test2Activity.class);
        startActivity(intent);
    }

    public static void getScreenDensity_ByWindowManager(Context context){
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG,"Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
        Log.d(TAG,"Screen mDisplayMetrics: "+mDisplayMetrics);
    }
    // 通过Resources获取
    public static  void getScreenDensity_ByResources(Context context){
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG,"Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
        Log.d(TAG,"Screen mDisplayMetrics: "+mDisplayMetrics);

    }
    // 获取屏幕的默认分辨率
    public static void getDefaultScreenDensity(Context context){
        Display mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int height = mDisplay.getHeight();
        Log.d(TAG,"Screen Default Ratio: ["+width+"x"+height+"]");
        Log.d(TAG,"Screen mDisplay: "+mDisplay);



    }
}
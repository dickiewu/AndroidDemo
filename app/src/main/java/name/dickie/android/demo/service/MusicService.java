package name.dickie.android.demo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import name.dickie.android.demo.utils.NotificationHelper;

public class MusicService extends Service {
    public MusicService() {
        Log.e("demo", "MusicService constructor...");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("demo", "MusicService create..., ");

    }

    private void setForgroundService() {
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        Notification.Builder builder = notificationHelper.getNotification1("title", "body");
        Notification notification = builder.build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("demo", "MusicService onStartCommand,flags:"+flags+",startId:" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("demo", "Music service onBind...");
        return new MusicBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {  // intent 拿来干啥?? unbindService 时没有传入
        Log.e("demo", "unbind service...");
        //return false;
        return true;

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e("demo", "MusicService onRebind...........");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("demo", "MusicService destory...");
    }

    public void action() {
        Log.e("demo", "MusicBinder action");
    }

    public class MusicBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }
}
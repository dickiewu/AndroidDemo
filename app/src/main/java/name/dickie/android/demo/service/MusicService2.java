package name.dickie.android.demo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import name.dickie.android.demo.utils.NotificationHelper;

public class MusicService2 extends Service {


    class IncomingHandler extends Handler{
        @Override
        public void dispatchMessage(Message msg) {
            Log.e("demo", "dispatchMessage message...");
            super.dispatchMessage(msg);
        }
    }

    private Messenger messenger = new Messenger(new IncomingHandler());


    public MusicService2() {
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
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {  // intent 拿来干啥?? unbindService 时没有传入
        Log.e("demo", "unbind service...");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("demo", "MusicService destory...");
    }

    public void action() {
        Log.e("demo", "MusicBinder action");
    }
}
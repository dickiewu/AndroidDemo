package name.dickie.android.demo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.io.IOException;

import name.dickie.android.demo.R;
import name.dickie.android.demo.db.MySqliteOpenHelper;
import name.dickie.android.demo.provider.Constant;
import name.dickie.android.demo.utils.AudioFocuHelper;
import name.dickie.android.demo.utils.MyMediaPlayer;

public class AudioActivity extends Activity {

    private static final String TAG = "WXD-DEMO";
    private MediaPlayer alarmPlayer;
    private AudioFocuHelper alarmFocus;
    private MySqliteOpenHelper mMySqliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void play(View view) throws IOException {
        AudioFocuHelper audioFocuHelper = new AudioFocuHelper();
        audioFocuHelper.setFocusChangeListener(new AudioFocuHelper.OnAudioFocusChangeListener() {
            @Override
            public void onFocusGain() {
                Log.e("demo", "music1 focus gain...");
                MyMediaPlayer.getInstance().start();
            }
            @Override
            public void onFocusLoss() {
                Log.e("demo", "music1 focus loss...");
                MyMediaPlayer.getInstance().pause();
            }

            @Override
            public void onFocusLossTransient() {
                Log.e("demo", "music1 focus loss transient...");
                MyMediaPlayer.getInstance().pause();
            }

            @Override
            public void onFocusLossMayDuck() {
                Log.e("demo", "music1 focus loss may duck......");
                MyMediaPlayer.getInstance().pause();
            }
        });
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        int result = audioFocuHelper.requestAudioFocus(audioAttributes,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("demo", "music request failed...");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("demo", "music reqeust granted...");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_DELAYED:
                Log.e("demo", "music request delayed....");
                break;
        }

        MyMediaPlayer.getInstance().setDataSourceAndPrepare("/sdcard/arose.mp3");
        MyMediaPlayer.getInstance().start();
    }

    public void pause(View view) {
        MyMediaPlayer.getInstance().pause();
    }

    public void play2(View view) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource("/sdcard/walk.mp3");
        mediaPlayer.prepare();
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    /**
     * 播放闹钟
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void playAlarm(View view) throws IOException {
        alarmFocus = new AudioFocuHelper();
        alarmFocus.setFocusChangeListener(new AudioFocuHelper.OnAudioFocusChangeListener() {
            @Override
            public void onFocusGain() {
                Log.e("demo", "alarm  focus gain....");
                alarmPlayer.start();
            }

            @Override
            public void onFocusLoss() {
                Log.e("demo", "alarm  focus loss....");
                alarmPlayer.pause();
            }

            @Override
            public void onFocusLossTransient() {
                Log.e("demo", "alarm  focus transient....");
                alarmPlayer.pause();

            }

            @Override
            public void onFocusLossMayDuck() {
                Log.e("demo", "alarm  focus may duck......");
                alarmPlayer.pause();

            }
        });

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        //int result = alarmFocus.requestAudioFocus(audioAttributes,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        int result = -2;
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("demo", "alarm request failed...");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("demo", "alarm reqeust granted...");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_DELAYED:
                Log.e("demo", "alarm request delayed....");
                break;
        }

        alarmPlayer = new MediaPlayer();
        alarmPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        alarmPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://name.dickie.android.demo/" + R.raw.alert_clock));
        alarmPlayer.prepare();
        alarmPlayer.setLooping(true);
        alarmPlayer.start();
    }

    public void stopAlarm(View view) {
        alarmPlayer.stop();
        alarmPlayer.release();
        alarmPlayer = null;
        int result = alarmFocus.abandomAudioFocus();
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("demo", "alram focus abandom granted...");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("demo", "alram focus abandom  failed...");
                break;
        }
    }

    public void getRingInfo(View view) {
        RingtoneManager ringtoneManager = new RingtoneManager(getApplicationContext());
        Cursor cursor = ringtoneManager.getCursor();
        while (cursor != null && cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            Log.e("wxd-demo", String.format("title:%s,id:%d", title, id));
        }
    }

    public void chooseRingtones(View view) {
        Intent intent = new Intent();
        intent.setAction(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, 100);
    }

    private Uri selectedAlarmUri;

    public void chooseAlarm(View view) {
        Intent intent = new Intent();
        intent.setAction(RingtoneManager.ACTION_RINGTONE_PICKER); // 打开选择 picker
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM); // 设置选择类型
        startActivityForResult(intent, 200);
    }

    public void setAlarm(View view) {
        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_ALARM, selectedAlarmUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        Log.e("wxd-demo", String.format("uri is :%s", uri.toString()));

        if (requestCode == 100) {

        } else if (requestCode == 200) {
            selectedAlarmUri = uri;
        }
    }

    public void playRingtone(View view) {
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse("content://media/internal/audio/media/49"));
        ringtone.play();

        String title = ringtone.getTitle(getApplicationContext());
        Log.e("wxd-demo", String.format("title is :%s", title));

    }

    public void initOpenHelper(View view) {
        mMySqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
    }

    public void insert(View view) {
        for (int i = 0; i < 20; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name","walk on the water:"+i);
            mMySqliteOpenHelper.insert(contentValues);
        }
    }

    public void queryByContentProvider(View view) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST}, null, null, null);
        while(cursor!=null && cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            long duration = cursor.getLong(cursor.getColumnIndex("duration"));
            Log.e(TAG, String.format("title is :%s,time is :%d:%d", title, duration / 1000 / 60, duration / 1000 % 60));
        }


    }


    public void testContentProvider(View view) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(Constant.UriStr), null, null, null, null, null);
    }

    public void insertByContentProvider(View view){
        ContentResolver contentResolver = getContentResolver();

    }

    public void deleteByContentProvider(View view){
        ContentResolver contentResolver = getContentResolver();


    }
}

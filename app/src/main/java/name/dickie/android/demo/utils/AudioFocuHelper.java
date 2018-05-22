package name.dickie.android.demo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import name.dickie.android.demo.ContextHolder;

/**
 * Created by wuxiaodong on 18/3/26.
 */

public class AudioFocuHelper implements AudioManager.OnAudioFocusChangeListener {

    private AudioManager audioManager;
    private AudioFocusRequest request;
    private String TAG = "wxd";

    public AudioFocuHelper(){
        init();
    }

    private void init() {
        audioManager = (AudioManager) ContextHolder.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public int requestAudioFocus(AudioAttributes audioAttributes){

        AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true) // 会让requestAudioFocus返回delayed
                .setOnAudioFocusChangeListener(this)
                ;


        request = builder.build();
        int result = audioManager.requestAudioFocus(request);
        return result;
    }

    public int requestAudioFocus(AudioAttributes audioAttributes,int hint){

        AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(hint)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(this)
                ;
        request = builder.build();
        int result = audioManager.requestAudioFocus(request);
        return result;
    }

    public int abandomAudioFocus(){
        int result = audioManager.abandonAudioFocusRequest(request);
        return result;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        Log.e(TAG, String.format("focus chagne: %d",focusChange ));
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                focusChangeListener.onFocusGain();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                focusChangeListener.onFocusLoss();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                focusChangeListener.onFocusLossTransient();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                focusChangeListener.onFocusLossMayDuck();
                break;
        }
    }

    private OnAudioFocusChangeListener focusChangeListener;

    public void setFocusChangeListener(OnAudioFocusChangeListener listener) {
        if (listener != null) {
            this.focusChangeListener = listener;
        }
    }

    public interface OnAudioFocusChangeListener {
        void onFocusGain();
        void onFocusLoss();
        void onFocusLossTransient();
        void onFocusLossMayDuck();
    }
}



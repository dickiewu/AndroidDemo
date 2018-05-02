package name.dickie.android.demo.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.util.Log;
import name.dickie.android.demo.ContextHolder;

/**
 * Created by wuxiaodong on 18/3/26.
 */

public class AudioFocuHelper implements AudioManager.OnAudioFocusChangeListener {

    private AudioManager audioManager;
    private AudioFocusRequest request;

    public AudioFocuHelper(){
        init();
    }

    private void init() {
        audioManager = (AudioManager) ContextHolder.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public int requestAudioFocus(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(this);


        request = builder.build();
        int result = audioManager.requestAudioFocus(request);
        return result;
    }

    public int requestAudioFocus(int hint){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(hint)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(this);
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
        void onFocusLoss();
        void onFocusGain();
        void onFocusLossTransient();
        void onFocusLossMayDuck();
    }
}



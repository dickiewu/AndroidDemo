package name.dickie.android.demo.utils;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;

/**
 * Created by wuxiaodong on 17/12/21.
 */

public class MyMediaPlayer {

    private final MediaPlayer mediaPlayer;


    private MyMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(new BufferUpdateListener());
        mediaPlayer.setOnErrorListener(new ErrorListener());

    }

    public static MyMediaPlayer getInstance() {
        return InstanceHolder.instance;
    }


    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void resume() {
        mediaPlayer.start();
    }


    /**
     * An IllegalStateException is thrown if setDataSource() is called in any other state.
     *
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public boolean setDataSource(String absolutePath) throws IOException {
        try {
            mediaPlayer.setDataSource(absolutePath);
            return true;
        } catch (IllegalStateException e) {
            Log.e("demo", "set data source in illegal state!!!");
            return false;
        }
    }

    public boolean prepare() {
        try {
            mediaPlayer.prepare();
            return true;
        } catch (IllegalStateException e) {
            Log.e("demo", "preapre in illegalstate...");
        } catch (IOException e) {
            Log.e("demo", "ioexception????");
        }
        return false;
    }

    public boolean setDataSourceAndPrepare(String absolutePath) throws IOException {
        if (Strings.isNullOrEmpty(absolutePath) || !new File(absolutePath).exists()) {
            return false;
        }
        return setDataSource(absolutePath) && prepare();
    }

    public void release() {
        mediaPlayer.release();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void start() {
        mediaPlayer.start();
    }

    /**
     * in any state can invoek reset,result in enter into idle state!!
     */
    public void reset() {
        mediaPlayer.reset();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isInStartedState() {
        return mediaPlayer.isPlaying();
    }


    private static class InstanceHolder {
        public static MyMediaPlayer instance = new MyMediaPlayer();
    }

    class CompleteListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("demo", "transfer to COMPLETE state...");

        }
    }

    class SeekListener implements MediaPlayer.OnSeekCompleteListener {

        @Override
        public void onSeekComplete(MediaPlayer mp) {
            int currentPosition = mp.getCurrentPosition();
            Log.e("demo", "SeekComplee, now currentPositon is:" + currentPosition);

        }
    }

    //处于 STARTED state 状态的回调， 只针对网络上的流进行
    class BufferUpdateListener implements MediaPlayer.OnBufferingUpdateListener {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.e("demo", "In STARTED state,percent:" + percent);
        }
    }


    class ErrorListener implements MediaPlayer.OnErrorListener {

        @Override
/*
        1,return fasle 会导致onCompleteListener调用
        2,调用reset 会由 ERROR state 转移到 IDLE state
*/ public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.e("demo", "transfered to the ERROR state, what:" + what + ", extra:" + extra);
            if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                Log.e("demo", "MEDIA_ERROR_UNKNOWN: Unspecified media player error.");
            } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                Log.e("demo",
                      "MEDIA_ERROR_SERVER_DIED: Media server died. In this case, the application must release the MediaPlayer object and instantiate a new one.");
            }

            if (extra == MediaPlayer.MEDIA_ERROR_IO) {
                Log.e("demo", "MEDIA_ERROR_IO:File or network related operation errors.");
            } else if (extra == MediaPlayer.MEDIA_ERROR_MALFORMED) {
                Log.e("demo",
                      "MEDIA_ERROR_MALFORMED:Bitstream is not conforming to the related coding standard or file spec.");
            } else if (extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
                Log.e("demo",
                      "MEDIA_ERROR_UNSUPPORTED:Bitstream is conforming to the related coding standard or file spec, but the media framework does not support the feature.");
            } else if (extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                Log.e("demo",
                      "MEDIA_ERROR_TIMED_OUT:Some operation takes too long to complete, usually more than 3-5 seconds.");
            }

            //must invoke this fucntion
            mediaPlayer.reset();
            return false;
        }
    }
}

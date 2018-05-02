package name.dickie.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLogTags;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.security.NoSuchAlgorithmException;

/**
 * Created by wuxiaodong on 18/3/24.
 */

public class TouchTestLayout extends FrameLayout {

    public TouchTestLayout(Context context) {
        super(context);
    }

    public TouchTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean b = false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e("demo", "touchTestLayout dispatch action down----");
                b = super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("demo", "touchTestLayout  dispatch action move----");
                b = super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "touchTestLayout dispatch action up----");
                b = super.dispatchTouchEvent(ev);
                break;
        }
        Log.e("demo", "touchTestLayout dispatchTouchEvent return "+b);
        return b;
    }

    private long downTime;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean b = super.onInterceptTouchEvent(ev);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                Log.e("demo", "touchTestLayout onIntercept action down----");
                b = super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("demo", "touchTestLayout  onIntercept action move----");
                b = super.onInterceptTouchEvent(ev);
                if (System.currentTimeMillis()-downTime>500) {
                    b = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "touchTestLayout onIntercept action up----");
                b = super.onInterceptTouchEvent(ev);
                break;
        }
        Log.e("demo", "touchTestLayout onIntercept return "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean b = super.onTouchEvent(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e("demo", "touchTestLayout onTouch action down----");
                b = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("demo", "touchTestLayout  onTouch action move----");
                b = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "touchTestLayout onTouch action up----");
                b = super.onTouchEvent(event);
                break;
        }
        Log.e("demo", "touchTestLayout onTouchEvent return "+b);
        return b;
    }
}

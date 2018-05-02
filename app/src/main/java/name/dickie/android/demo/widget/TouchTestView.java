package name.dickie.android.demo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by wuxiaodong on 18/3/24.
 */

public class TouchTestView extends View {
    public TouchTestView(Context context) {
        super(context);
    }

    public TouchTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean ret = false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e("demo", "     touchTestView dispatch action down....");
                ret = super.dispatchTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("demo", "     touchTestView  dispatch action move....");
                ret = super.dispatchTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "     touchTestView dispatch action up....");
                ret = super.dispatchTouchEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("demo", "     touchTestView dispatch action cancel....");
                ret = super.dispatchTouchEvent(event);
                break;
        }
        Log.e("demo", "     touchTestView dispatchTouchEvent return "+ret);
        return ret;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean ret = false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.e("demo", "     touchTestView onTouch action down....");
                ret  = super.onTouchEvent(event);
                ret = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("demo", "     touchTestView  onTouch action move....");
                ret  = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "     touchTestView onTouch action up....");
                ret  = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("demo", "     touchTestView onTouch action cancel....");
                ret  = super.onTouchEvent(event);
                break;
        }
        Log.e("demo", "     touchTestView onTouchEvent return "+ret);
        return ret;
    }
}

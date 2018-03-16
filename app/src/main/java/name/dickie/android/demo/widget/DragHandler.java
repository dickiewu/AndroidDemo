package name.dickie.android.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wuxiaodong on 18/3/13.
 */

public class DragHandler extends ImageView {
    private float lastDownX;
    private float lastDownY;
    private Handler handler = new Handler();
    private boolean flag = false;

    public DragHandler(Context context) {
        super(context);
        init();
    }

    public DragHandler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragHandler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("demo", event.toString());

        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //Log.e("demo", "action down");
                lastDownX = event.getX();
                lastDownY = event.getY();
                Log.e("demo", "scrollX:"+getScrollX()+",scrollY:"+getScrollY());
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.e("demo", "action move");
                float deltaX = event.getX()- lastDownX;
                float deltaY = event.getY()- lastDownY;
                Log.e("demo", "deltaX:"+deltaX);
                //offsetLeftAndRight((int) deltaX);
                //offsetTopAndBottom((int) deltaY);

                //scrollBy(-(int) deltaX, -(int) deltaY);
                //显示边界
                scrollTo(getScrollX()+(int)(-deltaX),getScrollY()+(int) (-deltaY));
                lastDownX = event.getX();
                lastDownY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                Log.e("demo", "action up");

                break;
        }
        return true;
    }
}
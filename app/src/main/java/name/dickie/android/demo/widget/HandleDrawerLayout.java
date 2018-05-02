package name.dickie.android.demo.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/3/25.
 */

public class HandleDrawerLayout extends ViewGroup {

    private View content;
    private View handle;
    private View drawer;
    private View drawGroup;
    private ViewDragHelper dragHelper;

    public HandleDrawerLayout(Context context) {
        this(context,null);
    }

    public HandleDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HandleDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1, new DragCallback());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        content.layout(0,0,content.getMeasuredWidth(),content.getMeasuredHeight());
        drawGroup.layout(-drawer.getMeasuredWidth(),0,handle.getMeasuredWidth(),drawer.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        content = findViewById(R.id.content);
        handle = findViewById(R.id.handle);
        drawer = findViewById(R.id.drawer);
        drawGroup = findViewById(R.id.drawer_group);

        handle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("demo", "click the handler......");
            }
        });
    }

    class DragCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==handle || child==drawer ;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);

        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        /*关于边界的回调*/
        @Override
        public boolean onEdgeLock(int edgeFlags) {
            Log.e("demo", "onEdgeLock，edgeFlags:"+edgeFlags);
            //return super.onEdgeLock(edgeFlags);
            return true;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.e("demo", "edge drag start,edge:"+edgeFlags);
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            Log.e("demo", "edge touched,edge:"+edgeFlags);
            super.onEdgeTouched(edgeFlags, pointerId);
            //dragHelper.captureChildView(contView,pointerId);
        }
    }
}
package name.dickie.android.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.logging.Logger;


/**
 * Created by wuxiaodong on 18/3/24.
 * 用于实现侧拉删除，模仿QQ的效果。
 * 用于演示 ViewDragHelper的使用
 */

public class SwipeLayout extends ViewGroup {


    private ViewDragHelper dragHelper;
    private View contView;
    private View deleteView;
    private int maxScrollLeft;
    private float scrollPercent;

    public SwipeLayout(Context context) {
        this(context,null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1, new DragCallback());
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
        Log.e("demo", "edge left:"+ViewDragHelper.EDGE_LEFT+",right:"+ViewDragHelper.EDGE_RIGHT+",edge top:"+ViewDragHelper.EDGE_TOP);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
        //return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("demo", "SwipeLaout onMeasure.....");
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("demo", "SwipeLayout onLayout....");
        int measuredWidth = getMeasuredWidth();
        contView.layout(0,0,measuredWidth,contView.getMeasuredHeight());
        deleteView.layout(measuredWidth,0,deleteView.getMeasuredWidth()+measuredWidth,contView.getMeasuredHeight());
        maxScrollLeft = deleteView.getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("demo", "SwipeLayout onDraw.....");
    }

    @Override
    public void computeScroll() {
        if(dragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    class DragCallback extends ViewDragHelper.Callback{
        

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.e("demo", "try to capture child :"+child.getTag());
            return child==contView;
        }

        /**
         * @param child
         * @param left 期望变化后的left值
         * @param dx   此次手势移动的delta值,  参数left = 实际left+dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.e("demo", "clamp horizontal,view:"+child.getTag()+"child left is:"+child.getLeft()+",left:"+left+",dx:"+dx);
            if(left<-deleteView.getWidth())
                left = -deleteView.getWidth();
            else if(left>0){
                left = 0;
            }
            return left;
        }

        /**
         * @param changedView
         * @param left   changedView 的 当前left值，
         * @param top
         * @param dx    当前left 和上一次变化前left的差值
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.e("demo", "view position changed,left:"+left+",top:"+top+",dx:"+dx+",dy:"+dy);
            //这里处理deleteView的问题
            deleteView.offsetLeftAndRight(dx);
            float percent = -changedView.getLeft()*1.0f / maxScrollLeft;
            listener.onPercentChange(percent);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //Log.e("demo", "clamp vertical:"+child.getTag()+",top:"+top+",dy:"+dy);
            return super.clampViewPositionVertical(child, top, dy);
        }



        @Override
        public int getViewHorizontalDragRange(View child) {
            Log.e("demo", "get view horizontal drag range,view:"+child.getTag());
            //return super.getViewHorizontalDragRange(child);
            return 300;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.e("demo", "view captured:"+capturedChild.getTag());
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            Log.e("demo", "drag state change,state:"+state);
        }



        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.e("demo", "view release,view:"+releasedChild.getTag()+",xvel:"+xvel+",yvel:"+yvel);
            if(xvel>ViewConfiguration.getMaximumFlingVelocity()){
                Log.e("demo", "great than max velocity...");
                dragHelper.smoothSlideViewTo(contView,0,0);
                invalidate();
            }else if(xvel<-ViewConfiguration.getMaximumFlingVelocity()){
                Log.e("demo", "great than max veleocity....");
                dragHelper.smoothSlideViewTo(contView,-deleteView.getWidth(),0);
                invalidate();
            }else{
                int left = releasedChild.getLeft();
                int threshHold = deleteView.getWidth() / 2;
                if(-left>threshHold){
                    //open
                    dragHelper.smoothSlideViewTo(contView,-deleteView.getWidth(),0);
                    invalidate();
                }else{
                    //close
                    dragHelper.smoothSlideViewTo(contView,0,0);
                    invalidate();
                }
            }

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

    private PercentListener listener;

    public void setPercent(PercentListener listener) {
        this.listener = listener;
    }

    public interface PercentListener{
        void onPercentChange(float percent);
    }



}

package name.dickie.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.security.NoSuchAlgorithmException;

import name.dickie.android.demo.ContextHolder;


/**
 * Created by wuxiaodong on 18/3/13.
 * 自定义可以滑动的viewpager
 *
 * 未定义完成
 */

public class MyViewPager extends ViewGroup {

    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private int currentPage;

    public MyViewPager(Context context) {
        super(context);
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        scroller  = new Scroller(ContextHolder.getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //measureChildren(widthMeasureSpec,heightMeasureSpec);
        int count = getChildCount();
        for(int i = 0; i < count; i++){
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.e("demo", "child count is:"+childCount);
        int startLeft = 0;
        for(int i=0;i<childCount;i++){
            View child = getChildAt(i);
            Log.e("demo", "i is:"+i+",width:"+child.getMeasuredWidth()+",height:"+child.getMeasuredHeight());
            child.layout(startLeft,0,startLeft+child.getMeasuredWidth(),child.getMeasuredHeight());
            startLeft+=child.getMeasuredWidth();
        }
    }

    private float lastDownX;
    private float lastDownY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(velocityTracker==null)
            velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(!scroller.isFinished())
                    scroller.abortAnimation();
                lastDownX = event.getX();
                lastDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX()- lastDownX;
                float deltaY = event.getY()- lastDownY;

                if(getScrollX()-deltaX <0){ //  左边边界 getScrollX  是posicitve
                    deltaX = getScrollX();
                }

                // TODO: 18/3/14 没有处理完善
                if(getScrollX()-deltaX>(getChildCount()-1)*getPageWidth()){
                    deltaX =  (getChildCount()-1)*getPageWidth()-getScrollX();
                }

                scrollBy(-(int) deltaX, 0);
                lastDownX = event.getX();
                lastDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000); // 单位 pix/second
                float xVelocity = velocityTracker.getXVelocity();
                int minimumFlingVelocity = ViewConfiguration.get(ContextHolder.getContext()).getScaledMaximumFlingVelocity()/4;


                //要滚动到第几页
                int whichPage = getCurrentPageNumber();
                if(xVelocity>0){ // 向右滑
                    if(xVelocity>minimumFlingVelocity){
                        whichPage = whichPage-1<0?whichPage:whichPage-1;
                        scrollTopage(whichPage);
                    }else{
                        scrollTopage(whichPage);
                    }
                }else{ //向左滑
                    if(xVelocity<-minimumFlingVelocity){
                        whichPage = whichPage == getChildCount()-1?whichPage:whichPage+1;
                        scrollTopage(whichPage);
                    }else{
                        scrollTopage(whichPage);
                    }
                }
                Log.e("demo", "xVelocity is :"+xVelocity+",mimun flingVelocity is:"+minimumFlingVelocity);
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }
        return true;
    }

    private void scrollTopage(int destationPage){

        if(destationPage > getChildCount() - 1){
            destationPage = getChildCount() - 1;
        }
        //计算滑动到指定Page还需要滑动的距离
        int dx = destationPage * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(),0,dx,0,Math.abs(dx) * 2);//动画时间设置为Math.abs(dx) * 2 ms
        //记住，使用Scroller类需要手动invalidate
        invalidate();
    }


    @Override
    public void computeScroll() {
        if(!scroller.isFinished()){
            scroller.computeScrollOffset();
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }else{
            currentPage = getCurrentPageNumber();
        }
    }

    /**计算静止时是第几page
     * @return
     */
    private int getCurrentPageNumber(){
        return (int) (getScrollX()/getWidth()+0.5);
    }

    private int getPageWidth(){
        int measuredWidth = getChildAt(0).getMeasuredWidth();
        return measuredWidth;
    }
}
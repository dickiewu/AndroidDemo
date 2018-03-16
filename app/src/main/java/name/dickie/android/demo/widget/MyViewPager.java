package name.dickie.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    private int currentPage = 0;

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
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
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
                /*int page = (int) (getScrollX()*1.0f/getPageWidth()+0.5);
                Log.e("demo", "当前page："+page);
                scrollTopage(page);*/

                /*判断是在第几个page*/
                int whichPage = (getScrollX() + getWidth() / 2 ) / getWidth() ;
                Log.e("demo", "当前page:"+ whichPage);
                scrollTopage(whichPage);
                break;
        }
        return true;
    }

    private void scrollTopage(int indexPage){
        /*int dx = currentPage * getPageWidth() - getScrollX();
        Log.e("demo", "the dx is:"+dx);
        scroller.startScroll(getScrollX(),getScrollY(),dx,0);
        invalidate();
        currentPage = indexPage;*/

        currentPage = indexPage;
        if(currentPage > getChildCount() - 1){
            currentPage = getChildCount() - 1;
        }
        //计算滑动到指定Page还需要滑动的距离
        int dx = currentPage * getWidth() - getScrollX();
        Log.e("demo", "dx is:"+dx);
        scroller.startScroll(getScrollX(),0,dx,0,Math.abs(dx) * 2);//动画时间设置为Math.abs(dx) * 2 ms
        //记住，使用Scroller类需要手动invalidate
        invalidate();

    }

    private void scrollToNextPage(){
        scroller.startScroll(getScrollX(), getScrollY(), -getPageWidth(), 0);
        invalidate();
    }

    private void scrollToPrevPage(){
        scroller.startScroll(getScrollX(), getScrollY(), getPageWidth(), 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(!scroller.isFinished()){
            scroller.computeScrollOffset();
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }else{

        }
    }

    private int getCurrentPageNumber(){
        //记住当前是第几个page
        int scrollX = getScrollX();
        float curPage = Math.abs(scrollX)*1.0f/getChildAt(0).getMeasuredWidth();
        Log.e("demo", "curpage is "+curPage);
        return (int)curPage;
    }

    private int getPageWidth(){
        int measuredWidth = getChildAt(0).getMeasuredWidth();
        return measuredWidth;
    }
}
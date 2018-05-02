package name.dickie.android.demo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/3/16.
 */

public class AdsView extends ViewGroup {
    private List<String> adTexts;
    //默认停留5s
    public static final int DEFAULT_INTERFAL=5000;
    //动画默认时间
    public static final int DEFAULT_DURATION=1000;

    private int duration;
    private int interval;
    private int adsNum;

    public AdsView(Context context) {
        this(context,null);
    }

    public AdsView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AdsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        adTexts = new ArrayList<>();
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AdsView);
            interval = typedArray.getInt(R.styleable.AdsView_interval, DEFAULT_INTERFAL);
            duration = typedArray.getInt(R.styleable.AdsView_duration, DEFAULT_DURATION);
            adsNum = typedArray.getInt(R.styleable.AdsView_adsNum,2);
            typedArray.recycle();
            Log.e("demo", String.format("interval:%d,duration:%d,adsNum:%d",interval,duration,adsNum));
        }
    }

    public void addAdsText(String text){
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.BLUE);
        textView.setBackgroundResource(R.drawable.border_bg);
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        addView(textView,params);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("demo", "AdsView on measure...");
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("demo", "AdsView on layout.....");
        setScrollY(0);
        int childCount = getChildCount();
        Log.e("demo", "child count:"+childCount);
        for(int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            int left = 0;
            int right = left+child.getMeasuredWidth();
            int top = i*child.getMeasuredHeight();
            int bottom = top+child.getMeasuredHeight();
            Log.e("demo", "left:"+left+",right:"+right+",top:"+top+",bottom:"+bottom+",height:"+(bottom-top));
            child.layout(left,top,right,bottom);
        }
    }

    public void startScroll() {
        int measuredHeight = getChildAt(0).getMeasuredHeight();
        Log.e("demo", "child height is:"+measuredHeight);

        /*ObjectAnimator objectAnimator = new ObjectAnimator();
        objectAnimator.setIntValues(0,getChildAt(0).getMeasuredHeight());
        objectAnimator.setDuration(1000);
        *//*objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });*//*

        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                scrollBy(0,value);
            }
        });
        objectAnimator.start();*/

        ValueAnimator va = ValueAnimator.ofInt(0,measuredHeight);
        va.setDuration(1000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                scrollTo(0,animatedValue);
            }
        });

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("demo", "animation end...,childCount:"+getChildCount());
                super.onAnimationEnd(animation);
                int left = 0;
                int right = left+getChildAt(0).getMeasuredWidth();
                int top = getChildAt(0).getMeasuredHeight();
                int bottom = top+getChildAt(0).getMeasuredHeight();
                //getChildAt(0).layout(left,top,right,bottom);
                View v = getChildAt(0);
                removeViewAt(0);
                addView(v);
            }
        });
        va.start();
    }
}
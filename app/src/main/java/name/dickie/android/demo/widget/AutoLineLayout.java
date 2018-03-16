package name.dickie.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuxiaodong on 18/3/2.
 */

public class AutoLineLayout extends ViewGroup {


    public AutoLineLayout(Context context) {
        this(context,null);
    }

    public AutoLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);  // 为什么是这样
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        Log.e("demo", "the width of layout is :"+width);
        int childCount = getChildCount();
        int perRowWidth = 0;
        int totalHeight = 0;
        int perRowMaxHeight = 0;
        for(int i=0;i<childCount;i++){
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();

            if(perRowWidth+measuredWidth<getWidth()){  //同一行内布局

            }else{ // 换行布局
                perRowWidth = 0;
                totalHeight += perRowMaxHeight;
                perRowMaxHeight = 0;
            }
            perRowMaxHeight = perRowMaxHeight<measuredHeight?measuredHeight:perRowMaxHeight;
            Log.e("demo", "left:"+perRowWidth+",width:"+measuredWidth+",top:"+totalHeight+",height:"+measuredHeight);
            child.layout(perRowWidth,totalHeight,perRowWidth+measuredWidth,totalHeight+measuredHeight);
            perRowWidth+=measuredWidth;
        }
    }
}
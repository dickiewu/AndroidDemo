package name.dickie.android.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import name.dickie.android.demo.R;
import name.dickie.android.demo.utils.CustomViews;


/**
 * Created by wuxiaodong on 18/3/2.
 */

public class AutoLineLayout extends ViewGroup {


    private static final String TAG = "wxd-dmeo";

    public AutoLineLayout(Context context) {
        this(context, null);
    }

    public AutoLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autolineStyle);
    }

    public AutoLineLayout(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

        int[] attrs1=new int[]{R.styleable.AutoLineLayout_lineMinHeight,R.styleable.AutoLineLayout_maxPerLineNumber};


        //从系统主题中获取属性
        //TypedArray typedArray = context.obtainStyledAttributes(R.styleable.AutoLineLayout);

        //从布局文件中获取属性
        //TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AutoLineLayout);

        //从资源文件中定义获得
        //TypedArray typedArray = context.obtainStyledAttributes(R.style.default_autoline_style,R.styleable.AutoLineLayout);


        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AutoLineLayout,R.attr.autolineStyle,R.style.default_autoline_style);

        int integer = typedArray.getInteger(R.styleable.AutoLineLayout_lineMinHeight, -1);
        String other = typedArray.getString(R.styleable.AutoLineLayout_tag);

        typedArray.recycle();
        init();
    }

    public AutoLineLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CustomViews.MeasureSpecTuple widthTuple = CustomViews.getSpec(widthMeasureSpec);
        CustomViews.MeasureSpecTuple heightTuple = CustomViews.getSpec(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getLayoutParams() instanceof MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }

        int totalHeight=0;
        int totalWidth = 0;
        if (heightTuple.mode == CustomViews.SPEC_MODE.AT_MOST) {
            for (int i = 0; i < childCount; i++) {  // 这个for 为了计算parent的高度
                View child = getChildAt(i);
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                if(i==0){  // 首先就是第一行
                    totalHeight+=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
                }

                int childWidth = child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
                if (totalWidth + childWidth > widthTuple.size) { // 该子view应该放在下一行，所以高度会增高
                    int perChildTotalHeight = child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
                    totalHeight += perChildTotalHeight;
                    totalWidth = childWidth;
                } else { // 还是在同一行， 只增加宽度
                    totalWidth += childWidth;
                }
            }
            setMeasuredDimension(resolveSize(widthTuple.size, widthMeasureSpec),
                                 resolveSize(totalHeight, heightMeasureSpec));
        } else if (heightTuple.mode == CustomViews.SPEC_MODE.EXACTLY) {
            setMeasuredDimension(resolveSize(widthTuple.size, widthMeasureSpec),
                                 resolveSize(heightTuple.size, heightMeasureSpec));
        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int perRowWidth = 0;
        int totalHeight = 0;
        int perRowMaxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            int leftMargin = ((LayoutParams)child.getLayoutParams()).leftMargin;
            int rightMargin = ((LayoutParams)child.getLayoutParams()).rightMargin;

            if (perRowWidth + measuredWidth+leftMargin < getWidth()) {  //同一行内布局

            } else { // 换行布局
                perRowWidth = 0;
                totalHeight += perRowMaxHeight;
                perRowMaxHeight = 0;
            }
            perRowMaxHeight = perRowMaxHeight < measuredHeight ? measuredHeight : perRowMaxHeight;
            child.layout(perRowWidth+leftMargin, totalHeight, perRowWidth+leftMargin + measuredWidth, totalHeight + measuredHeight);
            perRowWidth += measuredWidth+leftMargin+rightMargin;
        }
    }

    /*layout params 布局相关*/

    /**
     * 当添加的view没有 params 时候, 默认产生的layoutparams
     *
     * @return
     */
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        Log.e("demo", "generate defaualt params......");
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        Log.e("demo", "generate layout params from a layoutparams");
        if (lp instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) lp);
        } else if (lp instanceof ViewGroup.LayoutParams) {
            return new LayoutParams(lp);
        }
        return new LayoutParams(lp);
    }

    /**
     * 由 xml inflate到 View时会调用的方法
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            /*TypedArray typedArray = c.obtainStyledAttributes(attrs,R.styleable.AutoLineLayout_Layout);
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int attrIndex = typedArray.getIndex(i);
                Log.e("demo", "attrIndex is :" + attrIndex);
                switch (attrIndex) {
                    case R.styleable.AutoLineLayout_Layout_layout_toRightOf:
                        String toRightOf = typedArray.getString(attrIndex);
                        Log.e("demo", "toRightof is :" + toRightOf);
                        break;
                }
            }

            typedArray.recycle();*/
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
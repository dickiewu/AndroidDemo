package name.dickie.android.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/2/23.
 */

public class MyTextView extends View {

    private String text;
    private Paint textPaint;
    private Rect textBound;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0);
        CharSequence t = a.getText(R.styleable.MyTextView_text);
        text = TextUtils.isEmpty(t.toString())?"no text":t.toString();
        //获得绘制文本的宽和高
        textBound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        Log.e("demo", "text Height:"+textBound.height()+",text width:"+textBound.width());
        a.recycle();
    }

    private void init(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //解决onMeasure的问题
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){ // wrapContent
            width = textBound.width();
        }

        setMeasuredDimension(width,height);
    }


    private String test(){
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text,0,textBound.height(),textPaint);
    }
}

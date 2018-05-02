package name.dickie.android.demo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import name.dickie.android.demo.utils.CustomViews;


/**
 * Created by wuxiaodong on 18/4/1.
 */

public class CanvasView extends View {

    private Paint mPaint;
    private Paint mPaint2;
    private Paint otherPaint;

    public CanvasView(Context context) {
        this(context,null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));

        mPaint2 = new Paint();
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(3);
        mPaint2.setColor(getResources().getColor(android.R.color.holo_orange_light));

        otherPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        otherPaint.setStyle(Paint.Style.FILL);
        otherPaint.setColor(Color.GRAY);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(120);

    }

    /*private Path gestruePath = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                gestruePath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                gestruePath.lineTo(event.getX(),event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }*/

   /* *//**使用贝塞尔曲线
     * @param event
     * @return
     *//*
    private float preX;
    private float preY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = event.getX();
                preY = event.getY();
                gestruePath.moveTo(preX,preY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (preX+event.getX())/2;
                float endY = (preY+event.getY())/2;
                gestruePath.quadTo(preX,preY,endX,endY);

                preX = event.getX();
                preY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }*/

    private Path path1 = new Path();
    private int offsetX = -300;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 开始波动动画
     */
    private void startWave() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(-500, -100);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                offsetX = (int) animatedValue;
                path1.reset();
                invalidate();
            }
        });

        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //canvas.translate(150,0);
        //旋转
        //canvas.rotate(45);  //绕左上角旋转
        //canvas.rotate(45,getWidth()/2,getHeight()/2);  //绕中心点顺时针旋转45度
        //canvas.drawRect(rect,mPaint);


        /*//缩放
        int save = canvas.save();
        canvas.scale(2,1);
        canvas.drawRect(rect,mPaint);
        canvas.restoreToCount(save);

        //错切
        int save1 = canvas.save();
        canvas.skew(0.5f,0.5f);
        canvas.drawRect(rect,mPaint);
        canvas.restoreToCount(save1);

        //进行clip, 后续的canvas的paint只存在与该 rect内
        canvas.clipRect(100,100,220,220);
        //canvas.drawColor(Color.GREEN);
        canvas.drawRect(rect,mPaint);*/

        //drawBessel(canvas);


        //drawText(canvas);

        drawContent(canvas);
    }

    private void drawContent(Canvas canvas) {
        canvas.drawColor(Color.RED);
    }

    /**画波浪
     * @param canvas
     */
    private void drawWave(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        path1.moveTo(offsetX,200);
        for(int i=0;i<5;i++){
            path1.rQuadTo(100,100,200,0);
            path1.rQuadTo(100,-100,200,0);
        }

        path1.lineTo(width,height);
        path1.lineTo(0,height);
        path1.close();
        canvas.drawPath(path1,mPaint);
    }

    private Path path = new Path();
    private void drawBessel(Canvas canvas) {

        path.moveTo(0,200);
        path.quadTo(100,200,200,200);
        canvas.drawPath(path,mPaint);
    }

    private Paint textPaint;

    private void drawText(Canvas canvas){

        float baseline = 200;
        float startX=200;

        //画 top  bottom asent descent  线条
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float ascent = fontMetrics.ascent+baseline;
        float descent = fontMetrics.descent+baseline;
        float top = fontMetrics.top+baseline;
        float bottom = fontMetrics.bottom+baseline;
        Log.e("wxd-demo", "top:"+fontMetrics.top+",ascent:"+fontMetrics.ascent+",descent:"+fontMetrics.descent+",bottom:"+fontMetrics.bottom);



        String text = "hello world";
        float height  = bottom-top;
        float width = textPaint.measureText(text);
        Log.e("wxd-demo", "text :height"+height+",width:"+width);

        //子串串宽度和高度
        RectF rect = new RectF(0, top, width, bottom);
        canvas.drawRect(rect,otherPaint);

        Rect textBounds = new Rect();
        textPaint.getTextBounds(text,0,text.length(),textBounds);
        otherPaint.setColor(Color.BLUE);
        textBounds.top+=baseline;
        textBounds.bottom+=baseline;
        canvas.drawRect(textBounds,otherPaint);


        canvas.drawLine(startX,baseline,500,baseline,textPaint);
        canvas.drawLine(startX,ascent,500,ascent,textPaint);
        canvas.drawLine(startX,top,500,top,textPaint);
        canvas.drawLine(startX,descent,500,descent,textPaint);
        canvas.drawLine(startX,bottom,500,bottom,textPaint);

        canvas.drawText(text,0,200,textPaint);

        //
        canvas.translate(0,300);
        textPaint.setColor(Color.RED);
        textPaint.setTextSkewX(0.25f);
        textPaint.setUnderlineText(true);
        textPaint.setFakeBoldText(true);
        textPaint.setStrikeThruText(true);
        canvas.drawText("这是一个测试用的文字",0,0,textPaint);  //x,y 是baseline的坐标
        canvas.restore();


        canvas.drawPosText("吴晓东",new float[]{200,100,400,500,0,110},textPaint);
    }

    private void drawText2(Canvas canvas){
        
    }


}

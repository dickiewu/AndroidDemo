package name.dickie.android.demo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wuxiaodong on 18/3/21.
 *
 * 该TextView 用于闪烁的TextView
 */

public class ShineTextView extends View {

    private Paint paint;
    private LinearGradient shader;
    private int dx;

    public ShineTextView(Context context) {
        this(context, null);
    }

    public ShineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(40);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(-getMeasuredWidth(), getMeasuredWidth());
                valueAnimator.setDuration(3000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer animatedValue = (Integer) animation.getAnimatedValue();
                        dx = animatedValue;
                        invalidate();
                    }
                });
                valueAnimator.start();
            }
        }, 5000);


        shader = new LinearGradient(-getMeasuredWidth(),
                                    0,
                                    0,
                                    0,
                                    new int[]{Color.RED, Color.GREEN, Color.RED},
                                    new float[]{0.0f, 0.5f, 1.0f},
                                    Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("demo", "shineText onMeasure....");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("demo", "shineText onDraw....");
        /*paint.setShader(new LinearGradient(0, 0, getMeasuredWidth(), 0,
                                           getResources().getColor(android.R.color.holo_orange_dark),
                                           getResources().getColor(android.R.color.holo_blue_dark),
                                           Shader.TileMode.CLAMP));*/


        shader = new LinearGradient(dx,
                                    0,
                                    dx + getMeasuredWidth(),
                                    0,
                                    new int[]{Color.RED, Color.GREEN, Color.RED},
                                    new float[]{0.0f, 0.5f, 1.0f},
                                    Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawText("ehhgehgewasdlfdsfasdgdsf", 0, 50, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("demo", "shineText onAttachToWindow....");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("demo", "shineText onDetachWindow....");
    }
}

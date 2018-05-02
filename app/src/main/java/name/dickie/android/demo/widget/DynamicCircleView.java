package name.dickie.android.demo.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.security.NoSuchAlgorithmException;

/**
 * Created by wuxiaodong on 18/3/24.
 */

public class DynamicCircleView extends View {

    private Paint paint;
    private Circle circle;
    private ValueAnimator valueAnimator;

    public DynamicCircleView(Context context) {
        this(context,null);
    }

    public DynamicCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DynamicCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
        paint.setStyle(Paint.Style.FILL);
        circle = new Circle(20);
        valueAnimator = ValueAnimator.ofObject(new CircleEvaluator(), circle, new Circle(200));
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circle = (Circle) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    /**方便ObjectAnimator的调用
     * @param radius
     */
    public void setCircleRadius(int radius){
        circle = new Circle(radius);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,circle.getRadius(),paint);
        /*if(!valueAnimator.isRunning()){
            Log.e("demo", "start animation..");
            valueAnimator.start();
        }*/
    }

    public static class CircleEvaluator implements  TypeEvaluator<Circle>{

        @Override
        public Circle evaluate(float fraction, Circle startValue, Circle endValue){
            int startValueRadius = startValue.getRadius();
            int endValueRadius = endValue.getRadius();
            float curRadius = startValueRadius + fraction * (endValueRadius - startValueRadius);
            return new Circle((int) curRadius);
        }
    }

    public  static class Circle {
        private int radius;

        public Circle(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }
    }
}

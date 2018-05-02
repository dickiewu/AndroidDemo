package name.dickie.android.demo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import name.dickie.android.demo.R;

/**
 * 模仿网易播放动画
 * Created by wuxiaodong on 18/4/6.
 */

public class NetEasePlayButton extends View {

    private Paint mPaint;
    private int sweepAngle;
    private int radius = 50;

    public NetEasePlayButton(Context context) {
        this(context, null);
    }

    public NetEasePlayButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetEasePlayButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti();
    }

    private void inti() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //mPaint.setColor(getResources().getColor(R.color.));
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);

        mPaint.setColor(getResources().getColor(R.color.netease_red));

        RectF round = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
        canvas.drawArc(round, -90, sweepAngle, false, mPaint);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(60 * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}

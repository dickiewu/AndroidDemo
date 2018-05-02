package name.dickie.android.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by wuxiaodong on 18/3/18.
 */

public class CouponLayout extends LinearLayout {

    private Paint borderPaint;

    public CouponLayout(Context context) {
        this(context,null);
    }

    public CouponLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CouponLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG );
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e("demo", "drawpath");
        Path path = new Path();
        path.lineTo(4,0);
        int width = getWidth()-4*2;
        float perwidth = width / 20.0f;
        for(int i=0;i<20;i++){
            path.rLineTo(perwidth/2,20);
            path.rLineTo(perwidth/2,-20);
        }
        path.rLineTo(4,0);

        int height = getHeight()-8;
        float perHeight = height/20;
        path.rLineTo(0,4);
        for(int i = 0;i<20;i++){
            RectF rectF = new RectF();
            rectF.left = getWidth()-perHeight/2;
            rectF.right = rectF.left+perHeight;
            rectF.top = i*perHeight;
            rectF.bottom = rectF.top+perHeight;
            path.arcTo(rectF,-90,-180,false);
        }
        path.rLineTo(0,4);
        //path.rLineTo(0,getHeight());
        path.rLineTo(-getWidth(),0);
        path.rLineTo(0,-getHeight());
        path.moveTo(getWidth()/2,getHeight()/2);
        RectF rectF = new RectF(20, 20, 200, 200);

        path.addRect(rectF, Path.Direction.CW);
        path.arcTo(rectF,-90,180);
        canvas.drawPath(path,borderPaint);
    }
}

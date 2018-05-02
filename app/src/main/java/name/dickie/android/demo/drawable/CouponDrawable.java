package name.dickie.android.demo.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by wuxiaodong on 18/3/18.
 */

public class CouponDrawable extends Drawable {

    private final Paint borderPaint;
    private Path path;

    public CouponDrawable(){
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        path = new Path();
        path.lineTo(4, 0);
        int width = bounds.width()-4*2;
        float perwidth = width / 20.0f;
        for(int i=0;i<20;i++){
            path.rLineTo(perwidth/2, 20);
            path.rLineTo(perwidth/2, -20);
        }
        path.rLineTo(4, 0);

        int height = bounds.height()-8;
        float perHeight = height/20;
        path.rLineTo(0, 4);
        for(int i = 0;i<20;i++){
            RectF rectF = new RectF();
            rectF.left = bounds.width()-perHeight/2;
            rectF.right = rectF.left+perHeight;
            rectF.top = i*perHeight;
            rectF.bottom = rectF.top+perHeight;
            path.arcTo(rectF, -90, -180, false);
        }
        path.rLineTo(0, 4);
        //path.rLineTo(0,getHeight());
        path.rLineTo(-bounds.width(), 0);
        path.rLineTo(0, -bounds.height());
        path.moveTo(bounds.width()/2, bounds.height()/2);
        RectF rectF = new RectF(20, 20, 200, 200);

        path.addRect(rectF, Path.Direction.CW);
        path.arcTo(rectF, -90, 180);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(path,borderPaint);
    }


    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return super.onStateChange(state);
    }


    @Override
    public void setAlpha(int alpha) {
        borderPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        borderPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
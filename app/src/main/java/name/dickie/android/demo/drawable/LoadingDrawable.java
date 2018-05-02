package name.dickie.android.demo.drawable;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;

/**
 * Created by wuxiaodong on 18/4/1.
 * 继承自 BitmapDrawable,
 */

public class LoadingDrawable extends BitmapDrawable {

    private int rotate;
    private final ObjectAnimator mObjectAnimator;

    public LoadingDrawable(Bitmap bitmap) {
        //this.bitmap = bitmap;
        super(bitmap);
        mObjectAnimator = ObjectAnimator.ofInt(this, "rotate", 0, 360);
        mObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.setDuration(1000);
    }

    public void setRotate(int degree){
        this.rotate = degree;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(rotate,bounds.width()/2,bounds.height()/2);
        //canvas.drawBitmap(bitmap,0,0,mPaint);
        super.draw(canvas);
        canvas.restore();
    }

    public void start(){
        mObjectAnimator.start();
    }
}

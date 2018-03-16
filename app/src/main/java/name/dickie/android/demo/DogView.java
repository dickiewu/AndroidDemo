package name.dickie.android.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by wuxiaodong on 18/1/28.
 */

public class DogView extends View {
    private Paint paint = new Paint();
    private Random random = new Random();
    private Bitmap bitmap;
    private Matrix matrix;

    public DogView(Context context) {
        this(context, null);
    }

    public DogView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.liveness_head);
        matrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawCircle(200.0f,200.0f,100,paint);

        //随机画100 个点
        for (int i = 0; i < 100; i++) {
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawPoint(random.nextInt(1080), random.nextInt(200), paint);
        }

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(200, 200, 600, 400, 0, 90, true, paint);

        Paint paint = null;
        Xfermode xfermode = paint.getXfermode();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));


    }
}
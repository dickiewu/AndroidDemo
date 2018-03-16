package name.dickie.android.demo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/2/22.
 */

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

    private Paint paint;
    private BitmapShader bitmapShader;
    private int bitmapWidth;
    private int bitmapHeight;

    private final RectF drawableRect = new RectF();
    private float mDrawableRadius;
    private Matrix shaderMatrix = new Matrix();

    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("demo", "on size changed...");
        init();
    }

    public void init(){
        Log.e("demo", "execute init method....");
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.norway_swiden_border),
                                        Shader.TileMode.CLAMP,
                                        Shader.TileMode.CLAMP);


        Drawable drawable = getDrawable();
        Bitmap bitmap = getBitmapFromDrawable(drawable);
        if(bitmap !=null){
            bitmapWidth = bitmap.getWidth();
            bitmapHeight = bitmap.getHeight();
        }

        //设置drawable 的边界
        drawableRect.set(0, 0, getWidth(), getHeight());

        updateShaderMatrix();
        //获取边界矩形的内切圆的半径
        mDrawableRadius = Math.min(drawableRect.height(), drawableRect.width())/2;
    }

    private void updateShaderMatrix(){
        shaderMatrix.reset();
        float dx = 0,dy = 0,scale = 0;
        if(bitmapWidth/drawableRect.width() > bitmapHeight/drawableRect.height()){
            scale = drawableRect.height()/bitmapHeight;
            dx = (drawableRect.width()-scale*bitmapWidth)*0.5f;
        }else{
            scale = drawableRect.width()/bitmapWidth;
            dy = (drawableRect.height()-scale*bitmapHeight)*0.5f;
        }
        Log.e("demo", "the scale is :"+scale+",dx is:"+dx+",dy is :"+dy);

        shaderMatrix.preTranslate(dx,dy);
        shaderMatrix.postScale(scale,scale);
        bitmapShader.setLocalMatrix(shaderMatrix);
    }

    public Bitmap getBitmapFromDrawable(Drawable drawable){
        if(drawable == null)
            return null;
        if(drawable instanceof BitmapDrawable){
            Log.e("demo", "is bitmapdrawable...");
            return ((BitmapDrawable) drawable).getBitmap();
        }

        return null;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(drawable == null)
            super.onDraw(canvas);
        else{
            paint.setShader(bitmapShader);
            //paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,paint);
        }
    }
}

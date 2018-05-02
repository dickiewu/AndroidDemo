package name.dickie.android.demo.widget;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.security.NoSuchAlgorithmException;

import name.dickie.android.demo.ContextHolder;
import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/3/31.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {


    private Drawable divider;
    public MyItemDecoration(){
        divider = ContextHolder.getContext().getDrawable(R.drawable.decoration_line);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.e("wxd-demo", "divider height:"+divider.getIntrinsicHeight());
        outRect.set(60, 0, 60, 30);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight()+200;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }
}

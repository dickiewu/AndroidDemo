package name.dickie.android.demo.utils;

import android.util.Log;
import android.util.Pair;
import android.view.View;

import static android.view.View.MeasureSpec;

/**
 * Created by wuxiaodong on 18/4/7.
 * 自定义控件帮助类
 */

public class CustomViews {


    public static void invisibleView(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public static void goneView(View view){
        view.setVisibility(View.GONE);
    }

    public static void visibleView(View view){
        view.setVisibility(View.VISIBLE);
    }


    /**
     * @param view
     * @param action
     */
    public static void  addMinimumIntervalClickAction(View view, Runnable action){
        view.setOnClickListener(new View.OnClickListener() {
            private long lastClick;
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis()-lastClick<1000){
                    Log.e("wxd-demo", String.format("按钮单击时间太短!!"));
                }else{
                    action.run();
                    lastClick = System.currentTimeMillis();
                }
            }
        });
    }


    public static MeasureSpecTuple getSpec(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        MeasureSpecTuple spec = null;
        switch (mode) {
            case MeasureSpec.AT_MOST:
                spec = new MeasureSpecTuple(SPEC_MODE.AT_MOST, MeasureSpec.getSize(measureSpec));
                break;
            case MeasureSpec.EXACTLY:
                spec = new MeasureSpecTuple(SPEC_MODE.EXACTLY, MeasureSpec.getSize(measureSpec));
                break;
            case MeasureSpec.UNSPECIFIED:
                spec = new MeasureSpecTuple(SPEC_MODE.UNSPECIFIED, MeasureSpec.getSize(measureSpec));
                break;
        }
        return spec;
    }

    public static class MeasureSpecTuple {
        public SPEC_MODE mode;
        public Integer size;

        public MeasureSpecTuple(SPEC_MODE mode, Integer size) {
            this.mode = mode;
            this.size = size;
        }
    }

    public enum SPEC_MODE{
        AT_MOST,EXACTLY,UNSPECIFIED
    }
}

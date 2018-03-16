package name.dickie.android.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import name.dickie.android.demo.ContextHolder;
import name.dickie.android.demo.R;

public class TouchTestActivity extends AppCompatActivity {

    private View touchTest;
    private boolean flag = false;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test);
        touchTest = findViewById(R.id.drag_test);


    }

    private void addWindow() {
        WindowManager windowManager = (WindowManager) ContextHolder.getContext()
                                                                   .getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;


    }

    public void showInfomation(View view) {
        int scrollX = touchTest.getScrollX();
        int scrollY = touchTest.getScrollY();
        Log.e("demo", "scrollX:" + scrollX + ",scrollY:" + scrollY);
    }
}
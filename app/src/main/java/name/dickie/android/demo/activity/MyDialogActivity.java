package name.dickie.android.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;

import name.dickie.android.demo.R;

public class MyDialogActivity extends AppCompatActivity {

    private static final String TAG = "WXD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.activity_my_dialog);

        SeekBar seekBar= findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                Log.e(TAG, String.format("stop tracking...progress is :%d", progress));
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = progress*1.0f/100;
                getWindow().setAttributes(params);
            }
        });


        SeekBar seekbar2 = findViewById(R.id.seekbar2);
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e(TAG, String.format("调整黑暗度,progress:%d", seekBar.getProgress()));
                int progress = seekBar.getProgress();
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.dimAmount = progress*1.0f/100;
                getWindow().setAttributes(params);
            }
        });

    }
}

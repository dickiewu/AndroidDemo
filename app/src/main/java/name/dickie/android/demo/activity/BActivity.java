package name.dickie.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import name.dickie.android.demo.MainActivity;
import name.dickie.android.demo.R;
import name.dickie.android.demo.fragment.BaseFragmentActivity;

public class BActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }


    public void enerAActivity(View view) {
        Intent intent = new Intent(this, AActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.activity.startActivity(intent);
    }
}

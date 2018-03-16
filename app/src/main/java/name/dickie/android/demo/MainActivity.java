package name.dickie.android.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import name.dickie.android.demo.activity.AActivity;
import name.dickie.android.demo.activity.TouchTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String dataString = getIntent().getDataString();
        Log.e("demo", "the dataString:"+dataString);

    }

    public void enterTouchTest(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TouchTestActivity.class);
        startActivity(intent);
    }

    public void testIntent(View view) {
        //隐式intent
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("name.dickie.android.demo", "name.dickie.android.demo.TouchTestActivity");
        intent.setClass(getApplicationContext(),TouchTestActivity.class);
        startActivity(intent);
    }

    public void enterAActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), AActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }
}
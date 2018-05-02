package name.dickie.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import name.dickie.android.demo.MainActivity;
import name.dickie.android.demo.R;

public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.e("demo", "BActivity create.....");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("demo", "BActivity start....");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("demo", "BActivity resume...");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("demo", "BActivity onNewIntent...");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("demo", "BActivity pause...");

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("demo", "BActivity stop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("demo", "BActivity desroy...");
    }

    public void enerAActivity(View view) {
        Intent intent = new Intent(this, AActivity.class);
        MainActivity.activity.startActivity(intent);
    }
}

package name.dickie.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import name.dickie.android.demo.MainActivity;
import name.dickie.android.demo.R;

public class AActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.e("demo", "create AActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("demo", "AActivity start..");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("demo", "AActivity resume..");
    }

    @Override
    protected void onNewIntent(Intent intent) {  // ?? onNewIntent
        super.onNewIntent(intent);
        Log.e("demo", "AActivity on New Intent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("demo", "AActivity onpause......");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("demo", "AActivity onstop...");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("demo", "AActivity ondestroy......");

    }

    public void enterBActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),BActivity.class);
        MainActivity.activity.startActivity(intent);
    }

    public void launcheSelf(View view){
        Intent intent = new Intent(getApplicationContext(),AActivity.class);
        startActivity(intent);
    }
}
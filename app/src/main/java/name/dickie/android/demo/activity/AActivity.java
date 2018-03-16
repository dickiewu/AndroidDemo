package name.dickie.android.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    public void enterBActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),BActivity.class);
        startActivity(intent);
    }

    public void launcheSelf(View view){
        Intent intent = new Intent(getApplicationContext(),AActivity.class);
        startActivity(intent);
    }
}
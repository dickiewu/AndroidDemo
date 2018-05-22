package name.dickie.android.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import name.dickie.android.demo.MainActivity;
import name.dickie.android.demo.R;
import name.dickie.android.demo.fragment.BaseFragmentActivity;

public class AActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

    }


    public void enterBActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), BActivity.class);
        MainActivity.activity.startActivity(intent);
    }

    public void launcheSelf(View view) {
        Intent intent = new Intent(getApplicationContext(), AActivity.class);
        startActivity(intent);
    }
}
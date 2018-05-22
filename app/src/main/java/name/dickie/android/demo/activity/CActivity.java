package name.dickie.android.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import name.dickie.android.demo.R;
import name.dickie.android.demo.fragment.BaseFragmentActivity;

public class CActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

    }
}

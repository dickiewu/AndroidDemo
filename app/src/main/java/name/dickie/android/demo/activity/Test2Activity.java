package name.dickie.android.demo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import name.dickie.android.demo.R;
import name.dickie.android.demo.fragment.AFragment;
import name.dickie.android.demo.fragment.AudioFragment;
import name.dickie.android.demo.fragment.BaseFragmentActivity;
import name.dickie.android.demo.fragment.BitmapTestFragment;
import name.dickie.android.demo.utils.Fragments;

public class Test2Activity extends BaseFragmentActivity {
    public static final String TAG = "WXD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Fragment fragment = Fragments.findFragmentByTag(getSupportFragmentManager(), BitmapTestFragment.class);
        if (fragment == null) {
            fragment = new BitmapTestFragment();
            Fragments.addFragment(R.id.fragment_container3, getSupportFragmentManager(), fragment);
        } else {
            Log.e(TAG, String.format("sorry the AudioFragment is exist!!!"));
        }


    }



    @Override
    public void onBackPressed() {
        Log.e(TAG, String.format("onBackPressed...."));
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void addFragment(View view) {
        AudioFragment audioFragment = new AudioFragment();
        Fragments.addFragment(R.id.fragment_container3, getSupportFragmentManager(), audioFragment);

    }

    public void addAFragment(View view) {
        AFragment aFragment = new AFragment();
        Fragments.addFragment(R.id.fragment_container3, getSupportFragmentManager(), aFragment);
    }


    public void showInfo(View view) {
        Fragments.showFragmentInfo(getSupportFragmentManager());
    }

    public void hideOrShowAudioFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(AudioFragment.class.getSimpleName());
        if (fragment.isHidden()) {
            fragmentTransaction.show(fragment);
        } else if (fragment.isVisible()) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commitNow();
    }
}

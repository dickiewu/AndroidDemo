package name.dickie.android.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

/**
 * Created by wuxiaodong on 18/4/21.
 */

public class BaseFragmentActivity extends AppCompatActivity {
    public static final String TAG = "WXD";
    private String className = "BaseFragmentActivity";


    public BaseFragmentActivity() {
        className = this.getClass().getSimpleName();
        Log.e(TAG, String.format("%s(%x) constructed..", className, this.hashCode()));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, String.format("%s(%x) oncreate, with savedInstanceState(%x)", className, this.hashCode(), savedInstanceStateHashcode(savedInstanceState)));
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onContentChanged() {
        Log.e(TAG, String.format("%s(%x) onContentChanged", className, hashCode()));
        super.onContentChanged();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.e(TAG, String.format("%s(%x) oncreate, with savedInstanceState and persistentState", className, this.hashCode()));
        super.onCreate(savedInstanceState, persistentState);

    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.e(TAG, String.format("%s(%x) onAttachFragment fragment(%x)", className, this.hashCode(), fragment.hashCode()));
        super.onAttachFragment(fragment);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, String.format("%s(%x) onActivityResult, requestCode:%d,resultCode:%d", className, this.hashCode(),requestCode,resultCode));
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, String.format("%s(%x) onNewIntent", className, this.hashCode()));
        super.onNewIntent(intent);

    }

    @Override
    protected void onRestart() {
        Log.e(TAG, String.format("%s(%x) onRestart", className, this.hashCode()));
        super.onRestart();

    }

    @Override
    protected void onStart() {
        Log.e(TAG, String.format("%s(%x) onStart", className, this.hashCode()));
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, String.format("%s(%x) onRestoreInstanceState with savedInstanceState(%x)", className, this.hashCode(), savedInstanceStateHashcode(savedInstanceState)));
        if (savedInstanceState != null) {
            int value = savedInstanceState.getInt("random", -1);
            Log.e(TAG, String.format("the random name value is:%s", value));
        }
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, String.format("%s(%x) onPostCreate, with savedInstanceState(%x)", className, this.hashCode(), savedInstanceStateHashcode(savedInstanceState)));
        super.onPostCreate(savedInstanceState);
    }



    @Override
    protected void onResume() {
        Log.e(TAG, String.format("%s(%x) onResume", className, this.hashCode()));
        super.onResume();
    }


    @Override
    protected void onResumeFragments() {
        Log.e(TAG, String.format("%s(%x) onResumeFragments", className, this.hashCode()));
        super.onResumeFragments();

    }

    @Override
    protected void onPostResume() {
        Log.e(TAG, String.format("%s(%x) onPostResume", className, this.hashCode()));
        super.onPostResume();

    }

    @Override
    public void onAttachedToWindow() {
        Log.e(TAG, String.format("%s(%x) onAttachedToWindow", className, this.hashCode()));
        super.onAttachedToWindow();

    }

    @Override
    protected void onUserLeaveHint() {
        Log.e(TAG, String.format("%s(%x) onUserLeaveHint", className, this.hashCode()));
        super.onUserLeaveHint();

    }

    @Override
    public void onUserInteraction() {
        Log.e(TAG, String.format("%s(%x) onUserInteraction", className, this.hashCode()));
        super.onUserInteraction();

    }

    @Override
    protected void onPause() {
        Log.e(TAG, String.format("%s(%x) onPause", className, this.hashCode()));
        super.onPause();

    }

    public static final Random r = new Random(System.currentTimeMillis());

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int randomInt = r.nextInt();
        Log.e(TAG, String.format("%s(%x) onSaveInstanceState outState(%x), storage int is:%d", className, this.hashCode(), outState.hashCode(), randomInt));
        outState.putInt("random", randomInt);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStop() {
        Log.e(TAG, String.format("%s(%x) onStop", className, this.hashCode()));
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, String.format("%s(%x) onDestroy", className, this.hashCode()));
        super.onDestroy();
        Log.e(TAG, String.format("--------------------------------------------------"));

    }

    private int savedInstanceStateHashcode(Bundle savedInstanceState) {
        return savedInstanceState == null ? 0 : savedInstanceState.hashCode();
    }
}

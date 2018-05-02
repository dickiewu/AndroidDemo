package name.dickie.android.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * Created by wuxiaodong on 18/4/15.
 */

public class BaseFragment extends Fragment {
    public static final String TAG = "wxd";
    private String className = "BaseFragment";

    public BaseFragment() {
        className = getClass().getSimpleName();
        Log.w(TAG, String.format("%s(%x) constructor%s", className, hashCode(), suffix()));
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        Log.w(TAG, String.format("%s(%x) onAttach, context is %s(%x)%s", className, hashCode(),context.getClass().getSimpleName(),context.hashCode(), suffix()));
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.w(TAG, String.format("%s(%x) onCreate savedInstanceState(%x)%s", className, hashCode(), savedInstanceStateHashcode(savedInstanceState), suffix()));
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG, String.format("%s(%x) onCreateView savedInstanceState(%x) %s", className, hashCode(), savedInstanceStateHashcode(savedInstanceState), suffix()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.w(TAG, String.format("%s(%x) onViewCreated savedInstanceState(%x) %s", className, hashCode(), savedInstanceStateHashcode(savedInstanceState), suffix()));
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.w(TAG, String.format("%s(%x) onActivityCreated savedInstanceState(%x) %s", className, hashCode(), savedInstanceStateHashcode(savedInstanceState), suffix()));
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.w(TAG, String.format("%s(%x) onViewStateRestored savedInstanceState(%x) %s", className, hashCode(), savedInstanceStateHashcode(savedInstanceState), suffix()));
        if (savedInstanceState != null) {
            int random = savedInstanceState.getInt("random");
            Log.w(TAG, String.format("the random is %d", random));
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w(TAG, String.format("%s(%x) onActivityResult requestcode:%d,resultcode:%d%s", className, hashCode(),requestCode,resultCode, suffix()));
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        Log.w(TAG, String.format("%s(%x) onStart %s", className, hashCode(), suffix()));
        super.onStart();

    }

    @Override
    public void onResume() {
        Log.w(TAG, String.format("%s(%x) onResume %s", className, hashCode(), suffix()));
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.w(TAG, String.format("%s(%x) onPause %s", className, hashCode(), suffix()));
        super.onPause();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.w(TAG, String.format("%s(%x) onSaveInstanceState, outState(%x) %s", className, hashCode(), outState.hashCode(), suffix()));
        Random random = new Random(System.currentTimeMillis());
        int i = random.nextInt();
        Log.w(TAG, String.format("the generate random is:%d", i));
        outState.putInt("random", i);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStop() {
        Log.w(TAG, String.format("%s(%x) onStop %s", className, hashCode(), suffix()));
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        Log.w(TAG, String.format("%s(%x) onDestroyView %s", className, hashCode(), suffix()));
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, String.format("%s(%x) onDestroy %s", className, hashCode(), suffix()));
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        Log.w(TAG, String.format("%s(%x) onDetach %s", className, hashCode(), suffix()));
        super.onDetach();

    }

    private StringBuilder sb = new StringBuilder();

    private String suffix() {
        sb.delete(0, sb.length());
        for (int i = 0; i < className.length(); i++) {
            sb.append('.');
        }
        return sb.toString();
    }

    private int savedInstanceStateHashcode(Bundle savedInstanceState) {
        return savedInstanceState == null ? 0 : savedInstanceState.hashCode();
    }
}

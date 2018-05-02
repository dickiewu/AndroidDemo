package name.dickie.android.demo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/4/15.
 */

public class MyDialogFragment extends DialogFragment {
    public static final String TAG="WXD";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, String.format("MyDialogFragment  onCreateView..."));
        View inflate = inflater.inflate(R.layout.fragment_audio,container, false);
        return inflate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e(TAG, String.format("MyDialogFragment  onCreateDialog..."));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("hello DialogFragment");
        builder.setMessage("this is message content");
        builder.setCancelable(true);
        builder.setPositiveButton("true",null);
        AlertDialog alertDialog = builder.create();

        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.colorPrimary);
        window.setWindowAnimations(R.style.dialog_window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.horizontalMargin = 0;
        attributes.horizontalWeight = 0;
        attributes.gravity = Gravity.TOP;
        window.setAttributes(attributes);
        return alertDialog;
    }
}

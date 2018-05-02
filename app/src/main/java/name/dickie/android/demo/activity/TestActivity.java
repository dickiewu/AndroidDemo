package name.dickie.android.demo.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import name.dickie.android.demo.R;
import name.dickie.android.demo.fragment.AFragment;
import name.dickie.android.demo.fragment.AudioFragment;
import name.dickie.android.demo.fragment.BFragment;
import name.dickie.android.demo.fragment.MyDialogFragment;
import name.dickie.android.demo.utils.Fragments;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "wxd";
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        fragmentContainer = findViewById(R.id.fragment_container);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void testBlur(View view) {

        ImageView iv = findViewById(R.id.blurView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.norway_swiden_border);


        RenderScript renderScript = RenderScript.create(getApplication());

        //2
        Allocation input = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        //3
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        //4
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(1);
        scriptIntrinsicBlur.forEach(output);

        output.copyTo(bitmap);
        iv.setImageBitmap(bitmap);
        renderScript.destroy();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件更新通知").setIcon(R.drawable.play).setPositiveButton("确认更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("wxd-demo", String.format("click positive button,dialog:%s, which:%d", dialog.toString(), which));
            }
        }).setNegativeButton("不进行更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("wxd-demo", String.format("click negative button, which:%d", which));
            }
        }).setNeutralButton("忽略此版本", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("wxd-demo", String.format("click neutral button, which:%d", which));

            }
        })
        //.setMessage("下面我们通过使用不同的内容区域的设置方法，实现几个常用的对话框；\n" + "基本思路是在MainActivity中添加几个Button，点击后分别弹出对应的AlertDialog")
        ;
        //设置点击dialog 框外是否可以进行取消
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("wxd-demo", String.format("cancel the alert Dialog"));
            }
        });

        CharSequence[] charSequences = {"1,women", "2, hello", "3,dfasdfa"};

        builder.setSingleChoiceItems(charSequences, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("wxd-demo", String.format("single choose ,which %d", which));
            }
        });


        /*AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("自定义view title");
        //AlertDialog alertDialog = builder.create();
        AlertDialog alertDialog = builder2.create();
        Window window = alertDialog.getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(R.style.dialog_window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.horizontalMargin = 0;
        attributes.horizontalWeight = 0;
        attributes.gravity = Gravity.TOP;
        window.setAttributes(attributes);
        alertDialog.show();*/

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.colorAccent);
        window.setWindowAnimations(R.style.dialog_window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(attributes);
        dialog.show();
    }


    public void openDialogActivity(View view) {
        Intent intent = new Intent(this, MyDialogActivity.class);
        startActivity(intent);

    }

    public void loadFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AudioFragment audioFragment = new AudioFragment();
        //AudioFragment audioFragment2 = new AudioFragment();
        transaction.add(R.id.fragment_container, audioFragment, "audioFragment");
        //transaction.add(R.id.fragment_container2, audioFragment2, "audioFragment2");
        transaction.commit();

    }

    public void loadAFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AFragment aFragment = new AFragment();
        transaction.add(R.id.fragment_container, aFragment, "aFragment");
        transaction.commit();
    }

    public void removeAudioFragment(View view) {
        Fragments.removeFragmentByTag(getSupportFragmentManager(), "audioFragment2");
    }

    public void replaceAllFragment(View view) {
        BFragment bFragment = new BFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, bFragment);
        transaction.addToBackStack("haha");
        transaction.commit();


    }

    public void showDialogFragment(View view) {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        //Fragments.addFragment(R.id.fragment_container,this,myDialogFragment,"myDialogFragment");
        myDialogFragment.show(getFragmentManager(), "myDialogFragment");
    }

    public void showBottomSheetDialog(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_exit);
        bottomSheetDialog.show();
    }
}
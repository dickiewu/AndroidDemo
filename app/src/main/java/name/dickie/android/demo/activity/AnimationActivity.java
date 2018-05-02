package name.dickie.android.demo.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.nio.channels.NonWritableChannelException;
import java.security.KeyFactory;

import name.dickie.android.demo.R;
import name.dickie.android.demo.widget.DynamicCircleView;

public class AnimationActivity extends AppCompatActivity {
    public static final String TAG="WXD";

    private View aniTest;
    private DynamicCircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        aniTest = findViewById(R.id.animate_test);
        circleView = findViewById(R.id.dynamic_circle_view);


        ValueAnimator animator = (ValueAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.demo_animator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, String.format("value:%d",(int)animation.getAnimatedValue()));
            }
        });
        animator.start();

    }

    public void test1(View view) {
        ValueAnimator va = ValueAnimator.ofInt(1,100);
        va.setDuration(10000);
        va.start();
    }


    public void test2(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(circleView, "circleRadius", 20, 200);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    public void test3(View view){
        //复合动画
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("rotation", 60, -60);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("backgroundColor", Color.RED, Color.BLUE);
        colorHolder.setEvaluator(new ArgbEvaluator());
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(aniTest, rotationHolder, colorHolder);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    public void test4(View view){
        //复合动画

        Keyframe keyframe0 = Keyframe.ofFloat(0, 90);
        Keyframe keyframe1 = Keyframe.ofFloat(0.2f,0);
        keyframe1.setInterpolator(new BounceInterpolator());
        Keyframe keyframe2 = Keyframe.ofFloat(1, -90);
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofKeyframe("rotation", keyframe0,keyframe1,keyframe2);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("backgroundColor", Color.RED, Color.BLUE);
        colorHolder.setEvaluator(new ArgbEvaluator());
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(aniTest, rotationHolder, colorHolder);

        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();


        Keyframe keyframe = Keyframe.ofInt(0.5f);

    }

    public void test5(View view) {
        View viewById = findViewById(R.id.norway);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.demo_object_animator);
        animator.setTarget(viewById); // import
        animator.start();

    }

    public void testAnimatorSet(View view) {
        View norway = findViewById(R.id.norway);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(norway, "translationX", -((View) norway.getParent()).getWidth(), 0);
        translationX.setDuration(1000);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(norway, "alpha", 0, 1);
        AnimatorSet set= new AnimatorSet();
        set.play(translationX).with(alpha);
        set.setDuration(5000);
        set.start();


        /*AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.demo_set_animator);
        animator.setTarget(norway);
        animator.start();*/
    }
}

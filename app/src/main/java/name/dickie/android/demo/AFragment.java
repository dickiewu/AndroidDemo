package name.dickie.android.demo;


import android.app.WallpaperManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import okio.Okio;

import static java.util.stream.Collectors.toList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {


    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private View ivTest;
    private ViewGroup texts;
    private ScheduledFuture<?> schedule;

    public AFragment() {
        Log.e("demo", "construct A fragment....");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivTest = view.findViewById(R.id.iv_test);
        texts = view.findViewById(R.id.ll_text);
        /*view.findViewById(R.id.test_button).setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v,
                                              "hello owrldfsfsadfasdfasdfasdfadsafs owrldfsfsadfasdfasdfasdfadsafs owrldfsfsadfasdfasdfasdfadsafs",
                                              Snackbar.LENGTH_LONG)
                                        .setAction("取消", (view1) -> Log.e("demo", "click cancel," + view1.getClass()));

            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

        });*/

        view.findViewById(R.id.testInflate).setOnClickListener((view1) -> {
            testSpringAnimation();
        });

        view.findViewById(R.id.test_wallpaper).setOnClickListener(v -> {
            if (schedule != null) {
                schedule.cancel(true);
            }
            schedule = scheduledExecutorService.scheduleAtFixedRate(this::testWallpaper, 0, 30, TimeUnit.SECONDS);
        });


    }

    private void testWallpaper() {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        //动态设置壁纸
        // http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
        String urlStr = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
        URL url = null;
        try {
            url = new URL(urlStr);
            InputStream inputStream = url.openConnection().getInputStream();
            String s = new String(ByteStreams.toByteArray(inputStream));
            if (!Strings.isNullOrEmpty(s)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
                JsonArray results = jsonObject.getAsJsonArray("results");
                int i = results.size();
                int i1 = new Random().nextInt(i);
                String picUrl = results.get(i1).getAsJsonObject().getAsJsonPrimitive("url").getAsString();
                InputStream inputStream1 = new URL(picUrl).openConnection().getInputStream();
                wallpaperManager.setStream(inputStream1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void testSpringAnimation() {
       /* SpringForce springForce = new SpringForce(0).setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                                                    .setStiffness(SpringForce.STIFFNESS_VERY_LOW)
                                                    .setFinalPosition(500);

        SpringAnimation springAnimation = new SpringAnimation(ivTest, SpringAnimation.X).setSpring(
                springForce);
        springAnimation.setStartValue(1000);
        springAnimation.start();*/

        List<SpringAnimation> springAnimations = IntStream.range(0, texts.getChildCount())
                                                          .mapToObj(texts::getChildAt)
                                                          .map(v -> {
                                                              SpringAnimation springAnimation = new SpringAnimation(v,
                                                                                                                    SpringAnimation.Y,
                                                                                                                    0);
                                                              SpringForce springForce = springAnimation.getSpring();
                                                              springForce.setFinalPosition(0);
                                                              springForce.setStiffness(SpringForce.STIFFNESS_VERY_LOW);
                                                              springForce.setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
                                                              springAnimation.setStartValue(1920);
                                                              return springAnimation;
                                                          })
                                                          .collect(toList());
        Handler handler = new Handler(Looper.getMainLooper());

        for (int i = 0; i < springAnimations.size(); i++) {
            handler.postDelayed(springAnimations.get(i)::start, i * 50);
        }
    }
}
package name.dickie.android.demo.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/4/21.
 */

public class BitmapTestFragment extends BaseFragment {

    private Bitmap mBitmap;
    private ImageView scenery;

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handler's call back receive message");
            return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "SubHandle receive message");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_e, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.decodeBitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDecodeBitmap();
            }
        });

        view.findViewById(R.id.testReuseBitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reuseBitmap();
            }
        });

        view.findViewById(R.id.testJustbounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInjustDecodeBounds();
            }
        });

        view.findViewById(R.id.testInsampleSize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInSmapleSize();
            }
        });

        view.findViewById(R.id.testGlide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testGlide();
            }
        });
        scenery = view.findViewById(R.id.scenery);

    }

    private void testDecodeBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scenery_3, options);

        showBitmapInfo(options, mBitmap);
        scenery.setImageBitmap(mBitmap);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void reuseBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //inbitmap
        options.inBitmap = mBitmap;
        options.inMutable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scenery_huge, options);
        int byteCount = bitmap.getByteCount();
        int allocationByteCount = bitmap.getAllocationByteCount();
        Log.e(TAG, String.format("bitmap(%x) ,byteCount:%s,allocationByteCount:%s", bitmap.hashCode(), Formatter.formatFileSize(getContext(), byteCount), Formatter.formatFileSize(getContext(), allocationByteCount)));

    }

    private void testInjustDecodeBounds() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), R.drawable.scenery_huge, options);

        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inDensity = options.inDensity;
        int inTargetDensity = options.inTargetDensity;
        Log.e(TAG, String.format("origin width:%d,origin height:%d,inDensity:%d,inTargetDensity:%d", outWidth, outHeight, inDensity, inTargetDensity));
    }

    private void testInSmapleSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 10;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scenery_huge, options);
        showBitmapInfo(options, bitmap);
        scenery.setImageBitmap(bitmap);

    }

    private void showBitmapInfo(BitmapFactory.Options options, Bitmap bitmap) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        Log.e(TAG, String.format("origin picture width:%d,origin picture height:%d", outWidth, outHeight));

        int inDensity = options.inDensity;
        int inTargetDensity = options.inTargetDensity;
        Log.e(TAG, String.format("inDensity:%d,inTargetDensity:%d", inDensity, inTargetDensity));

        showBitmapInfo(bitmap);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showBitmapInfo(Bitmap bitmap) {
        int rowBytes = bitmap.getRowBytes();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Log.e(TAG, String.format("rowBytes:%s, bitmap width:%d,height:%d", Formatter.formatFileSize(getContext(), rowBytes), width, height));

        int byteCount = bitmap.getByteCount();
        int allocationByteCount = bitmap.getAllocationByteCount();
        Log.e(TAG, String.format("bitmap(%x) ,byteCount:%s,allocationByteCount:%s", bitmap.hashCode(), Formatter.formatFileSize(getContext(), byteCount), Formatter.formatFileSize(getContext(), allocationByteCount)));
    }

    private void testGlide() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.weather_ic_duoyun);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.error(R.drawable.norway_swiden_border);  // 加载错误
        requestOptions.override(100,100);   // 指定像素

        String url="http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        //url = "http://p1.pstatp.com/large/166200019850062839d3";
        RequestManager requestManager = Glide.with(this);

        RequestBuilder<Bitmap> bitmapRequestBuilder = requestManager.asBitmap();
        RequestBuilder<Bitmap> apply = bitmapRequestBuilder.apply(requestOptions);

        RequestBuilder<Bitmap> load = apply.load(url);
        ViewTarget<ImageView, Bitmap> into = load.into(scenery);

        scenery.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ((BitmapDrawable) scenery.getDrawable()).getBitmap();
                Log.e(TAG, String.format("width:%d,height:%d", bitmap.getWidth(), bitmap.getHeight()));
            }
        },5000);

    }
}

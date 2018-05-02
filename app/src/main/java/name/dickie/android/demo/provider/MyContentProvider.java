package name.dickie.android.demo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.util.Log;

import name.dickie.android.demo.db.MySqliteOpenHelper;


/**
 * Created by wuxiaodong on 18/4/11.
 */

public class MyContentProvider extends ContentProvider {
    public static final int CODE_TEST = 1;
    private static final String TAG = "WXD-DEMO";
    public static UriMatcher matcher;
    static{
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Constant.AUTHORITY, Constant.PATH, CODE_TEST);
    }


    @Override
    public boolean onCreate() {
        Log.e("wxd-demo", String.format("MyContentProvider onCreate..."));
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        long l = ContentUris.parseId(uri);
        Log.e(TAG, String.format("id is:%d", l));
        Log.e("wxd-demo", String.format("queryByContentProvider..,uri is :%s", uri.toString()));
        int match = matcher.match(uri);
        Log.e("wxd-demo", String.format("match code is %d", match));
        switch (matcher.match(uri)) {
            case CODE_TEST:
                MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(getContext());
                mySqliteOpenHelper.show();
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e("wxd-demo", String.format("getType"));
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e("wxd-demo", String.format("insert..."));
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e("wxd-demo", String.format("delete..."));
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e("wxd-demo", String.format("upate..."));
        return 0;
    }
}

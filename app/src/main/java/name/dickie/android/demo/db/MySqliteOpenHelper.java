package name.dickie.android.demo.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wuxiaodong on 18/4/11.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="music.db";
    public static final String TABLE_NAME="songs";
    public static final String INIT_SQL="create table songs(_id integer primary key autoincrement, name text)";
    public static final int VERSON=1;

    public MySqliteOpenHelper(Context context){
        this(context,DB_NAME,VERSON);
    }


    public MySqliteOpenHelper(Context context, String name , int version) {
        this(context, name, null, version);
        Log.e("wxd-demo", String.format("MySqliteOpenHelper,factory is null"));

    }

    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("wxd-demo", String.format("create sqlite open helper,db is %s",db.toString()));
        db.execSQL(INIT_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("wxd-demo", String.format("upgrade db,oldVersion:%d,newVersion:%d", oldVersion, newVersion));
    }

    public  void insert(ContentValues contentValues){
        Log.e("wxd-demo", String.format("insert ,contentValues:%s", contentValues.toString()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public void show(){
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor query = writableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while(query!=null && query.moveToNext()) {
            String name = query.getString(query.getColumnIndex("name"));
            Log.e("wxd-demo", String.format("name is :%s", name));
        }
        query.close();
        writableDatabase.close();
    }
}

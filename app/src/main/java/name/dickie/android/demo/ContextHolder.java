package name.dickie.android.demo;

import android.content.Context;

/**
 * Created by wuxiaodong on 18/3/13.
 */

public class ContextHolder {
    private static Context context;
    public static Context getContext(){
        return context;
    }

    public static void setContext(Context _context){
        context = _context;
    }
}

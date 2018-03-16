package name.dickie.android.demo.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by wuxiaodong on 18/3/15.
 */

public class OtherUtils {


    /**关闭锁屏
     * @param activity
     */
    public static void forbidCloseScreen(Activity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}

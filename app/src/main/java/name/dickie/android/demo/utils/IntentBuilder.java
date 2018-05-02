package name.dickie.android.demo.utils;

import android.content.Intent;

/**
 * Created by wuxiaodong on 18/4/4.
 */

public class IntentBuilder {

    public IntentBuilder(){

    }

    public Intent build(){
        return null;
    }

    private String action;
    public IntentBuilder setAction(String action) {
        this.action = action;
        return this;
    }
}

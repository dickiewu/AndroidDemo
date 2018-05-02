package name.dickie.android.demo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.QuickContactBadge;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.chrono.MinguoChronology;

import name.dickie.android.demo.R;
import name.dickie.android.demo.utils.CustomViews;
import name.dickie.android.demo.utils.PxUtils;

public class DrawerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Handler mHandler = new Handler();
    private Runnable r;
    private View netEase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_drawer);

        toolbar = findViewById(R.id.toolbar);
        hideActionBar();
        initToolbar();

        View viewById = findViewById(R.id.testfull);
        CustomViews.addMinimumIntervalClickAction(viewById,()->{
            Log.e("wxd-demo", String.format("click the test full screen!"));
        });

    }

    private void testPopMenu(){
        View v = findViewById(R.id.bt_context);
        PopupMenu popup = new PopupMenu(this, v);
        popup.setGravity(Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popup.getMenu());
        popup.show();
    }

    private void testContext(){
        ActionMode.Callback callback = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.e("wxd-demo", String.format("onCreateActionMode"));
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.context_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Log.e("wxd-demo", String.format("onPrepareActionMode"));
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Log.e("wxd-demo", String.format("onActionItemClicked"));
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.e("wxd-demo", String.format("onDestroyActionMode"));
            }
        };

        View viewById = findViewById(R.id.bt_context);
        viewById.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("wxd-demo", String.format("long click button"));
                ActionMode actionMode = startActionMode(callback);
                viewById.setSelected(true);
                return false;
            }
        });


    }

    private void testContextMenu() {
        //注册上下文菜单
        View viewById = findViewById(R.id.bt_context);
        registerForContextMenu(viewById);
    }


    /**创建上下文菜单
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("wxd-demo", String.format("创建上下文菜单"));
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return super.onContextItemSelected(item);
    }



    private void testClipDrawable() {
        //ImageView clipImage = findViewById(R.id.clipImage);
        ImageView clipImage = null;
        ClipDrawable drawable = (ClipDrawable) clipImage.getDrawable();
        r = ()->{
            if(drawable.getLevel()<10000){
                drawable.setLevel(drawable.getLevel()+100);
                mHandler.postDelayed(r,1000);
            }
        };
        mHandler.postDelayed(r,1000);
    }

    private void initToolbar() {
        //toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("我的钱包");
        //toolbar.setSubtitle("subtitle");
        toolbar.setTitleMarginStart(PxUtils.dp2px(20));
        //toolbar.inflateMenu(R.menu.toolbar_menu); // 手动填充
        setSupportActionBar(toolbar);  // 复用onCreateOptionsMenu 方法进行填充
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.vertical_overflow));
        //toolbar.setBackgroundResource(R.color.colorAccent); // 设置背景色

        int miminumheight = PxUtils.px2dp(toolbar.getMinimumHeight());
        Log.e("wxd-demo", String.format("minium height is %d", miminumheight));
        toolbar.showOverflowMenu();
    }

    /**
     * 让选项菜单始终显示在actionbar上
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("wxd-demo", String.format("创建菜单"));
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        if(item!=null)
            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    Log.e("wxd-demo", String.format("action expand..."));
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    Log.e("wxd-demo", String.format("aciton collapse..."));
                    return true;
                }
            });
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e("wxd-demo", String.format("prepare option menu"));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("wxd-demo", String.format("option item selected"));
        Log.e("wxd-demo", String.format(item.getTitle().toString()));
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e("wxd-demo", String.format("click home method"));
                Activity parent = getParent();

                Intent parentActivityIntent = getParentActivityIntent();
                Log.e("wxd-demo", String.format("parentActivityIntent:" + parentActivityIntent));
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.e("wxd-demo", String.format("menu opened...."));
        return super.onMenuOpened(featureId, menu);
    }*/


    /*@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.e("wxd-demo", String.format("menu item selected..."));
        Log.e("wxd-demo", String.format(item.getTitle().toString()));
        return super.onMenuItemSelected(featureId, item);
    }*/

    private void hideActionBar() {
        ActionBar actionBar = getActionBar();
        if(actionBar==null)
            return;
        actionBar.hide();
    }

    private void customTitleBar() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_drawer);
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_custom);
    }

    private void setNoTitleBar() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

    }

    public void fullScreen(View view) {
        Window window = getWindow();
    }
}

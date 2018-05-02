package name.dickie.android.demo.utils;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.checkerframework.checker.linear.qual.Linear;

/**
 * Created by wuxiaodong on 18/3/30.
 */

public class WidgetUtils {


    /**获取View 的toString 表达式
     * @param view
     * @return
     */
    public static String toString(View view){
        if(view == null)
            return "view is null";
        int id = view.getId();
        Object tag = view.getTag();
        String simpleName = view.getClass().getSimpleName();
        return String.format("%s, hashcode:%x, id:%d, tag:%s",simpleName,view.hashCode(),id,tag);
    }

    /**获取一个View可见的百分比例
     * @param view
     * @return
     */
    public static float getViewVisiblePercent(View view){
        if(view == null){
            return 0;
        }
        Rect visibleRect = new Rect();
        view.getLocalVisibleRect(visibleRect);
        return visibleRect.height()*1.0f/view.getHeight();
    }


    /**返回TextView的第 line 行 文字
     * @param tv
     * @param line
     * @return
     */
    public CharSequence getTextViewLine(TextView tv, int line) {
        int lines = tv.getLayout().getLineCount();
        if (line >= lines) {
            return "";
        }
        Layout layout = tv.getLayout();
        return tv.getText().subSequence(layout.getLineStart(line), layout.getLineEnd(line));
    }


    public static void smoothScrollToPosition(RecyclerView recyclerView , int position){
        //暂时支持vertical 布局
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();

        if (position <= firstItem) {
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            recyclerView.smoothScrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.smoothScrollToPosition(position);
            recyclerView.addOnScrollListener(new RecyclerViewTempListener(true,position));
        }
    }



    static  class RecyclerViewTempListener extends RecyclerView.OnScrollListener{
        private boolean flag;
        private int position;

        public RecyclerViewTempListener(boolean flag,int position){
            this.flag = flag;
            this.position = position;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if(newState==RecyclerView.SCROLL_STATE_IDLE && flag ){
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                flag = false;
                int n = position - layoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    recyclerView.smoothScrollBy(0, top);
                }
                //移除 本listener
                recyclerView.removeOnScrollListener(this);
            }
        }
    }
}

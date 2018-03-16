package name.dickie.android.demo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;

import java.util.List;

/**
 * Created by wuxiaodong on 18/1/26.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<View> views;

    public MyPagerAdapter(List<View> views){
        this.views = views;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position),0);
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "a--";
    }
}

package name.dickie.android.demo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

import name.dickie.android.demo.R;


/**
 * Created by wuxiaodong on 18/4/14.
 */

public class Fragments {
    public static final String TAG = "WXD";

    public static <T> T findFragmentByTag(FragmentManager manager, Class<T> clazz) {
        Fragment resultFragment = manager.findFragmentByTag(clazz.getSimpleName());
        return (T) resultFragment;
    }

    public static void removeFragmentByTag(FragmentManager fragmentManager, String tag) {
        Fragment audioFragment = fragmentManager.findFragmentByTag("audioFragment");
        if (audioFragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(audioFragment);
            transaction.commit();
        }
    }

    public static void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public static void addFragment(int fragmentContainer, Fragment from, Fragment addFragment) {
        String tag = addFragment.getClass().getSimpleName();
        FragmentManager fragmentManager = from.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //添加默认动画
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.add(fragmentContainer, addFragment, tag).hide(from).addToBackStack(tag).commit();
    }

    public static void addFragment(int fragmentContainer, FragmentManager fragmentManager, Fragment fragment) {
        String fragmentName = fragment.getClass().getSimpleName();
        addFragment(fragmentContainer, fragmentManager, fragment, fragmentName);
    }

    public static void addFragment(int fragmentContainer, FragmentManager fragmentManager, Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //添加默认动画
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.add(fragmentContainer, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public static void replaceFragment(int fragmentContainer, FragmentManager manager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


    public static void showFragmentInfo(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {

        } else {
            Log.e(TAG, String.format("size of added fragment: %d", fragments.size()));
            for (Fragment fragment : fragments) {
                Log.e(TAG, String.format("added fragment: %s(%x)", fragment.getClass().getSimpleName(), fragment.hashCode()));
            }
        }

        try {
            Method getActiveFragments = null;
            getActiveFragments = fragmentManager.getClass().getDeclaredMethod("getActiveFragments");
            getActiveFragments.setAccessible(true);
            List<Fragment> activeFragments = (List<Fragment>) getActiveFragments.invoke(fragmentManager);
            Log.e(TAG, String.format("size of active fragment : %d", activeFragments.size()));
            for (Fragment activeFragment : activeFragments) {
                if (activeFragment == null) {
                    continue;
                }
                Log.e(TAG, String.format("active fragment: %s(%x)", activeFragment.getClass().getSimpleName(), activeFragment.hashCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        Log.e(TAG, String.format("backEntryCount:%d", backStackEntryCount));
        for (int i = 0; i < backStackEntryCount; i++) {
            FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(i);
            Log.e(TAG, String.format("%s", backStackEntryAt));
        }
    }
}

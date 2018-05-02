package name.dickie.android.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.dickie.android.demo.R;
import name.dickie.android.demo.utils.Fragments;

/**
 * Created by wuxiaodong on 18/4/21.
 */

public class DFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_d, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bt_replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapTestFragment bitmapTestFragment = new BitmapTestFragment();
                Fragments.replaceFragment(R.id.fragment_container3, getFragmentManager(), bitmapTestFragment);
            }
        });
    }
}

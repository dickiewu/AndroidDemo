package name.dickie.android.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.dickie.android.demo.R;
import name.dickie.android.demo.activity.BActivity;
import name.dickie.android.demo.utils.Fragments;

/**
 * Created by wuxiaodong on 18/4/21.
 */

public class CFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_c,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.addToDFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragments.addFragment(R.id.fragment_container3,getFragmentManager(),new DFragment());
            }
        });

        view.findViewById(R.id.addNestedFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
                NestedFragment nestedFragment = new NestedFragment();
                fragmentTransaction.add(R.id.nestedFragmentContainer,nestedFragment,NestedFragment.class.getSimpleName());
                fragmentTransaction.addToBackStack(NestedFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        });

        view.findViewById(R.id.enterBActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

}

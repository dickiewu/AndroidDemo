package name.dickie.android.demo.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import name.dickie.android.demo.adapter.PackageAdapter;
import name.dickie.android.demo.R;
import name.dickie.android.demo.utils.WidgetUtils;

import static java.util.stream.Collectors.toList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends BaseFragment {


    private RecyclerView packages;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        packages = view.findViewById(R.id.packages);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),
                                                                          LinearLayoutManager.VERTICAL,
                                                                          false);
        packages.setLayoutManager(linearLayoutManager);
        List<PackageInfo> results = view.getContext()
                                        .getPackageManager()
                                        .getInstalledPackages(0)
                                        .stream()
                                        .filter(packageInfo -> (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                                        .collect(toList());
        PackageAdapter packageAdapter = new PackageAdapter(results);
        packages.setAdapter(packageAdapter);

        view.findViewById(R.id.showInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });
    }

    private void showInfo(){
        RecyclerView.Adapter adapter = packages.getAdapter();
        LinearLayoutManager layoutManager = (LinearLayoutManager) packages.getLayoutManager();

        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        Log.e(TAG, String.format("firstVisible:%d,firstCompleteVisible:%d", firstVisibleItemPosition, firstCompletelyVisibleItemPosition));

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        Log.e(TAG, String.format("lastVisible:%d,lastCompleteVisible:%d", lastVisibleItemPosition, lastCompletelyVisibleItemPosition));

        int childCount = packages.getChildCount();
        Log.e(TAG, String.format("childCount:%d",childCount));



        /*View lastvisibleChild = packages.getChildAt(lastVisibleItemPosition);
        View lastCompleteVisibleChild = packages.getChildAt(lastCompletelyVisibleItemPosition);
        Log.e(TAG, String.format("lastCompleteVisibleView:%s,lastView:%s", WidgetUtils.toString(lastCompleteVisibleChild),WidgetUtils.toString(lastvisibleChild)));

        View lastvisible = layoutManager.getChildAt(lastVisibleItemPosition);
        View lastCompleteVisible = layoutManager.getChildAt(lastCompletelyVisibleItemPosition);
        Log.e(TAG, String.format("lastCompleteVisible:%s,lastvisible:%s", WidgetUtils.toString(lastCompleteVisible),WidgetUtils.toString(lastvisible)));
*/
        /*正确使用是findViewby position*/
        View lastComplete3 = layoutManager.findViewByPosition(lastCompletelyVisibleItemPosition);
        View lastVisible3 = layoutManager.findViewByPosition(lastVisibleItemPosition);
        Log.e(TAG, String.format("lastComplete3:%s,lastVisible3:%s", WidgetUtils.toString(lastComplete3),WidgetUtils.toString(lastVisible3)));

        Rect globalLastRect = new Rect();
        lastVisible3.getGlobalVisibleRect(globalLastRect);
        Log.e(TAG, String.format("globalLastRect: %s", globalLastRect.toString()));

        Rect globalLastCompleteRect = new Rect();
        lastComplete3.getGlobalVisibleRect(globalLastCompleteRect);
        Log.e(TAG, String.format("globalLastCompleteRect: %s", globalLastCompleteRect.toString()));

        Rect localLastRect = new Rect();
        lastVisible3.getLocalVisibleRect(localLastRect);
        Log.e(TAG, String.format("localLastRect: %s", localLastRect.toString()));

        Rect localLastCompleteRect = new Rect();
        lastComplete3.getLocalVisibleRect(localLastCompleteRect);
        Log.e(TAG, String.format("localLastCompleteRect: %s", localLastCompleteRect.toString()));


        int height = lastComplete3.getHeight();
        int width = lastComplete3.getWidth();
        Log.e(TAG, String.format("completeVisible height:%d,width:%d", height, width));
    }
}
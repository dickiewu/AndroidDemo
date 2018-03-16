package name.dickie.android.demo;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment {


    private RecyclerView packages;

    public BFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

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
    }
}
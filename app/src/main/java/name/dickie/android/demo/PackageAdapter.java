package name.dickie.android.demo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wuxiaodong on 18/1/26.
 */

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageInfoHolder> {
    private List<PackageInfo> packageInfos;

    public PackageAdapter(List<PackageInfo> packageInfos){
        this.packageInfos = packageInfos;
    }

    @Override
    public PackageInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, null);
        return new PackageInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(PackageInfoHolder holder, int position) {
        PackageInfo packageInfo = packageInfos.get(position);
        holder.bindData(packageInfo);
    }

    @Override
    public int getItemCount() {
        return packageInfos.size();
    }

    static class PackageInfoHolder extends RecyclerView.ViewHolder{

        private final TextView appName;
        private ImageView appIcon;
        private Button open;

        public PackageInfoHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            open = itemView.findViewById(R.id.open_app);
        }

        public void bindData(PackageInfo packageInfo) {
            appName.setText(packageInfo.applicationInfo.loadLabel(appName.getContext().getPackageManager()));
            appIcon.setImageDrawable(packageInfo.applicationInfo.loadIcon(appIcon.getContext().getPackageManager()));
            open.setOnClickListener(view->{
                Intent openIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
                view.getContext().startActivity(openIntent);
            });
        }
    }
}

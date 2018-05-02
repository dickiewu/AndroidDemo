package name.dickie.android.demo.adapter;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;

import org.w3c.dom.Text;

import java.util.List;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/1/26.
 */

public class PackageAdapter extends RecyclerView.Adapter {
    private List<PackageInfo> packageInfos = Lists.newArrayList();
    private int i = 0;

    public PackageAdapter(List<PackageInfo> packageInfos){
        Log.e("wxd-demo", "the packageInfo size is:"+packageInfos.size());
        this.packageInfos.addAll(packageInfos);
    }

    public void appendData(List<PackageInfo> packageInfos){
        this.packageInfos.addAll(packageInfos);
        notifyDataSetChanged();
    }

    public void appendDataAt(int position,PackageInfo packageInfo){
        notifyItemInserted(position);
    }

    public void removeDataAt(int position){
        packageInfos.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(i==1){
            Log.e("wxd-demo", parent.getClass().toString()+":"+parent.getTag());
        }

        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, null);
            view.setTag(i + "---");
            i++;
            return new PackageInfoHolder(view);
        }else if(viewType==1){
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_load_more_footer, null);
            return new FooterHolder(footer);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==0){
            PackageInfo packageInfo = packageInfos.get(position);
            PackageInfoHolder  viewHodler = (PackageInfoHolder) holder;
            viewHodler.bindData(packageInfo,position);

        }else{
            Log.e("wxd-demo", "bind footer data...");
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.bindData(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        /*if(position <packageInfos.size()){
            return 0;
        }else{
            return 1;
        }*/
        return 0;
    }


    @Override
    public int getItemCount() {
        Log.e("wxd-demo", "return the count of items");
        //return packageInfos.size() + 1;  //  加的这个1 是footer的布局
        return packageInfos.size();  //  加的这个1 是footer的布局
    }

    /*private boolean noMoreData;
    public void hintNoMoreData() {
        noMoreData = true;
        notifyDataSetChanged();
    }*/


    /**当重新被RecyclerView 重新替换的时候调用
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }


    static class PackageInfoHolder extends RecyclerView.ViewHolder{

        private final TextView appName;
        private TextView index;
        private ImageView appIcon;
        private Button open;

        public PackageInfoHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            open = itemView.findViewById(R.id.open_app);
            index = itemView.findViewById(R.id.tv_index);
        }

        public void bindData(PackageInfo packageInfo, int position) {
            Object tag = itemView.getTag();
            Log.e("wxd-demo", "is binding data,tag is :"+tag);
            appName.setText(packageInfo.applicationInfo.loadLabel(appName.getContext().getPackageManager()));
            appIcon.setImageDrawable(packageInfo.applicationInfo.loadIcon(appIcon.getContext().getPackageManager()));
            index.setText(position+"  ");
            open.setOnClickListener(view->{
                Intent openIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
                view.getContext().startActivity(openIntent);
            });
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{
        public  TextView tv;
        public FooterHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.recycler_foot);
        }

        public void bindData(int position) {
            /*if(noMoreData){
                tv.setText("没有更多数据!!!");
            }*/
        }
    }
}

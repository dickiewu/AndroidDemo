package name.dickie.android.demo.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import name.dickie.android.demo.R;

/**
 * Created by wuxiaodong on 18/5/1.
 */

public class PackageAdapter2 extends BaseItemDraggableAdapter<PackageInfo, BaseViewHolder> {

    public PackageAdapter2(int layoutResId, @Nullable List<PackageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PackageInfo item) {
        Context context = baseViewHolder.itemView.getContext();

        baseViewHolder.setText(R.id.app_name, item.applicationInfo.loadLabel(context.getPackageManager()));
        baseViewHolder.setImageDrawable(R.id.app_icon, item.applicationInfo.loadIcon(context.getPackageManager()));

        int layoutPosition = baseViewHolder.getLayoutPosition();
        baseViewHolder.setText(R.id.tv_index, String.valueOf(layoutPosition));
        baseViewHolder.addOnClickListener(R.id.open_app);
        Log.e("wxd-demo", "is binding data,tag is :" + baseViewHolder.itemView.getTag() + ",layoutposiition:" + layoutPosition);
    }
}
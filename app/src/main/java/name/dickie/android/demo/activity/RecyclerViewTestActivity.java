package name.dickie.android.demo.activity;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.common.collect.Lists;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import name.dickie.android.demo.R;
import name.dickie.android.demo.adapter.PackageAdapter;
import name.dickie.android.demo.adapter.PackageAdapter2;
import name.dickie.android.demo.utils.WidgetUtils;

import static java.util.stream.Collectors.toList;
import static name.dickie.android.demo.ContextHolder.getContext;

public class RecyclerViewTestActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {
    public static final String TAG = "WXD";

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recycleView;

    private PackageAdapter2 mPackageAdapter;
    private List<List<PackageInfo>> mPartition;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        initUiBeforeData();
        initData();
        initUiAfterData();
        initListener();

    }

    private void initUiBeforeData() {
        refreshLayout = findViewById(R.id.srl_layout);
        recycleView = findViewById(R.id.recycleTest);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        initHeader();
        initRefreshLayout();


    }

    private void initHeader() {

    }

    private void initRefreshLayout() {
        refreshLayout.setDragRate(0.7f);
        refreshLayout.setReboundDuration(500);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnablePureScrollMode(true);
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshLayout.setEnableAutoLoadMore(false); //是否在列表滚动到底部时自动加载更多,默认为true,默认加载更多
        refreshLayout.setEnableLoadMore(true);  // 是否允许loadMore， 默认不可以

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initData() {
        List<PackageInfo> results = getContext().getPackageManager().getInstalledPackages(0).stream().filter(packageInfo -> (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0).collect(toList());
        mPartition = Lists.partition(results, 20).subList(0, 2);
    }

    private void initUiAfterData() {
        //mPackageAdapter = new PackageAdapter(mPartition.get(page));
        mPackageAdapter = new PackageAdapter2(R.layout.item_package, mPartition.get(page));
        recycleView.setAdapter(mPackageAdapter);
    }

    private void initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int scrollState = recyclerView.getScrollState();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.e("wxd-demo", "new scroll state idle..");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.e("wxd-demo", "new scroll state dragging...");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.e("wxd-demo", "new scroll state setting...");
                        break;
                }

                //判断加载更多
                /*if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = llManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                        if (fetchMoreFinished) {
                            fetchMore();
                            fetchMoreFinished = false;
                        }
                    }
                }*/
            }
        });
        registObserver();


        mPackageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, String.format("item click....,position:%d,view:%s", position, WidgetUtils.toString(view)));
            }
        });

        mPackageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, String.format("item child click!!!,positon:%d,view:%s", position, WidgetUtils.toString(view)));

                PackageInfo packageInfo = (PackageInfo) adapter.getItem(position);
                Intent openIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
                view.getContext().startActivity(openIntent);
            }
        });

        TextView textView = new TextView(getContext());
        textView.setText("hellow orld");
        mPackageAdapter.addHeaderView(textView);

        //initDragAndDelete();
    }

    private void registObserver() {

        mPackageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.e("wxd-demo", "data changed...");
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                Log.e("wxd-demo", "onItemRangeChanged, positionStart:" + positionStart + ",itemCount:" + itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                Log.e("wxd-demo", "onItemRangeChanged, positionStart:" + positionStart + ",itemCount:" + itemCount + ",payload:" + payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                Log.e("wxd-demo", "on ItemRangeInserted positionStart:" + positionStart + ",itemcount:" + itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                Log.e("wxd-demo", "item range removed,positionStart:" + positionStart + ",itemCount:" + itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                Log.e("wxd-demo", "item range removed,fromPosition:" + fromPosition + ",toPosition:" + toPosition + ",itemCount:" + itemCount);
            }
        });


    }


    private void initDragAndDelete() {
        OnItemDragListener onItemDragListener = new OnItemDragListener() {

            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e(TAG, String.format("drag start,pos is:%d", pos));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.e(TAG, String.format("item drag moving,from:%d,to:%d", from, to));
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e(TAG, String.format("item drag end:%d", pos));
            }
        };

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mPackageAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recycleView);

        // open drag
        mPackageAdapter.enableDragItem(itemTouchHelper, R.id.open_app, true);
        mPackageAdapter.setOnItemDragListener(onItemDragListener);

        mPackageAdapter.disableSwipeItem();
        mPackageAdapter.setOnItemDragListener(onItemDragListener);


    }

    private void fetchMore() {
        Log.e("wxd-demo", "fetch more....");
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            page++;
            if (page >= mPartition.size()) {
                //mPackageAdapter.hintNoMoreData();
                //refreshLayout.setNoMoreData(true); // 只是让footer显示 没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData(); // 加载完成，并提示没有更多数据, 并且后续不会调用 onLoadMore接口
            } else {
                refreshLayout.finishLoadMore();
                //mPackageAdapter.appendData(mPartition.get(page));

            }
        }, 3000);
    }

    public void autoScroll(View view) {
        WidgetUtils.smoothScrollToPosition(recycleView, 20);
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        Log.e("wxd-demo", "load more...");

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        Log.e("wxd-demo", "refresh...");
        //refreshLayout.finishRefresh(2000);
        refreshLayout.finishRefresh(5000, false);
        refreshLayout.setNoMoreData(true);
    }


    public void removeFirst(View view) {
        PackageAdapter adapter = (PackageAdapter) recycleView.getAdapter();
        adapter.removeDataAt(0);

    }

    public void addFirst(View view) {
        PackageAdapter adapter = (PackageAdapter) recycleView.getAdapter();
        adapter.appendDataAt(2, null);
        adapter.notifyDataSetChanged();
    }
}
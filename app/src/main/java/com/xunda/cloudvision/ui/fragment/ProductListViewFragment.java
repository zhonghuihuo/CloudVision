package com.xunda.cloudvision.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;
import com.xunda.cloudvision.ui.adapter.ProductListViewAdapter;
import com.xunda.cloudvision.utils.LogUtils;

/**
 * 产品列表模式Fragment
 * Created by yinglovezhuzhu@gmail.com on 2016/9/21.
 */

public class ProductListViewFragment extends BaseFragment {

    private ProductListViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = getResources().getDisplayMetrics().widthPixels / 2;
        int height = width * 3 / 2;
        mAdapter = new ProductListViewAdapter(getActivity(), width, height, 2, 0);

        mAdapter.setOnProductItemClickListener(new ProductListViewAdapter.OnProductItemClickListener() {
            @Override
            public void onItemClicked(int row, int column) {
                showShortToast("position-----" + row + " <> " + column);
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_list_view, container, false);

        initView(contentView);

        return contentView;
    }

    private void initView(View contentView) {

        final PullListView lvProduct = (PullListView) contentView.findViewById(R.id.lv_product_list);
        lvProduct.setAdapter(mAdapter);
//        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
//                i.setData(Uri.parse("http://120.24.234.204/static/upload/video/FUKESI.mp4"));
//                startActivity(i);
//            }
//        });
        final Handler handler = new Handler();
        lvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        lvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvProduct.refreshCompleted();
                    }
                }, 3000);
            }
        });
        lvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvProduct.loadMoreCompleted(true);
                    }
                }, 3000);
            }
        });
    }

}

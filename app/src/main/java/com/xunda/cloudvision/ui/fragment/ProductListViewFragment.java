package com.xunda.cloudvision.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.opensource.pullview.IPullView;
import com.opensource.pullview.OnLoadMoreListener;
import com.opensource.pullview.OnRefreshListener;
import com.opensource.pullview.PullListView;
import com.xunda.cloudvision.Config;
import com.xunda.cloudvision.R;
import com.xunda.cloudvision.bean.ProductBean;
import com.xunda.cloudvision.bean.resp.QueryProductResp;
import com.xunda.cloudvision.observer.ProductObserver;
import com.xunda.cloudvision.ui.activity.ProductActivity;
import com.xunda.cloudvision.ui.activity.ProductDetailActivity;
import com.xunda.cloudvision.ui.adapter.ProductListViewAdapter;

/**
 * 产品列表模式Fragment（列表模式）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/15.
 */

public class ProductListViewFragment extends BaseFragment {

    private PullListView mLvProduct;
    public ProductListViewAdapter mAdapter;

    private ProductObserver mProductObserver = new ProductObserver() {
        @Override
        public void onQueryProductResult(boolean isRefresh, QueryProductResp result) {
            if(null != mLvProduct) {
                mLvProduct.refreshCompleted();
                // FIXME 是否可以加载更多根据加载分页结果决定
                mLvProduct.loadMoreCompleted(true);
            }
            if(isRefresh) {
                mAdapter.clear(true);
            }
            mAdapter.addAll(result.getProduct(), true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductListViewAdapter(getActivity());

        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            mAdapter.addAll(((ProductActivity) activity).getProductData(), true);
            ((ProductActivity) activity).registerProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_product_list_view, container, false);

        initView(contentView);

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLvProduct = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final Activity activity = getActivity();
        if(activity instanceof ProductActivity) {
            ((ProductActivity) activity).unregisterProductObserver(mProductObserver);
        } else {
            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
        }
    }

    private void initView(View contentView) {

        mLvProduct = (PullListView) contentView.findViewById(R.id.lv_product_list);
        mLvProduct.setAdapter(mAdapter);
        mLvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProductBean product = mAdapter.getItem(position);
                if(null == product) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(Config.EXTRA_DATA, product);
                startActivity(intent);
            }
        });
        final Handler handler = new Handler();
        mLvProduct.setLoadMode(IPullView.LoadMode.PULL_TO_LOAD); // 设置为上拉加载更多（默认滑动到底部自动加载）
        mLvProduct.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Activity activity = getActivity();
                        if(activity instanceof ProductActivity) {
                            ((ProductActivity) activity).refresh();
                        } else {
                            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
                        }
                    }
                }, 3000);
            }
        });
        mLvProduct.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // TODO 加载下一页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Activity activity = getActivity();
                        if(activity instanceof ProductActivity) {
                            ((ProductActivity) activity).loadMore();
                        } else {
                            throw new IllegalStateException("Only attach by " + ProductActivity.class.getName());
                        }
                    }
                }, 3000);
            }
        });
    }
}

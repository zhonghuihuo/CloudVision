package com.vrcvp.cloudvision.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.vrcvp.cloudvision.R;
import com.vrcvp.cloudvision.bean.ProductBean;
import com.vrcvp.cloudvision.ui.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 产品列表浏览模式适配器
 * Created by yinglovezhuzhu@gmail.com on 2016/9/22.
 */

public class ProductGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductBean> mData = new ArrayList<>();
    private int mWidth = 200;
    private int mHeight = 430;
    private int mNumColumns = 2;
    private int mHorizontalSpacing = 0;

    private OnProductItemClickListener mOnProductItemClickListener;

    public ProductGridViewAdapter(Context context, int width, int height,
                                  int numColumns, int horizontalSpacing) {
        mContext = context;
        this.mWidth = width;
        this.mHeight = height;
        this.mNumColumns = numColumns;
        this.mHorizontalSpacing = horizontalSpacing;
    }

    public void addAll(Collection<ProductBean> products, boolean notifyDataSetChanged) {
        if(null == products || products.isEmpty()) {
            return;
        }
        mData.addAll(products);
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void clear(boolean notifyDataSetChanged) {
        mData.clear();
        if(notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    /**
     * 设置点击监听
     * @param listener 点击监听
     */
    public void setOnProductItemClickListener(OnProductItemClickListener listener) {
        this.mOnProductItemClickListener = listener;
    }

    /**
     * 获取数据
     * @param row 行
     * @param column 列
     * @return 数据
     */
    public ProductBean getData(int row, int column) {
        final ProductBean [] rowData = getItem(row);
        if(column < 0 || column >= rowData.length) {
            return null;
        }
        return rowData[column];
    }

    @Override
    public int getCount() {
        if(mData.size() % mNumColumns == 0) {
            return mData.size() / mNumColumns;
        } else {
            return mData.size() / mNumColumns + 1;
        }
    }

    @Override
    public ProductBean [] getItem(int i) {
        final ProductBean [] rowData = new ProductBean[mNumColumns];
        int index = i * mNumColumns;
        for(int j = 0; j < mNumColumns; j++) {
            if(index + j < mData.size()) {
                rowData[j] = mData.get(index + j);
            } else {
                break;
            }
        }
        return rowData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if(null == view) {
            viewHolder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_product_grid_view, null);
            viewHolder.gridView = (NoScrollGridView) view.findViewById(R.id.gv_item_product_grid);
            viewHolder.divider = view.findViewById(R.id.view_item_product_divider);
            viewHolder.gridView.setNumColumns(mNumColumns);
            viewHolder.gridView.setHorizontalSpacing(mHorizontalSpacing);
            viewHolder.divider.setMinimumHeight(mHorizontalSpacing);
            ViewGroup.LayoutParams lp = viewHolder.divider.getLayoutParams();
            if(null == lp) {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHorizontalSpacing);
            } else {
                lp.height = mHorizontalSpacing;
            }
            viewHolder.divider.setLayoutParams(lp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.gridView.setAdapter(new ProductGridViewItemAdapter(mContext, mWidth,
                mHeight, mNumColumns, getItem(i)));
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null != mOnProductItemClickListener) {
                    mOnProductItemClickListener.onItemClicked(i, position);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        NoScrollGridView gridView;
        View divider;
    }

    public interface OnProductItemClickListener {
        void onItemClicked(int row, int column);
    }
}

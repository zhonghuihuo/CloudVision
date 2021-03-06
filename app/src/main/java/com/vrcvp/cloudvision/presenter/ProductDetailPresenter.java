package com.vrcvp.cloudvision.presenter;

import android.content.Context;

import com.vrcvp.cloudvision.bean.AttrValueBean;
import com.vrcvp.cloudvision.bean.resp.QueryProductDetailResp;
import com.vrcvp.cloudvision.bean.resp.QuerySkuPriceResp;
import com.vrcvp.cloudvision.http.HttpAsyncTask;
import com.vrcvp.cloudvision.model.IProductDetailModel;
import com.vrcvp.cloudvision.model.ProductDetailModel;
import com.vrcvp.cloudvision.view.IProductDetailView;

import java.util.Collection;

/**
 * 产品详情Presenter
 * Created by yinglovezhuzhu@gmail.com on 2016/10/12.
 */

public class ProductDetailPresenter {
    private IProductDetailView mView;
    private IProductDetailModel mModel;

    public ProductDetailPresenter(Context context, IProductDetailView view) {
        this.mView = view;
        this.mModel = new ProductDetailModel(context);
    }

    public void queryProductDetail(String productId) {
        mModel.queryProductDetail(productId, new HttpAsyncTask.Callback<QueryProductDetailResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(QueryProductDetailResp result) {
                mView.onQueryProductDetailResult(result);
            }
        });
    }

    /**
     * 查询Sku价格
     * @param productId 产品id
     * @param attrValues 属性组合列表
     */
    public void querySkuPrice(String productId, Collection<AttrValueBean> attrValues) {
        mModel.querySkuPrice(productId, attrValues, new HttpAsyncTask.Callback<QuerySkuPriceResp>() {
            @Override
            public void onPreExecute() {
                mView.onPreExecute(null);
            }

            @Override
            public void onCanceled() {
                mView.onCanceled(null);
            }

            @Override
            public void onResult(QuerySkuPriceResp result) {
                mView.onQuerySkuPriceResult(result);
            }
        });
    }
}

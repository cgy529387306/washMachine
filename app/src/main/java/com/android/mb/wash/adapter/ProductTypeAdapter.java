package com.android.mb.wash.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.entity.ProductType;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.TestHelper;
import com.android.mb.wash.view.ProductHotListActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class ProductTypeAdapter extends BaseQuickAdapter<ProductType, BaseViewHolder> {

    public ProductTypeAdapter(List<ProductType> data) {
        super(R.layout.item_home_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductType productType) {
        helper.setText(R.id.tv_type,productType.getTypeName());
        GridView gridProduct = helper.getView(R.id.gridProduct);
        gridProduct.setAdapter(new ProductHotAdapter(mContext, productType.getProducts()));
        helper.setOnClickListener(R.id.tv_more_hot, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.startActivity((Activity) mContext, ProductHotListActivity.class,null,false);
            }
        });
    }



}



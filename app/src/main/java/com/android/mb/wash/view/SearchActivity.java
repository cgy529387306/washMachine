package com.android.mb.wash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.adapter.HomeAdapter;
import com.android.mb.wash.adapter.PostAdapter;
import com.android.mb.wash.base.BaseMvpActivity;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.HomeData;
import com.android.mb.wash.entity.HomeItem;
import com.android.mb.wash.entity.VideoListData;
import com.android.mb.wash.fragment.MainFragment;
import com.android.mb.wash.presenter.HomePresenter;
import com.android.mb.wash.presenter.SearchPresenter;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.TestHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.interfaces.IHomeView;
import com.android.mb.wash.view.interfaces.ISearchView;
import com.android.mb.wash.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgy on 2018\8\20 0020.
 */

public class SearchActivity extends BaseMvpActivity<HomePresenter,
        IHomeView> implements IHomeView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener, OnLoadMoreListener {

    private EditText mEtSearch;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }


    @Override
    protected void bindViews() {
        mEtSearch = findViewById(R.id.et_search);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(LinearLayoutManager.VERTICAL));
        mAdapter = new HomeAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getListFormServer();
    }


    @Override
    protected void setListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                String keyword = mEtSearch.getText().toString();
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (Helper.isEmpty(keyword)){
                        ToastHelper.showToast("请输入搜索内容");
                    }else {
                        mCurrentPage =1;
                        getListFormServer();
                    }
                    return true;
                }
                return false;
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCurrentPage =1;
                getListFormServer();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel){
            finish();
        }
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void getHomeData(HomeData homeData) {
        if (homeData!=null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            List<HomeItem> dataList = new ArrayList<>();
            HomeItem postItem = new HomeItem(HomeItem.POST);
            postItem.setPostBeanList(homeData.getDynamicList());
            dataList.add(postItem);
            HomeItem productItem = new HomeItem(HomeItem.PRODUCT);
            productItem.setProductTypeList(homeData.getProductList());
            dataList.add(productItem);
            mAdapter.setNewData(dataList);
        }
    }


    private void getListFormServer(){
        String keyword = mEtSearch.getText().toString();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("keyword", keyword);
        requestMap.put("currentPage",mCurrentPage);
        requestMap.put("pageSize", ProjectConstants.PAGE_SIZE);
        mPresenter.getHomeData(requestMap);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        getListFormServer();
    }

}

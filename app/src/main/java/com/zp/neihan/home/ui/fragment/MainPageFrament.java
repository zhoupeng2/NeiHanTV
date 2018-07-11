package com.zp.neihan.home.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.home.adapter.MainPageDuanZiAdapter;
import com.zp.neihan.home.contract.MainPageContract;
import com.zp.neihan.home.entity.JianDanComment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @CreatedDate 2018/7/3
 */
public class MainPageFrament extends BaseMVPFragment<MainPageContract.MainPageView, MainPageContract.MainPagePresenter> implements MainPageContract.MainPageView {

    @BindView(R.id.recycler_view_duanzi)
    RecyclerView recyclerViewDuanzi;
    @BindView(R.id.smart_refresh_layout_duanzi)
    SmartRefreshLayout duanZiSmartRefreshLayout;

    Unbinder unbinder;
    private MainPageContract.MainPagePresenter mainPagePresenter;
    private LinearLayoutManager linearLayoutManager;
    private MainPageDuanZiAdapter mainPageDuanZiAdapter;

    private int currentPage = 1;
    /**
     * 是否是第一次刷新
     */
    private boolean isFristRefresh = true;

    public static MainPageFrament newInstance() {
        return new MainPageFrament();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_main_page;
    }

    @Override
    protected void initView() {
        showLoadingView();
        mainPagePresenter.selectDataFromJianDan(currentPage, isFristRefresh);
        customShowCotentView(800);
    }

    @Override
    protected void intLogic() {

    }

    @Override
    protected void initEvent() {

        isFristRefresh = false;

        duanZiSmartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        duanZiSmartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        duanZiSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                mainPagePresenter.selectDataFromJianDan(currentPage, isFristRefresh);
                //  refreshlayout.finishRefresh(/*,false*/);//传入false表示刷新失败
            }
        });
        duanZiSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                currentPage++;
                mainPagePresenter.selectDataFromJianDan(currentPage, isFristRefresh);
                //   refreshlayout.finishLoadmore(2000);//传入false表示加载失败
            }
        });

    }


    @Override
    protected MainPageContract.MainPagePresenter createPresenter() {
        mainPagePresenter = new MainPageContract.MainPagePresenter();
        return mainPagePresenter;
    }


    @Override
    public void okResult(ArrayList<JianDanComment> jianDanList) {
        if (currentPage == 1) {
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewDuanzi.setLayoutManager(linearLayoutManager);
            mainPageDuanZiAdapter = new MainPageDuanZiAdapter(getActivity(), jianDanList);
            recyclerViewDuanzi.setAdapter(mainPageDuanZiAdapter);
        } else {
            mainPageDuanZiAdapter.notifyDataSetChanged();
        }

        duanZiSmartRefreshLayout.finishRefresh();
        duanZiSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void errorResult(String message) {
        Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
        duanZiSmartRefreshLayout.finishRefresh();
        duanZiSmartRefreshLayout.finishLoadmore();
    }

}

package com.zp.neihan.home.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    Unbinder unbinder;
    private MainPageContract.MainPagePresenter mainPagePresenter;
    private LinearLayoutManager linearLayoutManager;
    private MainPageDuanZiAdapter mainPageDuanZiAdapter;


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
        mainPagePresenter.selectDataFromJianDan(1);
        customShowCotentView(800);
    }

    @Override
    protected void intLogic() {

    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected MainPageContract.MainPagePresenter createPresenter() {
        mainPagePresenter = new MainPageContract.MainPagePresenter();
        return mainPagePresenter;
    }


    @Override
    public void setDataToView(ArrayList<String> userInfoList) {

    }

    @Override
    public void okResult(ArrayList<JianDanComment> jianDanList) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDuanzi.setLayoutManager(linearLayoutManager);
        mainPageDuanZiAdapter = new MainPageDuanZiAdapter(getActivity(), jianDanList);
        recyclerViewDuanzi.setAdapter(mainPageDuanZiAdapter);
    }

    @Override
    public void errorResult(String message) {
        Toast.makeText(getActivity(),"数据加载失败",Toast.LENGTH_SHORT).show();
    }

}

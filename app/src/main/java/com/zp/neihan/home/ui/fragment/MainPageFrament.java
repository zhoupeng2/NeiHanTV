package com.zp.neihan.home.ui.fragment;


import android.widget.TextView;

import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.home.contract.MainPageContract;

import butterknife.BindView;

/**
 * @CreatedDate 2018/7/3
 */
public class MainPageFrament extends BaseMVPFragment<MainPageContract.MainPageView, MainPageContract.MainPagePresenter> implements MainPageContract.MainPageView {

    @BindView(R.id.tv_mainpage)
    TextView tvMainpage;
    private MainPageContract.MainPagePresenter mainPagePresenter;

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
        mainPagePresenter.getUserInfo("9527",getActivity());
       customShowCotentView(800);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void intLogic() {

    }

    @Override
    protected MainPageContract.MainPagePresenter createPresenter() {
        mainPagePresenter= new MainPageContract.MainPagePresenter();
        return mainPagePresenter;
    }

    @Override
    public void setDataToView(String userInfo) {
        tvMainpage.setText(userInfo);
    }

}

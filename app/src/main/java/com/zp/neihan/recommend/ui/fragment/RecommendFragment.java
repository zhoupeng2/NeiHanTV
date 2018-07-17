package com.zp.neihan.recommend.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.recommend.adapter.RecommendAdapter;
import com.zp.neihan.recommend.contract.RecommendContract;
import com.zp.neihan.recommend.entity.RecommendEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.internal.Utils;
import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * @author ZP
 * @CreatedDate 2018/7/5
 */
public class RecommendFragment extends BaseMVPFragment<RecommendContract.RecommendView, RecommendContract.RecommendPresenter> {
    @BindView(R.id.recycler_view_recommend)
    RecyclerView recyclerViewRecommend;
    @BindView(R.id.image_watcher)
    ImageWatcher imageWatcher;
    private RecommendAdapter recommendAdapter;
    private ArrayList<RecommendEntity> recommendEntityArrayList;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }


    @Override
    protected int setContentViewId() {
        return R.layout.fragment_neihan_recommend;
    }

    @Override
    protected void initView() {
        showContentView();
        RecommendEntity recommendEntity1 = new RecommendEntity();
        recommendEntity1.setContributorName("笑笑的哇哈");
        recommendEntity1.setContributeText("感觉这届世界杯的一个魔咒，就是哪支队里面有接拍过中国品牌的广告的球员存在，哪支就被淘汰，梅西的某牛，c罗的某洗发水，接下来是不是就是内马尔了。 哈哈哈~隔");
        recommendEntity1.setContributeType(0);
        RecommendEntity recommendEntity2 = new RecommendEntity();
        recommendEntity2.setContributorName("笑笑的哇哈");
        recommendEntity2.setContributeText("在家吃零食看古装剧，看到一个妃子被两个人用被子裹起来，扛着去侍寝。\n" +
                "一旁的小外甥好像要说话，我打住：“不要问，这些事你长大就知道。”\n" +
                "他说：“小姨，如果是你的话，应该要三个人才扛得起来。”");
        recommendEntity2.setContributeType(0);
        RecommendEntity recommendEntity3 = new RecommendEntity();
        recommendEntity3.setContributorName("森林之王");
        recommendEntity3.setContributeText("1、儿子拿着一张破纸，对我说:“爸爸，我有一张藏宝图，我送给你，你给我10块钱好吗？”\n" +
                "我拿过来一看，说道:“你这不就是画的咱家吗，怎么就叫藏宝图了，还给我要10块钱。”\n" +
                "儿子:“爸爸，你看看用红笔画的地方.”\n" +
                "我仔细一看，沙发底下，桌子腿底下……**，这逼崽子，这可是老子藏私房钱的地方，\n" +
                "我赶紧跟儿子说：“那啥，宝贝儿，我给你50块！”");
        recommendEntity3.setContributeType(0);

        List<String > imgUrls=new ArrayList<>();
        imgUrls.add("https://imgsa.baidu.com/forum/w%3D580/sign=49451f4facec08fa260013af69ef3d4d/3800a18b87d6277f206989c824381f30e824fccd.jpg");
        imgUrls.add("https://imgsa.baidu.com/forum/w%3D580/sign=d6bc0984e7f81a4c2632ecc1e72b6029/1fed08fa513d26978361b3ad59fbb2fb4216d896.jpg");
        imgUrls.add("https://imgsa.baidu.com/forum/w%3D580/sign=3e0f38c036f33a879e6d0012f65d1018/b3f2d7ca7bcb0a46192930c96763f6246b60af3d.jpg");
        imgUrls.add("https://imgsa.baidu.com/forum/w%3D580/sign=c3d128dea4d3fd1f3609a232004f25ce/85282df5e0fe9925fd6bd2a038a85edf8cb171d8.jpg");

        RecommendEntity recommendEntity4 = new RecommendEntity();
        recommendEntity4.setContributorName("鲤鱼跃龙门");
        recommendEntity4.setContributeText("#哆啦A梦  老师，可能这是一场误会");
        recommendEntity4.setContributeType(2);
        recommendEntity4.setImageUrls(imgUrls);
        recommendEntityArrayList = new ArrayList<>();
        recommendEntityArrayList.add(recommendEntity1);
        recommendEntityArrayList.add(recommendEntity2);
        recommendEntityArrayList.add(recommendEntity4);
        recommendEntityArrayList.add(recommendEntity3);
        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));

        recommendAdapter = new RecommendAdapter(getActivity(), recommendEntityArrayList, recyclerViewRecommend);
        recyclerViewRecommend.setAdapter(recommendAdapter);
        recyclerViewRecommend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                } else {
                    Glide.with(getActivity()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    /*    recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecommend.addItemDecoration(new FriendsCircleAdapterDivideLine());
        mFriendCircleAdapter = new RecommendAdapter(this, recyclerView, mImageWatcher);
        recyclerViewRecommend.setAdapter(mFriendCircleAdapter);*/
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void intLogic() {

    }

    @Override
    protected RecommendContract.RecommendPresenter createPresenter() {
        return new RecommendContract.RecommendPresenter();
    }
}

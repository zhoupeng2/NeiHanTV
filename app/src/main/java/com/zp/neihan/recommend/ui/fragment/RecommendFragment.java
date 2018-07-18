package com.zp.neihan.recommend.ui.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.home.ui.activity.MainActivity;
import com.zp.neihan.recommend.adapter.RecommendAdapter;
import com.zp.neihan.recommend.contract.RecommendContract;
import com.zp.neihan.utils.CommonString;
import com.zp.neihan.videopage.cover.GestureCover;
import com.zp.neihan.videopage.entity.VideoBean;
import com.zp.neihan.videopage.play.AssistPlayer;
import com.zp.neihan.videopage.play.DataInter;
import com.zp.neihan.videopage.play.ReceiverGroupManager;
import com.zp.neihan.videopage.utils.GlideSimpleTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.internal.Utils;
import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * @author ZP
 * @CreatedDate 2018/7/5
 */
public class RecommendFragment extends BaseMVPFragment<RecommendContract.RecommendView, RecommendContract.RecommendPresenter> implements OnReceiverEventListener, OnPlayerEventListener {
    @BindView(R.id.recycler_view_recommend)
    RecyclerView recyclerViewRecommend;

    private RecommendAdapter recommendAdapter;
    private ArrayList<VideoBean> recommendEntityArrayList;



    /**

     * 视频部分
     */
    FrameLayout listPlayContainer;
    private ReceiverGroup mReceiverGroup;

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
        /**
         * 视频部分
         */
        listPlayContainer= MainActivity.mainActivityInstance.findViewById(R.id.listPlayContainer);

        AssistPlayer.get().addOnReceiverEventListener(this);
        AssistPlayer.get().addOnPlayerEventListener(this);

        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(getActivity());
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);

        VideoBean recommendEntity1 = new VideoBean();
        recommendEntity1.setContributorName("笑笑的哇哈");
        recommendEntity1.setContributeText("感觉这届世界杯的一个魔咒，就是哪支队里面有接拍过中国品牌的广告的球员存在，哪支就被淘汰，梅西的某牛，c罗的某洗发水，接下来是不是就是内马尔了。 ");
        recommendEntity1.setContributeType(0);
        VideoBean recommendEntity2 = new VideoBean();
        recommendEntity2.setContributorName("笑笑的哇哈");
        recommendEntity2.setContributeText("在家吃零食看古装剧，看到一个妃子被两个人用被子裹起来，扛着去侍寝。\n" +
                "一旁的小外甥好像要说话，我打住：“不要问，这些事你长大就知道。”\n" +
                "他说：“小姨，如果是你的话，应该要三个人才扛得起来。”");
        recommendEntity2.setContributeType(0);
        VideoBean recommendEntity3 = new VideoBean();
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

        VideoBean recommendEntity4 = new VideoBean();
        recommendEntity4.setContributorName("鲤鱼跃龙门");
        recommendEntity4.setContributeText("#哆啦A梦  老师，可能这是一场误会");
        recommendEntity4.setContributeType(2);
        recommendEntity4.setImageUrls(imgUrls);


        VideoBean recommendEntity5 = new VideoBean();
        recommendEntity5.setContributorName("机锋剑豪-托儿索");
        recommendEntity5.setContributeText("#费玉清-污妖王  嘿嘿嘿 ");
        recommendEntity5.setDisplayName("#费玉清-污妖王  嘿嘿嘿 ");
        recommendEntity5.setCover("http://imgsrc.baidu.com/forum/w%3D580/sign=dbbaed839e58d109c4e3a9bae15accd0/cc8b9f3df8dcd100bc4f76dc7e8b4710bb122fcc.jpg");
        recommendEntity5.setPath("https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/8181270_10b023211c10e2f7d4c618581fb1bda3_0.mp4");
        recommendEntity5.setContributeType(1);
        recommendEntityArrayList = new ArrayList<>();
        recommendEntityArrayList.add(recommendEntity1);
        recommendEntityArrayList.add(recommendEntity5);
        recommendEntityArrayList.add(recommendEntity2);
        recommendEntityArrayList.add(recommendEntity4);
        recommendEntityArrayList.add(recommendEntity3);

        recyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        recommendAdapter = new RecommendAdapter(getActivity(), recommendEntityArrayList, recyclerViewRecommend,MainActivity.mainActivityInstance.getImageWatcher());
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CommonString.isLandScape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            attachFullScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            attachList();
        }
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, CommonString.isLandScape);
    }


    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                getActivity().onBackPressed();
                break;
            case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                getActivity().setRequestedOrientation(CommonString.isLandScape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                AssistPlayer.get().stop();
                break;
        }
    }

    private void attachFullScreen() {
        mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(getActivity()));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
//        if(AssistPlayer.get().isPlaying())
        AssistPlayer.get().play(listPlayContainer, null);
    }

    private void attachList() {
        if (recommendAdapter != null) {
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
            mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
            recommendAdapter.getListPlayLogic().attachPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AssistPlayer.get().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
        AssistPlayer.get().setReceiverGroup(mReceiverGroup);
        if(CommonString.isLandScape){
            attachFullScreen();
        }else{
            attachList();
        }
        int state = AssistPlayer.get().getState();
        if(state!= IPlayer.STATE_PLAYBACK_COMPLETE){
            AssistPlayer.get().resume();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AssistPlayer.get().removeReceiverEventListener(this);
        AssistPlayer.get().removePlayerEventListener(this);
        AssistPlayer.get().destroy();

    }




}

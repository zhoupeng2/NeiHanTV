package com.zp.neihan.videopage.ui.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.zp.neihan.base.BaseFragment_NetworkState;
import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.home.ui.activity.MainActivity;
import com.zp.neihan.utils.CommonString;
import com.zp.neihan.videopage.adapter.VideoListAdapter;
import com.zp.neihan.videopage.contract.VideoPageContract;
import com.zp.neihan.videopage.cover.GestureCover;
import com.zp.neihan.videopage.entity.VideoBean;
import com.zp.neihan.videopage.play.AssistPlayer;
import com.zp.neihan.videopage.play.DataInter;
import com.zp.neihan.videopage.play.ReceiverGroupManager;
import com.zp.neihan.videopage.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.kk.taurus.playerbase.config.AppContextAttach.getApplicationContext;

/**
 * @author ZP
 * @CreatedDate 2018/7/5
 */
public class NeiHanShiPingFragment extends BaseMVPFragment implements OnReceiverEventListener, OnPlayerEventListener, VideoListAdapter.OnListListener {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    FrameLayout listPlayContainer;

    private List<VideoBean> mItems = new ArrayList<>();
    private VideoListAdapter mAdapter;


    private ReceiverGroup mReceiverGroup;

    public static NeiHanShiPingFragment newInstance() {
        return new NeiHanShiPingFragment();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_neihan_shiping;
    }

    @Override
    public void initView() {
        showContentView();
        listPlayContainer= MainActivity.mainActivityInstance.findViewById(R.id.listPlayContainer);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        AssistPlayer.get().addOnReceiverEventListener(this);
        AssistPlayer.get().addOnPlayerEventListener(this);

        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(getActivity());
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);

        mItems.addAll(DataUtils.getVideoList());
        mAdapter = new VideoListAdapter(getApplicationContext(), mRecyclerView, mItems);
        mAdapter.setOnListListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void intLogic() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new VideoPageContract.VideoPagePresenter();
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

    @Override
    public void onTitleClick(VideoBean item, int position) {
        Toast.makeText(getActivity(), "调整", Toast.LENGTH_SHORT).show();
    }


    private void attachFullScreen() {
        mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(getActivity()));
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
//        if(AssistPlayer.get().isPlaying())
        AssistPlayer.get().play(listPlayContainer, null);
    }

    private void attachList() {
        if (mAdapter != null) {
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
            mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
            mAdapter.getListPlayLogic().attachPlay();
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

  /*  @Override
    public void onBackPressed() {
        if(isLandScape){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }*/
}

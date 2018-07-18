package com.zp.neihan.videopage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zp.neihan.R;
import com.zp.neihan.recommend.adapter.RecommendAdapter;
import com.zp.neihan.utils.CommonUtil;
import com.zp.neihan.videopage.entity.VideoBean;
import com.zp.neihan.videopage.play.ListPlayLogic;
import com.zp.neihan.videopage.utils.PUtil;

import java.util.List;

/**
 * Created by Taurus on 2018/4/15.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoItemHolder> {

    private Context mContext;
    private List<VideoBean> mItems;
    private OnListListener onListListener;

    private ListPlayLogic mListPlayLogic;

    private int mScreenUseW;

    public VideoListAdapter(Context context, RecyclerView recyclerView, List<VideoBean> list) {
        this.mContext = context;
        this.mItems = list;
        mScreenUseW = PUtil.getScreenW(context);
        mListPlayLogic = new ListPlayLogic(context, recyclerView, this,list);
    }

    public ListPlayLogic getListPlayLogic() {
        return mListPlayLogic;
    }

    public void setOnListListener(OnListListener onListListener) {
        this.onListListener = onListListener;
    }

    @Override
    public VideoListAdapter.VideoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListAdapter.VideoItemHolder(View.inflate(mContext, R.layout.recyclerview_item_neihan_video, null));
    }

    @Override
    public void onBindViewHolder(final VideoListAdapter.VideoItemHolder holder, final int position) {
        //     ViewCompat.setElevation(holder.card, PUtil.dip2px(mContext, 3));
        updateWH(holder);
        final VideoBean item = getItem(position);
        if (TextUtils.isEmpty(item.getCover())) {
            Glide.with(mContext)
                    .load(item.getPath())
                    .apply(CommonUtil.getGlideOptions())
                    .into(holder.albumImage);
        } else {
            Glide.with(mContext)
                    .load(item.getCover())
                    .apply(CommonUtil.getGlideOptions())
                    .into(holder.albumImage);
        }
        holder.txt_duanzi_content.setText(item.getDisplayName());
        holder.layoutContainer.removeAllViews();
        holder.albumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlayPosition(position);
                mListPlayLogic.playPosition(position);
            }
        });

        if (onListListener != null) {
            holder.txt_duanzi_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePlayPosition(position);
                    onListListener.onTitleClick(item, position);
                }
            });
        }
    }

    private void updateWH(VideoListAdapter.VideoItemHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.layoutBox.getLayoutParams();
        layoutParams.width = mScreenUseW;
        layoutParams.height = mScreenUseW * 9 / 16;
        holder.layoutBox.setLayoutParams(layoutParams);
    }

    public void updatePlayPosition(int position) {
        mListPlayLogic.updatePlayPosition(position);
    }

    public VideoBean getItem(int position) {
        if (mItems == null)
            return null;
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return 0;
        return mItems.size();
    }

    public static class VideoItemHolder extends RecommendAdapter.BaseNeiHanViewHolder {

        //   View card;
        public FrameLayout layoutContainer;
        public RelativeLayout layoutBox;
        public View albumLayout;
        public ImageView albumImage;
        public TextView txt_duanzi_content;

        public VideoItemHolder(View itemView) {
            super(itemView);
            //   card = itemView.findViewById(R.id.card);
            layoutContainer = itemView.findViewById(R.id.layoutContainer);
            layoutBox = itemView.findViewById(R.id.layBox);
            albumLayout = itemView.findViewById(R.id.album_layout);
            albumImage = itemView.findViewById(R.id.albumImage);
            txt_duanzi_content = itemView.findViewById(R.id.txt_duanzi_content);
        }

    }

    public interface OnListListener {
        void onTitleClick(VideoBean item, int position);
    }

}

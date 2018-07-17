package com.zp.neihan.recommend.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zp.neihan.R;
import com.zp.neihan.recommend.entity.RecommendEntity;
import com.zp.neihan.utils.CommonUtil;
import com.zp.neihan.videopage.adapter.VideoListAdapter;
import com.zp.neihan.videopage.entity.VideoBean;
import com.zp.neihan.videopage.play.ListPlayLogic;
import com.zp.neihan.videopage.ui.fragment.NeiHanShiPingFragment;
import com.zp.neihan.videopage.utils.PUtil;
import com.zp.neihan.view.NineGridView;

import java.util.ArrayList;

/**
 * @author ZP
 * @CreatedDate 2018/7/13
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.BaseNeiHanViewHolder> {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<RecommendEntity> recommendEntityArrayList;


    /**
     * 视频加载部分
     */
    private ListPlayLogic mListPlayLogic;
    private int mScreenUseW;
    /**
     * Glide图片加载配置参数
     */
    private RequestOptions mRequestOptions;
    private DrawableTransitionOptions mDrawableTransitionOptions;

    public RecommendAdapter(Context context, ArrayList<RecommendEntity> recommendEntityArrayList,RecyclerView recyclerView) {
        this.context = context;
        this.recommendEntityArrayList = recommendEntityArrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        mScreenUseW = PUtil.getScreenW(context);
        mListPlayLogic = new ListPlayLogic(context, recyclerView, this,recommendEntityArrayList);
    }

    @NonNull
    @Override
    public RecommendAdapter.BaseNeiHanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new NeiHanDuanZiViewHolder(mLayoutInflater.inflate(R.layout.recyclerview_item_neihan_duanzi, parent, false));
        } else if (viewType == 1) {
            return new NeiHanDuanVideoHolder(mLayoutInflater.inflate(R.layout.recyclerview_item_neihan_video, parent, false));
        } else if (viewType == 2) {
            return new NeiHanTextAndImagesViewHolder(mLayoutInflater.inflate(R.layout.recyclerview_item_neihan_txt_imgs, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.BaseNeiHanViewHolder holder, final int position) {
        if (holder != null && recommendEntityArrayList != null && position < recommendEntityArrayList.size()) {
            RecommendEntity recommendEntity = recommendEntityArrayList.get(position);
            /**
             *  投稿人名字
             */
            holder.txt_contributor_name.setText(recommendEntity.getContributorName());
            /**
             * 投稿文字内容
             */
            holder.txt_duanzi_content.setText(recommendEntity.getContributeText());

            if (holder instanceof NeiHanDuanZiViewHolder) {
                NeiHanDuanZiViewHolder neiHanDuanZiViewHolder = (NeiHanDuanZiViewHolder) holder;
            } else if (holder instanceof NeiHanDuanVideoHolder) {
                NeiHanDuanVideoHolder neiHanDuanVideoHolder = (NeiHanDuanVideoHolder) holder;

                updateWH(neiHanDuanVideoHolder);
                if (TextUtils.isEmpty(recommendEntity.getCover())) {
                    Glide.with(context)
                            .load(recommendEntity.getPath())
                            .apply(CommonUtil.getGlideOptions())
                            .into(neiHanDuanVideoHolder.albumImage);
                } else {
                    Glide.with(context)
                            .load(recommendEntity.getCover())
                            .apply(CommonUtil.getGlideOptions())
                            .into(neiHanDuanVideoHolder.albumImage);
                }
                neiHanDuanVideoHolder.layoutContainer.removeAllViews();
                neiHanDuanVideoHolder.albumLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePlayPosition(position);
                        mListPlayLogic.playPosition(position);
                    }
                });
            } else if (holder instanceof NeiHanTextAndImagesViewHolder) {
                NeiHanTextAndImagesViewHolder wordAndImagesViewHolder = (NeiHanTextAndImagesViewHolder) holder;
                wordAndImagesViewHolder.nineGridView.setAdapter(new NineImageAdapter(context, mRequestOptions,
                        mDrawableTransitionOptions, recommendEntity.getImageUrls()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return recommendEntityArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return recommendEntityArrayList.get(position).getContributeType();
    }

    /**
     * 多种类型适配 RecylerView  父ViewHolder
     */
    public static class BaseNeiHanViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_contributor_name;
        private TextView txt_duanzi_content;

        public BaseNeiHanViewHolder(View itemView) {
            super(itemView);
            txt_contributor_name = itemView.findViewById(R.id.txt_contributor_name);
            txt_duanzi_content = itemView.findViewById(R.id.txt_duanzi_content);
        }

    }

    /**
     * 多种类型适配 RecylerView  段子纯文字类型
     */
    public static class NeiHanDuanZiViewHolder extends BaseNeiHanViewHolder {


        public NeiHanDuanZiViewHolder(View itemView) {
            super(itemView);

        }
    }

    /**
     *
     * 视频item部分
     * 多种类型适配 RecylerView  段子视频类型
     */
    public static class NeiHanDuanVideoHolder extends BaseNeiHanViewHolder {
        public FrameLayout layoutContainer;
        public RelativeLayout layoutBox;
        View albumLayout;
        ImageView albumImage;

        public NeiHanDuanVideoHolder(View itemView) {
            super(itemView);
            layoutContainer = itemView.findViewById(R.id.layoutContainer);
            layoutBox = itemView.findViewById(R.id.layBox);
            albumLayout = itemView.findViewById(R.id.album_layout);
            albumImage = itemView.findViewById(R.id.albumImage);
        }
    }

    /**
     * 多种类型适配 RecylerView  段子文字+图片类型
     */
    public static class NeiHanTextAndImagesViewHolder extends BaseNeiHanViewHolder {
        NineGridView nineGridView;

        public NeiHanTextAndImagesViewHolder(View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
        }
    }

    /**
     * 视频item部分
     */
    public ListPlayLogic getListPlayLogic() {
        return mListPlayLogic;
    }
    /**
     * 视频item部分
     */
    private void updateWH(NeiHanDuanVideoHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.layoutBox.getLayoutParams();
        layoutParams.width = mScreenUseW;
        layoutParams.height = mScreenUseW * 9 / 16;
        holder.layoutBox.setLayoutParams(layoutParams);
    }
    /**
     * 视频item部分
     */
    public void updatePlayPosition(int position) {
        mListPlayLogic.updatePlayPosition(position);
    }
}

package com.zp.neihan.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zp.neihan.R;
import com.zp.neihan.home.entity.JianDanComment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZP
 * @CreatedDate 2018/7/6
 */
public class MainPageDuanZiAdapter extends RecyclerView.Adapter<MainPageDuanZiAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<JianDanComment> jianDanCommentList;

    public MainPageDuanZiAdapter(Context context, ArrayList<JianDanComment> transactionList) {
        this.context = context;
        this.jianDanCommentList = transactionList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_neihan_duanzi, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JianDanComment jianDanCommentEntity = jianDanCommentList.get(position);
        holder.txt_duanzi_content.setText(jianDanCommentEntity.getComment_content());
    }

    @Override
    public int getItemCount() {
        return jianDanCommentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username, txt_duanzi_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            txt_duanzi_content = (TextView) itemView.findViewById(R.id.txt_duanzi_content);
        }
    }
}

package com.example.haniln.test1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<item> mItems;
    Context mContext;
    public RecyclerViewAdapter(ArrayList itemList) {
        mItems = itemList;
    }
    // 필수 오버라이드 : View 생성, ViewHolder 호출
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView tv_code;
        public TextView tv_gear;
        public TextView tv_name;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv_code = itemView.findViewById(R.id.code);
            tv_gear = itemView.findViewById(R.id.gear);
            tv_name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        mContext = parent.getContext();
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }
    // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        // 해당 position 에 해당하는 데이터 결합
        holder.img.setImageResource(mItems.get(position).img);
        holder.tv_code.setText(mItems.get(position).zcode);
        holder.tv_gear.setText(mItems.get(position).zday);
        holder.tv_name.setText(mItems.get(position).zdpt4);

        // 이벤트처리 : 생성된 List 중 선택된 목록번호를 Toast로 출력
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 필수 오버라이드 : 데이터 갯수 반환
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

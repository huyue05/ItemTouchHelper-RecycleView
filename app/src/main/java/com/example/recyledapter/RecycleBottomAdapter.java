package com.example.recyledapter;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.recycleitemhelpapplication.R;
import com.example.com.example.Listener.OnItemOnclikListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class RecycleBottomAdapter extends RecyclerView.Adapter<MybottomViewHolder>{

    LayoutInflater mInflater;
    Context context;
    ArrayList<String> mData;

    //点击事件监听的声明和设置
    public OnItemOnclikListener mOnClikListener;


    public void SetOnClikListener(OnItemOnclikListener mOnClikListener) {
        this.mOnClikListener = mOnClikListener;
    }
    public  void  addsmData(String str){
        mData.add(str);
    }
    public  void  deletesmData(int  pos){
        mData.remove(pos);
    }
    public  void setmData( ArrayList<String> mData){
        this.mData=mData;
    }
    public RecycleBottomAdapter( Context context, ArrayList<String> mData) {
        this.mInflater =LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MybottomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_nouseview, parent, false);
        MybottomViewHolder viewHolder = new MybottomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MybottomViewHolder holder, final int position) {
        holder.tv_item.setText(mData.get(position));
        if (mOnClikListener != null) {
            holder.tv_item.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mOnClikListener.OnItemClik(view, position);
                }

            });
            holder.tv_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnClikListener.OnItemLongClik(view, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
class MybottomViewHolder extends RecyclerView.ViewHolder {

    TextView tv_item;

    public MybottomViewHolder(View itemView) {
        super(itemView);
        tv_item = (TextView) itemView.findViewById(R.id.tv_bottom);
    }
}
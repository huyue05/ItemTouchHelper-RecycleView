package com.example.recyledapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recycleitemhelpapplication.R;
import com.example.com.example.Listener.OnItemOnclikListener;
import com.example.com.example.callback.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.Collections;


public class RecycleAdapter extends RecyclerView.Adapter<MyViewHolder> implements ItemDragHelperCallback.ItemTouchHelperAdapter {

    LayoutInflater mInflater;
    Context context;
    ArrayList<String> mData;
    //flag是true的话 就会出现XX
    Boolean flag;

    //以下2个方法是重写ItemTouchHelperAdapter
    // notifyItemRemoved()和 notifyItemMoved()的调用非常重要，
    // 有了它们Adapter才能知道发生了改变。同时还需要
    // 注意的是每当一个view切换到了一个新的索引时，我们都需要改变item的位置，而不是在拖动事件结束的时候。
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public  void setmData( ArrayList<String> mData){
        this.mData=mData;
    }

    public  void  addsmData(String str){
        mData.add(str);
    }
    public  void  deletesmData(int  pos){
        mData.remove(pos);
    }

    //点击事件监听的声明和设置
    public OnItemOnclikListener mOnClikListener;
    public void SetOnClikListener(OnItemOnclikListener mOnClikListener) {
        this.mOnClikListener = mOnClikListener;
    }


    public RecycleAdapter(ArrayList<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

        flag =true;

    }



    //创建viewholder
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_useview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //绑定viewholder
    public void onBindViewHolder(MyViewHolder holder, final int position) {
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
            holder.img_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClikListener.OnItemClik(view, position);
                }
            });
        }
        if (flag==false){
            holder.img_delete_item.setVisibility(View.GONE);
        }else {
            holder.img_delete_item.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  void changeAllImg(Boolean flag){
    this.flag =flag;

    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv_item;
    ImageView img_delete_item;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_item = (TextView) itemView.findViewById(R.id.tv_single);
        img_delete_item = (ImageView) itemView.findViewById(R.id.img_toprecycle_delete);
    }
}

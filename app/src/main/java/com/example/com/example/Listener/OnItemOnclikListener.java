package com.example.com.example.Listener;

import android.view.View;

/**
 * Created by Administrator on 2016/7/19 0019.
 */

    //点击事件的监听的接口
    public interface OnItemOnclikListener {
        public void OnItemLongClik(View view, int postion);
        public void OnItemClik(View view, int postion);
    }


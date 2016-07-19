package com.example.com.example.callback;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public ItemDragHelperCallback(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

// temTouchHelper可以让你轻易得到一个事件的方向。你需要重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。
// 使用helperItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。这里我们启用了上下左右两种方向。注：上下为拖动（drag），
// 左右为滑动（swipe）。
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
       //拖拽
        int dragFlags = ItemTouchHelper.UP   | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       //滑动
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


    //ItemTouchHelper可以用于没有滑动的拖动操作（或者反过来），你必须指明你到底要支持哪一种。
    // 要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
    // 或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动。这会在后面讲到
    public boolean isLongPressDragEnabled() {
        return true;
    }

//而要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了。
// 或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder) 来开始滑动操作。
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

//接下来的两个是onMove()和onSwiped()，用于通知底层数据的更新。首先我们创建一个可以将这些回调方法传递出去的接口。
    public interface ItemTouchHelperAdapter {

        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

    //滑动动画
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float width = (float) viewHolder.itemView.getWidth();
            float alpha = 1.0f - Math.abs(dX) / width;
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

}

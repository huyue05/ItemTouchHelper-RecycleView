# RecycleItemHelpDemo
仿写网易新闻客户端，顶部tabs，滑动排序，增加删除
比传统效果更好。
### 仿写网易新闻客户端头部tabs选择频道:RecycleView&ItemTouchHelp。*

####友情提示：观看本文，请结合代码看，为了版面的整洁和突出重点，本文没有过多的介绍基础代码。

####源码地址：
####[https://github.com/huyue05/ItemTouchHelper-RecycleView](https://github.com/huyue05/ItemTouchHelper-RecycleView)

#### 以下是演示图片
![这是演示图片](http://img.blog.csdn.net/20160720144155026?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
###核心：
- RecycleView的使用，将子布局转换成类似GridView的模式。适配好。
- 学会ItemTouchHelp的使用，ItemTouchHelp是为了方便管理RecycleView拖拽而产生的一个类。
- 接口回调，来进行数据和方法的回调。
### 前言：
- RecycleView取代ListView和GridView只是时间问题了，高效，流畅，优质。
- ListView的核心是子布局线性排列。
- RecycleView的排列是由LayoutManager管理，他只关系viewHolder的复用，而不关心是到底是如何排列的。

###步骤1:
####（Recycle的基本使用是很基础的东西，不过多讲述，可以先百度一下，网上也有很多免费的教程，建议学会基本的使用再看本文）

####**1.RecycleView的使用，将子布局转换成类似GridView的模式，适配好。**
	    bottomrecleView = (RecyclerView) findViewById(R.id.recyle_bottom);
        bottomadapter = new RecycleBottomAdapter(getApplication(),bottomRecleList);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getApplicationContext(),4);
        bottomrecleView.setLayoutManager(manager2);
        bottomrecleView.setAdapter(bottomadapter)
  RecycleView的适配器：在适配器代码里面值得需要注意的是下面这个方法：
  
	public  void changeAllImg(Boolean flag){
	this.flag =flag;
	}
这个通过在在适配器写一个标识符，来判断是否需要展示item上的X删除图片。
RecycleView和适配器的使用不过分介绍，都是一些基础的内容。
**2.学会ItemTouchHelp的使用，ItemTouchHelp是为了方便GridView而产生的一个类。**
	public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public ItemDragHelperCallback(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }、
 temTouchHelper可以让你轻易得到一个事件的方向。你需要重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。使用helperItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。这里我们启用了上下左右两种方向。注：上下为拖动（drag），左右为滑动（swipe）

	 public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolderviewHolder) {      
	 //拖拽        
	 int dragFlags = ItemTouchHelper.UP   | ItemTouchHelper.DOWN | 
		ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  
	//滑动       
	int swipeFlags = 0;       
	return makeMovementFlags(dragFlags, swipeFlags);   
	 }
	
####下面这个方法是移动和拖拽的方法，如果你想让他移动，则一定要返回ture。
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
####要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动.
	 public boolean isLongPressDragEnabled() {
        return true;
    }
####而要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了。 或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder) 来开始滑动操作。
	 public boolean isItemViewSwipeEnabled() {
        return true;
    }
####接下来的两个是onMove()和onSwiped()，用于通知底层数据的更新。首先我们创建一个可以将这些回调方法传递出去的接口。
	public interface ItemTouchHelperAdapter {

        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }
####滑动动画
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
####以上是item拖拽的一个回传代码，每一个方法我都写好了注释！
####最后给RecycleView设置监听，完成~
####上下2个RecycleView每个都用一个List（）；来存放数据。给adapter上写监听。当每一方的数据进行删除的时候，另一方就进行数据的添加。

####示例部分代码：点击下方的RecleView
		TextView tv = (TextView) view;
        String str = tv.getText().toString();
        topRecleList.add(str);
        bottomRecleList.remove(postion);
        bottomadapter.setmData(bottomRecleList);
        topadapter.setmData(topRecleList);
        topadapter.notifyDataSetChanged();
        bottomadapter.notifyDataSetChanged();
####完结：
####看这篇文章需要对RecycleView有一定初步的了解。
####如果有不懂得地方，可以下载源码看一看。（在文章首部）
####再不懂得情况下， 抄一遍代码就差不多了。
####如果有任何建议，欢迎联系我交流。
####个人微信：xindekaishine
####Email：huyuesa#Foxmail.com
####weibo：http://weibo.com/3483649781/profile?topnav=1&wvr=6&is_all=1
#### 仅作学习之用，不重复造轮子

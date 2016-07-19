package com.example.administrator.recycleitemhelpapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.Listener.OnItemOnclikListener;
import com.example.com.example.callback.ItemDragHelperCallback;
import com.example.recyledapter.RecycleAdapter;
import com.example.recyledapter.RecycleBottomAdapter;

import java.util.ArrayList;

public class MainActivity extends  AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView  bottomrecleView;
    RecycleAdapter topadapter;
    RecycleBottomAdapter bottomadapter;
    ArrayList<String> topRecleList;
    ArrayList<String> bottomRecleList;
    ArrayList<String> allRecleList;

    TextView tv_caozuo;
    Boolean caozuo_flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();
        initView();
        listenView();
        LoadData();
    }

    private void initData() {

        allRecleList = new ArrayList<String>();
        topRecleList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            topRecleList.add("" + i);
            allRecleList.add("" + i);
        }
        bottomRecleList = new ArrayList<String>();
        for (char i = 'A'; i <'J'; i++) {
            bottomRecleList.add("" + i);
            allRecleList.add("" + i);
        }
    }

    private void  initView() {

        tv_caozuo= (TextView) findViewById(R.id.tv_caozuo);
        caozuo_flag =true;

        //上部分的recycle进行初始化
        recyclerView= (RecyclerView) findViewById(R.id.recyle_top);
        topadapter=new RecycleAdapter(topRecleList,getApplication());
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(manager);
        //设置监听
        ItemDragHelperCallback callback =
                new ItemDragHelperCallback(topadapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(topadapter);

        //下部分的recycle进行初始化
        bottomrecleView = (RecyclerView) findViewById(R.id.recyle_bottom);
        bottomadapter = new RecycleBottomAdapter(getApplication(),bottomRecleList);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getApplicationContext(),4);
        bottomrecleView.setLayoutManager(manager2);
        bottomrecleView.setAdapter(bottomadapter);


        //
    }

    private void  listenView() {
        topadapter.SetOnClikListener(new OnItemOnclikListener() {

            public void OnItemLongClik(View view, int postion) {
                if (view.getId()==R.id.tv_single){
                    Toast.makeText(getApplication(),"long clike"+postion,Toast.LENGTH_SHORT).show();
                }

            }
            public void OnItemClik(View view, int postion) {
                if (view.getId()==R.id.tv_single){
                    Toast.makeText(getApplication(),"clik"+postion,Toast.LENGTH_SHORT).show();
                }else if (view.getId()==R.id.img_toprecycle_delete){
                    String str = topRecleList.get(postion);
                    bottomRecleList.add(str);
                    topRecleList.remove(postion);

                    bottomadapter.setmData(bottomRecleList);
                    topadapter.setmData(topRecleList);

                    topadapter.notifyDataSetChanged();
                    bottomadapter.notifyDataSetChanged();
                }

            }
        });
        bottomadapter.SetOnClikListener(new OnItemOnclikListener() {
            @Override
            public void OnItemLongClik(View view, int postion) {
                Toast.makeText(getApplication(),"long clike"+postion,Toast.LENGTH_SHORT).show();
                TextView tv = (TextView) view;
                String str = tv.getText().toString();
                topRecleList.add(str);
                bottomRecleList.remove(postion);
                bottomadapter.setmData(bottomRecleList);
                topadapter.setmData(topRecleList);

                topadapter.notifyDataSetChanged();
                bottomadapter.notifyDataSetChanged();

            }

            @Override
            public void OnItemClik(View view, int postion) {
                Toast.makeText(getApplication(),"clik"+postion,Toast.LENGTH_SHORT).show();

                TextView tv = (TextView) view;
                String str = tv.getText().toString();
                topRecleList.add(str);
                bottomRecleList.remove(postion);
                bottomadapter.setmData(bottomRecleList);
                topadapter.setmData(topRecleList);

                topadapter.notifyDataSetChanged();
                bottomadapter.notifyDataSetChanged();

            }
        });

        tv_caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (caozuo_flag== true){
                    caozuo_flag=false;
                    tv_caozuo.setText("编辑");
                }else{
                    caozuo_flag=true;
                    tv_caozuo.setText("完成");
                }
                topadapter.changeAllImg(caozuo_flag);
                topadapter.notifyDataSetChanged();
            }
        });
    }

    private void   LoadData() {
    }
}

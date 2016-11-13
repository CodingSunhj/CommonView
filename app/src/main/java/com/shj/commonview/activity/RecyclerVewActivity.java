package com.shj.commonview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.commonutils.BaseActivity;
import com.commonview.recyclerview.CommonAdapter;
import com.commonview.recyclerview.MultiItemAdapter;
import com.shj.commonview.R;
import com.shj.commonview.adapter.RvSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVewActivity  extends BaseActivity {

    private RecyclerView rv;
    private RvSimpleAdapter simpleAdapter;
    public static void start(Activity context){
        Intent intent = new Intent(context,RecyclerVewActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_vew);
        initView();
        initData();
    }

    private void initView() {
        rv = $(R.id.rv);
    }
    private void initData(){
        final List<String> datas = new ArrayList<>();
        for(int i=0;i<20;i++){
            datas.add("rv"+String.valueOf(i));
        }
        simpleAdapter = new RvSimpleAdapter(this,R.layout.simple_rv_title_item,datas);

        final View headerView = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        final View headerView2 = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        simpleAdapter.addHeaderView(headerView);
        simpleAdapter.addHeaderView(headerView2);
        simpleAdapter.setLoadMoreView(R.layout.item_load_more);
        simpleAdapter.setOnItemClickListener(new MultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerVewActivity.this,"test"+position+datas.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        simpleAdapter.setOnLoadMoreListener(new CommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed (new Runnable() {
                    @Override
                    public void run() {
                        if(datas.size()>=21){
//                           simpleAdapter.notifyDataCompleteLoad();
                            simpleAdapter.addList(null);
                            simpleAdapter.getLoadMoreView().setVisibility(View.GONE);
                            simpleAdapter.getLoadMoreView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                            simpleAdapter.getLoadMoreView().findViewById(R.id.loadText).setVisibility(View.GONE);
                           Log.i("TAG","OVER");
                        }else {
//                            datas.add("t1" + datas.size());
                            List<String> data = new ArrayList<String>();
                            data.add("t1"+simpleAdapter.getDatas().size());
                            simpleAdapter.addList(data);
//                            simpleAdapter.notifyDataSetChanged();
                        }
                    }
                },2000);
            }


        });
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(simpleAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package com.shj.commonview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.commonutils.BaseActivity;
import com.commonview.recyclerview.LoadMoreWrapper;
import com.commonview.recyclerview.MultiItemAdapter;
import com.shj.commonview.R;
import com.shj.commonview.adapter.RvSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVewActivity  extends BaseActivity {

    private RecyclerView rv;
    private RvSimpleAdapter simpleAdapter;
    private LoadMoreWrapper loadMoreWrapper;
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
        simpleAdapter.setHeaderView(headerView);
        loadMoreWrapper = new LoadMoreWrapper(simpleAdapter);
        simpleAdapter.setOnItemClickListener(new MultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerVewActivity.this,"test"+position+datas.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed (new Runnable() {
                    @Override
                    public void run() {
                        if(datas.size()==23){
                           loadMoreWrapper.notifyDataCompleteLoad();
                        }else {
                            datas.add("t1" + datas.size());
                            loadMoreWrapper.notifyDataSetChanged();
                        }
                    }
                },3000);
            }


        });
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(loadMoreWrapper);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package com.pzl.listviewcustomrecycler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pzl.listviewcustomrecycler.test.Constant;
import com.pzl.listviewcustomrecycler.test.PushJson;
import com.pzl.listviewcustomrecycler.test.RootJson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
    }

    private void initData() {
        adapter = new Adapter(this);
        lv.setAdapter(adapter);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RootJson>>() {
        }.getType();
        /**
         * 第一步拿到RootJson
         */
        List<RootJson> rootJsonList = gson.fromJson(Constant.json, type);
        /**
         * 二次解析pushjson
         */

        adapter.setData(rootJsonList);
        adapter.notifyDataSetChanged();

        for (int i = 0; i <= rootJsonList.size() - 1; i++) {
            RootJson rootJson = rootJsonList.get(i);
            PushJson pushJson = gson.fromJson(rootJson.pushjson, PushJson.class);
            for (String key : pushJson.data.keySet()) {
                System.out.println(key + " = " + pushJson.data.get(key).toString());
                System.out.println("\n\n");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "position=" + position, Toast.LENGTH_SHORT).show();
    }
}

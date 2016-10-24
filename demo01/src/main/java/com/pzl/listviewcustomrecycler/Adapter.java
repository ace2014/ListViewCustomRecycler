package com.pzl.listviewcustomrecycler;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pzl.listviewcustomrecycler.test.PushJson;
import com.pzl.listviewcustomrecycler.test.RootJson;
import com.pzl.recycler.IRecycleManager;
import com.pzl.recycler.Recycler;

import java.util.List;


public class Adapter extends BaseAdapter {
    private List<RootJson> rootJsonList;
    private Gson gson;
    private Recycler recycler;

    public Adapter(Context context) {
        gson = new Gson();
        recycler = Recycler.getInstance();
        recycler.init(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rootJsonList == null ? 0 : rootJsonList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LinearLayout container = (LinearLayout) recycler.getView(R.layout.container, IRecycleManager.CONTAINER, position);

        RootJson rootJson = rootJsonList.get(position);
        PushJson pushJson = gson.fromJson(rootJson.pushjson, PushJson.class);
        Object[] keys = pushJson.data.keySet().toArray();

        /**
         * 设置日期
         */
        LinearLayout date = (LinearLayout) recycler.getView(R.layout.item_date, IRecycleManager.ONLY_CHILD, position);

        TextView tvDate = (TextView) date.findViewById(R.id.tvDate);
        tvDate.setText(rootJson.date);
        container.addView(date, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        /**
         * 设置日期下面那整块，容器
         */
        LinearLayout card = (LinearLayout) recycler.getView(R.layout.container_card, IRecycleManager.CONTAINER, position);

        container.addView(card, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            card.setBackground(parent.getContext().getResources().getDrawable(R.drawable.shape_card));
        }

        /**
         * 设置第一项
         */

        LinearLayout first = (LinearLayout) recycler.getView(R.layout.item_lv, IRecycleManager.ONLY_CHILD, position);

        // LinearLayout first = (LinearLayout) inflater.inflate(R.layout.item_lv, null);
        TextView tvFirstKey = (TextView) first.findViewById(R.id.tvKey);
        TextView tvFirstValue = (TextView) first.findViewById(R.id.tvValue);
        tvFirstKey.setVisibility(View.GONE);
        tvFirstValue.setText(pushJson.data.get(keys[0]).value + "");
        tvFirstValue.setTextSize(16f);
        card.addView(first, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        /**
         * 中间项
         */

        for (int i = 0; i <= keys.length - 1 - 2; i++) {

            LinearLayout itemCenter = (LinearLayout) recycler.getView(R.layout.item_lv, IRecycleManager.ONLY_CHILD, position);

            // LinearLayout itemCenter = (LinearLayout) inflater.inflate(R.layout.item_lv, null);
            TextView tvCenterKey = (TextView) itemCenter.findViewById(R.id.tvKey);
            TextView tvCenterValue = (TextView) itemCenter.findViewById(R.id.tvValue);
            tvCenterKey.setText(keys[i + 1] + ":");
            tvCenterValue.setText(pushJson.data.get(keys[i + 1]).value + "");
            card.addView(itemCenter, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }

        /**
         * 设置最后一项
         */
        LinearLayout remark = (LinearLayout) recycler.getView(R.layout.item_lv, IRecycleManager.ONLY_CHILD, position);
        // LinearLayout remark = (LinearLayout) inflater.inflate(R.layout.item_lv, null);
        TextView tvRemarkKey = (TextView) remark.findViewById(R.id.tvKey);
        TextView tvRemarkValue = (TextView) remark.findViewById(R.id.tvValue);
        tvRemarkKey.setVisibility(View.GONE);
        tvRemarkValue.setText(pushJson.data.get(keys[keys.length - 1]).value + "");
        tvRemarkValue.setTextSize(16f);
        tvRemarkValue.setTextColor(Color.BLUE);
        card.addView(remark, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        recycler.recycle(new int[]{R.layout.item_date, R.layout.container, R.layout.container_card, R.layout.item_lv}, (ListView) parent);
        return container;
    }

    public void setData(List<RootJson> rootJsonList) {
        this.rootJsonList = rootJsonList;
        Log.e("pzl", "rootJsonList.size()=" + rootJsonList.size());

    }


}

package com.pzl.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * key layoutId ; 正在使用活跃的list复用的list position属性 ；
 * 活跃的bean position 小于firstVisiblePosition 或者 大于 firstVisiblePosition + childCount 则回收（可扩张域 如+2）
 * 仅限从布局解析的任意类型 ；有则从回收的pool拿没有则创建
 *
 * @author zl.peng
 * @version [1.0, 2016-10-20]
 */
public class Recycler extends RecycleManager {
    public static final String TAG = "Recycler";

    private static Recycler instance;
    /**
     * 可重用的layout
     */
    private Map<Integer, ArrayList<RecycleLayout>> recycleViews;
    /**
     * 活跃使用的layout
     */
    private Map<Integer, ArrayList<RecycleLayout>> activeViews;

    private LayoutInflater layoutInflater;

    private Recycler() {
        recycleViews = new HashMap();
        activeViews = new HashMap();
    }

    public static synchronized Recycler getInstance() {
        if (instance == null) {
            return new Recycler();
        }
        return instance;
    }

    public void init(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    protected void createActiveLayout(Integer layoutId, RecycleLayout recycleLayout) {
        Log.d(TAG, "createActiveLayout||recycleViews.size()=" + recycleViews.size() + ",activeViews.size()=" + activeViews.size());

        ArrayList<RecycleLayout> typeLayoutList = activeViews.get(layoutId);
        if (typeLayoutList == null) {
            typeLayoutList = new ArrayList();
            activeViews.put(layoutId, typeLayoutList);
        }
        typeLayoutList.add(recycleLayout);
    }

    @Override
    protected RecycleLayout getRecycleView(Integer layoutId) {
        Log.d(TAG, "getRecycleView||recycleViews.size()=" + recycleViews.size() + ",activeViews.size()=" + activeViews.size());

        ArrayList<RecycleLayout> typeLayoutList = recycleViews.get(layoutId);
        if (typeLayoutList == null || typeLayoutList.size() == 0 || typeLayoutList.get(0) == null) {
            if (typeLayoutList == null) {
                typeLayoutList = new ArrayList();
                recycleViews.put(layoutId, typeLayoutList);
            }
            return null;
        }
        RecycleLayout recycleLayout = recycleViews.get(layoutId).get(0);
        if (recycleLayout != null) {

            recycleViews.get(layoutId).remove(recycleLayout);
            activeViews.get(layoutId).add(recycleLayout);

            switch (recycleLayout.type) {
                case IRecycleManager.ONLY_CHILD:
                    if (recycleLayout.layout.getParent() != null) {
                        ((ViewGroup) recycleLayout.layout.getParent()).removeView(recycleLayout.layout);
                    }
                    break;
                case IRecycleManager.CONTAINER:
                    if (recycleLayout.layout.getParent() != null) {
                        ((ViewGroup) recycleLayout.layout.getParent()).removeView(recycleLayout.layout);
                    }
                    ((ViewGroup) recycleLayout.layout).removeAllViews();
                    break;
            }
        }
        return recycleLayout;
    }

    @Override
    protected void recycle(Integer layoutId, int firstVisiblePosition, int childCount) {//在小于positon大于position+childCount时回收( <position >position+childCount-1)

        ArrayList<RecycleLayout> activeList = activeViews.get(layoutId);
        ArrayList<RecycleLayout> recycledTempList = new ArrayList();
        for (RecycleLayout temp : activeList) {
            if (temp.position < firstVisiblePosition - 2 || temp.position > firstVisiblePosition + childCount + 2) {
                recycledTempList.add(temp);
                recycleViews.get(layoutId).add(temp);
            }
        }
        Log.d(TAG, "recycle||recycledTempList.size()=" + recycledTempList.size());
        activeList.removeAll(recycledTempList);

    }

    /**
     * @param layoutId
     * @param recycleType     RecycleLayout里面
     * @param currentPosition
     * @return
     */
    public View getView(int layoutId, int recycleType, int currentPosition) {
        View view = null;
        RecycleLayout recycleLayout = getRecycleView(layoutId);
        if (recycleLayout == null) {
            try {
                view = layoutInflater.inflate(layoutId, null);
                Log.d(TAG, "实例化View layoutId=" + layoutId);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "layoutInflater 没有初始化");
            }
            createActiveLayout(layoutId, new RecycleLayout(currentPosition, view, recycleType));
        } else {
            recycleLayout.position = currentPosition;
            view = recycleLayout.layout;
            Log.d(TAG, "重用View layoutId=" + layoutId);
        }
        return view;
    }

    @Override
    public void recycle(int[] layoutIds, ListView listView) {
        if (layoutIds == null || layoutIds.length == 0 || listView.getChildCount() == 0) return;
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int childCount = listView.getChildCount();
        Log.d(TAG, "recycle||firstVisiblePosition=" + firstVisiblePosition + ",childCount=" + childCount);

        for (int i = 0; i <= layoutIds.length - 1; i++) {
            recycle(layoutIds[i], firstVisiblePosition, childCount);
        }
    }

}

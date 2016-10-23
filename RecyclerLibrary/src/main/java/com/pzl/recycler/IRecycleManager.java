package com.pzl.recycler;

import android.widget.ListView;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-20]
 */
public interface IRecycleManager {

    /**
     * 创建活跃的布局
     *
     * @param layoutId
     * @param recycleLayout
     */
    void createActiveLayout(Integer layoutId, RecycleLayout recycleLayout);

    RecycleLayout getRecycleView(Integer layoutId);

    /**
     * 回收活跃的布局
     *
     * @param layoutId
     * @param listView
     */
    void recycle(Integer layoutId, ListView listView);

}

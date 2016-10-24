package com.pzl.recycler;

import android.view.View;
import android.widget.ListView;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-20]
 */
public interface IRecycleManager {
    /**
     * 仅仅作为子布局
     */
    int ONLY_CHILD = 1;
    /**
     * 作为容器
     */
    int CONTAINER = 2;


    /**
     * 获得重用的view或创建新的活跃的view
     *
     * @param layoutId
     * @param recycleType
     * @param currentPosition
     * @return
     */
    View getView(int layoutId, int recycleType, int currentPosition);

    /**
     * 回收活跃的布局
     *
     * @param layoutIds
     * @param listView
     */
    void recycle(int[] layoutIds, ListView listView);

}

package com.pzl.recycler;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-24]
 */
public abstract class RecycleManager implements IRecycleManager {

    /**
     * 创建新的活跃布局
     *
     * @param layoutId
     * @param recycleLayout
     */
    abstract protected void createActiveLayout(Integer layoutId, RecycleLayout recycleLayout);

    /**
     * 回收
     *
     * @param layoutId
     * @param firstVisiblePosition
     * @param childCount
     */
    abstract protected void recycle(Integer layoutId, int firstVisiblePosition, int childCount);

    /**
     * @param layoutId
     * @return
     */
    abstract protected RecycleLayout getRecycleView(Integer layoutId);

}

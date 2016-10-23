package com.pzl.recycler;

import android.view.View;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-21]
 */
public class RecycleLayout {
    public static final int ONLY_CHILD = 1;
    public static final int CONTAINER = 2;

    /**
     * 布局类型（1 子布局 ； 2 容器布局）
     */
    public int type;
    public int position;
    public View layout;


    public RecycleLayout(int position, View layout, int type) {
        this.position = position;
        this.layout = layout;
        this.type = type;
    }

}

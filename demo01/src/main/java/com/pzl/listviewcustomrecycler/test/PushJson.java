package com.pzl.listviewcustomrecycler.test;

import java.util.Map;

/**
 * @author zl.peng
 * @version [1.0, 2016-09-27]
 */
public class PushJson {

    public String touser;

    public String template_id;

    public String url;

    public String topcolor;

    public Map<String, Item> data;

    public static class Item {
        public String value;
        public String color;

        @Override
        public String toString() {
            return "Item{" +
                    "value='" + value + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }
}

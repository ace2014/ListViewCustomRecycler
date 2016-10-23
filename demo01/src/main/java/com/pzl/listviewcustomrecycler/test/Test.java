package com.pzl.listviewcustomrecycler.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zl.peng
 * @version [1.0, 2016-09-27]
 */
public class Test {

    public static void main(String args[]) {
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
        for (int i = 0; i <= rootJsonList.size() - 1; i++) {
            RootJson rootJson = rootJsonList.get(i);
            PushJson pushJson = gson.fromJson(rootJson.pushjson, PushJson.class);
            for (String key : pushJson.data.keySet()) {
                System.out.println(key + " = " + pushJson.data.get(key).toString());
                System.out.println("\n\n");
            }
        }
    }
}

package com.palmap.demo.huaweih2.factory;


/**
 * Created by aoc on 2016/6/16.
 */
public class ServereConfig {

    public static final String HOST;

    static {
        HOST = "http://expo.palmap.cn/dataApi/";
    }

    public static final String CECHE_FILE = "/wtmCache";
    public static final long CECHE_SIZE = 30 * 1024 * 1024;

}

package com.palmap.demo.huaweih2.util;

/**
 * Created by 王天明 on 2016/11/15.
 * 访客名字获取
 */
public class VisitorHelper {

    final static String[] names = new String[]{
            "舒凝芙", "李念烟", "李白山", "何从灵", "邓尔芙",
            "李迎蓉", "徐念寒", "张翠绿", "张翠芙", "刘靖儿",
            "张妙柏", "谢千凝", "谢小珍", "李雪枫", "王夏菡",
            "宋元绿", "张痴灵", "刘绮琴", "花雨双", "花听枫",
            "张觅荷", "徐凡之", "郑晓凡", "关雅彤", "杨香薇",
            "刘孤风", "邓从安", "王绮彤", "宋之玉", "马雨珍",
            "赵幻丝", "周代梅", "吴香波", "朱青亦", "许元菱",
            "孔海瑶", "吕飞槐", "金听露", "蓝梦岚", "方幻竹",
            "刘新冬", "张盼翠", "邓谷云", "皮忆霜", "夏水瑶",
            "田烨磊", "支文昊", "孟修洁", "范黎昕", "彭远航",
            "蔡旭尧", "丁鸿涛", "车伟祺", "叶荣轩", "龙越泽",
            "叶浩宇", "刘瑾瑜", "吴皓轩", "江擎苍", "温擎宇"
    };

    public final static String createName(int position) {
        if (position < names.length) {
            position += 3306;
        }
        return names[position % names.length];
    }

    public static void main(String[] args) {
        int pos = 0;
        while (pos < 1000) {
            System.out.println(createName(pos));
            pos++;
        }
    }

}

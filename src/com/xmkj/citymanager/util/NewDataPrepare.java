package com.xmkj.citymanager.util;

import com.avos.avoscloud.AVObject;


public class NewDataPrepare {

    public static void prepare() {
        AVObject testObject = new AVObject("NewData");
        testObject.put("author", "佚名 ");
        testObject.put("date", "2015-3-26");
        testObject.put("title", "宜宾市政府副秘书长李明宽 率城管、交警等部门负责同志到我市考察茶摊治理工作");
        testObject.put("summary", " 11月4日，宜宾市政府副秘书长李明宽 率城管、交警等部门负责同志到我市考察茶摊治理工作，我局杜安坚局长、林健副局长陪同接待。，");
        testObject.put("content", " 11月4日，宜宾市政府副秘书长李明宽 率城管、交警等部门负责同志到我市考察茶摊治理工作，我局杜安坚局长、林健副局长陪同接待。");
        testObject.saveInBackground();
    }
    
}

package com.dev.github;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:36
 */

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:36
 *
 */
public class SubscribeReq implements Serializable {
    private static final long serialVersionUID =1L;
    private String name;
    private int subScribeID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubScribeID() {
        return subScribeID;
    }

    public void setSubScribeID(int subScribeID) {
        this.subScribeID = subScribeID;
    }

    public String toString(){
        return JSONObject.toJSONString(this);
    }
}

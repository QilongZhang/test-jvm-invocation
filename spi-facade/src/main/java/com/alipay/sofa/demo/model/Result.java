/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.sofa.demo.model;

import java.util.Map;

/**
 * @author qilong.zql 19/3/11-下午1:43
 */
public class Result {
    private int i;
    private long l;
    private String s;
    private Map<String, String> m;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Map<String, String> getM() {
        return m;
    }

    public void setM(Map<String, String> m) {
        this.m = m;
    }
}
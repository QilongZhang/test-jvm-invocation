/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.sofa.demo.dynamic.service;

import com.alipay.sofa.demo.model.Result;
import com.alipay.sofa.demo.spi.SampleService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qilong.zql 19/3/11-下午1:51
 */
public class SampleServiceImpl implements SampleService {
    @Override
    public Result service() {
        Result result = new Result();
        Map<String, String> map = new HashMap<>();
        result.setI(10);
        result.setL(100);
        result.setS("dynamic-app");
        map.put("a", "1");
        map.put("b", "2");
        result.setM(map);
        return result;
    }
}
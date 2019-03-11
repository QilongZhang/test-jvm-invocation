/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.sofa.demo.dynamic.configuration;

import com.alipay.sofa.demo.dynamic.service.JobA;
import com.alipay.sofa.demo.dynamic.service.JobB;
import com.alipay.sofa.demo.dynamic.service.SampleServiceImpl;
import com.alipay.sofa.demo.spi.SampleService;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import org.quartz.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qilong.zql 19/3/11-下午2:25
 */
@Configuration
public class DynamicAutoConfiguration {

    @SofaService(bindings = {@SofaServiceBinding(serialize = false)})
    @Bean
    public SampleService sampleService() {
        return new SampleServiceImpl();
    }

    @SofaService(uniqueId = "A", bindings = {@SofaServiceBinding(serialize = false)})
    @Bean
    public Job jobA() {
        return new JobA();
    }

    @SofaService(uniqueId = "B", bindings = {@SofaServiceBinding(serialize = false)})
    @Bean
    public Job jobB() {
        return new JobB();
    }
}
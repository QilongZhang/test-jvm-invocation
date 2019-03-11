/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.sofa.demo.service;

import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author qilong.zql 19/3/11-下午5:01
 */
public class MasterJob  implements Job {
    private Job jobA;
    private Job jobB;
    public static ReferenceClient referenceClient;
    private static int count = 0;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (jobA == null) {
            ReferenceParam<Job> referenceParam = new ReferenceParam();
            referenceParam.setInterfaceType(Job.class);
            referenceParam.setUniqueId("A");
            jobA = referenceClient.reference(referenceParam);
        }
        if (jobB == null) {
            ReferenceParam<Job> referenceParam = new ReferenceParam();
            referenceParam.setInterfaceType(Job.class);
            referenceParam.setUniqueId("B");
            jobB = referenceClient.reference(referenceParam);
        }
        System.out.println("count = " + count + "\n");
        jobA.execute(context);
        jobB.execute(context);
        ++count;
    }
}
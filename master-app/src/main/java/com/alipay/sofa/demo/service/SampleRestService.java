/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.sofa.demo.service;

import com.alipay.sofa.demo.model.Result;
import com.alipay.sofa.demo.spi.SampleService;
import com.alipay.sofa.runtime.api.annotation.SofaClientFactory;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author qilong.zql 19/3/11-下午1:58
 */
@Controller
@RequestMapping("/")
public class SampleRestService {

    @SofaClientFactory
    private ReferenceClient referenceClient;

    @SofaReference
    private SampleService sampleService;

    private Scheduler scheduler;

    @ResponseBody
    @RequestMapping(value="/start-job",method = RequestMethod.GET)
    public String startJob() {
        //创建一个JobDetail实例，将该实例与MyJob Class绑定。
        JobDetail jobDetail= JobBuilder.newJob(MasterJob.class).withIdentity("masterJob", "master").build();
        //创建一个Trigger实例，定义该Job立即执行，并且每隔1秒钟重复一次，
        Trigger trigger= TriggerBuilder.newTrigger().withIdentity("masterTrigger","master")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
        try {
            SchedulerFactory sf=new StdSchedulerFactory();
            scheduler=sf.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Throwable throwable) {
            return "Fail to start job.";
        }
        return "Success to start job. please check console log.";
    }

    @ResponseBody
    @RequestMapping(value="/stop-job",method = RequestMethod.GET)
    public String stopJob() {
        try {
            if (scheduler != null) {
                scheduler.clear();
            }
            scheduler = null;
        } catch (Throwable throwable) {
            return "Fail to stop job.";
        }
        return "Success to stop job.";
    }

    @ResponseBody
    @RequestMapping(value="/sample-service",method = RequestMethod.GET)
    public String sampleService() {
        StringBuilder sb = new StringBuilder();
        Result result = sampleService.service();
        sb.append("result.i: ").append(result.getI()).append("\n");
        sb.append("result.l: ").append(result.getL()).append("\n");
        sb.append("result.s: ").append(result.getS()).append("\n");
        sb.append("result.map.keyA: ").append(result.getM().get("a")).append("\n");
        sb.append("result.map.keyB: ").append(result.getM().get("b")).append("\n");
        return sb.toString();
    }

    public class MasterJob implements Job {
        private Job jobA;
        private Job jobB;

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
            jobA.execute(context);
            jobB.execute(context);
        }
    }

}
本示例简单地演示了在宿主应用中调用动态应用定义的定时任务。

在该示例中，共包含三个模块。
### spi-facade
该模块定义了接口类 SampleService 和一个 POJO 类 Result. 主要用与演示在宿主应用 master-app 和动态引用 dynamic-app 之间的 JVM 服务调用，这里演示的是直接引用调用的 JVM 服务，跳过了序列化。
其次在这个模块中引入了 Quartz 依赖，并打包成了 ark-plugin. 将 SampleService 和 Quartz 相关的类都导出：

```java
    <exported>
        <packages>
            <package>com.alipay.sofa.demo.spi</package>
            <package>com.alipay.sofa.demo.model</package>
            <package>org.quartz.*</package>
            <package>org.terracotta.*</package>
        </packages>
    </exported>
```

之所以导出这些类，是因为我们希望在 SofaService 调用时，不走序列化形式，而是直接引用调用，从而提高效率。

### master-app
宿主应用，定义了三个 endpoint，详细可以参考类：com.alipay.sofa.demo.service.SampleRestService 分别用于演示在宿主应用中引用 dynamic-app 发布的 JVM 服务。在宿主应用中，引入了 dynamic-app 的 biz 包依赖，因此在启动宿主应用时，也会启动 dynamic-app.下面介绍这三个 endpoint
+ sample-service
> 演示宿主引用调用 dynamic-app 发布的 SampleService

+ start-job
> 启动定时任务，定时任务会去引用 dynamic-app 发布的定时任务服务

+ stop-job
> 停止定时任务

### dynamic-app
动态引用，该应用打包成了 biz 包，在宿主应用 master-app 中，被当成 maven 依赖引入，启动宿主应用时，会和宿主应用一起启动。在 dynamic-app 中，发布了三个服务：
+ SampleService 
+ Job (uniqueId="A")
+ Job (uniqueId="B")

以上三个 JVM 服务都会走直接引用的方式，不会进行序列化，因为在发布服务时， 通过 `@SofaService(bindings = {@SofaServiceBinding(serialize = false)})` 指定 `serialize` 为 false.
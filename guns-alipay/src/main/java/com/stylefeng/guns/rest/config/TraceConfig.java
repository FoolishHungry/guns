package com.stylefeng.guns.rest.config;

import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import brave.spring.beans.TracingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * @author : jiangzh
 * @program : com.jiangzh.dubbo.springboot.springboodubbo.dubboprovider1.config
 * @description : trace
 * @create : 2018-11-01 17:41
 **/
@Configuration
public class TraceConfig {

    @Bean
    public TracingFactoryBean getTracingBean(){
        TracingFactoryBean tracingFactoryBean = new TracingFactoryBean();
        tracingFactoryBean.setLocalServiceName("alipay");
        tracingFactoryBean.setSpanReporter(AsyncReporter.create(OkHttpSender.create("http://localhost:9411/api/v2/spans")));

        return tracingFactoryBean;
    }

}

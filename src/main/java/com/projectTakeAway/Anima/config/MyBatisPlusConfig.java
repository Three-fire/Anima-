package com.projectTakeAway.Anima.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@SuppressWarnings("all")
/**
 * @author CodemyMind
 * @Date 2022/5/15 8:36 PM
 * Description：                 配置MP的分页插件
 */


@Configuration
public class MyBatisPlusConfig {

    @Bean
    // Mp的拦截器 ==》添加分页插件PaginationInnerInterceptor
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return mybatisPlusInterceptor;
    }
}

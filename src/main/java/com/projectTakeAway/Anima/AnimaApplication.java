package com.projectTakeAway.Anima;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SuppressWarnings("all")

/**
 * @author 三火
 * @Date 2022/5/11 11:19 PM
 * Description：
 */

@Slf4j
@EnableCaching  //开启Spring Cache注解方式缓存功能
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class AnimaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimaApplication.class, args);
        log.info("项目启动成功...");
    }
}

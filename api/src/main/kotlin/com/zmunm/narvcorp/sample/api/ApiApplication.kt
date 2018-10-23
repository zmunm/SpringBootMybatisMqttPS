package com.zmunm.narvcorp.sample.api

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args){
        setBannerMode(Banner.Mode.OFF)
    }
}

@SpringBootApplication
@EnableAutoConfiguration(exclude = [
    DataSourceTransactionManagerAutoConfiguration::class,
    DataSourceAutoConfiguration::class,
    MybatisAutoConfiguration::class]
)
class ApiApplication

package com.zmunm.narvcorp.sample.api.config

import com.zmunm.narvcorp.sample.core.mysql.dao.*
import com.zmunm.narvcorp.sample.core.mysql.option.DatabaseOption
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.mybatis.spring.SqlSessionFactoryBean
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import javax.sql.DataSource

@Configuration
@Lazy
@EnableTransactionManagement
@MapperScan(basePackages = ["com.zmunm.narvcorp.sample.core.mysql.dao"])
class DatabaseConfig{
    @Bean(destroyMethod = "close")
    fun dataSource(): DataSource  =
            DatabaseOption.Local.configureDataSource(org.apache.tomcat.jdbc.pool.DataSource())

    @Bean
    fun sqlSessionFactory(): SqlSessionFactory? = SqlSessionFactoryBean()
            .apply { setDataSource(dataSource()) }
            .`object`

    private final inline fun <reified T> getDao() = SqlSessionTemplate(sqlSessionFactory())
            .getMapper(T::class.java)

    @Bean
    fun adminDao() = getDao<AdminDAO>()
}

package com.zzw.spring.boot.data.config;

import com.zzw.spring.boot.data.config.properties.DruidDataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Mybatis配置类
 *
 * @author zhaozhiwei
 * @date 2018/10/10 16:24
 */
@Configuration
@MapperScan(basePackages = "com.zzw.spring.boot.data.mapper", sqlSessionFactoryRef = "sessionFactory")
public class MybatisConfig {

    @Resource
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean
    public DataSource dataSource(){
        return druidDataSourceProperties.config();
    }

    @Bean
    @Primary
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:com/zzw/spring/boot/data/mapper/*Mapper.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager masterTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}

package com.zzw.spring.boot.data.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author zhaozhiwei
 * @desc MybatisPlusConfig
 * @date 2018/9/24 下午10:45
 */
@Slf4j
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidDataSourceProperties {
    private String url = "";
    private String username = "";
    private String password = "";
    private String driverClassName = "com.mysql.jdbc.Driver";
    private Integer initialSize = 5;
    private Integer minIdle = 1;
    private Integer maxActive = 200;
    private Integer maxWait = 60000;
    private Integer timeBetweenEvictionRunsMillis = 5000;
    private Integer minEvictableIdleTimeMillis = 300000;
    private String validationQuery = "SELECT 'x'";
    private Boolean testWhileIdle = true;
    private Boolean testOnBorrow = true;
    private Boolean testOnReturn = false;
    private Boolean poolPreparedStatements = true;
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;
    private String filters = "stat";

    public DruidDataSource config() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        //定义初始连接数
        dataSource.setInitialSize(initialSize);
        //最小空闲
        dataSource.setMinIdle(minIdle);
        //定义最大连接数
        dataSource.setMaxActive(maxActive);
        //最长等待时间
        dataSource.setMaxWait(maxWait);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            log.error("配置DruidDataSource异常", e);
        }
        return dataSource;
    }

}

package com.sptwin.apchy.web.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")// 扫描 Mapper 接口并容器管理
public class MasterDataSourceConfig {

    static final String PACKAGE = "com.sptwin.apchy.web.sys.mapper";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/**/*.xml";
    static final String CONFIG_LOCATION = "mybatis/mybatis-config.xml";
    @Autowired
    WallFilter wallFilter;

    @ConfigurationProperties("spring.datasource.master")
    @Primary
    @Bean(name = "masterDataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource =  new DruidDataSource();
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter);
        dataSource.setProxyFilters(filters);
        return dataSource;
    }
    @Bean(name = "masterTransactionManager")
    @Primary
    public PlatformTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory()
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(druidDataSource());
        sessionFactory.setVfs(SpringBootVFS.class);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new ClassPathResource(CONFIG_LOCATION));
        return sessionFactory.getObject();
    }
    @Bean(name = "wallConfig")
    WallConfig wallFilterConfig(){
        WallConfig wc = new WallConfig();
        wc.setMultiStatementAllow(true);
        return wc;
    }

    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    WallFilter wallFilter(WallConfig wallConfig){
        WallFilter wfilter = new WallFilter();
        wfilter.setConfig(wallConfig);
        return wfilter;
    }
    //AOP实现事务配置
    /*@Bean(name = "txAdvice")
    public TransactionInterceptor getAdvisor() {
        Properties properties = new Properties();
        properties.setProperty("save*", "PROPAGATION_REQUIRED");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED");
        properties.setProperty("add*", "PROPAGATION_REQUIRED");
        properties.setProperty("update*", "PROPAGATION_REQUIRED");
        properties.setProperty("edit*", "PROPAGATION_REQUIRED");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED");
        properties.setProperty("batch*", "PROPAGATION_REQUIRED");
        properties.setProperty("find*", "PROPAGATION_SUPPORTS,readOnly");
        properties.setProperty("select*", "PROPAGATION_SUPPORTS,readOnly");
        properties.setProperty("get*", "PROPAGATION_SUPPORTS,readOnly");
        properties.setProperty("query*", "PROPAGATION_SUPPORTS,readOnly");
        TransactionInterceptor tsi = new TransactionInterceptor(masterTransactionManager(),properties);
        return tsi;
    }
    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }*/
}

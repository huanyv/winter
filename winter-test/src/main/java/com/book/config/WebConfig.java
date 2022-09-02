package com.book.config;


import com.mysql.jdbc.Driver;
import top.huanyv.ioc.anno.Bean;
import top.huanyv.ioc.anno.Component;
import top.huanyv.ioc.anno.Configuration;
import top.huanyv.jdbc.core.JdbcConfigurer;
import top.huanyv.jdbc.extend.SimpleDataSource;
import top.huanyv.jdbc.extend.SqlContextFactoryBean;
import top.huanyv.web.config.CorsRegistry;
import top.huanyv.web.config.ResourceMappingRegistry;
import top.huanyv.web.config.ViewControllerRegistry;
import top.huanyv.web.config.WebConfigurer;

@Component
@Configuration
public class WebConfig implements WebConfigurer {

    @Override
    public void addViewController(ViewControllerRegistry registry) {
        registry.add("/", "index");
    }

    @Override
    public void addResourceMapping(ResourceMappingRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            // 设置允许跨域请求的域名
//            .allowedOriginPatterns("*")
//            // 是否允许cookie
//            .allowCredentials(true)
//            // 设置允许的请求方式
//            .allowedMethods("GET", "POST", "DELETE", "PUT")
//            // 设置允许的header属性
//            .allowedHeaders("*")
//            // 跨域允许时间
//            .maxAge(3600L);
        registry.addMapping("/**").defaultRule();
    }

//    @Bean
//    public DataSource dataSource() {
//        SimpleDataSource simpleDataSource = new SimpleDataSource();
//        simpleDataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
//        simpleDataSource.setDriverClassName(Driver.class.getName());
//        simpleDataSource.setUsername("root");
//        simpleDataSource.setPassword("2233");
//        return simpleDataSource;
//    }
//
//    @Bean
//    public MapperScanner mapperScanner() {
//        return new MapperScanner("com.book");
//    }
//
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean() {
//        return new SqlSessionFactoryBean();
//    }

    @Bean
    public SqlContextFactoryBean sqlContextFactoryBean() {
        // 加载配置
        JdbcConfigurer jdbcConfigurer = JdbcConfigurer.create();

        SimpleDataSource simpleDataSource = new SimpleDataSource();
        simpleDataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        simpleDataSource.setDriverClassName(Driver.class.getName());
        simpleDataSource.setUsername("root");
        simpleDataSource.setPassword("2233");

        jdbcConfigurer.setDataSource(simpleDataSource);
        jdbcConfigurer.setScanPackages("com.book");

        return new SqlContextFactoryBean();
    }

}

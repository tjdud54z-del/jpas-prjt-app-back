package com.spring.jpas.global.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(
        basePackages = "com.spring.jpas.domain.**.query.infra",
        sqlSessionFactoryRef = "mybatisSqlSessionFactory"
)
public class MybatisConfig {

    @Bean(name = "mybatisSqlSessionFactory")
    public SqlSessionFactory mybatisSqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // Spring Boot 환경에서 리소스/클래스 스캔 안정화 (특히 jar 패키징 시)
        factoryBean.setVfs(SpringBootVFS.class);

        // Mapper XML 경로 (CQRS Read 전용)
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/query/**/*.xml")
        );

        //  underscore -> camelCase 적용
        org.apache.ibatis.session.Configuration mybatisConf = new org.apache.ibatis.session.Configuration();
        mybatisConf.setMapUnderscoreToCamelCase(true);
        mybatisConf.setCallSettersOnNulls(true); // null 컬럼도 setter 호출(원하면 유지)
        factoryBean.setConfiguration(mybatisConf);

        // alias를 꼭 쓸 때만, 명확한 패키지로만 지정 (와일드카드 ** 비추)
        // factoryBean.setTypeAliasesPackage("com.spring.jpas.domain.employee.query.dto");

        return factoryBean.getObject();
    }

    @Bean(name = "mybatisSqlSessionTemplate")
    public SqlSessionTemplate mybatisSqlSessionTemplate(SqlSessionFactory mybatisSqlSessionFactory) {
        return new SqlSessionTemplate(mybatisSqlSessionFactory);
    }
}
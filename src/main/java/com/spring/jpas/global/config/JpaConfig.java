package com.spring.jpas.global.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.spring.jpas.domain.**.command.infra",   // JPA Repository (Write)
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaTransactionManager"
)
public class JpaConfig {

    /**
     * EntityManagerFactory (Command / Write 전용)
     */
    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(vendorAdapter);

        // JPA Entity 스캔 (command.domain 만!)
        emf.setPackagesToScan(
                "com.spring.jpas.domain.**.command.domain"
        );

        // Hibernate 옵션
        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put(
                "hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
        );

        emf.setJpaProperties(props);

        return emf;
    }

    /**
     * JPA TransactionManager (Write 전용)
     */
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(
            EntityManagerFactory jpaEntityManagerFactory) {

        return new JpaTransactionManager(jpaEntityManagerFactory);
    }
}
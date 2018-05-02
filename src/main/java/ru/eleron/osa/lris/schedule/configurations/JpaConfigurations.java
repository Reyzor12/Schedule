package ru.eleron.osa.lris.schedule.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class JPA configuration
 * @author Reyzor
 * @version 1.0
 * @since 30.04.2018
 * */

@Configuration
@EnableJpaRepositories(basePackages = "ru.eleron.osa.lris.schedule.database", entityManagerFactoryRef = "sessionFactory")
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = {"ru.eleron.osa.lris.schedule"})
public class JpaConfigurations {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSourceConfig.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSourceConfig.setUsername(environment.getProperty("jdbc.username"));
        dataSourceConfig.setPassword(environment.getProperty("jdbc.password"));
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sessionFactory() {
        LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        sessionFactory.setPackagesToScan("ru.eleron.osa.lris.schedule.database.entities");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        sessionFactory.setJpaProperties(jpaProperties);
        return sessionFactory;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory sessionFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(sessionFactory);
        return txManager;
    }
}
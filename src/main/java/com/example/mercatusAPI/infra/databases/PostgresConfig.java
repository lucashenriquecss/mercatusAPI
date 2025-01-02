package com.example.mercatusAPI.infra.databases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaAuditing  
@EnableJpaRepositories(
    basePackages = "com.example.repository.postgres",
    entityManagerFactoryRef = "postgresEntityManager",
    transactionManagerRef = "postgresTransactionManager"
)
public class PostgresConfig {

    @Bean
    @Primary
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("${POSTGRES_URL}")
                .username("${POSTGRES_USERNAME}")
                .password("${POSTGRES_PASSWORD}")
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(
            @Qualifier("postgresDataSource") DataSource dataSource) {
        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSource);
            setPackagesToScan("com.example.entity.postgres");
            setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        }};
    }

    @Bean
    @Primary
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

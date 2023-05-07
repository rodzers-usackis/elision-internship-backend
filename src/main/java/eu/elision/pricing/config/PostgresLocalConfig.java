package eu.elision.pricing.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("developmentPostgres")
public class PostgresLocalConfig {

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql:docreview")
                .username("postgres")
                .password("postgres")
                .build();
    }
}

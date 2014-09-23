package com.pivotal.cities.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Profile("!cloud")
@Configuration
public class LocalDataSourceConfig implements EnvironmentAware {

	   private RelaxedPropertyResolver propertyResolver;

	    private Environment environment;

	    private final Logger log = LoggerFactory.getLogger(LocalDataSourceConfig.class);
	    
	    @Override
	    public void setEnvironment(Environment environment) {
	        this.environment = environment;
	        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
	    }
    
    
    @Bean
    public DataSource dataSource() {
    	
    	 System.out.println("*****************************************************************************************"+propertyResolver.getProperty("url") );
    	
    	
        log.debug("Configuring Datasource");
        if (propertyResolver.getProperty("url") == null && propertyResolver.getProperty("databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(environment.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(propertyResolver.getProperty("dataSourceClassName"));
        if (propertyResolver.getProperty("url") == null || "".equals(propertyResolver.getProperty("url"))) {
            config.addDataSourceProperty("databaseName", propertyResolver.getProperty("databaseName"));
            config.addDataSourceProperty("serverName", propertyResolver.getProperty("serverName"));
        } else {
            config.addDataSourceProperty("url", propertyResolver.getProperty("url"));
        }
        config.addDataSourceProperty("user", propertyResolver.getProperty("username"));
        config.addDataSourceProperty("password", propertyResolver.getProperty("password"));
        
        
        
        log.debug("*****************************************************************************************"+propertyResolver.getProperty("url") );
    	
        return new HikariDataSource(config);
    }
 
}

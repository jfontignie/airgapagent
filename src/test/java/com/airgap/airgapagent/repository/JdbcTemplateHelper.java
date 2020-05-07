package com.airgap.airgapagent.repository;

import org.springframework.batch.test.DataSourceInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/7/2020.
 */
public class JdbcTemplateHelper {

    static JdbcTemplate createJdbcTemplate() {

        Resource[] initScripts = {
                new ClassPathResource("delete-all.sql"),
                new ClassPathResource("schema-all.sql")
        };
        return createJdbcTemplate(initScripts);
    }


    static JdbcTemplate createFullJdbcTemplate() {

        Resource[] initScripts = {
                new ClassPathResource("delete-all.sql"),
                new ClassPathResource("schema-all.sql"),
                new ClassPathResource("test-all.sql")
        };
        return createJdbcTemplate(initScripts);
    }

    private static JdbcTemplate createJdbcTemplate(Resource[] initScripts) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:test.sqlite");
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setInitScripts(
                initScripts
        );
        initializer.afterPropertiesSet();

        return new JdbcTemplate(dataSource);
    }

}

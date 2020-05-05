package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.synchro.SynchroConfiguration;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/27/2020.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final String CONFIG_NAME = "config";

    @Value("org/springframework/batch/core/schema-drop-sqlite.sql")
    private Resource dropRepositoryTables;

    @Value("org/springframework/batch/core/schema-sqlite.sql")
    private Resource springRepositorySchema;

    @Value("schema-all.sql")
    private Resource airgapSchema;

    @Bean
    public DataSourceInitializer initializer(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator =
                new ResourceDatabasePopulator();

        databasePopulator.addScript(dropRepositoryTables);
        databasePopulator.addScript(springRepositorySchema);
        databasePopulator.addScript(airgapSchema);
        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:repository.sqlite");
        return dataSource;
    }

    @Bean
    public SynchroConfiguration config(
            ApplicationArguments applicationArguments) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        if (!applicationArguments.containsOption(CONFIG_NAME)) {
            throw new IllegalStateException("config has not been specified");
        }
        return objectMapper.readValue(new File(applicationArguments.getOptionValues(CONFIG_NAME).get(0)), SynchroConfiguration.class);
    }

    @Bean
    public ItemReader<PathInfo> reader(FolderItemReader folderItemReader, SynchroConfiguration configuration) {
        folderItemReader.setFolder(
                Path.of(configuration.getBaseFolder())
        );
        return folderItemReader;
    }

    @Bean
    public PathInfoProcessor processor(
            SynchroConfiguration configuration
    ) {
        return new PathInfoProcessor(configuration.getFlow());
    }

    @Bean
    protected Step step1(
            StepBuilderFactory stepBuilderFactory,
            ItemReader<PathInfo> reader,
            ItemProcessor<PathInfo, PathInfo> processor) {
        return stepBuilderFactory.get("step")
                .<PathInfo, PathInfo>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(items -> {

                })
                .build();
    }

    @Bean
    public Job job(
            JobBuilderFactory jobBuilderFactory,
            Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}

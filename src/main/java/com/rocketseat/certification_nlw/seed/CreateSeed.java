package com.rocketseat.certification_nlw.seed;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateSeed {
    private final JdbcTemplate jdbcTemplate;

    public CreateSeed(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public static void main(String[] args) {
        DriverManagerDataSource dataSourceDriver = new DriverManagerDataSource();
        dataSourceDriver.setDriverClassName("org.postgresql.Driver");
        dataSourceDriver.setUrl("jdbc:postgresql://localhost:5432/pg_nlw");
        dataSourceDriver.setUsername("postgres");
        dataSourceDriver.setPassword("123");

        CreateSeed createSeed = new CreateSeed(dataSourceDriver);
        createSeed.run(args);
    }

    public void run(String... args){
        executeSqlFile("src/main/resources/create.sql");
    }

    private void executeSqlFile(String filePath) {
        try {
            String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));    

            jdbcTemplate.execute(sqlScript);
            System.out.println("Seed realizado com sucesso");

        } catch (Exception e) {
            System.err.println("Erro ao executar arquivo"+ e.getMessage());
        }
        
    }
}

package account;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@TestConfiguration
@SpringBootTest
@ComponentScan(basePackages = {"com.bank.account.repository", "account"})
public class TestConfigure {
    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:integration_test_db;INIT=CREATE SCHEMA IF NOT EXISTS test_schema;DB_CLOSE_DELAY=-1; DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .build();
    }
}
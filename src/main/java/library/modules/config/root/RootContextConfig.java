package library.modules.config.root;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * The root context configuration of the application - the beans in this context will be globally visible
 * in all servlet contexts.
 *
 */

@Configuration
@ComponentScan({"library.modules.app.services", "library.modules.app.persistence",
        "library.modules.app.init", "library.modules.app.security"})
public class RootContextConfig {

    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(DriverManagerDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}

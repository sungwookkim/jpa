package web.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import web.db.vendor.h2.H2Config;

@Configuration
@EnableTransactionManagement 
public class DBConfig {

	@Autowired H2Config h2Config;
	
	@Bean
    public DataSource dataSource() throws Exception {
    	DataSource dataSource = h2Config.connConfig();

        return dataSource;
    }
    
    /*
	<bean id="dataSource" class="net.sf.log4jdbc.Log4jdbcProxyDataSource">
		<constructor-arg ref="dataSourceSpied" />
	</bean>
     */
    @Bean
    public DataSource log4jdbcDataSource(DataSource datasource) {    	
    	return datasource;
    }

}

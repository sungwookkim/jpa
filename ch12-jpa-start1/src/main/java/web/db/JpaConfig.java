package web.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {

	@Autowired DataSource dataSource;

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(dataSource);
		
		return jpaTransactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		Properties jpaProperties = new Properties();

		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		localContainerEntityManagerFactoryBean.setPackagesToScan("domain");
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		jpaProperties.setProperty("hibernate.show_sql", "false");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		jpaProperties.setProperty("hibernate.use_sql_comments", "true");
		jpaProperties.setProperty("hibernate.id.new_generator_mappings", "true");
/*		jpaProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");*/
		/*jpaProperties.setProperty("hibernate.enhancer.enableDirtyTracking", "true");*/
		/*jpaProperties.setProperty("hibernate.enhancer.enableLazyInitialization", "true");*/
		/*jpaProperties.setProperty("hibernate.enhancer.enableAssociationManagement", "true");*/
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		
	    jpaProperties.setProperty("org.hibernate.SQL", "DEBUG");
	    jpaProperties.setProperty("org.hibernate.tool.hbm2ddl", "DEBUG");
	    jpaProperties.setProperty("org.hibernate.type", "TRACE");
	    jpaProperties.setProperty("org.hibernate.stat", "DEBUG");
	    jpaProperties.setProperty("org.hibernate.type.BasicTypeRegistry", "WARN");
		
		localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);

		return localContainerEntityManagerFactoryBean;
		
	}

}

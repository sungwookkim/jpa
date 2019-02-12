package web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(
	basePackages = {"repository"}
	, entityManagerFactoryRef = "entityManagerFactory"
	, transactionManagerRef = "transactionManager")
@ComponentScan({"domain", "service"})
public class WebcConfig extends WebMvcConfigurationSupport  {

}

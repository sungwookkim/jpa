package web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
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

	/*
	 * 15.1.3 스프링 프레임워크에서 JPA 예외 변환기 적용
	 * JPA 예외를 스프링 프레임워크가 제공하는 추상화된 예외로 변경하려면
	 * PersistenceExceptionTranslationPostProcessor를 스프링 빈으로 등록하면 된다.
	 * 이것은 @Repository 어노테이션을 사용한 곳에서 예외 변환 AOP를 적용해서 JPA 예외를
	 * 스프링 프레임워크가 추상화한 예외로 변환해준다.
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}

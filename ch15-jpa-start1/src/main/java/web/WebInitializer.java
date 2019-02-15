package web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import web.db.DBConfig;
import web.db.JpaConfig;
import web.db.vendor.h2.H2Config;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
			H2Config.class
			, DBConfig.class
			, JpaConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
			WebcConfig.class	// Spring Config 클래스.
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);       
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter)
        	.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");        
        
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        servletContext.addFilter("openEntityManagerInViewFilter", openEntityManagerInViewFilter)
        	.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        
        super.onStartup(servletContext);
	}
}

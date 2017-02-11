package com.infopulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by vedmant on 2/4/17.
 */
@Configuration
public class JSPConfig {

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        System.out.println("getViewResolver");

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

}

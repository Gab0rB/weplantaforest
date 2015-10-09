package org.dicadeveloper.weplantaforest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticDevelopmentContent extends WebMvcConfigurerAdapter {
    @Autowired
    Environment env;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        List<String> activeProfiles = Arrays.asList(env.getProperty("spring.profiles.active").split(","));
        if (activeProfiles.contains("dev")) {
            registry.addViewController("/index.html").setViewName("forward:/index.dev.html");  
        }
    }
}

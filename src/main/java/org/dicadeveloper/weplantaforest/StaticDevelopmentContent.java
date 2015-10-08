package org.dicadeveloper.weplantaforest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticDevelopmentContent extends WebMvcConfigurerAdapter {

    //private Log log = LogFactory.getLog(StaticDevelopmentContent.class);
    
    //@Value("${mode}")
    //private String _mode;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         *  TODO Not needed right now but becomes handy if we can manage auto reload triggered by UI
         *  In this case the "index.dev.html" will have additional script for reloading the content
         */
        // String viewName = _mode == "dev" ? "redirect:/index.dev.html" : "redirect:/index.html";
        // log.info("Main webapp entry point is: " + viewName);
        registry.addViewController("/").setViewName("redirect:/index.html");
    }
}

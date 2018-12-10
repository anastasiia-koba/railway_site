package system.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("system")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setOrder(2);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public ResourceBundleViewResolver resourceBundleViewResolver() {
        ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
        resolver.setOrder(1);
        resolver.setBasename("views");
        return resolver;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties exceptionMappings = new Properties();

        exceptionMappings.put("java.lang.Exception", "errors");
        exceptionMappings.put("java.lang.RuntimeException", "errors");
        exceptionMappings.setProperty(NullPointerException.class.getName(), "errors");
        exceptionMappings.put(".PageNotFound", "errors");

        resolver.setExceptionMappings(exceptionMappings);
        resolver.addStatusCode("/errors.jsp", 404);
        resolver.addStatusCode("/errors.jsp", 500);
        resolver.setDefaultErrorView("/errors.jsp");
        resolver.setDefaultStatusCode(400);

        return resolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}

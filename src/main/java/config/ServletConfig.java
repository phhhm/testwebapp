package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan({"controller", "service"})
public class ServletConfig implements WebMvcConfigurer, ServletContextAware {

    ServletContext servletContext;

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine(ServletContextTemplateResolver servletContextTemplateResolver){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(servletContextTemplateResolver);
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        return thymeleafViewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxInMemorySize(2000000);
        multipartResolver.setMaxUploadSize(4000000);
        FileSystemResource fileSystemResource = new FileSystemResource(System.getProperty("java.io.tmpdir"));
        try {
            multipartResolver.setUploadTempDir(fileSystemResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartResolver;
    }


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/resource/images/" , "file:C:\\Users\\student\\Desktop\\file-server\\");
    }
}

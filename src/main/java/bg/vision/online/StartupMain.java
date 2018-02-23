package bg.vision.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath:/spring/spring-servlet.xml")
class StartupMain {

    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx = SpringApplication.run(StartupMain.class, args);
    	ctx.getBean("viewResolverJSP");
    	System.out.println("load resolver jsp");
    	ctx.start();
    	System.out.println("send out ctx event");
    }
}

@Configuration
class MyConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new DocumentInterceptor()).addPathPatterns("/**");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
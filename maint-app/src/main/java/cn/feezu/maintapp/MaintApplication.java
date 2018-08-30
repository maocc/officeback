package cn.feezu.maintapp;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

import cn.feezu.maint.job.scheduler.SyncService;
import cn.feezu.maintapp.config.ApplicationProperties;
/**
 * 
 * @author zhangfx
 *
 */
@SpringBootApplication(scanBasePackages = { "cn.feezu"})
@EnableAutoConfiguration(/*exclude = { MultipartAutoConfiguration.class }*/)
@MapperScan({"cn.feezu.maint.mapper","cn.feezu.maint.authority.mapper","cn.feezu.maint.assign.mapper","cn.feezu.maint.order.mapper"})
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableAsync
public class MaintApplication extends WebMvcConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(MaintApplication.class);
	
	public static void main(String[] args) throws BeansException, Exception{
		ConfigurableApplicationContext  content = new SpringApplication(MaintApplication.class).run(args);
		 Environment env = content.getEnvironment();
	        String protocol = "http";
	        if (env.getProperty("server.ssl.key-store") != null) {
	            protocol = "https";
	        }
	        log.info("\n----------------------------------------------------------\n\t" +
	                "Application '{}' is running! Access URLs:\n\t" +
	                "Local: \t\t{}://localhost:{}\n\t" +
	                "External: \t{}://{}:{}\n----------------------------------------------------------",
	            env.getProperty("spring.application.name"),
	            protocol,
	            env.getProperty("server.port"),
	            protocol,
	            InetAddress.getLocalHost().getHostAddress(),
	            env.getProperty("server.port"));
	        
	        content.getBean(SyncService.class).buidDeleteUserLoginErrorCount();  
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/ValidationMessages", 
				"messages/messages",
				"messages/form");
		messageSource.setDefaultEncoding("UTF-8");

		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setCacheSeconds(60);
		return messageSource;
	}
	
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {

		FastJsonHttpMessageConverter4 oFastConverter = new FastJsonHttpMessageConverter4();

		FastJsonConfig oFastJsonConfig = new FastJsonConfig();
		oFastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		oFastConverter.setFastJsonConfig(oFastJsonConfig);
		List<MediaType> oFastMediaTypeList = new ArrayList<MediaType>();
		oFastMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
		oFastConverter.setSupportedMediaTypes(oFastMediaTypeList);

		return new HttpMessageConverters(oFastConverter);
	}
}
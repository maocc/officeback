package cn.feezu.maintapp.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import cn.feezu.wzc.common.auth.SecurityUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author zhangfx
 *
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@Profile({"dev", "local"})//在生产环境不开启
public class Swagger2Config {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.feezu.maintapp.web.rest"))//该包下的类将会自动生成文档
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
    }
    private ApiInfo ApiInfo() {
        return new ApiInfoBuilder()
        		.contact(new Contact("zhangfx", "https://git.feezu.cn/maintenance/backoffice.git", "zhangfx@feezu.cn"))
        		.contact(new Contact("changyh", "https://git.feezu.cn/maintenance/backoffice.git", "changyh@feezu.cn"))
                .title("运维端app接口")
                .version("1.0")
                .build();
    }

    /**
     * 扩展头部增加token选项
     * @return
     */
    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(SecurityUtils.HEAD_KEY).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}

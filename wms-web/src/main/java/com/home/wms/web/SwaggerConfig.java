package com.home.wms.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by fitz on 2018/4/16.
 */
@Configurable
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.home.wms.web.api"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("WMS Swagger2")
                //创建人
                .contact(new Contact("fitz", "http://www.edianzu.com", "allen_hou@126.com"))
                //版本号
                .version("1.0.0")
                //描述
                .description("API")
                .build();
    }
}

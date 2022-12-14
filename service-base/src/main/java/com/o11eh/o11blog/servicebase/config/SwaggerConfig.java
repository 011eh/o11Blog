package com.o11eh.o11blog.servicebase.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).
                useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<SecurityScheme> securitySchemes() {
        return new ArrayList<SecurityScheme>(1) {{
            add(new ApiKey("frtoken", "frtoken", "header"));
        }};
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList<SecurityReference>(1) {{
            add(new SecurityReference("frtoken", authorizationScopes));
        }};
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList<SecurityContext>(1) {{
            add(SecurityContext
                    .builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex("^(?!auth).*$"))
                    .build()
            );
        }};
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Blog")
                .description("????????????")
                .version("1.0")
                .build();
    }
}

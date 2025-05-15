package pe.edu.upc.center.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI learningPlatformOpenApi() {
    final String securitySchemeName = "BearerAuth";
    // General configuration
    // var openApi = new OpenAPI();
    return new OpenAPI()
            .info(new Info()
                    .title("Safe Child API")
                    .description("Safe Child application REST API documentation.")
                    .version("v1.0.0")
                    .license(new License().name("Apache 2.0")
                            .url("https://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                    .description(" Learning Platform Documentation")
                    .url("https://github.com/upc-is-si729/daos-language-reference"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                    .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
  }
}

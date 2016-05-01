package sako.fabio.nasa.configuration;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("sako.fabio.nasa")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("mars-discovery").apiInfo(apiInfo()).select()
				.paths(regex("/mars-discovery.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Mars Discovery")
				.description("Mission: Explore Mars")
				.contact("Fábio Sako").version("1.0")
				.build();
	}
}

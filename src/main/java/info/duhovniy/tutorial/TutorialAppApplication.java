package info.duhovniy.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cloud.aws.context.config.annotation.EnableContextCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableContextCredentials(accessKey = "AKIAJJ5QMGMSOPKWZZYA", secretKey = "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")
public class TutorialAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TutorialAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TutorialAppApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		return new GuavaCacheManager("fragments");
	}
}

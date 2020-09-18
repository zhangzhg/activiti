package com.fk;

import com.fk.common.repository.impl.BaseJpaRepositoryImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 去掉activiti启动默认的依赖： spring security
 */
@SpringBootApplication

@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class, basePackages = "com.fk.**.repository")
@MapperScan(basePackages = "com.fk.**.mapper")
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ActivitiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
}

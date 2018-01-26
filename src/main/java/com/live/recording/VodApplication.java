package com.live.recording;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement // 启注解事务管理
//@EnableScheduling
public class VodApplication {

	public static void main(String[] args) {
		SpringApplication.run(VodApplication.class, args);
	}
}

package com.tdh.gps.console.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * @ClassName: Application
 * @Description: (springcloud注册中心服务启动类)
 * @author wxf
 * @date 2018年11月26日 上午11:05:43
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
	static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("console register started successfully");
	}
}

package com.ntd.bank.transactionservice;

import com.ntd.bank.transactionservice.component.ReadPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@SpringBootApplication
@EnablePluginRegistries(ReadPlugin.class)
@EnableFeignClients
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}

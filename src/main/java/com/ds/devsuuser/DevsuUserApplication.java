package com.ds.devsuuser;

import com.ds.devsuuser.infraestructure.utils.ScopeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevsuUserApplication {

	public static void main(String[] args) {
		ScopeUtils.calculateScopeSuffix();
		SpringApplication.run(DevsuUserApplication.class, args);
	}

}

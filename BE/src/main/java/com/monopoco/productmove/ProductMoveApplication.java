package com.monopoco.productmove;

import com.monopoco.productmove.entity.AuditorAwareImpl;
import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entity.Role;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.service.BranchService;
import com.monopoco.productmove.service.ProductService;
import com.monopoco.productmove.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class ProductMoveApplication {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductMoveApplication.class, args);
	}


	@Bean
	CommandLineRunner run(UserService userService, BranchService branchService, ProductService productService) {
		return args -> {
			userService.addNewRole(new Role(null, "ADMIN", null));
			userService.addNewRole(new Role(null, "FACTORY", null));
			userService.addNewRole(new Role(null, "DISTRIBUTOR", null));
			userService.addNewRole(new Role(null, "WARRANTY", null));

			userService.addNewUser(new UserDTO(null, "hungdinh", "123456", null, null, null, null));
			userService.addRoleToUser("hungdinh", "ADMIN");
			branchService.addBranchType(new BranchType(null,"Factory", "FTR", null));
			branchService.addBranchType(new BranchType(null,"Distributor Agent", "DIA", null));
			branchService.addBranchType(new BranchType(null,"Warranty Center", "WAC", null));
			productService.addNewColor("red", "#f51110");
			productService.addNewColor("blue", "#f51120");
			productService.addNewColor("green", "#f51310");
			productService.addNewCapacity(128);
			productService.addNewCapacity(256);
			productService.addNewCapacity(512);
			productService.addNewCapacity(1024);


		};
	}
}

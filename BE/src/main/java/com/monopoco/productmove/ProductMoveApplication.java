package com.monopoco.productmove;

import com.monopoco.productmove.entity.AuditorAwareImpl;
import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entity.Role;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.service.BranchService;
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
	CommandLineRunner run(UserService userService, BranchService branchService) {
		return args -> {
			userService.addNewRole(new Role(null, "ADMIN", null));
			userService.addNewUser(new UserDTO(null, "hungdinh", "123456", null, null));
			userService.addRoleToUser("hungdinh", "ADMIN");
			branchService.addBranchType(new BranchType(null,"Factory", "FTR", null));
			branchService.addBranchType(new BranchType(null,"Distributor Agent", "DIA", null));
			branchService.addBranchType(new BranchType(null,"Warranty Center", "WAC", null));

		};
	}
}

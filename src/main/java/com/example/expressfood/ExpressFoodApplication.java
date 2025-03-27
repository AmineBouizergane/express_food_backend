package com.example.expressfood;

import com.example.expressfood.dao.*;
import com.example.expressfood.entities.*;
import com.example.expressfood.shared.RoleEnum;
import com.example.expressfood.shared.StatusEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ExpressFoodApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExpressFoodApplication.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// Set the maximum file size (e.g., 10MB)
		factory.setMaxFileSize(DataSize.ofMegabytes(10));
		// Set the maximum request size (e.g., 10MB)
		factory.setMaxRequestSize(DataSize.ofMegabytes(10));
		return factory.createMultipartConfig();
	}

	@Bean
	CommandLineRunner start(ProductRepos productRepos,
							CategoryRepos categoryRepos,
							UniteRepos uniteRepos,
							CookRepos cookRepos,
							RoleRepos roleRepos,
							CartRepos cartRepos,
							CartItemsRepos cartItemsRepos,
							StatusRepos statusRepos,
							AdminRepos adminRepos,
							ClientRepos clientRepos){
		return args -> {

			/*Category category =categoryRepos.save(new Category(null, "Entrée", null));

			Unite unite =uniteRepos.save(new Unite(null, "Plat", null));

			Product product =productRepos.save(new Product(null, "Salade Marocain",null,null,LocalDateTime.now(), 40.00, 30.00, unite, category,null, false, true));
			Product product2 =productRepos.save(new Product(null, "Salade Nisoine", null,null,LocalDateTime.now(),45.00, 33.00, unite, category, null, false, true));

			Cook cook = cookRepos.save(
					new Cook(null, "Ayoub", "AitLhimer", new Date(), "0678787878", "Mhamid", null, "ayoub_lhaimer", "12345678", null, true));

			roleRepos.save(new Role(null, RoleEnum.ADMIN.value()));
			roleRepos.save(new Role(null, RoleEnum.USER.value()));
			roleRepos.save(new Role(null, RoleEnum.COOK.value()));
			roleRepos.save(new Role(null, RoleEnum.CLIENT.value()));
			roleRepos.save(new Role(null, RoleEnum.DELIVERY.value()));
*/
			/*Cart cart = cartRepos.findById(Long.valueOf(1))
					.orElseThrow();

			Product product = productRepos.findById(Long.valueOf(1))
					.orElseThrow();

			CartItems cartItems = new CartItems(null, cart, product, 5, 17, 85);
			cartItemsRepos.save(cartItems);*/

			/*statusRepos.save(new Status(null, StatusEnum.PENDING.value(), null));
			statusRepos.save(new Status(null, StatusEnum.IN_PROCESS.value(), null));
			statusRepos.save(new Status(null, StatusEnum.READY_FOR_DISPATCH.value(), null));
			statusRepos.save(new Status(null, StatusEnum.CANCELLED.value(), null));*/

			/*Collection<Role> roles = new ArrayList<>();
			roles.add(roleRepos.findByRoleName(RoleEnum.ADMIN.value()));
			roles.add(roleRepos.findByRoleName(RoleEnum.USER.value()));
			Admin admin = new Admin(null, "Amine", "Bouizergane", new Date(), "0909099090", "Salmia 2", null, "amine_bouizer", bCryptPasswordEncoder().encode(("12345678")), roles, true);

			adminRepos.save(admin);*/
		};
	}
}

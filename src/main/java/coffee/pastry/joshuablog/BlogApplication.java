package coffee.pastry.joshuablog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;

@SpringBootApplication
public class BlogApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner init(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			User song = User.builder()
					.username("song")
					.password(passwordEncoder.encode("1234"))
					.email("song@nate.com")
					.role("USER")
					.profile("person.png")
					.status(true)
					.build();
			userRepository.save(song);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}

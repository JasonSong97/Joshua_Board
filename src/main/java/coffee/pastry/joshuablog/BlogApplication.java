package coffee.pastry.joshuablog;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import coffee.pastry.joshuablog.model.board.Board;
import coffee.pastry.joshuablog.model.board.BoardRepository;
import coffee.pastry.joshuablog.model.user.User;
import coffee.pastry.joshuablog.model.user.UserRepository;

@SpringBootApplication
public class BlogApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner init(UserRepository userRepository, BoardRepository boardRepository,
			BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			User song = User.builder()
					.username("song")
					.password(passwordEncoder.encode("1234"))
					.email("song@nate.com")
					.role("USER")
					.profile("person.png")
					.status(true)
					.build();
			User park = User.builder()
					.username("park")
					.password(passwordEncoder.encode("1234"))
					.email("park@nate.com")
					.role("USER")
					.profile("person.png")
					.build();
			userRepository.saveAll(Arrays.asList(song, park));

			Board b1 = Board.builder()
					.title("제목1")
					.content("내용1")
					.user(song)
					.thumbnail("/upload/person.png")
					.build();
			Board b2 = Board.builder()
					.title("제목2")
					.content("내용2")
					.user(park)
					.thumbnail("/upload/person.png")
					.build();
			boardRepository.saveAll(Arrays.asList(b1, b2));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}

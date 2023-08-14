package coffee.pastry.joshuablog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class BlogApplication extends DummyEntity {

	@Profile("dev")
	@Bean
	CommandLineRunner init(UserRepository userRepository, BoardRepository boardRepository,
			BCryptPasswordEncoder passwordEncoder) {
		return args -> {

			User song = newUser("song", passwordEncoder);
			User park = newUser("park", passwordEncoder);
			userRepository.saveAll(Arrays.asList(song, park));

			List<Board> boardList = new ArrayList<>();
			for (int i = 1; i < 11; i++) {
				boardList.add(newBoard("제목" + i, song));
			}
			for (int i = 11; i < 21; i++) {
				boardList.add(newBoard("제목" + i, park));
			}
			boardRepository.saveAll(boardList);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}

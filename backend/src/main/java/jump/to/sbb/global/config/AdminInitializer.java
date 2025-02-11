package jump.to.sbb.global.config;

import static jump.to.sbb.domain.user.entity.UserRole.*;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (userRepository.findByEmail("admin").isEmpty()) {
			SiteUser admin = new SiteUser(
				"관리자",
				"admin",
				passwordEncoder.encode("1234"),
				Set.of(ADMIN)
			);
			userRepository.save(admin);
		}
	}
}

package jump.to.sbb.domain.auth.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jump.to.sbb.domain.auth.dto.SignupRequest;
import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.entity.UserRole;
import jump.to.sbb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(SignupRequest request) {
		SiteUser user = new SiteUser(
			request.username(),
			request.email(),
			passwordEncoder.encode(request.password()),
			Set.of(UserRole.USER)
		);
		userRepository.save(user);
	}
}

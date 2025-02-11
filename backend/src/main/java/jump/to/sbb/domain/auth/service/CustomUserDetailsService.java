package jump.to.sbb.domain.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jump.to.sbb.domain.auth.dto.CustomUserDetails;
import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.repository.UserRepository;
import jump.to.sbb.global.exception.BizzException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		SiteUser user = userRepository.findByEmail(email)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));

		return new CustomUserDetails(
			user.getId(),
			user.getUsername(),
			user.getPassword(),
			user.getRoles()
		);
	}
}

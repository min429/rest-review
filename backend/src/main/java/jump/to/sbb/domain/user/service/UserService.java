package jump.to.sbb.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jump.to.sbb.domain.user.dto.SiteUserDetails;
import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.repository.UserRepository;
import jump.to.sbb.global.exception.BizzException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public SiteUserDetails getUserDetails(Long userId) {
		SiteUser user = getUser(userId);
		return SiteUserDetails.from(user);
	}

	private SiteUser getUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}
}

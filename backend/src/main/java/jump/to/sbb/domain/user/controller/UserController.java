package jump.to.sbb.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jump.to.sbb.domain.auth.dto.CustomUserDetails;
import jump.to.sbb.domain.user.dto.SiteUserDetails;
import jump.to.sbb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<SiteUserDetails> getUserDetails(@AuthenticationPrincipal CustomUserDetails user) {
		return ResponseEntity.ok(userService.getUserDetails(user.getId()));
	}
}

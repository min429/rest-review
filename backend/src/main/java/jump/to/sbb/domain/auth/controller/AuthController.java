package jump.to.sbb.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jump.to.sbb.domain.auth.dto.LoginRequest;
import jump.to.sbb.domain.auth.dto.SignupRequest;
import jump.to.sbb.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
		authService.create(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		request.getSession().invalidate();
		response.setHeader("Set-Cookie", "JSESSIONID=; Path=/; HttpOnly; Secure; Max-Age=0");

		return ResponseEntity.ok().build();
	}

	@GetMapping("/unauthorized")
	public void unauthorized(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
	}
}

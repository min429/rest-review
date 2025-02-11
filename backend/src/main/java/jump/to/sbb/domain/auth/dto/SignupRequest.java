package jump.to.sbb.domain.auth.dto;

public record SignupRequest(
	String username,
	String email,
	String password
) {
}

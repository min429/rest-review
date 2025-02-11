package jump.to.sbb.domain.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
	@NotEmpty(message = "이메일은 필수항목입니다.")
	String email,

	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	String password
) {
}

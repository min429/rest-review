package jump.to.sbb.domain.post.answer.dto;

import jakarta.validation.constraints.NotEmpty;

public record ModifyAnswerRequest(
	@NotEmpty(message = "내용은 필수항목입니다.")
	String content
) {
}

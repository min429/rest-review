package jump.to.sbb.domain.post.answer.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jump.to.sbb.domain.post.answer.entity.Answer;
import jump.to.sbb.domain.post.question.entity.Question;
import jump.to.sbb.domain.user.entity.SiteUser;

public record CreateAnswerRequest(
	@NotNull(message = "id값은 필수항목입니다.")
	Long questionId,

	@NotEmpty(message = "내용은 필수항목입니다.")
	String content
) {
	public Answer toEntity(Question quesiton, SiteUser user) {
		return Answer.builder()
			.content(content)
			.createDate(LocalDateTime.now())
			.question(quesiton)
			.author(user)
			.build();
	}
}

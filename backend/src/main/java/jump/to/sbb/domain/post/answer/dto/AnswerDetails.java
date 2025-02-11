package jump.to.sbb.domain.post.answer.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import jump.to.sbb.domain.post.answer.entity.Answer;
import jump.to.sbb.domain.user.dto.SiteUserDetails;

public record AnswerDetails(
	Long id,
	String content,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	SiteUserDetails author,
	Set<SiteUserDetails> voters
) {

	public static AnswerDetails from(Answer a) {
		return new AnswerDetails(
			a.getId(),
			a.getContent(),
			a.getCreateDate(),
			a.getModifyDate(),
			SiteUserDetails.from(a.getAuthor()),
			a.getVoter().stream().map(SiteUserDetails::from).collect(Collectors.toSet())
		);
	}

	public Answer toEntity() {
		return Answer.builder()
			.id(id)
			.content(content)
			.createDate(createDate)
			.modifyDate(modifyDate)
			.author(author.toEntity())
			.voter(voters.stream().map(SiteUserDetails::toEntity).collect(Collectors.toSet()))
			.build();
	}
}

package jump.to.sbb.domain.post.question.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jump.to.sbb.domain.post.answer.dto.AnswerDetails;
import jump.to.sbb.domain.post.question.entity.Question;
import jump.to.sbb.domain.user.dto.SiteUserDetails;

public record QuestionDetails(
	Long id,
	String subject,
	String content,
	LocalDateTime createDate,
	LocalDateTime modifyDate,
	List<AnswerDetails> answers,
	SiteUserDetails author,
	Set<SiteUserDetails> voters
) {
	public static QuestionDetails from(Question q) {
		return new QuestionDetails(
			q.getId(),
			q.getSubject(),
			q.getContent(),
			q.getCreateDate(),
			q.getModifyDate(),
			q.getAnswerList().stream().map(AnswerDetails::from).collect(Collectors.toList()),
			SiteUserDetails.from(q.getAuthor()),
			q.getVoter().stream().map(SiteUserDetails::from).collect(Collectors.toSet())
		);
	}

	public Question toEntity() {
		return Question.builder()
			.id(id)
			.subject(subject)
			.content(content)
			.createDate(createDate)
			.modifyDate(modifyDate)
			.answerList(answers.stream().map(AnswerDetails::toEntity).collect(Collectors.toList()))
			.author(author.toEntity())
			.voter(voters.stream().map(SiteUserDetails::toEntity).collect(Collectors.toSet()))
			.build();
	}
}

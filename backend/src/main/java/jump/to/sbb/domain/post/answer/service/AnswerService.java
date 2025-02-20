package jump.to.sbb.domain.post.answer.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jump.to.sbb.domain.post.answer.dto.AnswerDetails;
import jump.to.sbb.domain.post.answer.dto.CreateAnswerRequest;
import jump.to.sbb.domain.post.answer.dto.ModifyAnswerRequest;
import jump.to.sbb.domain.post.answer.entity.Answer;
import jump.to.sbb.domain.post.answer.repository.AnswerRepository;
import jump.to.sbb.domain.post.question.entity.Question;
import jump.to.sbb.domain.post.question.repository.QuestionRepository;
import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.repository.UserRepository;
import jump.to.sbb.global.exception.BizzException;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;

	public AnswerDetails create(CreateAnswerRequest request, Long userId) {
		Question question = getQuestion(request.questionId());
		SiteUser author = getSiteUser(userId);
		Answer answer = request.toEntity(question, author);
		question.add(answer);
		return AnswerDetails.from(answer);
	}

	public void modify(Long id, ModifyAnswerRequest request, Long userId) {
		Answer answer = getAnswer(id);

		if (!answer.isAuthor(userId)) {
			throw new BizzException(HttpStatus.UNAUTHORIZED);
		}

		answer.modifyAnswer(request.content());
	}

	public void delete(Long id, Long userId) {
		Answer answer = getAnswer(id);

		if (!answer.isAuthor(userId)) {
			throw new BizzException(HttpStatus.UNAUTHORIZED);
		}

		answerRepository.delete(answer);
	}

	public void vote(Long id, Long userId) {
		Answer answer = getAnswer(id);
		SiteUser voter = getSiteUser(userId);
		answer.vote(voter);
		answerRepository.save(answer);
	}

	private Question getQuestion(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}

	private SiteUser getSiteUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}

	private Answer getAnswer(Long answerId) {
		return answerRepository.findById(answerId)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}
}

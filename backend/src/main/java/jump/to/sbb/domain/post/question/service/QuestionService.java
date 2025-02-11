package jump.to.sbb.domain.post.question.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jump.to.sbb.domain.post.answer.entity.Answer;
import jump.to.sbb.domain.post.question.dto.ModifyQuestionRequest;
import jump.to.sbb.domain.post.question.dto.QuestionDetails;
import jump.to.sbb.domain.post.question.entity.Question;
import jump.to.sbb.domain.post.question.repository.QuestionRepository;
import jump.to.sbb.domain.user.entity.SiteUser;
import jump.to.sbb.domain.user.repository.UserRepository;
import jump.to.sbb.global.exception.BizzException;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public QuestionDetails getQuestionDetails(Long id) {
		return QuestionDetails.from(getQuestion(id));
	}

	public void create(String subject, String content, Long userId) {
		SiteUser author = getSiteUser(userId);
		questionRepository.save(new Question(subject, content, author));
	}

	@Transactional(readOnly = true)
	public Page<QuestionDetails> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return questionRepository.findAllByKeyword(kw, pageable).map(QuestionDetails::from);
	}

	public void modify(Long id, ModifyQuestionRequest request, Long userId) {
		Question question = getQuestion(id);

		if (!question.isAuthor(userId)) {
			throw new BizzException(HttpStatus.UNAUTHORIZED);
		}

		question.modifyQuestion(request.subject(), request.content());
	}

	public void delete(Long id, Long userId) {
		Question question = getQuestion(id);

		if (!question.isAuthor(userId)) {
			throw new BizzException(HttpStatus.UNAUTHORIZED);
		}

		questionRepository.delete(question);
	}

	public void vote(Long id, Long userId) {
		Question question = getQuestion(id);
		SiteUser voter = getSiteUser(userId);

		question.vote(voter);
		questionRepository.save(question);
	}

	private Specification<Question> search(String kw) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);  // 중복을 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
					cb.like(q.get("content"), "%" + kw + "%"),      // 내용
					cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
					cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
					cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
			}
		};
	}

	private SiteUser getSiteUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}

	private Question getQuestion(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(() -> new BizzException(HttpStatus.NOT_FOUND));
	}
}

package jump.to.sbb.domain.post.question.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jump.to.sbb.domain.post.answer.entity.Answer;
import jump.to.sbb.domain.user.entity.SiteUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answerList;

	@ManyToOne
	private SiteUser author;

	@ManyToMany
	private Set<SiteUser> voter;

	public Question(String subject, String content, SiteUser author) {
		this.subject = subject;
		this.content = content;
		this.author = author;
		this.createDate = LocalDateTime.now();
		this.voter = Set.of();
		this.answerList = List.of();
	}

	public void modifyQuestion(String subject, String content) {
		this.subject = subject;
		this.content = content;
		this.modifyDate = LocalDateTime.now();
	}

	public void add(Answer answer) {
		answerList.add(answer);
	}

	public void vote(SiteUser voter) {
		this.voter.add(voter);
	}

	public boolean isAuthor(Long userId) {
		return author.getId().equals(userId);
	}
}

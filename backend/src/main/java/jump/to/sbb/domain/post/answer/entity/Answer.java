package jump.to.sbb.domain.post.answer.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jump.to.sbb.domain.post.question.entity.Question;
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
public class Answer {

	@ManyToMany
	Set<SiteUser> voter;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "TEXT")
	private String content;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	@ManyToOne
	private Question question;
	@ManyToOne
	private SiteUser author;

	public void modifyAnswer(String content) {
		this.content = content;
		this.modifyDate = LocalDateTime.now();
	}

	public void vote(SiteUser voter) {
		this.voter.add(voter);
	}

	public boolean isAuthor(Long userId) {
		return author.getId().equals(userId);
	}
}

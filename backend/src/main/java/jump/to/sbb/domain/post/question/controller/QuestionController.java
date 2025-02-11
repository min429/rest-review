package jump.to.sbb.domain.post.question.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jump.to.sbb.domain.auth.dto.CustomUserDetails;
import jump.to.sbb.domain.post.question.dto.CreateQuestionRequest;
import jump.to.sbb.domain.post.question.dto.ModifyQuestionRequest;
import jump.to.sbb.domain.post.question.dto.QuestionDetails;
import jump.to.sbb.domain.post.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/question")
@RestController
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;

	@GetMapping("/list")
	public ResponseEntity<Page<QuestionDetails>> list(@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<QuestionDetails> paging = questionService.getList(page, kw);
		return ResponseEntity.ok(paging);
	}

	@GetMapping(value = "/detail/{id}")
	public ResponseEntity<QuestionDetails> detail(@PathVariable("id") Long id) {
		return ResponseEntity.ok(questionService.getQuestionDetails(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Void> create(@RequestBody @Valid CreateQuestionRequest request,
		@AuthenticationPrincipal CustomUserDetails user) {
		questionService.create(request.subject(), request.content(), user.getId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/modify/{id}")
	public ResponseEntity<Void> modify(@RequestBody @Valid ModifyQuestionRequest request,
		@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		questionService.modify(id, request, user.getId());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> questionDelete(@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		questionService.delete(id, user.getId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/vote/{id}")
	public ResponseEntity<Void> questionVote(@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		questionService.vote(id, user.getId());
		return ResponseEntity.ok().build();
	}
}

package jump.to.sbb.domain.post.answer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jump.to.sbb.domain.auth.dto.CustomUserDetails;
import jump.to.sbb.domain.post.answer.dto.CreateAnswerRequest;
import jump.to.sbb.domain.post.answer.dto.ModifyAnswerRequest;
import jump.to.sbb.domain.post.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {

	private final AnswerService answerService;

	@PostMapping("/create")
	public ResponseEntity<Void> createAnswer(@RequestBody @Valid CreateAnswerRequest request,
		@AuthenticationPrincipal CustomUserDetails user) {
		answerService.create(request, user.getId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/modify/{id}")
	public ResponseEntity<Void> answerModify(@RequestBody @Valid ModifyAnswerRequest request,
		@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		answerService.modify(id, request, user.getId());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> answerDelete(@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		answerService.delete(id, user.getId());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/vote/{id}")
	public ResponseEntity<Void> answerVote(@PathVariable("id") Long id,
		@AuthenticationPrincipal CustomUserDetails user) {
		answerService.vote(id, user.getId());
		return ResponseEntity.ok().build();
	}
}

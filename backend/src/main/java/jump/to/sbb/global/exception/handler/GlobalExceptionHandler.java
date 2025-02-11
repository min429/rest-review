package jump.to.sbb.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jump.to.sbb.global.exception.BizzException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {AuthenticationException.class, BizzException.class})
	public <T extends BizzException> ResponseEntity<HttpStatus> handleBizzException(T e) {
		log.error("[BizzException] ", e);
		return from(e);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	public ResponseEntity<String> handleValidationException(Exception e) {
		log.error("[ValidationException] ", e);
		return ResponseEntity.badRequest().body(extractErrorMessage(e));
	}

	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		log.error("[Exception] ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	private <T extends BizzException> ResponseEntity<HttpStatus> from(T e) {
		return ResponseEntity.status(e.getStatus()).body(e.getStatus());
	}

	private String extractErrorMessage(Exception e) {
		if (e instanceof MethodArgumentNotValidException ex) {
			return ex.getBindingResult().getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.findFirst()
				.orElse("잘못된 요청입니다.");
		}
		if (e instanceof ConstraintViolationException ex) {
			return ex.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.findFirst()
				.orElse("잘못된 요청입니다.");
		}
		return "잘못된 요청입니다.";
	}
}

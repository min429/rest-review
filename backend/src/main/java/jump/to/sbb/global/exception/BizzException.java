package jump.to.sbb.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BizzException extends RuntimeException {
	private final HttpStatus status;

	public BizzException(HttpStatus status) {
		super(status.getReasonPhrase());
		this.status = status;
	}
}

package eu.proximagroup.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import eu.proximagroup.accounts.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{

	
	public ResponseEntity<ResponseErrorDto<String>> handlerResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseErrorDto<String>(
						request.getRequestURI(),
						request.getMethod(),
						HttpStatus.NOT_FOUND,
						exception.getMessage()
					)
				);
		
	}
}

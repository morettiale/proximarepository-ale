package eu.proximagroup.cards.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.proximagroup.accounts.constants.CustomerConstants;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

public class GeneralTools {
	
	public static ResponseEntity<ResponseErrorDto<String>> checkId(String pathId,HttpServletRequest request) {
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		return null;
	}
}

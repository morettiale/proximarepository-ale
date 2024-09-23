package eu.proximagroup.loans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

	@Autowired
	private Environment envirorment;
	
	@Value("${build.version}")
	private String buildVersion;
	
	
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(HttpStatus.OK).body("Sta correndo");
	}
	
	@GetMapping("/version")
	public ResponseEntity<String> buildVersion(){
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}
	
	@GetMapping("/env")
	public ResponseEntity<String> env(){
		return ResponseEntity.status(HttpStatus.OK).body(envirorment.getProperty("ambiente"));
	}
}

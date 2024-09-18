package eu.proximagroup.cards.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.proximagroup.cards.dtos.ResponseErrorDto;
import eu.proximagroup.cards.dtos.ResponseSuccessDto;
import eu.proximagroup.cards.models.Card;
import eu.proximagroup.cards.services.CardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@GetMapping
	public ResponseEntity<ResponseSuccessDto<List<Card>>> index() {
		List<Card> cards=this.cardService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<List<Card>>(
					HttpStatus.OK,
					"IMMETTERE MESSAGGIO CONTANT",
					cards
					)
				);
	}
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Card card,BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()) {
			List<String> errorMessages=new ArrayList<String>();
			result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseErrorDto<List<String>>(
							request.getRequestURI(), 
							request.getMethod(), 
							HttpStatus.BAD_REQUEST, 
							errorMessages
						)
					
					);
		}
		
		this.cardService.getById(card.getId());
		
		Card cardSaved=this.cardService.store(card);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ResponseSuccessDto<String>(
						HttpStatus.CREATED, 
						"INSERIRE UNA COSTANTE", 
						cardSaved
					)
				);
	}
	
}

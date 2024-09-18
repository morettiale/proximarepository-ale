package eu.proximagroup.cards.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.proximagroup.cards.dtos.ResponseSuccessDto;
import eu.proximagroup.cards.models.Card;
import eu.proximagroup.cards.services.CardService;

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
	
	
}

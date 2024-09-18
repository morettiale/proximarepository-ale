package eu.proximagroup.cards.services;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.proximagroup.cards.exceptions.ResourceNotFoundException;
import eu.proximagroup.cards.models.Card;
import eu.proximagroup.cards.repositories.CardRepository;

@Service
public class CardService {

	private CardRepository cardRepository;
	
	public CardService(CardRepository cardRepository){
		this.cardRepository = cardRepository;
	}
	
	public List<Card> getAll(){
		return this.cardRepository.findAll();
	}
	
	public Card getById(Long id) {
		Card card=this.cardRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Card", "id", id.toString()));
		return card;
	}
	
	public Card store(Card card) {
		return this.cardRepository.save(card);
	}
	
}

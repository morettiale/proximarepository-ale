package eu.proximagroup.services;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.proximagroup.models.Loan;
import eu.proximagroup.repositories.LoanRepository;

@Service
public class LoanService {

	private LoanRepository loanRepository;
	
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository=loanRepository;
	}
	
	public List<Loan> getAll(){
		return loanRepository.findAll();
	}
}

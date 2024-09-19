package eu.proximagroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import eu.proximagroup.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}

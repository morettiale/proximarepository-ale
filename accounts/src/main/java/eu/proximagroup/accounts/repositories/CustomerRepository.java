package eu.proximagroup.accounts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.accounts.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmailOrMobileNumber(String email, String mobileNumber);
}

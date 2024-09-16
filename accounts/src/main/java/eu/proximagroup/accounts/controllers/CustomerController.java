package eu.proximagroup.accounts.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.proximagroup.accounts.constants.CustomerConstants;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import eu.proximagroup.accounts.dto.ResponseSuccessDto;
import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.services.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private EntityManager entityManager;
	private CustomerService customerService;
	
	public CustomerController(
		CustomerService customerService, 
		EntityManager entityManager
	)
	{
		this.customerService = customerService;
		this.entityManager = entityManager;
	}
	
	@GetMapping
	public ResponseEntity<ResponseSuccessDto<List<Customer>>> index()
	{
		List<Customer> customers = this.customerService.getAll();

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<List<Customer>>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				customers
			)
		);
	}
	
	@GetMapping("/{pathId}")
	public ResponseEntity<?> show(@PathVariable String pathId, HttpServletRequest request)
	{
				
		// Validazione che l'ID sia un numero valido
        if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body("Invalid ID: must be a numeric value.");
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
		
		
		Optional<Customer> customerOptional = this.customerService.getById(id);
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		CustomerConstants.MESSAGE_404
            	)
            );
		}
		
		return ResponseEntity.ok(customerOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Customer customer, BindingResult result, HttpServletRequest request)
	{
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
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
		
		Customer customerInserted = this.customerService.store(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_201,
				customerInserted
			)
		);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Customer customer, BindingResult result, HttpServletRequest request)
	{
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
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
		
		Optional<Customer> customerOptional = this.customerService.getById(id);
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		CustomerConstants.MESSAGE_404
            	)
            );
		}
				
		this.customerService.update(customer, id);
		
		this.entityManager.clear();
		
		Optional<Customer> customerUpdated = this.customerService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(customerUpdated.get());
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateCustomerFields(@PathVariable Long id, @Valid @RequestBody Map<String, String> updateFields, HttpServletRequest request)
	{
		
		List<String> allowedFields = Arrays.asList("firstName", "email");
		
		// Verifica che i campi ricevuti siano validi
        for (String field : updateFields.keySet()) {
            if (!allowedFields.contains(field)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            		new ResponseErrorDto<String>(
                		request.getRequestURI(),
                		request.getMethod(),
                		HttpStatus.BAD_REQUEST,
                		"Field " + field + " not allowed"
                	)
                );
            }
        }
		
		Optional<Customer> customerOptional = this.customerService.getById(id);
		
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		CustomerConstants.MESSAGE_404
            	)
            );
		}
		
		if (updateFields.containsKey("firstName"))
		{
			customerOptional.get().setFirstName(updateFields.get("firstName"));
		}
		
		if (updateFields.containsKey("email"))
		{
			customerOptional.get().setEmail(updateFields.get("email"));
		}
				
		this.customerService.update(customerOptional.get(), id);
		
		this.entityManager.clear();
		
		Optional<Customer> customerUpdated = this.customerService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(customerUpdated.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> destroy(@PathVariable Long id, HttpServletRequest request)
	{
		Optional<Customer> customerOptional = this.customerService.getById(id);
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		CustomerConstants.MESSAGE_404
            	)
            );
		}

		this.customerService.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
			new ResponseSuccessDto<String>(
				HttpStatus.NO_CONTENT,
				CustomerConstants.MESSAGE_204,
				""
			)
		);
	}
}

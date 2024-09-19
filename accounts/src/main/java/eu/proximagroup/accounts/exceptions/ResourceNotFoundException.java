package eu.proximagroup.accounts.exceptions;

//utilizzo runtime perché così creo un'eccezione che genera errore nel momento in cui lancio il programma
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
 
}

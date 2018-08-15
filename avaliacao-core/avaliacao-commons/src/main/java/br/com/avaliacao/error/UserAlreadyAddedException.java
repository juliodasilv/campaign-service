package br.com.avaliacao.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Julio
 */
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class UserAlreadyAddedException extends RuntimeException{

	private static final long serialVersionUID = 1317501303980207462L;

	private String campaings;
	
	public UserAlreadyAddedException(String message, String campaings) {
		super(message);
		this.campaings = campaings;
	}

	public String getCampaings() {
		return campaings;
	}

	public void setCampaings(String campaings) {
		this.campaings = campaings;
	}
}
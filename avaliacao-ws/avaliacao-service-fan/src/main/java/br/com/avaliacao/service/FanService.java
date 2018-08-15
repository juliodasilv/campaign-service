package br.com.avaliacao.service;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.avaliacao.error.ResourceNotFoundException;
import br.com.avaliacao.error.UserAlreadyAddedException;
import br.com.avaliacao.model.Campaign;
import br.com.avaliacao.model.Team;
import br.com.avaliacao.model.User;
import br.com.avaliacao.repository.TeamRepository;
import br.com.avaliacao.repository.UserRepository;

@Service
public class FanService {

	@Autowired
	private UserRepository userDAO;

	@Autowired
	private TeamRepository teamDAO;

	public User saveUser(User user) {
		//Verifica se o usuario ja existe pelo email
		if(userDAO.findByEmail(user.getEmail()) != null){
			RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<List<Campaign>> response = restTemplate.exchange("http://localhost:8081/v1/campaigns", HttpMethod.GET, new HttpEntity<List<Campaign>>(createHeaders("admin", "admin")), new ParameterizedTypeReference<List<Campaign>>(){
	        });
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	//Set pretty printing of json
	    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        
			try {
				throw new UserAlreadyAddedException("User already added!", objectMapper.writeValueAsString(response.getBody()));
			} catch (JsonProcessingException e) {
				throw new UserAlreadyAddedException("User already added!", e.getMessage());
			}
		}
		
		//Verifica se o time existe, caso contrario lanca excecao
		Team team = teamDAO.findByNameIgnoreCase(user.getTeam().getName());
		if(team==null){
			throw new ResourceNotFoundException("Team not found!");
		}else
			user.setTeam(team);
		
		//persiste o usuario
		user = userDAO.save(user);
		return user; 
	}
	
	@SuppressWarnings("serial")
	private static HttpHeaders createHeaders(String username, String password){
		   return new HttpHeaders() {{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.encodeBase64( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		}
}

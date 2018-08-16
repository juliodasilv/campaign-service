package br.com.avaliacao.client;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.avaliacao.dto.CampaignDTO;

@Component
public class CampaignClient {
	
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

	public List<CampaignDTO> findAllCampaignsByIdUser(Long IdCustomer) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<CampaignDTO>> response = restTemplate.exchange("http://localhost:8081/v1/campaigns/customer/" + IdCustomer, HttpMethod.GET, new HttpEntity<List<CampaignDTO>>(createHeaders("admin", "admin")), new ParameterizedTypeReference<List<CampaignDTO>>(){
        });
		return response.getBody();
	}

	public void addCampaign(Long customerId, CampaignDTO campaignDTO) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = createHeaders("admin", "admin");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampaignDTO> entity = new HttpEntity<CampaignDTO>(campaignDTO,headers);
		ResponseEntity<CampaignDTO> out = restTemplate.exchange("http://localhost:8081/v1/campaigns/" + customerId, HttpMethod.POST, entity, CampaignDTO.class);
		System.out.println(out.getBody());
	}
}
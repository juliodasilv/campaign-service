package br.com.avaliacao.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import br.com.avaliacao.client.CampaignClient;
import br.com.avaliacao.dto.CampaignDTO;
import br.com.avaliacao.error.ResourceNotFoundException;
import br.com.avaliacao.model.Customer;
import br.com.avaliacao.model.Team;
import br.com.avaliacao.repository.CustomerRepository;
import br.com.avaliacao.repository.TeamRepository;

@Service
public class FanService {

	@Autowired
	private CustomerRepository customerDAO;

	@Autowired
	private TeamRepository teamDAO;

	@Autowired
	private CampaignClient client;

	public Customer saveCustomer(Customer customer) {
		// Verifica se o usuario ja existe pelo email
		if (customerDAO.findByEmail(customer.getEmail()) != null) {
			// Retorna todas as campanhas associadas ao cliente
			List<CampaignDTO> campaigns = client.findAllCampaignsByIdUser(customer.getId());
			if (!campaigns.isEmpty()) {
				customer.setCampaigns(Lists.newArrayList());
			}
		}

		// Verifica se o time existe, caso contrario lanca excecao
		Team team = teamDAO.findByNameIgnoreCase(customer.getTeam().getName());
		if (team == null) {
			throw new ResourceNotFoundException("Team not found!");
		} else
			customer.setTeam(team);

		// persiste o usuario
		customer = customerDAO.save(customer);
		Long customerId = customer.getId();
		
		// adiciona as campanhas do cliente
		try {
			customer.getCampaigns().forEach(campaign -> {
				campaign.setIdTeam(team.getId());
				client.addCampaign(customerId, campaign);	
			});
		} catch (ResourceAccessException e) {
			// se o servico de campainhas estiver fora do ar não faz nada
		}
		return customer;
	}
}
package br.com.avaliacao.service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.avaliacao.model.Campaign;
import br.com.avaliacao.repository.CampaignRepository;

@Service
public class CampaignService {

	@Autowired
	private CampaignRepository campaignDAO;

	/**
	 * O Sistema apenas retorna campanhas que estão com a data de vigência
	 * validas;
	 * 
	 * @param pageable
	 * @return
	 */
	public List<Campaign> findAllCampaign(Pageable pageable) {
		return campaignDAO.findAllByEndGreaterThanEqual(Calendar.getInstance());
	}

	public Campaign save(Campaign campaign) {
		// No cadastramento de uma nova campanha, deve-se verificar se já existe
		// uma campanha ativa para aquele período (vigência), caso exista uma
		// campanha ou N campanhas associadas naquele período, o sistema deverá
		// somar um dia no término da vigência de cada campanha já existente.
		// Caso a data final da vigência seja igual a outra campanha, deverá ser
		// acrescido um dia a mais de forma que as campanhas não tenham a mesma
		// data de término de vigência. Por fim, efetuar o cadastramento da nova
		// campanha:
		List<Campaign> activeCampaigns = campaignDAO
				.findAllByEndGreaterThanEqualAndEndLessThanEqual(campaign.getStart(), campaign.getEnd());

		activeCampaigns.sort(Comparator.comparing(Campaign::getName).reversed());
		
		if (!activeCampaigns.isEmpty()){
			activeCampaigns.add(campaign);
			activeCampaigns.forEach( c -> {
				if(c.getName().equals(campaign.getName()))
					return;
				Set<Calendar> dates = activeCampaigns.stream().map(Campaign::getEnd).collect(Collectors.toSet());
				while(dates.contains(c.getEnd())){
					c.plusDayToEndDate();
					campaignDAO.save(c);
				}
			});
		}

		return campaignDAO.save(campaign);
	}

	public Campaign findOne(Long id) {
		return campaignDAO.findOne(id);
	}

	public void delete(Long id) {
		campaignDAO.delete(id);
	}

	public List<Campaign> findByNameIgnoreCaseContaining(String name) {
		return campaignDAO.findByNameIgnoreCaseContaining(name);
	}
}

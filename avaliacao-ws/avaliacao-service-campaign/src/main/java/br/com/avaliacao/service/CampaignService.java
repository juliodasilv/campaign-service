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

	/**
	 * No cadastramento de uma nova campanha, deve-se verificar se já existe
	 * uma campanha ativa para aquele período (vigência), caso exista uma
	 * campanha ou N campanhas associadas naquele período, o sistema deverá
	 * somar um dia no término da vigência de cada campanha já existente.
	 * Caso a data final da vigência seja igual a outra campanha, deverá ser
	 * acrescido um dia a mais de forma que as campanhas não tenham a mesma
	 * data de término de vigência. Por fim, efetuar o cadastramento da nova campanha
	 * @param idCustomer 
	 * 
	 * @param campaign
	 * @return
	 */
	public Campaign save(Long idCustomer, Campaign campaign) {
		//busca todas as campanhas dentro da vigencia a ser cadastrada
		List<Campaign> activeCampaigns = campaignDAO.findAllByEndGreaterThanEqualAndEndLessThanEqual(campaign.getStart(), campaign.getEnd());
		//inverte a ordem da lista de campanhas
		activeCampaigns.sort(Comparator.comparing(Campaign::getName).reversed());
		
		String campaignName = campaign.getName();
		if (!activeCampaigns.isEmpty()){
			//adiciona a campanha atual na lista apenas para verificação de data final
			activeCampaigns.add(campaign);
			activeCampaigns.forEach( c -> {
				//se a campanha for o item a ser adicionado passa para a proxima
				if(c.getName().equals(campaignName))
					return;
				//Quebra a lista de campanhas em uma colecao de data final
				Set<Calendar> dates = activeCampaigns.stream().map(Campaign::getEnd).collect(Collectors.toSet());
				//se a data final da campanha ja existir, adiciona um dia e atualiza a campanha alterada na base
				while(dates.contains(c.getEnd())){
					c.plusDayToEndDate();
					campaignDAO.save(c);
				}
			});
		}

		//persiste a campanha atual
		campaign.setIdCustomer(idCustomer);
		campaign = campaignDAO.save(campaign);
		
		return campaign;
	}

	public Campaign findOne(Long id) {
		return campaignDAO.findOne(id);
	}

	public void delete(Long id) {
		campaignDAO.delete(id);
	}

	public Campaign findByNameIgnoreCaseContaining(String name) {
		return campaignDAO.findByNameIgnoreCaseContaining(name);
	}

	public List<Campaign> findAllCampaignByIdCustomer(Long idCustomer) {
		return campaignDAO.findAllByIdCustomer(idCustomer);
	}
}
package br.com.avaliacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.avaliacao.model.Campaign;
import br.com.avaliacao.repository.CampaignRepository;

@Service
public class CampaignService {

	@Autowired
	private CampaignRepository campaignDAO;

	public List<Campaign> findAllCampaign(Pageable pageable) {
		return campaignDAO.findAll();
	}

	public Campaign save(Campaign campaign) {
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

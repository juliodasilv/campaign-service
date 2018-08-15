package br.com.avaliacao.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{
	List<Campaign> findByNameIgnoreCaseContaining(String name);
	
	List<Campaign> findAllByEndGreaterThanEqual(Calendar currentDate);

	List<Campaign> findAllByEndGreaterThanEqualAndEndLessThanEqual(Calendar start, Calendar end);
}
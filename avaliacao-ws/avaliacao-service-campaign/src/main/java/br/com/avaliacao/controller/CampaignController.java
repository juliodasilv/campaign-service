package br.com.avaliacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avaliacao.error.ResourceNotFoundException;
import br.com.avaliacao.model.Campaign;
import br.com.avaliacao.service.CampaignService;

@RestController
@RequestMapping("v1")
public class CampaignController {

	@Autowired
	private CampaignService campaignService;
	
	@GetMapping(path="campaigns", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> listAll(Pageable pageable){
		return new ResponseEntity<>(campaignService.findAllCampaign(pageable), HttpStatus.OK);
	}

	@GetMapping(path="campaigns/customer/{idcustomer}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> listAll(@PathVariable("idcustomer") Long idCustomer){
		return new ResponseEntity<>(campaignService.findAllCampaignByIdCustomer(idCustomer), HttpStatus.OK);
	}

	@PostMapping(path="campaigns/{idCustomer}", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> save(@PathVariable("idCustomer") Long idCustomer, @Valid @RequestBody Campaign campaign){
		return new ResponseEntity<>(campaignService.save(idCustomer, campaign), HttpStatus.CREATED);
	}

	@DeleteMapping(path="admin/campaigns/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		verifyIfCampaignExists(campaignService.findOne(id), id);
		campaignService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path="campaigns", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@PathVariable("idCustomer") Long idCustomer, @RequestBody Campaign campaign){
		verifyIfCampaignExists(campaignService.findOne(campaign.getId()), campaign.getId());
		campaignService.save(idCustomer, campaign);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path="campaigns/findByName/{name}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		return new ResponseEntity<>(campaignService.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}

	private Campaign verifyIfCampaignExists(Campaign campaign, Long id) {
		if(campaign == null)
			throw new ResourceNotFoundException("Campaign not found for ID: " + id);
		return campaign;
	}
}
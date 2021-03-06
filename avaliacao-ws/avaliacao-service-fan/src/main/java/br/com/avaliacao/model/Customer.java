package br.com.avaliacao.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;

import br.com.avaliacao.CalendarSerializer;
import br.com.avaliacao.dto.CampaignDTO;

@Entity
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 5215661112014554477L;

	@NotEmpty
	@Column(unique = true)
	private String name;

	@NotEmpty
	@Email
	private String email;

	@NotNull
	@JsonSerialize(using = CalendarSerializer.class)
	@JsonDeserialize(using = CalendarDeserializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar birth;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team")
    private Team team;

	@Transient
	private List<CampaignDTO> campaigns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getBirth() {
		return birth;
	}

	public void setBirth(Calendar birth) {
		this.birth = birth;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<CampaignDTO> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<CampaignDTO> campaigns) {
		this.campaigns = campaigns;
	}
	
	
}
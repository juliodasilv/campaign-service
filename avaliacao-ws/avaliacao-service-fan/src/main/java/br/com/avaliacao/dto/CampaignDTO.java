package br.com.avaliacao.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;

import br.com.avaliacao.CalendarSerializer;

public class CampaignDTO{

	@JsonIgnore
	private Long id;

	private String name;

	private Long idTeam;

	@JsonSerialize(using = CalendarSerializer.class)
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar start;
	
	@JsonSerialize(using = CalendarSerializer.class)
	@JsonDeserialize(using = CalendarDeserializer.class)
	private Calendar end;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public Long getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(Long idTeam) {
		this.idTeam = idTeam;
	}

	public void plusDayToEndDate() {
		end.add(Calendar.DATE, 1);
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "Campaign [name=" + name + ", idTeam=" + idTeam + ", start=" + sdf.format(start.getTime()) + ", end=" + sdf.format(end.getTime()) + "]";
	}
	
}
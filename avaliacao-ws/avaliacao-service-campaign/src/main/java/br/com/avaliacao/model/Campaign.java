package br.com.avaliacao.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;

@Entity
@JsonPropertyOrder({ "name", "idTeam", "startDate", "endDate"})
public class Campaign extends AbstractEntity{

	private static final long serialVersionUID = -645790858764812308L;

	@NotEmpty
	@Column(unique = true)
	private String name;

	private int idTeam;

	@NotNull
	@JsonSerialize(using = CalendarSerializer.class)
	@JsonDeserialize(using = CalendarDeserializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar start;
	
	@NotNull
	@JsonDeserialize(using = CalendarDeserializer.class)
	@JsonSerialize(using = CalendarSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar end;

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

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
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
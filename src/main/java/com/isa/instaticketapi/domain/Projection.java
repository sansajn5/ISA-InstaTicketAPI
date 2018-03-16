package com.isa.instaticketapi.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Milica Kovacevic Model for projection
 */

@Entity
@Table(name = "Projection")
public class Projection extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 5, max = 5)
	@Column(name = "startTime", length = 5)
	private String startTime;

	@NotNull
	@Column(name = "endTime", length = 5)
	private String endTime;

	@Column(name = "date", length = 10)
	private String date;

	@ManyToOne(fetch = FetchType.EAGER)
	private Hall hall;

	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;

	@ManyToOne(fetch = FetchType.EAGER)
	private Repertory reperotry;

	public Repertory getReperotry() {
		return reperotry;
	}

	public void setReperotry(Repertory reperotry) {
		this.reperotry = reperotry;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

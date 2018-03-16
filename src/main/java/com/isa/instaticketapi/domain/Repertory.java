package com.isa.instaticketapi.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity od repertory.
 * 
 * @author Milica Kovacevic
 *
 */
@Entity
@Table(name = "Reperotry")
public class Repertory extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date", length = 10, unique = true)
	private String date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

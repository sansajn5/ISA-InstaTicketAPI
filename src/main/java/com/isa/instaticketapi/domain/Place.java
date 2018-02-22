package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.isa.instaticketapi.config.Constants;

/**
 * Model for placas
 * 
 * @author Milica Kovacevic
 *
 */
@Entity
@Table(name = "Place")
public class Place extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name",length = 50, unique = true)
	private String name;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "address",length = 50, unique = true)
	private String address;

	@Size(min = 1, max = 5000)
	@Column(name = "descripton",length = 5000)
	private String descripton;
	
	@Size(min = 1, max = 20)
	@Column(name = "type",length = 20)
	private String type;
	

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", address=" + address + ", descripton=" + descripton + "]";
	}

}

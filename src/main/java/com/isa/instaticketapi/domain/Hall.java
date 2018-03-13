package com.isa.instaticketapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Milica Kovacevic Entity od Hall.
 *
 */
@Entity
@Table(name = "Hall")
public class Hall {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name", length = 50)
	private String name;

	@NotNull
	@Size(min = 1, max = 3)
	@Column(name = "column", length = 3)
	private int column;

	@NotNull
	@Size(min = 1, max = 3)
	@Column(name = "row", length = 3)
	private int row;

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

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

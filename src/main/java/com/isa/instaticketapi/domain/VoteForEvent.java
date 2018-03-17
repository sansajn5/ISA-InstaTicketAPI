package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model for vote for event
 * 
 * @author Milica Kovacevic
 *
 */
@Entity
@Table(name = "Vote for place")
public class VoteForEvent extends AbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
}

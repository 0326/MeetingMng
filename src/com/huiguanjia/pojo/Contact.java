package com.huiguanjia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Contact entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "contact", catalog = "meetingmngdatabase")
public class Contact implements java.io.Serializable {

	// Fields

	private Integer id;
	private OrdinaryUser cellphone;
	private OrdinaryUser contactCellphone;

	// Constructors

	/** default constructor */
	public Contact() {
	}

	/** full constructor */
	public Contact(OrdinaryUser cellphone,
			OrdinaryUser contactCellphone) {
		this.cellphone = cellphone;
		this.contactCellphone = contactCellphone;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cellphone", nullable = false)
	public OrdinaryUser getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(OrdinaryUser cellphone) {
		this.cellphone = cellphone;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contactCellphone", nullable = false)
	public OrdinaryUser getContactCellphone() {
		return this.contactCellphone;
	}

	public void setContactCellphone(OrdinaryUser contactCellphone) {
		this.contactCellphone = contactCellphone;
	}

}
package com.huiguanjia.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ProvinceAndCity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "province_and_city", catalog = "meetingmngdatabase")
public class ProvinceAndCity implements java.io.Serializable {

	// Fields

	private String cityCode;
	private String cityName;
	private String provinceName;
	

	// Constructors

	/** default constructor */
	public ProvinceAndCity() {
	}

	/** minimal constructor */
	public ProvinceAndCity(String cityCode, String cityName, String provinceName) {
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.provinceName = provinceName;
	}

	

	// Property accessors
	@Id
	@Column(name = "cityCode", unique = true, nullable = false, length = 9)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "cityName", nullable = false, length = 20)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "provinceName", nullable = false, length = 15)
	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	

}

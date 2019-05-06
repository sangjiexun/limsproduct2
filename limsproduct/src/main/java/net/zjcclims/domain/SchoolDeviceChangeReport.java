package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolDeviceChangeReports", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberAdd", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberAdd = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberLast", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberLast = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberReduce", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberReduce = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberThis", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberThis = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberValueLast", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberValueLast = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDeviceNumberValueThis", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.deviceNumberValueThis = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceAdd", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceAdd = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceLast", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceLast = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceReduce", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceReduce = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceThis", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceThis = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceValueLast", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceValueLast = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByDevicePriceValueThis", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.devicePriceValueThis = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportById", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.id = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByPrimaryKey", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.id = ?1"),
		@NamedQuery(name = "findSchoolDeviceChangeReportByYearCode", query = "select mySchoolDeviceChangeReport from SchoolDeviceChangeReport mySchoolDeviceChangeReport where mySchoolDeviceChangeReport.yearCode = ?1") })
@Table(name = "school_device_change_report")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/dao/domain", name = "SchoolDeviceChangeReport")
public class SchoolDeviceChangeReport implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ��ѧ��ĩ�豸ʵ����
	 * 
	 */

	@Column(name = "device_number_last")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberLast;
	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */

	@Column(name = "device_price_last", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceLast;
	/**
	 * ��ѧ��ĩ����10��Ԫ���豸����
	 * 
	 */

	@Column(name = "device_number_value_last")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberValueLast;
	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�۸�
	 * 
	 */

	@Column(name = "device_price_value_last", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceValueLast;
	/**
	 * ��ѧ�����ӵ��豸����
	 * 
	 */

	@Column(name = "device_number_add")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberAdd;
	/**
	 * ��ѧ�����ӵ��豸�ļ۸�
	 * 
	 */

	@Column(name = "device_price_add", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceAdd;
	/**
	 * ��ѧ�걨�ϵ��豸����
	 * 
	 */

	@Column(name = "device_number_reduce")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberReduce;
	/**
	 * ��ѧ�걨�ϵ��豸�ļ۸�
	 * 
	 */

	@Column(name = "device_price_reduce", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceReduce;
	/**
	 * ��ѧ��ĩ�豸������
	 * 
	 */

	@Column(name = "device_number_this")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberThis;
	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */

	@Column(name = "device_price_this", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceThis;
	/**
	 * ��ѧ��ĩ����10��Ԫ���豸������
	 * 
	 */

	@Column(name = "device_number_value_this")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer deviceNumberValueThis;
	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�ļ۸�
	 * 
	 */

	@Column(name = "device_price_value_this", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePriceValueThis;
	/**
	 * ѧ��id
	 * 
	 */

	@Column(name = "year_code")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer yearCode;

	/**
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��ѧ��ĩ�豸ʵ����
	 * 
	 */
	public void setDeviceNumberLast(Integer deviceNumberLast) {
		this.deviceNumberLast = deviceNumberLast;
	}

	/**
	 * ��ѧ��ĩ�豸ʵ����
	 * 
	 */
	public Integer getDeviceNumberLast() {
		return this.deviceNumberLast;
	}

	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */
	public void setDevicePriceLast(BigDecimal devicePriceLast) {
		this.devicePriceLast = devicePriceLast;
	}

	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */
	public BigDecimal getDevicePriceLast() {
		return this.devicePriceLast;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸����
	 * 
	 */
	public void setDeviceNumberValueLast(Integer deviceNumberValueLast) {
		this.deviceNumberValueLast = deviceNumberValueLast;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸����
	 * 
	 */
	public Integer getDeviceNumberValueLast() {
		return this.deviceNumberValueLast;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�۸�
	 * 
	 */
	public void setDevicePriceValueLast(BigDecimal devicePriceValueLast) {
		this.devicePriceValueLast = devicePriceValueLast;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�۸�
	 * 
	 */
	public BigDecimal getDevicePriceValueLast() {
		return this.devicePriceValueLast;
	}

	/**
	 * ��ѧ�����ӵ��豸����
	 * 
	 */
	public void setDeviceNumberAdd(Integer deviceNumberAdd) {
		this.deviceNumberAdd = deviceNumberAdd;
	}

	/**
	 * ��ѧ�����ӵ��豸����
	 * 
	 */
	public Integer getDeviceNumberAdd() {
		return this.deviceNumberAdd;
	}

	/**
	 * ��ѧ�����ӵ��豸�ļ۸�
	 * 
	 */
	public void setDevicePriceAdd(BigDecimal devicePriceAdd) {
		this.devicePriceAdd = devicePriceAdd;
	}

	/**
	 * ��ѧ�����ӵ��豸�ļ۸�
	 * 
	 */
	public BigDecimal getDevicePriceAdd() {
		return this.devicePriceAdd;
	}

	/**
	 * ��ѧ�걨�ϵ��豸����
	 * 
	 */
	public void setDeviceNumberReduce(Integer deviceNumberReduce) {
		this.deviceNumberReduce = deviceNumberReduce;
	}

	/**
	 * ��ѧ�걨�ϵ��豸����
	 * 
	 */
	public Integer getDeviceNumberReduce() {
		return this.deviceNumberReduce;
	}

	/**
	 * ��ѧ�걨�ϵ��豸�ļ۸�
	 * 
	 */
	public void setDevicePriceReduce(BigDecimal devicePriceReduce) {
		this.devicePriceReduce = devicePriceReduce;
	}

	/**
	 * ��ѧ�걨�ϵ��豸�ļ۸�
	 * 
	 */
	public BigDecimal getDevicePriceReduce() {
		return this.devicePriceReduce;
	}

	/**
	 * ��ѧ��ĩ�豸������
	 * 
	 */
	public void setDeviceNumberThis(Integer deviceNumberThis) {
		this.deviceNumberThis = deviceNumberThis;
	}

	/**
	 * ��ѧ��ĩ�豸������
	 * 
	 */
	public Integer getDeviceNumberThis() {
		return this.deviceNumberThis;
	}

	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */
	public void setDevicePriceThis(BigDecimal devicePriceThis) {
		this.devicePriceThis = devicePriceThis;
	}

	/**
	 * ��ѧ��ĩ�豸�ļ۸�
	 * 
	 */
	public BigDecimal getDevicePriceThis() {
		return this.devicePriceThis;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸������
	 * 
	 */
	public void setDeviceNumberValueThis(Integer deviceNumberValueThis) {
		this.deviceNumberValueThis = deviceNumberValueThis;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸������
	 * 
	 */
	public Integer getDeviceNumberValueThis() {
		return this.deviceNumberValueThis;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�ļ۸�
	 * 
	 */
	public void setDevicePriceValueThis(BigDecimal devicePriceValueThis) {
		this.devicePriceValueThis = devicePriceValueThis;
	}

	/**
	 * ��ѧ��ĩ����10��Ԫ���豸�ļ۸�
	 * 
	 */
	public BigDecimal getDevicePriceValueThis() {
		return this.devicePriceValueThis;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public void setYearCode(Integer yearCode) {
		this.yearCode = yearCode;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public Integer getYearCode() {
		return this.yearCode;
	}

	/**
	 */
	public SchoolDeviceChangeReport() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolDeviceChangeReport that) {
		setId(that.getId());
		setDeviceNumberLast(that.getDeviceNumberLast());
		setDevicePriceLast(that.getDevicePriceLast());
		setDeviceNumberValueLast(that.getDeviceNumberValueLast());
		setDevicePriceValueLast(that.getDevicePriceValueLast());
		setDeviceNumberAdd(that.getDeviceNumberAdd());
		setDevicePriceAdd(that.getDevicePriceAdd());
		setDeviceNumberReduce(that.getDeviceNumberReduce());
		setDevicePriceReduce(that.getDevicePriceReduce());
		setDeviceNumberThis(that.getDeviceNumberThis());
		setDevicePriceThis(that.getDevicePriceThis());
		setDeviceNumberValueThis(that.getDeviceNumberValueThis());
		setDevicePriceValueThis(that.getDevicePriceValueThis());
		setYearCode(that.getYearCode());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("deviceNumberLast=[").append(deviceNumberLast).append("] ");
		buffer.append("devicePriceLast=[").append(devicePriceLast).append("] ");
		buffer.append("deviceNumberValueLast=[").append(deviceNumberValueLast).append("] ");
		buffer.append("devicePriceValueLast=[").append(devicePriceValueLast).append("] ");
		buffer.append("deviceNumberAdd=[").append(deviceNumberAdd).append("] ");
		buffer.append("devicePriceAdd=[").append(devicePriceAdd).append("] ");
		buffer.append("deviceNumberReduce=[").append(deviceNumberReduce).append("] ");
		buffer.append("devicePriceReduce=[").append(devicePriceReduce).append("] ");
		buffer.append("deviceNumberThis=[").append(deviceNumberThis).append("] ");
		buffer.append("devicePriceThis=[").append(devicePriceThis).append("] ");
		buffer.append("deviceNumberValueThis=[").append(deviceNumberValueThis).append("] ");
		buffer.append("devicePriceValueThis=[").append(devicePriceValueThis).append("] ");
		buffer.append("yearCode=[").append(yearCode).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolDeviceChangeReport))
			return false;
		SchoolDeviceChangeReport equalCheck = (SchoolDeviceChangeReport) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

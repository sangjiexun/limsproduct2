package net.zjcclims.domain;

import org.python.antlr.op.In;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


@Entity
@Table(name = "asset_storage")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetStorage")
public class AssetStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	@Column(name = "app_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer appId;

	@Column(name = "cabinet_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer cabinetId;


	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;


	@Column(name = "date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Date date;

	@Column(name = "batch_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String batchNumber;


	@Column(name = "academy_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyNumber;

	@Column(name = "center_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer centerId;

	@Column(name = "classification_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer classficationId;

	@Column(name = "total_price")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double totalPrice;

	@Column(name = "invoice_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String invoiceNumber;

	@Column(name = "invoice_image")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String invoiceImage;

	@Column(name = "godown_entry")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String goDownEntry;

	@Column(name = "remarks")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remarks;

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;

	@Column(name = "cur_audit_level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String curAuditLevel;

	@Column(name = "audit_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Date auditDate;

	@Column(name = "audit_user")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String auditUser;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getAcademyNumber() {
		return academyNumber;
	}

	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public Integer getClassficationId() {
		return classficationId;
	}

	public void setClassficationId(Integer classficationId) {
		this.classficationId = classficationId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceImage() {
		return invoiceImage;
	}

	public void setInvoiceImage(String invoiceImage) {
		this.invoiceImage = invoiceImage;
	}

	public String getGoDownEntry() {
		return goDownEntry;
	}

	public void setGoDownEntry(String goDownEntry) {
		this.goDownEntry = goDownEntry;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCurAuditLevel() {
		return curAuditLevel;
	}

	public void setCurAuditLevel(String curAuditLevel) {
		this.curAuditLevel = curAuditLevel;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
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
		if (!(obj instanceof AssetStorage))
			return false;
		AssetStorage equalCheck = (AssetStorage) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

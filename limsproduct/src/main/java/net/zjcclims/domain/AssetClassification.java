package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name = "findAsseClassificationtById", query = "select a from AssetClassification a where a.id = ?1")
})
@Table(name = "asset_classification")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetClassification")
public class AssetClassification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "cname")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;

	@Column(name = "ename")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String ename;


	@Column(name = "info")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String info;

	@Column(name = "apply_audit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String applyAudit;

	@Column(name = "storage_audit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String storageAudit;

	@Column(name = "receive_audit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String receiveAudit;

	@Column(name = "is_need_return")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isNeedReturn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getApplyAudit() {
		return applyAudit;
	}

	public void setApplyAudit(String applyAudit) {
		this.applyAudit = applyAudit;
	}

	public String getStorageAudit() {
		return storageAudit;
	}

	public void setStorageAudit(String storageAudit) {
		this.storageAudit = storageAudit;
	}

	public String getReceiveAudit() {
		return receiveAudit;
	}

	public void setReceiveAudit(String receiveAudit) {
		this.receiveAudit = receiveAudit;
	}

	public Integer getIsNeedReturn() {
		return isNeedReturn;
	}

	public void setIsNeedReturn(Integer isNeedReturn) {
		this.isNeedReturn = isNeedReturn;
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
		if (!(obj instanceof AssetClassification))
			return false;
		AssetClassification equalCheck = (AssetClassification) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

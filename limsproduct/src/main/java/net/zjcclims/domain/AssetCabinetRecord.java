package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@Entity
@NamedQueries({
		@NamedQuery(name = "findAssetCabinetRecordById", query = "select assetCabinetRecord from AssetCabinetRecord assetCabinetRecord where assetCabinetRecord.id = ?1"),
		@NamedQuery(name = "findAssetCabinetRecordsByAssetId", query = "select assetCabinetRecords from AssetCabinetRecord assetCabinetRecords where assetCabinetRecords.assetId = ?1")
})
@Table(name = "asset_cabinet_record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetCabinetRecord")
public class AssetCabinetRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	@Column(name = "cabinet_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer cabinetId;

	@Column(name = "asset_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetId;


	@Column(name = "stock_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer stockNumber;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
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
		if (!(obj instanceof AssetCabinetRecord))
			return false;
		AssetCabinetRecord equalCheck = (AssetCabinetRecord) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

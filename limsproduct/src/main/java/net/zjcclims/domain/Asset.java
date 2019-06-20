package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAssets", query = "select myAsset from Asset myAsset"),
		@NamedQuery(name = "findAssetByCas", query = "select myAsset from Asset myAsset where myAsset.cas = ?1"),
		@NamedQuery(name = "findAssetByCasContaining", query = "select myAsset from Asset myAsset where myAsset.cas like ?1"),
		@NamedQuery(name = "findAssetByCategory", query = "select myAsset from Asset myAsset where myAsset.category = ?1"),
		@NamedQuery(name = "findAssetByChAlias", query = "select myAsset from Asset myAsset where myAsset.chAlias = ?1"),
		@NamedQuery(name = "findAssetByChAliasContaining", query = "select myAsset from Asset myAsset where myAsset.chAlias like ?1"),
		@NamedQuery(name = "findAssetByChName", query = "select myAsset from Asset myAsset where myAsset.chName = ?1"),
		@NamedQuery(name = "findAssetByChNameContaining", query = "select myAsset from Asset myAsset where myAsset.chName like ?1"),
		@NamedQuery(name = "findAssetByEnAlias", query = "select myAsset from Asset myAsset where myAsset.enAlias = ?1"),
		@NamedQuery(name = "findAssetByEnAliasContaining", query = "select myAsset from Asset myAsset where myAsset.enAlias like ?1"),
		@NamedQuery(name = "findAssetByEnName", query = "select myAsset from Asset myAsset where myAsset.enName = ?1"),
		@NamedQuery(name = "findAssetByEnNameContaining", query = "select myAsset from Asset myAsset where myAsset.enName like ?1"),
		@NamedQuery(name = "findAssetByFlag", query = "select myAsset from Asset myAsset where myAsset.flag = ?1"),
		@NamedQuery(name = "findAssetById", query = "select myAsset from Asset myAsset where myAsset.id = ?1"),
		@NamedQuery(name = "findAssetByLevel", query = "select myAsset from Asset myAsset where myAsset.level = ?1"),
		@NamedQuery(name = "findAssetByLevelContaining", query = "select myAsset from Asset myAsset where myAsset.level like ?1"),
		@NamedQuery(name = "findAssetByAssetLimit", query = "select myAsset from Asset myAsset where myAsset.assetLimit = ?1"),
		@NamedQuery(name = "findAssetByMem", query = "select myAsset from Asset myAsset where myAsset.mem = ?1"),
		@NamedQuery(name = "findAssetByPingyin", query = "select myAsset from Asset myAsset where myAsset.pingyin = ?1"),
		@NamedQuery(name = "findAssetByPingyinContaining", query = "select myAsset from Asset myAsset where myAsset.pingyin like ?1"),
		@NamedQuery(name = "findAssetByPrimaryKey", query = "select myAsset from Asset myAsset where myAsset.id = ?1"),
		@NamedQuery(name = "findAssetByRank", query = "select myAsset from Asset myAsset where myAsset.rank = ?1"),
		@NamedQuery(name = "findAssetBySecurityLevel", query = "select myAsset from Asset myAsset where myAsset.securityLevel = ?1"),
		@NamedQuery(name = "findAssetBySpecifications", query = "select myAsset from Asset myAsset where myAsset.specifications = ?1"),
		@NamedQuery(name = "findAssetBySpecificationsContaining", query = "select myAsset from Asset myAsset where myAsset.specifications like ?1"),
		@NamedQuery(name = "findAssetByUnit", query = "select myAsset from Asset myAsset where myAsset.unit = ?1") })
@Table(name = "asset")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "Asset")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class Asset implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���ʱ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ����������
	 * 
	 */

	@Column(name = "ch_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String chName;
	/**
	 * �������ı���
	 * 
	 */

	@Column(name = "ch_alias")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String chAlias;
	/**
	 * ����Ӣ����
	 * 
	 */

	@Column(name = "en_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String enName;
	/**
	 * ����Ӣ�ı���
	 * 
	 */

	@Column(name = "en_alias")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String enAlias;
	/**
	 * ����CAS��
	 * 
	 */

	@Column(name = "cas")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cas;

	/**
	 * ���ʼ���
	 * 
	 */

	@Column(name = "rank")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer rank;
	/**
	 * �������
	 * 
	 */

	@Column(name = "category")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer category;
	/**
	 * ��ȫ�� ��� c_asset_sevurity_level
	 * 
	 */

	@Column(name = "security_level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer securityLevel;
	/**
	 * ������λ
	 * 
	 */

	@Column(name = "unit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String unit;
	/**
	 * ���ʹ��
	 * 
	 */

	@Column(name = "specifications")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String specifications;
	/**
	 * 1 ��Ҫ������� 0 ����Ҫ�������
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "asset_limit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetLimit;
	/**
	 * ���ʼ���
	 * 
	 */

	@Column(name = "level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String level;
	/**
	 * ƴ����������д��
	 * 
	 */

	@Column(name = "pingyin")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String pingyin;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "mem", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String mem;
	
	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;


	@Column(name = "qRCode_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String qrCode;

	@Column(name = "factory")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String factory;

	@Column(name = "price")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String price;

	@Column(name = "function")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String function;

	@Column(name = "center_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer centerId;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}


	
	/**
	 */
	@OneToMany(mappedBy = "asset", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppRecord> assetAppRecords;
	
	/**
	 */
	@OneToMany(mappedBy = "asset", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetReceiveRecord> assetReceiveRecords;
	
	/**
	 */
	@OneToMany(mappedBy = "asset", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord> assetCabinetWarehouseAccessRecords;


	@OneToMany(mappedBy = "asset", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ItemAssets> assetsByItemAssets;
	/**
	 * ���ʱ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���ʱ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����������
	 * 
	 */
	public void setChName(String chName) {
		this.chName = chName;
	}

	/**
	 * ����������
	 * 
	 */
	public String getChName() {
		return this.chName;
	}

	/**
	 * �������ı���
	 * 
	 */
	public void setChAlias(String chAlias) {
		this.chAlias = chAlias;
	}

	/**
	 * �������ı���
	 * 
	 */
	public String getChAlias() {
		return this.chAlias;
	}

	/**
	 * ����Ӣ����
	 * 
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * ����Ӣ����
	 * 
	 */
	public String getEnName() {
		return this.enName;
	}

	/**
	 * ����Ӣ�ı���
	 * 
	 */
	public void setEnAlias(String enAlias) {
		this.enAlias = enAlias;
	}

	/**
	 * ����Ӣ�ı���
	 * 
	 */
	public String getEnAlias() {
		return this.enAlias;
	}

	/**
	 * ����CAS��
	 * 
	 */
	public void setCas(String cas) {
		this.cas = cas;
	}

	/**
	 * ����CAS��
	 * 
	 */
	public String getCas() {
		return this.cas;
	}

	
	/**
	 * ���ʼ���
	 * 
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * ���ʼ���
	 * 
	 */
	public Integer getRank() {
		return this.rank;
	}

	/**
	 * �������
	 * 
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * �������
	 * 
	 */
	public Integer getCategory() {
		return this.category;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ��ȫ�� ��� c_asset_sevurity_level
	 * 
	 */
	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	/**
	 * ��ȫ�� ��� c_asset_sevurity_level
	 * 
	 */
	public Integer getSecurityLevel() {
		return this.securityLevel;
	}

	/**
	 * ������λ
	 * 
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * ������λ
	 * 
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * ���ʹ��
	 * 
	 */
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	/**
	 * ���ʹ��
	 * 
	 */
	public String getSpecifications() {
		return this.specifications;
	}

	/**
	 * 1 ��Ҫ������� 0 ����Ҫ�������
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 1 ��Ҫ������� 0 ����Ҫ�������
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
	}

	/**
	 * ��������
	 * 
	 */
	public void setAssetLimit(Integer limit) {
		this.assetLimit = limit;
	}

	/**
	 * ��������
	 * 
	 */
	public Integer getAssetLimit() {
		return this.assetLimit;
	}

	/**
	 * ���ʼ���
	 * 
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * ���ʼ���
	 * 
	 */
	public String getLevel() {
		return this.level;
	}

	/**
	 * ƴ����������д��
	 * 
	 */
	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}

	/**
	 * ƴ����������д��
	 * 
	 */
	public String getPingyin() {
		return this.pingyin;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMem(String mem) {
		this.mem = mem;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMem() {
		return this.mem;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	/**
	 */
	public void setAssetCabinetWarehouseAccessRecords(Set<AssetCabinetWarehouseAccessRecord> assetCabinetWarehouseAccessRecords) {
		this.assetCabinetWarehouseAccessRecords = assetCabinetWarehouseAccessRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetCabinetWarehouseAccessRecord> getAssetCabinetWarehouseAccessRecords() {
		if (assetCabinetWarehouseAccessRecords == null) {
			assetCabinetWarehouseAccessRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord>();
		}
		return assetCabinetWarehouseAccessRecords;
	}
	/**
	 */
	public void setAssetAppRecords(Set<AssetAppRecord> assetAppRecords) {
		this.assetAppRecords = assetAppRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetAppRecord> getAssetAppRecords() {
		if (assetAppRecords == null) {
			assetAppRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppRecord>();
		}
		return assetAppRecords;
	}


	
	/**
	 */
	public void setAssetReceiveRecords(Set<AssetReceiveRecord> assetReceiveRecords) {
		this.assetReceiveRecords = assetReceiveRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetReceiveRecord> getAssetReceiveRecords() {
		if (assetReceiveRecords == null) {
			assetReceiveRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetReceiveRecord>();
		}
		return assetReceiveRecords;
	}


	public Set<ItemAssets> getAssetsByItemAssets() {
		return assetsByItemAssets;
	}

	public void setAssetsByItemAssets(Set<ItemAssets> assetsByItemAssets) {
		this.assetsByItemAssets = assetsByItemAssets;
	}

	/**
	 */
	public Asset() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(Asset that) {
		setId(that.getId());
		setChName(that.getChName());
		setChAlias(that.getChAlias());
		setEnName(that.getEnName());
		setEnAlias(that.getEnAlias());
		setCas(that.getCas());
		setRank(that.getRank());
		setCategory(that.getCategory());
		setSecurityLevel(that.getSecurityLevel());
		setUnit(that.getUnit());
		setSpecifications(that.getSpecifications());
		setFlag(that.getFlag());
		setAssetLimit(that.getAssetLimit());
		setLevel(that.getLevel());
		setPingyin(that.getPingyin());
		setMem(that.getMem());
		setAssetAppRecords(new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppRecord>(that.getAssetAppRecords()));
		setAssetReceiveRecords(new java.util.LinkedHashSet<net.zjcclims.domain.AssetReceiveRecord>(that.getAssetReceiveRecords()));
		setAssetCabinetWarehouseAccessRecords(new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord>(that.getAssetCabinetWarehouseAccessRecords()));
		setAssetsByItemAssets(new java.util.LinkedHashSet<>(that.getAssetsByItemAssets()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("chName=[").append(chName).append("] ");
		buffer.append("chAlias=[").append(chAlias).append("] ");
		buffer.append("enName=[").append(enName).append("] ");
		buffer.append("enAlias=[").append(enAlias).append("] ");
		buffer.append("cas=[").append(cas).append("] ");
		buffer.append("rank=[").append(rank).append("] ");
		buffer.append("category=[").append(category).append("] ");
		buffer.append("securityLevel=[").append(securityLevel).append("] ");
		buffer.append("unit=[").append(unit).append("] ");
		buffer.append("specifications=[").append(specifications).append("] ");
		buffer.append("flag=[").append(flag).append("] ");
		buffer.append("assetLimit=[").append(assetLimit).append("] ");
		buffer.append("level=[").append(level).append("] ");
		buffer.append("pingyin=[").append(pingyin).append("] ");
		buffer.append("mem=[").append(mem).append("] ");

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
		if (!(obj instanceof Asset))
			return false;
		Asset equalCheck = (Asset) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

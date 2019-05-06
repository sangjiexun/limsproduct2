package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findItemAssetsById", query = "select myItemAssets from ItemAssets myItemAssets where myItemAssets.id = ?1")
})
@Table(name = "item_assets")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ItemAssets")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ItemAssets implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	/**
	 * 数量
	 */
	@Column(name = "amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer amount;

	/**
	 * 项目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_item", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	/**
	 * 关联物资
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "asset_id", referencedColumnName = "id") })
	@XmlTransient
	Asset asset;

	/**
	 * ��������
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��������
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	public OperationItem getOperationItem() {
		return operationItem;
	}

	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/**
	 */
	public ItemAssets() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ItemAssets that) {
		setId(that.getId());
		setOperationItem(that.getOperationItem());
		setAsset(that.getAsset());
		setAmount(that.getAmount());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("amount=[").append(amount).append("]");

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
		if (!(obj instanceof ItemAssets))
			return false;
		ItemAssets equalCheck = (ItemAssets) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

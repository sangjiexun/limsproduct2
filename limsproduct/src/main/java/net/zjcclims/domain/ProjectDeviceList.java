package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectDeviceLists", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList"),
		@NamedQuery(name = "findProjectDeviceListByAmount", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.amount = ?1"),
		@NamedQuery(name = "findProjectDeviceListByBudgetaryItem", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.budgetaryItem = ?1"),
		@NamedQuery(name = "findProjectDeviceListByBudgetaryItemContaining", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.budgetaryItem like ?1"),
		@NamedQuery(name = "findProjectDeviceListById", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.id = ?1"),
		@NamedQuery(name = "findProjectDeviceListByOtherFundsSource", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.otherFundsSource = ?1"),
		@NamedQuery(name = "findProjectDeviceListByOtherFundsSourceContaining", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.otherFundsSource like ?1"),
		@NamedQuery(name = "findProjectDeviceListByPrimaryKey", query = "select myProjectDeviceList from ProjectDeviceList myProjectDeviceList where myProjectDeviceList.id = ?1") })
@Table(name = "project_device_list")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectDeviceList")
public class ProjectDeviceList implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * �������Դ�����
	 * 
	 */

	@Column(name = "other_funds_source")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String otherFundsSource;
	/**
	 * Ԥ���Ŀ
	 * 
	 */

	@Column(name = "budgetary_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String budgetaryItem;
	/**
	 * ���
	 * 
	 */

	@Column(name = "amount", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal amount;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "funds_app_id", referencedColumnName = "id") })
	@XmlTransient
	CFundAppItem CFundAppItem;

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
	 * �������Դ�����
	 * 
	 */
	public void setOtherFundsSource(String otherFundsSource) {
		this.otherFundsSource = otherFundsSource;
	}

	/**
	 * �������Դ�����
	 * 
	 */
	public String getOtherFundsSource() {
		return this.otherFundsSource;
	}

	/**
	 * Ԥ���Ŀ
	 * 
	 */
	public void setBudgetaryItem(String budgetaryItem) {
		this.budgetaryItem = budgetaryItem;
	}

	/**
	 * Ԥ���Ŀ
	 * 
	 */
	public String getBudgetaryItem() {
		return this.budgetaryItem;
	}

	/**
	 * ���
	 * 
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * ���
	 * 
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 */
	public void setCFundAppItem(CFundAppItem CFundAppItem) {
		this.CFundAppItem = CFundAppItem;
	}

	/**
	 */
	public CFundAppItem getCFundAppItem() {
		return CFundAppItem;
	}

	/**
	 */
	public ProjectDeviceList() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectDeviceList that) {
		setId(that.getId());
		setOtherFundsSource(that.getOtherFundsSource());
		setBudgetaryItem(that.getBudgetaryItem());
		setAmount(that.getAmount());
		setCFundAppItem(that.getCFundAppItem());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("otherFundsSource=[").append(otherFundsSource).append("] ");
		buffer.append("budgetaryItem=[").append(budgetaryItem).append("] ");
		buffer.append("amount=[").append(amount).append("] ");

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
		if (!(obj instanceof ProjectDeviceList))
			return false;
		ProjectDeviceList equalCheck = (ProjectDeviceList) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

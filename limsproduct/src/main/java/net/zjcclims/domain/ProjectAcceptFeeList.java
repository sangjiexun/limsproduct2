package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAcceptFeeLists", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList"),
		@NamedQuery(name = "findProjectAcceptFeeListByAmount", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.amount = ?1"),
		@NamedQuery(name = "findProjectAcceptFeeListByBudgetaryItem", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.budgetaryItem = ?1"),
		@NamedQuery(name = "findProjectAcceptFeeListByBudgetaryItemContaining", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.budgetaryItem like ?1"),
		@NamedQuery(name = "findProjectAcceptFeeListById", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.id = ?1"),
		@NamedQuery(name = "findProjectAcceptFeeListByOtherFundsSource", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.otherFundsSource = ?1"),
		@NamedQuery(name = "findProjectAcceptFeeListByPrimaryKey", query = "select myProjectAcceptFeeList from ProjectAcceptFeeList myProjectAcceptFeeList where myProjectAcceptFeeList.id = ?1") })
@Table(name = "project_accept_fee_list")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAcceptFeeList")
public class ProjectAcceptFeeList implements Serializable {
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
	 * �������Դ�����
	 * 
	 */

	@Column(name = "other_funds_source", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal otherFundsSource;
	/**
	 */

	@Column(name = "budgetary_item", length = 40)
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplication;

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
	public void setOtherFundsSource(BigDecimal otherFundsSource) {
		this.otherFundsSource = otherFundsSource;
	}

	/**
	 * �������Դ�����
	 * 
	 */
	public BigDecimal getOtherFundsSource() {
		return this.otherFundsSource;
	}

	/**
	 */
	public void setBudgetaryItem(String budgetaryItem) {
		this.budgetaryItem = budgetaryItem;
	}

	/**
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
	public void setProjectAcceptanceApplication(ProjectAcceptanceApplication projectAcceptanceApplication) {
		this.projectAcceptanceApplication = projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplication() {
		return projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptFeeList() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAcceptFeeList that) {
		setId(that.getId());
		setOtherFundsSource(that.getOtherFundsSource());
		setBudgetaryItem(that.getBudgetaryItem());
		setAmount(that.getAmount());
		setCFundAppItem(that.getCFundAppItem());
		setProjectAcceptanceApplication(that.getProjectAcceptanceApplication());
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
		if (!(obj instanceof ProjectAcceptFeeList))
			return false;
		ProjectAcceptFeeList equalCheck = (ProjectAcceptFeeList) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

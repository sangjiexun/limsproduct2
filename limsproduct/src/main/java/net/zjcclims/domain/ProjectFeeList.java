package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectFeeLists", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList"),
		@NamedQuery(name = "findProjectFeeListByAmount", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.amount = ?1"),
		@NamedQuery(name = "findProjectFeeListByBudgetaryItem", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.budgetaryItem = ?1"),
		@NamedQuery(name = "findProjectFeeListByBudgetaryItemContaining", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.budgetaryItem like ?1"),
		@NamedQuery(name = "findProjectFeeListById", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.id = ?1"),
		@NamedQuery(name = "findProjectFeeListByOtherFundsSource", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.otherFundsSource = ?1"),
		@NamedQuery(name = "findProjectFeeListByPrimaryKey", query = "select myProjectFeeList from ProjectFeeList myProjectFeeList where myProjectFeeList.id = ?1") })
@Table(name = "project_fee_list")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectFeeList")
public class ProjectFeeList implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;

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
	 */
	public void setOtherFundsSource(BigDecimal otherFundsSource) {
		this.otherFundsSource = otherFundsSource;
	}

	/**
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
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
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
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}

	/**
	 */
	public ProjectFeeList() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectFeeList that) {
		setId(that.getId());
		setOtherFundsSource(that.getOtherFundsSource());
		setBudgetaryItem(that.getBudgetaryItem());
		setAmount(that.getAmount());
		setCFundAppItem(that.getCFundAppItem());
		setLabConstructApp(that.getLabConstructApp());
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
		if (!(obj instanceof ProjectFeeList))
			return false;
		ProjectFeeList equalCheck = (ProjectFeeList) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

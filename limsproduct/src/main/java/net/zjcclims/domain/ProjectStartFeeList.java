package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectStartFeeLists", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList"),
		@NamedQuery(name = "findProjectStartFeeListByAmount", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.amount = ?1"),
		@NamedQuery(name = "findProjectStartFeeListByBudgetaryItem", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.budgetaryItem = ?1"),
		@NamedQuery(name = "findProjectStartFeeListByBudgetaryItemContaining", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.budgetaryItem like ?1"),
		@NamedQuery(name = "findProjectStartFeeListById", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.id = ?1"),
		@NamedQuery(name = "findProjectStartFeeListByOtherFundsSource", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.otherFundsSource = ?1"),
		@NamedQuery(name = "findProjectStartFeeListByPrimaryKey", query = "select myProjectStartFeeList from ProjectStartFeeList myProjectStartFeeList where myProjectStartFeeList.id = ?1") })
@Table(name = "project_start_fee_list")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectStartFeeList")
public class ProjectStartFeeList implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "project_start_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;
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
	public void setProjectStartedReport(ProjectStartedReport projectStartedReport) {
		this.projectStartedReport = projectStartedReport;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReport() {
		return projectStartedReport;
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
	public ProjectStartFeeList() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectStartFeeList that) {
		setId(that.getId());
		setOtherFundsSource(that.getOtherFundsSource());
		setBudgetaryItem(that.getBudgetaryItem());
		setAmount(that.getAmount());
		setProjectStartedReport(that.getProjectStartedReport());
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
		if (!(obj instanceof ProjectStartFeeList))
			return false;
		ProjectStartFeeList equalCheck = (ProjectStartFeeList) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

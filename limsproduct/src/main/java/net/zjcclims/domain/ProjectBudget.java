package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectBudgets", query = "select myProjectBudget from ProjectBudget myProjectBudget"),
		@NamedQuery(name = "findProjectBudgetByBudgetFee", query = "select myProjectBudget from ProjectBudget myProjectBudget where myProjectBudget.budgetFee = ?1"),
		@NamedQuery(name = "findProjectBudgetById", query = "select myProjectBudget from ProjectBudget myProjectBudget where myProjectBudget.id = ?1"),
		@NamedQuery(name = "findProjectBudgetByPrimaryKey", query = "select myProjectBudget from ProjectBudget myProjectBudget where myProjectBudget.id = ?1"),
		@NamedQuery(name = "findProjectBudgetByUseFee", query = "select myProjectBudget from ProjectBudget myProjectBudget where myProjectBudget.useFee = ?1") })
@Table( name = "project_budget")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectBudget")
public class ProjectBudget implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���������龭��Ԥ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
    Integer id;
	/**
	 * ����Ԥ�����
	 * 
	 */

	@Column(name = "budget_fee", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal budgetFee;
	/**
	 * ʵ��ʹ�÷���
	 * 
	 */

	@Column(name = "use_fee", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal useFee;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "budget_id", referencedColumnName = "id") })
	@XmlTransient
	CProjectBudget CProjectBudget;

	/**
	 * ���������龭��Ԥ��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���������龭��Ԥ��
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ����Ԥ�����
	 * 
	 */
	public void setBudgetFee(BigDecimal budgetFee) {
		this.budgetFee = budgetFee;
	}

	/**
	 * ����Ԥ�����
	 * 
	 */
	public BigDecimal getBudgetFee() {
		return this.budgetFee;
	}

	/**
	 * ʵ��ʹ�÷���
	 * 
	 */
	public void setUseFee(BigDecimal useFee) {
		this.useFee = useFee;
	}

	/**
	 * ʵ��ʹ�÷���
	 * 
	 */
	public BigDecimal getUseFee() {
		return this.useFee;
	}

	/**
	 */
	public void setCProjectBudget(CProjectBudget CProjectBudget) {
		this.CProjectBudget = CProjectBudget;
	}

	/**
	 */
	public CProjectBudget getCProjectBudget() {
		return CProjectBudget;
	}

	/**
	 */
	public ProjectBudget() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectBudget that) {
		setId(that.getId());
		setBudgetFee(that.getBudgetFee());
		setUseFee(that.getUseFee());
		setCProjectBudget(that.getCProjectBudget());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("budgetFee=[").append(budgetFee).append("] ");
		buffer.append("useFee=[").append(useFee).append("] ");

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
		if (!(obj instanceof ProjectBudget))
			return false;
		ProjectBudget equalCheck = (ProjectBudget) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

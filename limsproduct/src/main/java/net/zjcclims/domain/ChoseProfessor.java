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
		@NamedQuery(name = "findAllChoseProfessors", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor"),
		@NamedQuery(name = "findChoseProfessorByExceptNumber", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor where myChoseProfessor.expectNumber = ?1"),
		@NamedQuery(name = "findChoseProfessorByFinalNumber", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor where myChoseProfessor.finalNumber = ?1"),
		@NamedQuery(name = "findChoseProfessorById", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor where myChoseProfessor.id = ?1"),
		@NamedQuery(name = "findChoseProfessorByPrimaryKey", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor where myChoseProfessor.id = ?1"),
		@NamedQuery(name = "findChoseProfessorByType", query = "select myChoseProfessor from ChoseProfessor myChoseProfessor where myChoseProfessor.type = ?1") })
@Table(name = "chose_professor")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseProfessor")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseProfessor implements Serializable {
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
	 * ����ѧ������
	 * 
	 */

	@Column(name = "expect_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer expectNumber;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "final_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer finalNumber;
	/**
	 */

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer type;
	@Column(name = "is_audit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer isAudit;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "theme_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseTheme choseTheme;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@OneToMany(mappedBy = "choseProfessor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessorRecord> choseProfessorRecords;

	/**
	 */
	@OneToMany(mappedBy = "choseProfessor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseDissertation> choseDissertations;

	/**
	 */
	public void setChoseDissertations(Set<ChoseDissertation> choseDissertations) {
		this.choseDissertations = choseDissertations;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseDissertation> getChoseDissertations() {
		if (choseDissertations == null) {
			choseDissertations = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertation>();
		}
		return choseDissertations;
	}

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
	 * ����ѧ������
	 *
	 */
	public void setExpectNumber(Integer expectNumber) {
		this.expectNumber = expectNumber;
	}

	/**
	 * ����ѧ������
	 *
	 */
	public Integer getExpectNumber() {
		return this.expectNumber;
	}

	/**
	 * ��������
	 *
	 */
	public void setFinalNumber(Integer finalNumber) {
		this.finalNumber = finalNumber;
	}

	/**
	 * ��������
	 *
	 */
	public Integer getFinalNumber() {
		return this.finalNumber;
	}

	/**
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 */
	public Integer getIsAudit() {
		return this.isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit =isAudit;
	}

	/**
	 */
	public Integer getType() {
		return this.type;
	}
	/**
	 */
	public void setChoseTheme(ChoseTheme choseTheme) {
		this.choseTheme = choseTheme;
	}

	/**
	 */
	@JsonIgnore
	public ChoseTheme getChoseTheme() {
		return choseTheme;
	}

	/**
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 */
	public void setChoseProfessorRecords(Set<ChoseProfessorRecord> choseProfessorRecords) {
		this.choseProfessorRecords = choseProfessorRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseProfessorRecord> getChoseProfessorRecords() {
		if (choseProfessorRecords == null) {
			choseProfessorRecords = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorRecord>();
		}
		return choseProfessorRecords;
	}

	/**
	 */
	public ChoseProfessor() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseProfessor that) {
		setId(that.getId());
		setExpectNumber(that.getExpectNumber());
		setFinalNumber(that.getFinalNumber());
		setType(that.getType());
		setChoseTheme(that.getChoseTheme());
		setUser(that.getUser());
		setChoseProfessorRecords(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorRecord>(that.getChoseProfessorRecords()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("exceptNumber=[").append(expectNumber).append("] ");
		buffer.append("finalNumber=[").append(finalNumber).append("] ");
		buffer.append("type=[").append(type).append("] ");

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
		if (!(obj instanceof ChoseProfessor))
			return false;
		ChoseProfessor equalCheck = (ChoseProfessor) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

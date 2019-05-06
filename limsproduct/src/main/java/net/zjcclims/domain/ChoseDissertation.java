package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseDissertations", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation"),
		@NamedQuery(name = "findChoseDissertationByContent", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.content = ?1"),
		@NamedQuery(name = "findChoseDissertationByContentContaining", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.content like ?1"),
		@NamedQuery(name = "findChoseDissertationById", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.id = ?1"),
		@NamedQuery(name = "findChoseDissertationByPrimaryKey", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.id = ?1"),
		@NamedQuery(name = "findChoseDissertationByRemark", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.remark = ?1"),
		@NamedQuery(name = "findChoseDissertationByRemarkContaining", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.remark like ?1"),
		@NamedQuery(name = "findChoseDissertationByState", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.state = ?1"),
		@NamedQuery(name = "findChoseDissertationByTittle", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.tittle = ?1"),
		@NamedQuery(name = "findChoseDissertationByTittleContaining", query = "select myChoseDissertation from ChoseDissertation myChoseDissertation where myChoseDissertation.tittle like ?1") })
@Table(name = "chose_dissertation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseDissertation")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseDissertation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
    Integer id;
	/**
	 * ��Ŀ
	 * 
	 */

	@Column(name = "tittle")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String tittle;
	
	@Column(name = "student")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String student;
	/**
	 * ���ݼ��
	 * 
	 */

	@Column(name = "content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String content;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String remark;
	/**
	 * ״̬��1δ���2��˾ܾ�3���ͨ��
	 * 
	 */

	@Column(name = "state")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer state;
	
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "finish_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar finishTime;
	
	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * ��ѡ����ʱ��
	 * 
	 */
	public Calendar getFinishTime() {
		return this.finishTime;
	}
	
	/**
	 */
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "department", referencedColumnName = "id") })
	@XmlTransient
	ChoseDissertationDirection choseDissertationDirection;*/
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "professor_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseProfessor choseProfessor;
	/**
	 */
	@OneToMany(mappedBy = "choseDissertation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseDissertationRecord> choseDissertationRecords;
	/**
	 */
	@OneToMany(mappedBy = "choseDissertation", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseUser> choseUsers;

	/**
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}




	@Column(name = "department")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String department;

	@Column(name = "direction")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String direction;


	/**
	 */
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}


	public String getStudent() {
		return this.student;
	}
	public void setStudent(String student) {
		this.student = student;
	}

	public String getDirection() {
		return this.direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 */


	/**
	 * ��Ŀ
	 *
	 */
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * ��Ŀ
	 *
	 */
	public String getTittle() {
		return this.tittle;
	}

	/**
	 * ���ݼ��
	 *
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ���ݼ��
	 *
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * ��ע
	 *
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * ��ע
	 *
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * ״̬��1δ���2��˾ܾ�3���ͨ��
	 *
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * ״̬��1δ���2��˾ܾ�3���ͨ��
	 *
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 */
	/*public void setChoseDissertationDirection(ChoseDissertationDirection choseDissertationDirection) {
		this.choseDissertationDirection = choseDissertationDirection;
	}

	*//**
	 *//*
	@JsonIgnore
	public ChoseDissertationDirection getChoseDissertationDirection() {
		return choseDissertationDirection;
	}*/

	/**
	 */
	public void setChoseProfessor(ChoseProfessor choseProfessor) {
		this.choseProfessor = choseProfessor;
	}

	/**
	 */
	@JsonIgnore
	public ChoseProfessor getChoseProfessor() {
		return choseProfessor;
	}

	/**
	 */
	public void setChoseDissertationRecords(Set<ChoseDissertationRecord> choseDissertationRecords) {
		this.choseDissertationRecords = choseDissertationRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseDissertationRecord> getChoseDissertationRecords() {
		if (choseDissertationRecords == null) {
			choseDissertationRecords = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertationRecord>();
		}
		return choseDissertationRecords;
	}

	/**
	 */
	public void setChoseUsers(Set<ChoseUser> choseUsers) {
		this.choseUsers = choseUsers;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseUser> getChoseUsers() {
		if (choseUsers == null) {
			choseUsers = new java.util.LinkedHashSet<>();
		}
		return choseUsers;
	}

	/**
	 */
	public ChoseDissertation() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseDissertation that) {
		setId(that.getId());
		setTittle(that.getTittle());
		setContent(that.getContent());
		setRemark(that.getRemark());
		setState(that.getState());
		//setChoseDissertationDirection(that.getChoseDissertationDirection());
		setChoseProfessor(that.getChoseProfessor());
		setChoseDissertationRecords(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertationRecord>(that.getChoseDissertationRecords()));
		setChoseUsers(new java.util.LinkedHashSet<>(that.getChoseUsers()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("tittle=[").append(tittle).append("] ");
		buffer.append("content=[").append(content).append("] ");
		buffer.append("remark=[").append(remark).append("] ");
		buffer.append("state=[").append(state).append("] ");

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
		if (!(obj instanceof ChoseDissertation))
			return false;
		ChoseDissertation equalCheck = (ChoseDissertation) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

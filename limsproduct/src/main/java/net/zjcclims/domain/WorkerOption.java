package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllWorkerOptions", query = "select myWorkerOption from WorkerOption myWorkerOption"),
		@NamedQuery(name = "findWorkerOptionById", query = "select myWorkerOption from WorkerOption myWorkerOption where myWorkerOption.id = ?1"),
		@NamedQuery(name = "findWorkerOptionByPrimaryKey", query = "select myWorkerOption from WorkerOption myWorkerOption where myWorkerOption.id = ?1"),
		@NamedQuery(name = "findWorkerOptionByWorker", query = "select myWorkerOption from WorkerOption myWorkerOption where myWorkerOption.worker = ?1") })
@Table(name = "worker_option")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "WorkerOption")
public class WorkerOption implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ʦ��ԤԼ��λ�����ñ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ���ο�ԤԼ��λ��
	 * 
	 */

	@Column(name = "worker")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer worker;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher_name", referencedColumnName = "username") })
	@XmlTransient
	User user;

	/**
	 * ��ʦ��ԤԼ��λ�����ñ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��ʦ��ԤԼ��λ�����ñ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ���ο�ԤԼ��λ��
	 * 
	 */
	public void setWorker(Integer worker) {
		this.worker = worker;
	}

	/**
	 * ���ο�ԤԼ��λ��
	 * 
	 */
	public Integer getWorker() {
		return this.worker;
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
	public WorkerOption() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(WorkerOption that) {
		setId(that.getId());
		setWorker(that.getWorker());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("worker=[").append(worker).append("] ");

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
		if (!(obj instanceof WorkerOption))
			return false;
		WorkerOption equalCheck = (WorkerOption) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

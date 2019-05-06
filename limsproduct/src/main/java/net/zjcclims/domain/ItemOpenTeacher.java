package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({})
@Table(name = "item_open_teacher")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ItemOpenTeacher")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ItemOpenTeacher implements Serializable {
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
	 * 项目
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_item", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	/**
	 * 关联用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
	@XmlTransient
	User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	public ItemOpenTeacher() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ItemOpenTeacher that) {
		setId(that.getId());
		setOperationItem(that.getOperationItem());
		setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");

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
		if (!(obj instanceof ItemOpenTeacher))
			return false;
		ItemOpenTeacher equalCheck = (ItemOpenTeacher) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

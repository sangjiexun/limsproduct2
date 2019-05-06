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
		@NamedQuery(name = "findAllCLabRoomTypes", query = "select myCLabRoomType from CLabRoomType myCLabRoomType"),
		@NamedQuery(name = "findCLabRoomTypeById", query = "select myCLabRoomType from CLabRoomType myCLabRoomType where myCLabRoomType.id = ?1"),
		@NamedQuery(name = "findCLabRoomTypeByName", query = "select myCLabRoomType from CLabRoomType myCLabRoomType where myCLabRoomType.name = ?1"),
		@NamedQuery(name = "findCLabRoomTypeByNameContaining", query = "select myCLabRoomType from CLabRoomType myCLabRoomType where myCLabRoomType.name like ?1"),
		@NamedQuery(name = "findCLabRoomTypeByPrimaryKey", query = "select myCLabRoomType from CLabRoomType myCLabRoomType where myCLabRoomType.id = ?1") })
@Table(name = "c_lab_room_type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CLabRoomType")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CLabRoomType implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҷ�������ֵ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
    Integer id;
	/**
	 * ʵ���ҷ������
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String name;



	/**
	 * ʵ���ҷ�������ֵ��
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҷ�������ֵ��
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ʵ���ҷ������
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ʵ���ҷ������
	 *
	 */
	public String getName() {
		return this.name;
	}

	/**
	 */
	public CLabRoomType() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CLabRoomType that) {
		setId(that.getId());
		setName(that.getName());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("name=[").append(name).append("] ");

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
		if (!(obj instanceof CLabRoomType))
			return false;
		CLabRoomType equalCheck = (CLabRoomType) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

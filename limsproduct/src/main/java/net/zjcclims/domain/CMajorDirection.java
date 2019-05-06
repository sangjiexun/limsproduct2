package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCMajorDirections", query = "select myCMajorDirection from CMajorDirection myCMajorDirection"),
		@NamedQuery(name = "findCMajorDirectionByDirectionCode", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.directionCode = ?1"),
		@NamedQuery(name = "findCMajorDirectionByDirectionCodeContaining", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.directionCode like ?1"),
		@NamedQuery(name = "findCMajorDirectionByDirectionName", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.directionName = ?1"),
		@NamedQuery(name = "findCMajorDirectionByDirectionNameContaining", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.directionName like ?1"),
		@NamedQuery(name = "findCMajorDirectionById", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.id = ?1"),
		@NamedQuery(name = "findCMajorDirectionByMajorId", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.majorId = ?1"),
		@NamedQuery(name = "findCMajorDirectionByPrimaryKey", query = "select myCMajorDirection from CMajorDirection myCMajorDirection where myCMajorDirection.id = ?1") })
@Table( name = "c_major_direction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CMajorDirection")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CMajorDirection implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * רҵ�����ֵ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * רҵ�������
	 * 
	 */

	@Column(name = "direction_code", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String directionCode;
	/**
	 * רҵ�������
	 * 
	 */

	@Column(name = "direction_name", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String directionName;
	/**
	 * רҵid
	 * 
	 */

	@Column(name = "major_id", precision = 12)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal majorId;

	/**
	 */
	@OneToMany(mappedBy = "CMajorDirection", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<User> users;

	/**
	 * רҵ�����ֵ��
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * רҵ�����ֵ��
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * רҵ�������
	 *
	 */
	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}

	/**
	 * רҵ�������
	 *
	 */
	public String getDirectionCode() {
		return this.directionCode;
	}

	/**
	 * רҵ�������
	 *
	 */
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}

	/**
	 * רҵ�������
	 *
	 */
	public String getDirectionName() {
		return this.directionName;
	}

	/**
	 * רҵid
	 *
	 */
	public void setMajorId(BigDecimal majorId) {
		this.majorId = majorId;
	}

	/**
	 * רҵid
	 *
	 */
	public BigDecimal getMajorId() {
		return this.majorId;
	}

	/**
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 */
	@JsonIgnore
	public Set<User> getUsers() {
		if (users == null) {
			users = new java.util.LinkedHashSet<User>();
		}
		return users;
	}

	/**
	 */
	public CMajorDirection() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CMajorDirection that) {
		setId(that.getId());
		setDirectionCode(that.getDirectionCode());
		setDirectionName(that.getDirectionName());
		setMajorId(that.getMajorId());
		setUsers(new java.util.LinkedHashSet<User>(that.getUsers()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("directionCode=[").append(directionCode).append("] ");
		buffer.append("directionName=[").append(directionName).append("] ");
		buffer.append("majorId=[").append(majorId).append("] ");

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
		if (!(obj instanceof CMajorDirection))
			return false;
		CMajorDirection equalCheck = (CMajorDirection) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

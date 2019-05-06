package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSmartAgentUsers", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser"),
		@NamedQuery(name = "findSmartAgentUserByAcademy", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.academy = ?1"),
		@NamedQuery(name = "findSmartAgentUserByAcademyContaining", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.academy like ?1"),
		@NamedQuery(name = "findSmartAgentUserByCname", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.cname = ?1"),
		@NamedQuery(name = "findSmartAgentUserByCnameContaining", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.cname like ?1"),
		@NamedQuery(name = "findSmartAgentUserById", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.id = ?1"),
		@NamedQuery(name = "findSmartAgentUserByPrimaryKey", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.id = ?1"),
		@NamedQuery(name = "findSmartAgentUserBySerialNo", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.serialNo = ?1"),
		@NamedQuery(name = "findSmartAgentUserBySerialNoContaining", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.serialNo like ?1"),
		@NamedQuery(name = "findSmartAgentUserByUsername", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.username = ?1"),
		@NamedQuery(name = "findSmartAgentUserByUsernameContaining", query = "select mySmartAgentUser from SmartAgentUser mySmartAgentUser where mySmartAgentUser.username like ?1") })
@Table(name = "smart_agent_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SmartAgentUser")
public class SmartAgentUser implements Serializable {
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
	 * �û�����
	 * 
	 */

	@Column(name = "username", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ����
	 * 
	 */

	@Column(name = "cname", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 * �û�����ѧԺ
	 * 
	 */

	@Column(name = "academy", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academy;
	/**
	 * ��Դ���������
	 * 
	 */

	@Column(name = "serial_no", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String serialNo;

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

	/**
	 * �û�����
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * �û�����
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ����
	 * 
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * ����
	 * 
	 */
	public String getCname() {
		return this.cname;
	}

	/**
	 * �û�����ѧԺ
	 * 
	 */
	public void setAcademy(String academy) {
		this.academy = academy;
	}

	/**
	 * �û�����ѧԺ
	 * 
	 */
	public String getAcademy() {
		return this.academy;
	}

	/**
	 * ��Դ���������
	 * 
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * ��Դ���������
	 * 
	 */
	public String getSerialNo() {
		return this.serialNo;
	}

	/**
	 */
	public SmartAgentUser() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SmartAgentUser that) {
		setId(that.getId());
		setUsername(that.getUsername());
		setCname(that.getCname());
		setAcademy(that.getAcademy());
		setSerialNo(that.getSerialNo());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("username=[").append(username).append("] ");
		buffer.append("cname=[").append(cname).append("] ");
		buffer.append("academy=[").append(academy).append("] ");
		buffer.append("serialNo=[").append(serialNo).append("] ");

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
		if (!(obj instanceof SmartAgentUser))
			return false;
		SmartAgentUser equalCheck = (SmartAgentUser) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

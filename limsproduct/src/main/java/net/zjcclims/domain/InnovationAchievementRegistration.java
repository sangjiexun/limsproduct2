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
		@NamedQuery(name = "findAllInnovationAchievementRegistrations", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByCname", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.cname = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByCnameContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.cname like ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationById", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.id = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByInnovationName", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.innovationName = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByInnovationNameContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.innovationName like ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByLabRoomName", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.labRoomName = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByLabRoomNameContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.labRoomName like ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByLabRoomNumber", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.labRoomNumber = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByLabRoomNumberContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.labRoomNumber like ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByPrimaryKey", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.id = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByScore", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.score = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByScoreContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.score like ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByUsername", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.username = ?1"),
		@NamedQuery(name = "findInnovationAchievementRegistrationByUsernameContaining", query = "select myInnovationAchievementRegistration from InnovationAchievementRegistration myInnovationAchievementRegistration where myInnovationAchievementRegistration.username like ?1") })
@Table(name = "innovation_achievement_registration")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclimsttt/zjcclims/domain", name = "InnovationAchievementRegistration")
public class InnovationAchievementRegistration implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �û���
	 * 
	 */

	@Column(name = "username", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;
	/**
	 * ����
	 * 
	 */

	@Column(name = "cname", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 * ��ʹ�õ�ʵ����
	 * 
	 */

	@Column(name = "lab_room_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomName;
	/**
	 * ���³ɹ����
	 * 
	 */

	@Column(name = "innovation_name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String innovationName;
	/**
	 * ��ֵ
	 * 
	 */

	@Column(name = "score", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String score;
	/**
	 * ʵ���ұ��
	 * 
	 */

	@Column(name = "lab_room_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomNumber;

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
	 * �û���
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * �û���
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
	 * ��ʹ�õ�ʵ����
	 * 
	 */
	public void setLabRoomName(String labRoomName) {
		this.labRoomName = labRoomName;
	}

	/**
	 * ��ʹ�õ�ʵ����
	 * 
	 */
	public String getLabRoomName() {
		return this.labRoomName;
	}

	/**
	 * ���³ɹ����
	 * 
	 */
	public void setInnovationName(String innovationName) {
		this.innovationName = innovationName;
	}

	/**
	 * ���³ɹ����
	 * 
	 */
	public String getInnovationName() {
		return this.innovationName;
	}

	/**
	 * ��ֵ
	 * 
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * ��ֵ
	 * 
	 */
	public String getScore() {
		return this.score;
	}

	/**
	 * ʵ���ұ��
	 * 
	 */
	public void setLabRoomNumber(String labRoomNumber) {
		this.labRoomNumber = labRoomNumber;
	}

	/**
	 * ʵ���ұ��
	 * 
	 */
	public String getLabRoomNumber() {
		return this.labRoomNumber;
	}

	/**
	 */
	public InnovationAchievementRegistration() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(InnovationAchievementRegistration that) {
		setId(that.getId());
		setUsername(that.getUsername());
		setCname(that.getCname());
		setLabRoomName(that.getLabRoomName());
		setInnovationName(that.getInnovationName());
		setScore(that.getScore());
		setLabRoomNumber(that.getLabRoomNumber());
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
		buffer.append("labRoomName=[").append(labRoomName).append("] ");
		buffer.append("innovationName=[").append(innovationName).append("] ");
		buffer.append("score=[").append(score).append("] ");
		buffer.append("labRoomNumber=[").append(labRoomNumber).append("] ");

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
		if (!(obj instanceof InnovationAchievementRegistration))
			return false;
		InnovationAchievementRegistration equalCheck = (InnovationAchievementRegistration) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

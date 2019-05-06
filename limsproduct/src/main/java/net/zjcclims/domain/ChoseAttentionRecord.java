package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseAttentionRecords", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord"),
		@NamedQuery(name = "findChoseAttentionRecordById", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord where myChoseAttentionRecord.id = ?1"),
		@NamedQuery(name = "findChoseAttentionRecordByPrimaryKey", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord where myChoseAttentionRecord.id = ?1"),
		@NamedQuery(name = "findChoseAttentionRecordByThemeId", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord where myChoseAttentionRecord.themeId = ?1"),
		@NamedQuery(name = "findChoseAttentionRecordByUsername", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord where myChoseAttentionRecord.username = ?1"),
		@NamedQuery(name = "findChoseAttentionRecordByUsernameContaining", query = "select myChoseAttentionRecord from ChoseAttentionRecord myChoseAttentionRecord where myChoseAttentionRecord.username like ?1") })
@Table(name = "chose_attention_record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseAttentionRecord")
public class ChoseAttentionRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
    Integer id;
	/**
	 * �����û���(�����)
	 * 
	 */

	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String username;
	/**
	 * ������٣������
	 * 
	 */

	@Column(name = "theme_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer themeId;

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
	 * �����û���(�����)
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * �����û���(�����)
	 * 
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ������٣������
	 * 
	 */
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	/**
	 * ������٣������
	 * 
	 */
	public Integer getThemeId() {
		return this.themeId;
	}

	/**
	 */
	public ChoseAttentionRecord() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseAttentionRecord that) {
		setId(that.getId());
		setUsername(that.getUsername());
		setThemeId(that.getThemeId());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("username=[").append(username).append("] ");
		buffer.append("themeId=[").append(themeId).append("] ");

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
		if (!(obj instanceof ChoseAttentionRecord))
			return false;
		ChoseAttentionRecord equalCheck = (ChoseAttentionRecord) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

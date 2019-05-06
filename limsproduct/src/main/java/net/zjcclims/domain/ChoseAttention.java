package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseAttentions", query = "select myChoseAttention from ChoseAttention myChoseAttention"),
		@NamedQuery(name = "findChoseAttentionByContent", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.content = ?1"),
		@NamedQuery(name = "findChoseAttentionById", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.id = ?1"),
		@NamedQuery(name = "findChoseAttentionByPrimaryKey", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.id = ?1"),
		@NamedQuery(name = "findChoseAttentionByTittle", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.tittle = ?1"),
		@NamedQuery(name = "findChoseAttentionByTittleContaining", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.tittle like ?1"),
		@NamedQuery(name = "findChoseAttentionByType", query = "select myChoseAttention from ChoseAttention myChoseAttention where myChoseAttention.type = ?1") })
@Table(name = "chose_attention")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseAttention")
public class ChoseAttention implements Serializable {
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
	 * ����
	 * 
	 */

	@Column(name = "tittle")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String tittle;
	/**
	 * ����
	 * 
	 */

	@Column(name = "content", columnDefinition = "LONGTEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
    String content;
	/**
	 * ��������1��ʦ�ƻ�ѡ��ʦ2��ʦ�ƻ�ѡѧ��3�����ƻ�ѡ��ʦ4�����ƻ�ѡѧ��
	 * 
	 */

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer type;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "theme_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseTheme choseTheme;

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
	 * ����
	 * 
	 */
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * ����
	 * 
	 */
	public String getTittle() {
		return this.tittle;
	}

	/**
	 * ����
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ����
	 * 
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * ��������1��ʦ�ƻ�ѡ��ʦ2��ʦ�ƻ�ѡѧ��3�����ƻ�ѡ��ʦ4�����ƻ�ѡѧ��
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * ��������1��ʦ�ƻ�ѡ��ʦ2��ʦ�ƻ�ѡѧ��3�����ƻ�ѡ��ʦ4�����ƻ�ѡѧ��
	 * 
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
	public ChoseAttention() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseAttention that) {
		setId(that.getId());
		setTittle(that.getTittle());
		setContent(that.getContent());
		setType(that.getType());
		setChoseTheme(that.getChoseTheme());
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
		if (!(obj instanceof ChoseAttention))
			return false;
		ChoseAttention equalCheck = (ChoseAttention) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

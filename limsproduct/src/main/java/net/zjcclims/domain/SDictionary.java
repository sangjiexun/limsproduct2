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
		@NamedQuery(name = "findAllSDictionarys", query = "select mySDictionary from SDictionary mySDictionary"),
		@NamedQuery(name = "findSDictionaryByCategory", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.category = ?1"),
		@NamedQuery(name = "findSDictionaryByCategoryContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.category like ?1"),
		@NamedQuery(name = "findSDictionaryById", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.id = ?1"),
		@NamedQuery(name = "findSDictionaryByName", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.name = ?1"),
		@NamedQuery(name = "findSDictionaryByNameContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.name like ?1"),
		@NamedQuery(name = "findSDictionaryByNo", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.no = ?1"),
		@NamedQuery(name = "findSDictionaryByNoContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.no like ?1"),
		@NamedQuery(name = "findSDictionaryByNoOne", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noOne = ?1"),
		@NamedQuery(name = "findSDictionaryByNoOneContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noOne like ?1"),
		@NamedQuery(name = "findSDictionaryByNoThree", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noThree = ?1"),
		@NamedQuery(name = "findSDictionaryByNoThreeContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noThree like ?1"),
		@NamedQuery(name = "findSDictionaryByNoTwo", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noTwo = ?1"),
		@NamedQuery(name = "findSDictionaryByNoTwoContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.noTwo like ?1"),
		@NamedQuery(name = "findSDictionaryByPrimaryKey", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.id = ?1"),
		@NamedQuery(name = "findSDictionaryByTypeDictionary", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.typeDictionary = ?1"),
		@NamedQuery(name = "findSDictionaryByTypeDictionaryContaining", query = "select mySDictionary from SDictionary mySDictionary where mySDictionary.typeDictionary like ?1") })
@Table(name = "s_dictionary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SDictionary")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ȫ����ֵ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    Integer id;
	/**
	 * ��
	 * 
	 */

	@Column(name = "category", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String category;
	/**
	 * �ࣨ4����һ�����⣨1�����������⣨2��������⣨3��
	 * 
	 */

	@Column(name = "type_dictionary", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String typeDictionary;
	/**
	 * ���
	 * 
	 */

	@Column(name = "no", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String no;
	/**
	 * ��ȫ������Ŀ
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String name;
	/**
	 * һ�����⣨1��2��3,4,5...��
	 * 
	 */

	@Column(name = "no_one", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String noOne;
	/**
	 * �������⣨1��2��3,4,5...��
	 * 
	 */

	@Column(name = "no_two", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String noTwo;
	/**
	 */

	@Column(name = "no_three", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String noThree;

	/**
	 */
	@OneToMany(mappedBy = "SDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabSecurityCheckDetails> labSecurityCheckDetailses;

	/**
	 * ��ȫ����ֵ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��ȫ����ֵ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��
	 * 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * ��
	 * 
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * �ࣨ4����һ�����⣨1�����������⣨2��������⣨3��
	 * 
	 */
	public void setTypeDictionary(String typeDictionary) {
		this.typeDictionary = typeDictionary;
	}

	/**
	 * �ࣨ4����һ�����⣨1�����������⣨2��������⣨3��
	 * 
	 */
	public String getTypeDictionary() {
		return this.typeDictionary;
	}

	/**
	 * ���
	 * 
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * ���
	 * 
	 */
	public String getNo() {
		return this.no;
	}

	/**
	 * ��ȫ������Ŀ
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ��ȫ������Ŀ
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * һ�����⣨1��2��3,4,5...��
	 * 
	 */
	public void setNoOne(String noOne) {
		this.noOne = noOne;
	}

	/**
	 * һ�����⣨1��2��3,4,5...��
	 * 
	 */
	public String getNoOne() {
		return this.noOne;
	}

	/**
	 * �������⣨1��2��3,4,5...��
	 * 
	 */
	public void setNoTwo(String noTwo) {
		this.noTwo = noTwo;
	}

	/**
	 * �������⣨1��2��3,4,5...��
	 * 
	 */
	public String getNoTwo() {
		return this.noTwo;
	}

	/**
	 */
	public void setNoThree(String noThree) {
		this.noThree = noThree;
	}

	/**
	 */
	public String getNoThree() {
		return this.noThree;
	}

	/**
	 */
	public void setLabSecurityCheckDetailses(Set<LabSecurityCheckDetails> labSecurityCheckDetailses) {
		this.labSecurityCheckDetailses = labSecurityCheckDetailses;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabSecurityCheckDetails> getLabSecurityCheckDetailses() {
		if (labSecurityCheckDetailses == null) {
			labSecurityCheckDetailses = new java.util.LinkedHashSet<net.zjcclims.domain.LabSecurityCheckDetails>();
		}
		return labSecurityCheckDetailses;
	}

	/**
	 */
	public SDictionary() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SDictionary that) {
		setId(that.getId());
		setCategory(that.getCategory());
		setTypeDictionary(that.getTypeDictionary());
		setNo(that.getNo());
		setName(that.getName());
		setNoOne(that.getNoOne());
		setNoTwo(that.getNoTwo());
		setNoThree(that.getNoThree());
		setLabSecurityCheckDetailses(new java.util.LinkedHashSet<net.zjcclims.domain.LabSecurityCheckDetails>(that.getLabSecurityCheckDetailses()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("category=[").append(category).append("] ");
		buffer.append("typeDictionary=[").append(typeDictionary).append("] ");
		buffer.append("no=[").append(no).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("noOne=[").append(noOne).append("] ");
		buffer.append("noTwo=[").append(noTwo).append("] ");
		buffer.append("noThree=[").append(noThree).append("] ");

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
		if (!(obj instanceof SDictionary))
			return false;
		SDictionary equalCheck = (SDictionary) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

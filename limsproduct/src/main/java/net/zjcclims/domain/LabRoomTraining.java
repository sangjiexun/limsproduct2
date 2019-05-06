package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.LinkedHashSet;
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
		@NamedQuery(name = "findAllLabRoomTrainings", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining"),
		@NamedQuery(name = "findLabRoomTrainingByAccessJoin", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.accessJoin = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByAccessJoinContaining", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.accessJoin like ?1"),
		@NamedQuery(name = "findLabRoomTrainingByAddress", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.address = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByAddressContaining", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.address like ?1"),
		@NamedQuery(name = "findLabRoomTrainingByContent", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.content = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByFlag", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.flag = ?1"),
		@NamedQuery(name = "findLabRoomTrainingById", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.id = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByJoinNumber", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.joinNumber = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByNumber", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.number = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByPassRate", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.passRate = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByPassRateContaining", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.passRate like ?1"),
		@NamedQuery(name = "findLabRoomTrainingByPrimaryKey", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.id = ?1"),
		@NamedQuery(name = "findLabRoomTrainingByTime", query = "select myLabRoomTraining from LabRoomTraining myLabRoomTraining where myLabRoomTraining.time = ?1") })
@Table(name = "lab_room_training")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomTraining")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabRoomTraining implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ����ѵ��
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��ѵʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar time;
	/**
	 * ��ѵ�ص�
	 * 
	 */

	@Column(name = "address")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String address;
	/**
	 * ��ѵ����
	 * 
	 */

	@Column(name = "content", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String content;
	/**
	 * ��ѵ����
	 * 
	 */

	@Column(name = "number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer number;
	/**
	 * ��ѵͨ����
	 * 
	 */

	@Column(name = "pass_rate")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String passRate;
	/**
	 * �μ���ѵ������
	 * 
	 */

	@Column(name = "join_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer joinNumber;
	/**
	 * �Ƿ�����ԤԼ����Ե�ǰ��¼�ˣ�
	 * 
	 */

	@Column(name = "access_join")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String accessJoin;
	/**
	 * �Ƿ�Ϊ����μӵ���ѵ��0 �Ǳ��룬��˼��ֻҪ�μӹ�������ѵ��ͨ��˴���ѵ���Բ��μ�  1 ���룩
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "status", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@OneToMany(mappedBy = "labRoomTraining", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomTrainingPeople> labRoomTrainingPeoples;

	/**
	 * ʵѵ����ѵ��
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ����ѵ��
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��ѵʱ��
	 * 
	 */
	public void setTime(Calendar time) {
		this.time = time;
	}

	/**
	 * ��ѵʱ��
	 * 
	 */
	public Calendar getTime() {
		return this.time;
	}

	/**
	 * ��ѵ�ص�
	 * 
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * ��ѵ�ص�
	 * 
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * ��ѵ����
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ��ѵ����
	 * 
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * ��ѵ����
	 * 
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * ��ѵ����
	 * 
	 */
	public Integer getNumber() {
		return this.number;
	}

	/**
	 * ��ѵͨ����
	 * 
	 */
	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	/**
	 * ��ѵͨ����
	 * 
	 */
	public String getPassRate() {
		return this.passRate;
	}

	/**
	 * �μ���ѵ������
	 * 
	 */
	public void setJoinNumber(Integer joinNumber) {
		this.joinNumber = joinNumber;
	}

	/**
	 * �μ���ѵ������
	 * 
	 */
	public Integer getJoinNumber() {
		return this.joinNumber;
	}

	/**
	 * �Ƿ�����ԤԼ����Ե�ǰ��¼�ˣ�
	 * 
	 */
	public void setAccessJoin(String accessJoin) {
		this.accessJoin = accessJoin;
	}

	/**
	 * �Ƿ�����ԤԼ����Ե�ǰ��¼�ˣ�
	 * 
	 */
	public String getAccessJoin() {
		return this.accessJoin;
	}

	/**
	 * �Ƿ�Ϊ����μӵ���ѵ��0 �Ǳ��룬��˼��ֻҪ�μӹ�������ѵ��ͨ��˴���ѵ���Բ��μ�  1 ���룩
	 * 
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * �Ƿ�Ϊ����μӵ���ѵ��0 �Ǳ��룬��˼��ֻҪ�μӹ�������ѵ��ͨ��˴���ѵ���Բ��μ�  1 ���룩
	 * 
	 */
	public Integer getFlag() {
		return this.flag;
	}

	/**
	 */
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	@JsonIgnore
	public LabRoom getLabRoom() {
		return labRoom;
	}

	/**
	 */
	public void setCDictionary(CDictionary CDictionary) {
		this.CDictionary = CDictionary;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionary() {
		return CDictionary;
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
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public void setLabRoomTrainingPeoples(Set<LabRoomTrainingPeople> labRoomTrainingPeoples) {
		this.labRoomTrainingPeoples = labRoomTrainingPeoples;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomTrainingPeople> getLabRoomTrainingPeoples() {
		if (labRoomTrainingPeoples == null) {
			labRoomTrainingPeoples = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomTrainingPeople>();
		}
		return labRoomTrainingPeoples;
	}

	/**
	 */
	public LabRoomTraining() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomTraining that) {
		setId(that.getId());
		setTime(that.getTime());
		setAddress(that.getAddress());
		setContent(that.getContent());
		setNumber(that.getNumber());
		setPassRate(that.getPassRate());
		setJoinNumber(that.getJoinNumber());
		setAccessJoin(that.getAccessJoin());
		setFlag(that.getFlag());
		setLabRoom(that.getLabRoom());
		setCDictionary(that.getCDictionary());
		setUser(that.getUser());
		setSchoolTerm(that.getSchoolTerm());
		setLabRoomTrainingPeoples(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomTrainingPeople>(that.getLabRoomTrainingPeoples()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("time=[").append(time).append("] ");
		buffer.append("address=[").append(address).append("] ");
		buffer.append("content=[").append(content).append("] ");
		buffer.append("number=[").append(number).append("] ");
		buffer.append("passRate=[").append(passRate).append("] ");
		buffer.append("joinNumber=[").append(joinNumber).append("] ");
		buffer.append("accessJoin=[").append(accessJoin).append("] ");
		buffer.append("flag=[").append(flag).append("] ");

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
		if (!(obj instanceof LabRoomTraining))
			return false;
		LabRoomTraining equalCheck = (LabRoomTraining) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

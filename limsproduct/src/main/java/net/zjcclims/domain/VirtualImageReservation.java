package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.skyway.spring.util.databinding.TypeConversionUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllVirtualImageReservations", query = "select myVirtualImageReservation from VirtualImageReservation myVirtualImageReservation"),
		@NamedQuery(name = "findVirtualImageReservationByPrimaryKey", query = "select myVirtualImageReservation from VirtualImageReservation myVirtualImageReservation where myVirtualImageReservation.id = ?1"),
})

@Table(name = "virtual_image_reservation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "VirtualImageReservation")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class VirtualImageReservation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ����ԤԼ��
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;


	@Column(name = "machine_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer machineId;
	/**
	 * ԤԼ����/ʵ������
	 *
	 */

	@Column(name = "remarks")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String remarks;

	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;


	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;

	/**
	 *创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar CreateTime;


	@Column(name = "audit_stage")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditStage;

	@Column(name = "audit_result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditResult;

	@Column(name = "button_mark")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buttonMark;

	@Column(name = "username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String username;

	@Column(name = "course_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseId;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "contacts", referencedColumnName = "username") })
	@XmlTransient
	User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "virtual_image", referencedColumnName = "id") })
	@XmlTransient
	VirtualImage virtualImage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher", referencedColumnName = "username") })
	@XmlTransient
	User teacher;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_reservet_ype", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabReservetYpe;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Calendar getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Calendar createTime) {
		CreateTime = createTime;
	}

	public Integer getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(Integer auditStage) {
		this.auditStage = auditStage;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VirtualImage getVirtualImage() {
		return virtualImage;
	}

	public void setVirtualImage(VirtualImage virtualImage) {
		this.virtualImage = virtualImage;
	}

	public CDictionary getCDictionaryByLabReservetYpe() {
		return CDictionaryByLabReservetYpe;
	}

	public void setCDictionaryByLabReservetYpe(CDictionary CDictionaryByLabReservetYpe) {
		this.CDictionaryByLabReservetYpe = CDictionaryByLabReservetYpe;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	public Integer getButtonMark() {
		return buttonMark;
	}

	public void setButtonMark(Integer buttonMark) {
		this.buttonMark = buttonMark;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

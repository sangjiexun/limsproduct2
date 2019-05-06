package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSystemBuilds", query = "select mySystemBuild from SystemBuild mySystemBuild"),
		@NamedQuery(name = "findSystemBuildByBuildName", query = "select mySystemBuild from SystemBuild mySystemBuild where mySystemBuild.buildName = ?1"),
		@NamedQuery(name = "findSystemBuildByBuildNameContaining", query = "select mySystemBuild from SystemBuild mySystemBuild where mySystemBuild.buildName like ?1"),
		@NamedQuery(name = "findSystemBuildByBuildNumber", query = "select mySystemBuild from SystemBuild mySystemBuild where mySystemBuild.buildNumber = ?1"),
		@NamedQuery(name = "findSystemBuildByBuildNumberContaining", query = "select mySystemBuild from SystemBuild mySystemBuild where mySystemBuild.buildNumber like ?1"),
		@NamedQuery(name = "findSystemBuildByPrimaryKey", query = "select mySystemBuild from SystemBuild mySystemBuild where mySystemBuild.buildNumber = ?1") })
@Table(name = "system_build")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SystemBuild")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SystemBuild implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ¥����
	 * 
	 */

	@Column(name = "build_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String buildNumber;

	@Column(name = "build_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String buildName;
	
	@Column(name = "area", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String area;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedDate;
	
	@Column(name = "enabled")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean enabled;
	
	/**
	 *实验室图片中的定位：x坐标
	 * 
	 */

	@Column(name = "x_coordinate", scale = 2, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal xCoordinate;
	
	/**
	 * 实验室图片中的定位：y坐标
	 * 
	 */

	@Column(name = "y_coordinate", scale = 2, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal yCoordinate;
	
	//层数
	@Column(name = "floor_num")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer floorNum;

	//最低楼层
	@Column(name = "min_floor")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer minFloor;

	/**
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;
	
	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	public BigDecimal getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(BigDecimal xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public BigDecimal getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(BigDecimal yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "username") })
	@XmlTransient
	User userByCreatedBy;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "campus_number", referencedColumnName = "campus_number") })
	@XmlTransient
	SystemCampus systemCampus;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "updated_by", referencedColumnName = "username") })
	@XmlTransient
	User userByUpdatedBy;

	/**
	 */
	@OneToMany(mappedBy = "systemBuild", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabCenter> labCenters;
	/**
	 */
	@OneToMany(mappedBy = "systemBuild", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SystemRoom> systemRooms;
	/**
	 */
	@OneToMany(mappedBy = "systemBuild", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevices;

	@OneToMany(mappedBy = "systemBuild", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRooms;

	public Set<LabRoom> getLabRooms() {
		return labRooms;
	}

	public void setLabRooms(Set<LabRoom> labRooms) {
		this.labRooms = labRooms;
	}

	/**
	 * ¥����
	 * 
	 */
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * ¥����
	 * 
	 */
	public String getBuildNumber() {
		return this.buildNumber;
	}

	/**
	 * ¥�����
	 * 
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * ¥�����
	 * 
	 */
	public String getBuildName() {
		return this.buildName;
	}

	/**
	 */
	public void setLabCenters(Set<LabCenter> labCenters) {
		this.labCenters = labCenters;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Calendar updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public User getUserByCreatedBy() {
		return userByCreatedBy;
	}

	public void setUserByCreatedBy(User userByCreatedBy) {
		this.userByCreatedBy = userByCreatedBy;
	}

	public SystemCampus getSystemCampus() {
		return systemCampus;
	}

	public void setSystemCampus(SystemCampus systemCampus) {
		this.systemCampus = systemCampus;
	}

	public SchoolAcademy getSchoolAcademy() {
		return schoolAcademy;
	}

	public void setSchoolAcademy(SchoolAcademy schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	@JsonIgnore
	public User getUserByUpdatedBy() {
		return userByUpdatedBy;
	}

	public void setUserByUpdatedBy(User userByUpdatedBy) {
		this.userByUpdatedBy = userByUpdatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabCenter> getLabCenters() {
		if (labCenters == null) {
			labCenters = new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>();
		}
		return labCenters;
	}

	/**
	 */
	public void setSystemRooms(Set<SystemRoom> systemRooms) {
		this.systemRooms = systemRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<SystemRoom> getSystemRooms() {
		if (systemRooms == null) {
			systemRooms = new java.util.LinkedHashSet<net.zjcclims.domain.SystemRoom>();
		}
		return systemRooms;
	}

	/**
	 */
	public void setSchoolDevices(Set<SchoolDevice> schoolDevices) {
		this.schoolDevices = schoolDevices;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevices() {
		if (schoolDevices == null) {
			schoolDevices = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevices;
	}

	public Integer getMinFloor() {
		return minFloor;
	}

	public void setMinFloor(Integer minFloor) {
		this.minFloor = minFloor;
	}

	/**
	 */
	public SystemBuild() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SystemBuild that) {
		setBuildNumber(that.getBuildNumber());
		setBuildName(that.getBuildName());
		setArea(that.getArea());
		setCreatedDate(that.getCreatedDate());
		setUpdatedDate(that.getUpdatedDate());
		setEnabled(that.getEnabled());
		setLabCenters(new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>(that.getLabCenters()));
		setSystemRooms(new java.util.LinkedHashSet<net.zjcclims.domain.SystemRoom>(that.getSystemRooms()));
		setSchoolDevices(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevices()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("buildNumber=[").append(buildNumber).append("] ");
		buffer.append("buildName=[").append(buildName).append("] ");
		buffer.append("area=[").append(area).append("] ");
		buffer.append("createdDate=[").append(createdDate).append("] ");
		buffer.append("updatedDate=[").append(updatedDate).append("] ");
		buffer.append("enabled=[").append(enabled).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((buildNumber == null) ? 0 : buildNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SystemBuild))
			return false;
		SystemBuild equalCheck = (SystemBuild) obj;
		if ((buildNumber == null && equalCheck.buildNumber != null) || (buildNumber != null && equalCheck.buildNumber == null))
			return false;
		if (buildNumber != null && !buildNumber.equals(equalCheck.buildNumber))
			return false;
		return true;
	}
}

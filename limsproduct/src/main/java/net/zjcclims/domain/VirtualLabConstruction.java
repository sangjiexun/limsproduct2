package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllVirtualLabConstructions", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction"),
		@NamedQuery(name = "findVirtualLabConstructionByAddDeviceValue", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.addDeviceValue = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByBuildingArea", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.buildingArea = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByDeviceAmount", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.deviceAmount = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByDeviceMaintainFee", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.deviceMaintainFee = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByDeviceValue", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.deviceValue = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByDonateDeviceValue", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.donateDeviceValue = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByHugeDeviceAmount", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.hugeDeviceAmount = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionById", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.id = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByLabRoomDeviceRepairId", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.labRoomDeviceRepairId = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByMajorName", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.majorName = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByMajorNameContaining", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.majorName like ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByManagePeopleAmount", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.managePeopleAmount = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByMaterialFee", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.materialFee = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByOwnDeviceValue", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.ownDeviceValue = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByPartTimePeopleAmount", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.partTimePeopleAmount = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByPreDonateDeviceValue", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.preDonateDeviceValue = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByPrimaryKey", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.id = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByProjectBaseName", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.projectBaseName = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByProjectBaseNameContaining", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.projectBaseName like ?1"),
		@NamedQuery(name = "findVirtualLabConstructionBySiteArea", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.siteArea = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionBySupportAcademyName", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.supportAcademyName = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionBySupportAcademyNameContaining", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.supportAcademyName like ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByTrainingProjectAmount", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.trainingProjectAmount = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByTrainingProjectName", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.trainingProjectName = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByTrainingProjectNameContaining", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.trainingProjectName like ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByUseFrequencySchool", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.useFrequencySchool = ?1"),
		@NamedQuery(name = "findVirtualLabConstructionByUseFrequencySociety", query = "select myVirtualLabConstruction from VirtualLabConstruction myVirtualLabConstruction where myVirtualLabConstruction.useFrequencySociety = ?1") })
@Table( name = "virtual_lab_construction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "VirtualLabConstruction")
public class VirtualLabConstruction implements Serializable {
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
	 * ʵ�������
	 * 
	 */

	@Column(name = "project_base_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String projectBaseName;
	/**
	 * ����רҵ
	 * 
	 */

	@Column(name = "major_name", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String majorName;
	/**
	 * ֧�ֲ���
	 * 
	 */

	@Column(name = "support_academy_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String supportAcademyName;
	/**
	 * �������
	 * 
	 */

	@Column(name = "building_area", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal buildingArea;
	/**
	 * ռ�����
	 * 
	 */

	@Column(name = "site_area", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal siteArea;
	/**
	 * �豸��ֵ
	 * 
	 */

	@Column(name = "device_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal deviceValue;
	/**
	 * ���������豸ֵ
	 * 
	 */

	@Column(name = "add_device_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal addDeviceValue;
	/**
	 * ���������豸ֵ
	 * 
	 */

	@Column(name = "own_device_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal ownDeviceValue;
	/**
	 * �������豸ֵ
	 * 
	 */

	@Column(name = "donate_device_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal donateDeviceValue;
	/**
	 * ���׼�����豸ֵ
	 * 
	 */

	@Column(name = "pre_donate_device_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal preDonateDeviceValue;
	/**
	 * �豸����
	 * 
	 */

	@Column(name = "device_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer deviceAmount;
	/**
	 * �����豸��
	 * 
	 */

	@Column(name = "huge_device_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer hugeDeviceAmount;
	/**
	 * ����
	 * 
	 */

	@Column(name = "training_project_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer trainingProjectAmount;
	/**
	 * ��Ҫ��Ŀ���
	 * 
	 */

	@Column(name = "training_project_name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String trainingProjectName;
	/**
	 * ѧ��ʹ��Ƶ��-У��
	 * 
	 */

	@Column(name = "use_frequency_school", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal useFrequencySchool;
	/**
	 * ѧ��ʹ��Ƶ��-���
	 * 
	 */

	@Column(name = "use_frequency_society", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal useFrequencySociety;
	/**
	 * ԭ����(�Ĳ�)����
	 * 
	 */

	@Column(name = "material_fee", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal materialFee;
	/**
	 * �豸ά��
	 * 
	 */

	@Column(name = "device_maintain_fee", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    BigDecimal deviceMaintainFee;
	/**
	 * רְ����
	 * 
	 */

	@Column(name = "manage_people_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer managePeopleAmount;
	/**
	 */

	@Column(name = "part_time_people_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer partTimePeopleAmount;
	/**
	 */

	@Column(name = "lab_room_device_repair_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer labRoomDeviceRepairId;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "system_room_id", referencedColumnName = "room_number") })
	@XmlTransient
	SystemRoom systemRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_project_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomProject labRoomProject;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "school_device_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoomDevice labRoomDevice;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;

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
	 * ʵ�������
	 * 
	 */
	public void setProjectBaseName(String projectBaseName) {
		this.projectBaseName = projectBaseName;
	}

	/**
	 * ʵ�������
	 * 
	 */
	public String getProjectBaseName() {
		return this.projectBaseName;
	}

	/**
	 * ����רҵ
	 * 
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 * ����רҵ
	 * 
	 */
	public String getMajorName() {
		return this.majorName;
	}

	/**
	 * ֧�ֲ���
	 * 
	 */
	public void setSupportAcademyName(String supportAcademyName) {
		this.supportAcademyName = supportAcademyName;
	}

	/**
	 * ֧�ֲ���
	 * 
	 */
	public String getSupportAcademyName() {
		return this.supportAcademyName;
	}

	/**
	 * �������
	 * 
	 */
	public void setBuildingArea(BigDecimal buildingArea) {
		this.buildingArea = buildingArea;
	}

	/**
	 * �������
	 * 
	 */
	public BigDecimal getBuildingArea() {
		return this.buildingArea;
	}

	/**
	 * ռ�����
	 * 
	 */
	public void setSiteArea(BigDecimal siteArea) {
		this.siteArea = siteArea;
	}

	/**
	 * ռ�����
	 * 
	 */
	public BigDecimal getSiteArea() {
		return this.siteArea;
	}

	/**
	 * �豸��ֵ
	 * 
	 */
	public void setDeviceValue(BigDecimal deviceValue) {
		this.deviceValue = deviceValue;
	}

	/**
	 * �豸��ֵ
	 * 
	 */
	public BigDecimal getDeviceValue() {
		return this.deviceValue;
	}

	/**
	 * ���������豸ֵ
	 * 
	 */
	public void setAddDeviceValue(BigDecimal addDeviceValue) {
		this.addDeviceValue = addDeviceValue;
	}

	/**
	 * ���������豸ֵ
	 * 
	 */
	public BigDecimal getAddDeviceValue() {
		return this.addDeviceValue;
	}

	/**
	 * ���������豸ֵ
	 * 
	 */
	public void setOwnDeviceValue(BigDecimal ownDeviceValue) {
		this.ownDeviceValue = ownDeviceValue;
	}

	/**
	 * ���������豸ֵ
	 * 
	 */
	public BigDecimal getOwnDeviceValue() {
		return this.ownDeviceValue;
	}

	/**
	 * �������豸ֵ
	 * 
	 */
	public void setDonateDeviceValue(BigDecimal donateDeviceValue) {
		this.donateDeviceValue = donateDeviceValue;
	}

	/**
	 * �������豸ֵ
	 * 
	 */
	public BigDecimal getDonateDeviceValue() {
		return this.donateDeviceValue;
	}

	/**
	 * ���׼�����豸ֵ
	 * 
	 */
	public void setPreDonateDeviceValue(BigDecimal preDonateDeviceValue) {
		this.preDonateDeviceValue = preDonateDeviceValue;
	}

	/**
	 * ���׼�����豸ֵ
	 * 
	 */
	public BigDecimal getPreDonateDeviceValue() {
		return this.preDonateDeviceValue;
	}

	/**
	 * �豸����
	 * 
	 */
	public void setDeviceAmount(Integer deviceAmount) {
		this.deviceAmount = deviceAmount;
	}

	/**
	 * �豸����
	 * 
	 */
	public Integer getDeviceAmount() {
		return this.deviceAmount;
	}

	/**
	 * �����豸��
	 * 
	 */
	public void setHugeDeviceAmount(Integer hugeDeviceAmount) {
		this.hugeDeviceAmount = hugeDeviceAmount;
	}

	/**
	 * �����豸��
	 * 
	 */
	public Integer getHugeDeviceAmount() {
		return this.hugeDeviceAmount;
	}

	/**
	 * ����
	 * 
	 */
	public void setTrainingProjectAmount(Integer trainingProjectAmount) {
		this.trainingProjectAmount = trainingProjectAmount;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getTrainingProjectAmount() {
		return this.trainingProjectAmount;
	}

	/**
	 * ��Ҫ��Ŀ���
	 * 
	 */
	public void setTrainingProjectName(String trainingProjectName) {
		this.trainingProjectName = trainingProjectName;
	}

	/**
	 * ��Ҫ��Ŀ���
	 * 
	 */
	public String getTrainingProjectName() {
		return this.trainingProjectName;
	}

	/**
	 * ѧ��ʹ��Ƶ��-У��
	 * 
	 */
	public void setUseFrequencySchool(BigDecimal useFrequencySchool) {
		this.useFrequencySchool = useFrequencySchool;
	}

	/**
	 * ѧ��ʹ��Ƶ��-У��
	 * 
	 */
	public BigDecimal getUseFrequencySchool() {
		return this.useFrequencySchool;
	}

	/**
	 * ѧ��ʹ��Ƶ��-���
	 * 
	 */
	public void setUseFrequencySociety(BigDecimal useFrequencySociety) {
		this.useFrequencySociety = useFrequencySociety;
	}

	/**
	 * ѧ��ʹ��Ƶ��-���
	 * 
	 */
	public BigDecimal getUseFrequencySociety() {
		return this.useFrequencySociety;
	}

	/**
	 * ԭ����(�Ĳ�)����
	 * 
	 */
	public void setMaterialFee(BigDecimal materialFee) {
		this.materialFee = materialFee;
	}

	/**
	 * ԭ����(�Ĳ�)����
	 * 
	 */
	public BigDecimal getMaterialFee() {
		return this.materialFee;
	}

	/**
	 * �豸ά��
	 * 
	 */
	public void setDeviceMaintainFee(BigDecimal deviceMaintainFee) {
		this.deviceMaintainFee = deviceMaintainFee;
	}

	/**
	 * �豸ά��
	 * 
	 */
	public BigDecimal getDeviceMaintainFee() {
		return this.deviceMaintainFee;
	}

	/**
	 * רְ����
	 * 
	 */
	public void setManagePeopleAmount(Integer managePeopleAmount) {
		this.managePeopleAmount = managePeopleAmount;
	}

	/**
	 * רְ����
	 * 
	 */
	public Integer getManagePeopleAmount() {
		return this.managePeopleAmount;
	}

	/**
	 */
	public void setPartTimePeopleAmount(Integer partTimePeopleAmount) {
		this.partTimePeopleAmount = partTimePeopleAmount;
	}

	/**
	 */
	public Integer getPartTimePeopleAmount() {
		return this.partTimePeopleAmount;
	}

	/**
	 */
	public void setLabRoomDeviceRepairId(Integer labRoomDeviceRepairId) {
		this.labRoomDeviceRepairId = labRoomDeviceRepairId;
	}

	/**
	 */
	public Integer getLabRoomDeviceRepairId() {
		return this.labRoomDeviceRepairId;
	}

	/**
	 */
	public void setSystemRoom(SystemRoom systemRoom) {
		this.systemRoom = systemRoom;
	}

	/**
	 */
	public SystemRoom getSystemRoom() {
		return systemRoom;
	}

	/**
	 */
	public void setLabRoomProject(LabRoomProject labRoomProject) {
		this.labRoomProject = labRoomProject;
	}

	/**
	 */
	public LabRoomProject getLabRoomProject() {
		return labRoomProject;
	}

	/**
	 */
	public void setLabRoomDevice(LabRoomDevice labRoomDevice) {
		this.labRoomDevice = labRoomDevice;
	}

	/**
	 */
	public LabRoomDevice getLabRoomDevice() {
		return labRoomDevice;
	}

	/**
	 */
	public void setSchoolAcademy(SchoolAcademy schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	/**
	 */
	public SchoolAcademy getSchoolAcademy() {
		return schoolAcademy;
	}

	/**
	 */
	public VirtualLabConstruction() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(VirtualLabConstruction that) {
		setId(that.getId());
		setProjectBaseName(that.getProjectBaseName());
		setMajorName(that.getMajorName());
		setSupportAcademyName(that.getSupportAcademyName());
		setBuildingArea(that.getBuildingArea());
		setSiteArea(that.getSiteArea());
		setDeviceValue(that.getDeviceValue());
		setAddDeviceValue(that.getAddDeviceValue());
		setOwnDeviceValue(that.getOwnDeviceValue());
		setDonateDeviceValue(that.getDonateDeviceValue());
		setPreDonateDeviceValue(that.getPreDonateDeviceValue());
		setDeviceAmount(that.getDeviceAmount());
		setHugeDeviceAmount(that.getHugeDeviceAmount());
		setTrainingProjectAmount(that.getTrainingProjectAmount());
		setTrainingProjectName(that.getTrainingProjectName());
		setUseFrequencySchool(that.getUseFrequencySchool());
		setUseFrequencySociety(that.getUseFrequencySociety());
		setMaterialFee(that.getMaterialFee());
		setDeviceMaintainFee(that.getDeviceMaintainFee());
		setManagePeopleAmount(that.getManagePeopleAmount());
		setPartTimePeopleAmount(that.getPartTimePeopleAmount());
		setLabRoomDeviceRepairId(that.getLabRoomDeviceRepairId());
		setSystemRoom(that.getSystemRoom());
		setLabRoomProject(that.getLabRoomProject());
		setLabRoomDevice(that.getLabRoomDevice());
		setSchoolAcademy(that.getSchoolAcademy());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("projectBaseName=[").append(projectBaseName).append("] ");
		buffer.append("majorName=[").append(majorName).append("] ");
		buffer.append("supportAcademyName=[").append(supportAcademyName).append("] ");
		buffer.append("buildingArea=[").append(buildingArea).append("] ");
		buffer.append("siteArea=[").append(siteArea).append("] ");
		buffer.append("deviceValue=[").append(deviceValue).append("] ");
		buffer.append("addDeviceValue=[").append(addDeviceValue).append("] ");
		buffer.append("ownDeviceValue=[").append(ownDeviceValue).append("] ");
		buffer.append("donateDeviceValue=[").append(donateDeviceValue).append("] ");
		buffer.append("preDonateDeviceValue=[").append(preDonateDeviceValue).append("] ");
		buffer.append("deviceAmount=[").append(deviceAmount).append("] ");
		buffer.append("hugeDeviceAmount=[").append(hugeDeviceAmount).append("] ");
		buffer.append("trainingProjectAmount=[").append(trainingProjectAmount).append("] ");
		buffer.append("trainingProjectName=[").append(trainingProjectName).append("] ");
		buffer.append("useFrequencySchool=[").append(useFrequencySchool).append("] ");
		buffer.append("useFrequencySociety=[").append(useFrequencySociety).append("] ");
		buffer.append("materialFee=[").append(materialFee).append("] ");
		buffer.append("deviceMaintainFee=[").append(deviceMaintainFee).append("] ");
		buffer.append("managePeopleAmount=[").append(managePeopleAmount).append("] ");
		buffer.append("partTimePeopleAmount=[").append(partTimePeopleAmount).append("] ");
		buffer.append("labRoomDeviceRepairId=[").append(labRoomDeviceRepairId).append("] ");

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
		if (!(obj instanceof VirtualLabConstruction))
			return false;
		VirtualLabConstruction equalCheck = (VirtualLabConstruction) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

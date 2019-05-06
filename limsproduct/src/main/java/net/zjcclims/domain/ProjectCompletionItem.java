package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectCompletionItems", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem"),
		@NamedQuery(name = "findProjectCompletionItemByEquipmentAmount", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.equipmentAmount = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentInstructor", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentInstructor = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentName", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentName = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentNameContaining", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentName like ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentOutline", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentOutline = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentProperty", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentProperty = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByExperimentPropertyContaining", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.experimentProperty like ?1"),
		@NamedQuery(name = "findProjectCompletionItemById", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByObjectItem", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.objectItem = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByObjectItemContaining", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.objectItem like ?1"),
		@NamedQuery(name = "findProjectCompletionItemByPerGroupAmount", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.perGroupAmount = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByPrimaryKey", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByRequiredHour", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.requiredHour = ?1"),
		@NamedQuery(name = "findProjectCompletionItemBySimultaneouslyAmount", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.simultaneouslyAmount = ?1"),
		@NamedQuery(name = "findProjectCompletionItemByUseSitutation", query = "select myProjectCompletionItem from ProjectCompletionItem myProjectCompletionItem where myProjectCompletionItem.useSitutation = ?1") })
@Table(name = "project_completion_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectCompletionItem")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ProjectCompletionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */

	@Column(name = "experiment_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String experimentName;
	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */

	@Column(name = "experiment_property", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String experimentProperty;
	/**
	 * ����ѧʱ
	 * 
	 */

	@Column(name = "required_hour")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer requiredHour;
	/**
	 * ����
	 * 
	 */

	@Column(name = "object_item", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String objectItem;
	/**
	 * ����
	 * 
	 */

	@Column(name = "is_original", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String isOriginal;
	/**
	 * �豸̨����
	 * 
	 */

	@Column(name = "equipment_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer equipmentAmount;
	/**
	 * ͬʱ��������
	 * 
	 */

	@Column(name = "simultaneously_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer simultaneouslyAmount;
	/**
	 * ÿ��ʵ�飨ʵѵ������
	 * 
	 */

	@Column(name = "per_group_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer perGroupAmount;
	/**
	 * ����ʵ�飨ʵѵ�����
	 * 
	 */

	@Column(name = "experiment_outline")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer experimentOutline;
	/**
	 * ����ʵ�飨ʵѵ��ָ����
	 * 
	 */

	@Column(name = "experiment_instructor")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer experimentInstructor;
	/**
	 * ʹ��������Ƿ��裩
	 * 
	 */

	@Column(name = "use_situtation")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer useSitutation;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "start_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "approval_app_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplication;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;
	

/*	*//**
	 *//*
	@OneToMany(mappedBy = "projectCompletionItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.gvsun.domain.ProjectStartedReport> projectStartedReports;*/

	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */
	public String getExperimentName() {
		return this.experimentName;
	}

	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */
	public void setExperimentProperty(String experimentProperty) {
		this.experimentProperty = experimentProperty;
	}

	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */
	public String getExperimentProperty() {
		return this.experimentProperty;
	}

	/**
	 * ����ѧʱ
	 * 
	 */
	public void setRequiredHour(Integer requiredHour) {
		this.requiredHour = requiredHour;
	}

	/**
	 * ����ѧʱ
	 * 
	 */
	public Integer getRequiredHour() {
		return this.requiredHour;
	}

	/**
	 * ����
	 * 
	 */
	public void setObjectItem(String objectItem) {
		this.objectItem = objectItem;
	}

	/**
	 * ����
	 * 
	 */
	public String getObjectItem() {
		return this.objectItem;
	}

	/**
	 * �豸̨����
	 * 
	 */
	public void setEquipmentAmount(Integer equipmentAmount) {
		this.equipmentAmount = equipmentAmount;
	}

	/**
	 * �豸̨����
	 * 
	 */
	public Integer getEquipmentAmount() {
		return this.equipmentAmount;
	}

	/**
	 * ͬʱ��������
	 * 
	 */
	public void setSimultaneouslyAmount(Integer simultaneouslyAmount) {
		this.simultaneouslyAmount = simultaneouslyAmount;
	}

	/**
	 * ͬʱ��������
	 * 
	 */
	public Integer getSimultaneouslyAmount() {
		return this.simultaneouslyAmount;
	}

	/**
	 * ÿ��ʵ�飨ʵѵ������
	 * 
	 */
	public void setPerGroupAmount(Integer perGroupAmount) {
		this.perGroupAmount = perGroupAmount;
	}

	/**
	 * ÿ��ʵ�飨ʵѵ������
	 * 
	 */
	public Integer getPerGroupAmount() {
		return this.perGroupAmount;
	}

	/**
	 * ����ʵ�飨ʵѵ�����
	 * 
	 */
	public void setExperimentOutline(Integer experimentOutline) {
		this.experimentOutline = experimentOutline;
	}

	/**
	 * ����ʵ�飨ʵѵ�����
	 * 
	 */
	public Integer getExperimentOutline() {
		return this.experimentOutline;
	}

	/**
	 * ����ʵ�飨ʵѵ��ָ����
	 * 
	 */
	public void setExperimentInstructor(Integer experimentInstructor) {
		this.experimentInstructor = experimentInstructor;
	}

	/**
	 * ����ʵ�飨ʵѵ��ָ����
	 * 
	 */
	public Integer getExperimentInstructor() {
		return this.experimentInstructor;
	}

	/**
	 * ʹ��������Ƿ��裩
	 * 
	 */
	public void setUseSitutation(Integer useSitutation) {
		this.useSitutation = useSitutation;
	}

	/**
	 * ʹ��������Ƿ��裩
	 * 
	 */
	public Integer getUseSitutation() {
		return this.useSitutation;
	}

	/**
	 */
	public void setProjectStartedReport(ProjectStartedReport projectStartedReport) {
		this.projectStartedReport = projectStartedReport;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReport() {
		return projectStartedReport;
	}

	/**
	 */
	public void setProjectAcceptanceApplication(ProjectAcceptanceApplication projectAcceptanceApplication) {
		this.projectAcceptanceApplication = projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplication() {
		return projectAcceptanceApplication;
	}

	/**
	 */
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}


/*	*//**
	 *//*
	public void setProjectStartedReports(Set<ProjectStartedReport> projectStartedReports) {
		this.projectStartedReports = projectStartedReports;
	}

	*//**
	 *//*
	public Set<ProjectStartedReport> getProjectStartedReports() {
		if (projectStartedReports == null) {
			projectStartedReports = new java.util.LinkedHashSet<net.gvsun.domain.ProjectStartedReport>();
		}
		return projectStartedReports;
	}*/
	
	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}

	/**
	 */
	public ProjectCompletionItem() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectCompletionItem that) {
		setId(that.getId());
		setExperimentName(that.getExperimentName());
		setExperimentProperty(that.getExperimentProperty());
		setRequiredHour(that.getRequiredHour());
		setObjectItem(that.getObjectItem());
		setEquipmentAmount(that.getEquipmentAmount());
		setSimultaneouslyAmount(that.getSimultaneouslyAmount());
		setPerGroupAmount(that.getPerGroupAmount());
		setExperimentOutline(that.getExperimentOutline());
		setExperimentInstructor(that.getExperimentInstructor());
		setUseSitutation(that.getUseSitutation());
		setProjectStartedReport(that.getProjectStartedReport());
		setProjectAcceptanceApplication(that.getProjectAcceptanceApplication());
		setLabConstructApp(that.getLabConstructApp());
		//setProjectStartedReports(new java.util.LinkedHashSet<net.gvsun.domain.ProjectStartedReport>(that.getProjectStartedReports()));
		setIsOriginal(that.getIsOriginal());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("experimentName=[").append(experimentName).append("] ");
		buffer.append("experimentProperty=[").append(experimentProperty).append("] ");
		buffer.append("requiredHour=[").append(requiredHour).append("] ");
		buffer.append("objectItem=[").append(objectItem).append("] ");
		buffer.append("equipmentAmount=[").append(equipmentAmount).append("] ");
		buffer.append("simultaneouslyAmount=[").append(simultaneouslyAmount).append("] ");
		buffer.append("perGroupAmount=[").append(perGroupAmount).append("] ");
		buffer.append("experimentOutline=[").append(experimentOutline).append("] ");
		buffer.append("experimentInstructor=[").append(experimentInstructor).append("] ");
		buffer.append("useSitutation=[").append(useSitutation).append("] ");
		buffer.append("isOriginal=[").append(isOriginal).append("] ");

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
		if (!(obj instanceof ProjectCompletionItem))
			return false;
		ProjectCompletionItem equalCheck = (ProjectCompletionItem) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

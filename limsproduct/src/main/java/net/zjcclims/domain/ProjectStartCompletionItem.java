package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectStartCompletionItems", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem"),
		@NamedQuery(name = "findProjectStartCompletionItemByApprovalAppId", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.approvalAppId = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByEquipmentAmount", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.equipmentAmount = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentInstructor", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentInstructor = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentName", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentName = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentNameContaining", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentName like ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentOutline", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentOutline = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentProperty", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentProperty = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByExperimentPropertyContaining", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.experimentProperty like ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemById", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByIsOriginal", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.isOriginal = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByIsOriginalContaining", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.isOriginal like ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByObjectItem", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.objectItem = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByObjectItemContaining", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.objectItem like ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByPerGroupAmount", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.perGroupAmount = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByPrimaryKey", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByRequiredHour", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.requiredHour = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemBySimultaneouslyAmount", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.simultaneouslyAmount = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByStartReportId", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.startReportId = ?1"),
		@NamedQuery(name = "findProjectStartCompletionItemByUseSitutation", query = "select myProjectStartCompletionItem from ProjectStartCompletionItem myProjectStartCompletionItem where myProjectStartCompletionItem.useSitutation = ?1") })
@Table(name = "project_start_completion_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectStartCompletionItem")
public class ProjectStartCompletionItem implements Serializable {
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
	 * �Ƿ�ԭ��
	 * 
	 */

	@Column(name = "is_original")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String isOriginal;
	/**
	 * ʵ�飨ʵѵ�����
	 * 
	 */

	@Column(name = "experiment_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String experimentName;
	/**
	 * ʵ�飨ʵѵ������
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

	@Column(name = "start_report_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer startReportId;
	/**
	 */

	@Column(name = "approval_app_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer approvalAppId;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_start_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;

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
	 * �Ƿ�ԭ��
	 * 
	 */
	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}

	/**
	 * �Ƿ�ԭ��
	 * 
	 */
	public String getIsOriginal() {
		return this.isOriginal;
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
	 * ʵ�飨ʵѵ������
	 * 
	 */
	public void setExperimentProperty(String experimentProperty) {
		this.experimentProperty = experimentProperty;
	}

	/**
	 * ʵ�飨ʵѵ������
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
	public void setStartReportId(Integer startReportId) {
		this.startReportId = startReportId;
	}

	/**
	 */
	public Integer getStartReportId() {
		return this.startReportId;
	}

	/**
	 */
	public void setApprovalAppId(Integer approvalAppId) {
		this.approvalAppId = approvalAppId;
	}

	/**
	 */
	public Integer getApprovalAppId() {
		return this.approvalAppId;
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
	public ProjectStartCompletionItem() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectStartCompletionItem that) {
		setId(that.getId());
		setIsOriginal(that.getIsOriginal());
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
		setStartReportId(that.getStartReportId());
		setApprovalAppId(that.getApprovalAppId());
		setProjectStartedReport(that.getProjectStartedReport());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("isOriginal=[").append(isOriginal).append("] ");
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
		buffer.append("startReportId=[").append(startReportId).append("] ");
		buffer.append("approvalAppId=[").append(approvalAppId).append("] ");

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
		if (!(obj instanceof ProjectStartCompletionItem))
			return false;
		ProjectStartCompletionItem equalCheck = (ProjectStartCompletionItem) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

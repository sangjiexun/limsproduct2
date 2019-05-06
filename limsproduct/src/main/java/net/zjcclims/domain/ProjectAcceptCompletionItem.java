package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAcceptCompletionItems", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByApprovalAppId", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.approvalAppId = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByEquipmentAmount", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.equipmentAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentInstructor", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentInstructor = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentName", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentName = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentNameContaining", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentName like ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentOutline", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentOutline = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentProperty", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentProperty = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByExperimentPropertyContaining", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.experimentProperty like ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemById", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByIsOriginal", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.isOriginal = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByIsOriginalContaining", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.isOriginal like ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByObjectItem", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.objectItem = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByObjectItemContaining", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.objectItem like ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByPerGroupAmount", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.perGroupAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByPrimaryKey", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.id = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByRequiredHour", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.requiredHour = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemBySimultaneouslyAmount", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.simultaneouslyAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByStartReportId", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.startReportId = ?1"),
		@NamedQuery(name = "findProjectAcceptCompletionItemByUseSitutation", query = "select myProjectAcceptCompletionItem from ProjectAcceptCompletionItem myProjectAcceptCompletionItem where myProjectAcceptCompletionItem.useSitutation = ?1") })
@Table(name = "project_accept_completion_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAcceptCompletionItem")
public class ProjectAcceptCompletionItem implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "project_accept_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplication;

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
	public ProjectAcceptCompletionItem() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAcceptCompletionItem that) {
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
		setProjectAcceptanceApplication(that.getProjectAcceptanceApplication());
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
		if (!(obj instanceof ProjectAcceptCompletionItem))
			return false;
		ProjectAcceptCompletionItem equalCheck = (ProjectAcceptCompletionItem) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}

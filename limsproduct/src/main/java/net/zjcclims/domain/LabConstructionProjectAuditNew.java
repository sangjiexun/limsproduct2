
package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabConstructionProjectAuditNews", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewById", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.id = ?1"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewByPrimaryKey", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.id = ?1"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewByRemark", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.remark = ?1"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewByRemarkContaining", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.remark like ?1"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewByResult", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.result = ?1"),
		@NamedQuery(name = "findLabConstructionProjectAuditNewByStage", query = "select myLabConstructionProjectAuditNew from LabConstructionProjectAuditNew myLabConstructionProjectAuditNew where myLabConstructionProjectAuditNew.stage = ?1") })

@Table(name = "lab_construction_project_audit_new")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructionProjectAuditNew")

public class LabConstructionProjectAuditNew implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* ��ʵ����Ŀ�����־������
	* 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	* �׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */

	@Column(name = "stage")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Integer stage;
	/**
	* �����0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */

	@Column(name = "result")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Integer result;
	/**
	* ��ע
	* 
	 */

	@Column(name = "remark")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	String remark;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionGrandsonProject labConstructionGrandsonProject;

	/**
	* ��ʵ����Ŀ�����־������
	* 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* ��ʵ����Ŀ�����־������
	* 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	* �׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	/**
	* �׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public Integer getStage() {
		return this.stage;
	}

	/**
	* �����0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */
	public void setResult(Integer result) {
		this.result = result;
	}

	/**
	* �����0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */
	public Integer getResult() {
		return this.result;
	}

	/**
	* ��ע
	* 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	* ��ע
	* 
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 */
	public void setLabConstructionGrandsonProject(LabConstructionGrandsonProject labConstructionGrandsonProject) {
		this.labConstructionGrandsonProject = labConstructionGrandsonProject;
	}

	/**
	 */
	@JsonIgnore
	public LabConstructionGrandsonProject getLabConstructionGrandsonProject() {
		return labConstructionGrandsonProject;
	}

	/**
	 */
	public LabConstructionProjectAuditNew() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructionProjectAuditNew that) {
		setId(that.getId());
		setStage(that.getStage());
		setResult(that.getResult());
		setRemark(that.getRemark());
		setLabConstructionGrandsonProject(that.getLabConstructionGrandsonProject());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("stage=[").append(stage).append("] ");
		buffer.append("result=[").append(result).append("] ");
		buffer.append("remark=[").append(remark).append("] ");

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
		if (!(obj instanceof LabConstructionProjectAuditNew))
			return false;
		LabConstructionProjectAuditNew equalCheck = (LabConstructionProjectAuditNew) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
